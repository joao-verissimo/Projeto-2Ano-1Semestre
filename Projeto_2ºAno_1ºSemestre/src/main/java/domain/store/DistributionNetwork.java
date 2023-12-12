package domain.store;

import data.graph.Algorithms;
import data.graph.map.MapGraph;
import domain.model.Client;
import domain.model.Firm;
import domain.model.Producer;
import domain.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.*;

public class DistributionNetwork {

    public static class DistancePathPair implements Comparable<DistancePathPair> {
        private final Integer distance;
        private final LinkedList<User> path;

        public DistancePathPair(Integer distance, LinkedList<User> path) {
            this.distance = distance;
            this.path = path;
        }

        public Integer getDistance() {
            return distance;
        }

        public LinkedList<User> getPath() {
            return path;
        }

        @Override
        public int compareTo(DistancePathPair o) {
            return this.getDistance().compareTo(o.getDistance());
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("DistancePathPair: ");
            sb.append(distance).append(path).append("\n");
            return sb.toString();
        }
    }

    //undirected graph of Users
    private final MapGraph<User, Integer> graph;

    public DistributionNetwork() {
        this.graph = new MapGraph<>(false);
    }

    public MapGraph<User, Integer> getGraph() {
        return graph;
    }

    /**
     * Imports CSV file with users to distribution network graph.
     *
     * @param path File path
     * @throws IOException if file not found or cannot open file
     */
    public void importUserCSV(String path) throws IOException {
        String line;
        String[] metadata;
        User user;

        BufferedReader br = Files.newBufferedReader(Path.of(path));
        //skip header
        br.readLine();
        line = br.readLine();
        while (line != null) {
            metadata = line.split(Constants.CSV_SPLIT_REGEX);
            user = createUser(metadata[Constants.User.LOC_ID.ordinal()],
                    metadata[Constants.User.USER_ID.ordinal()],
                    Double.parseDouble(metadata[Constants.User.LAT.ordinal()]),
                    Double.parseDouble(metadata[Constants.User.LNG.ordinal()]));
            graph.addVertex(user);
            line = br.readLine();
        }
    }

    /**
     * Imports CSV file with connection between users graph.
     *
     * @param path File path
     * @throws IOException if file not found or cannot open file
     */
    public void importDistanceCSV(String path) throws IOException {
        String line;
        String[] metadata;

        BufferedReader br = Files.newBufferedReader(Path.of(path));
        //skip header
        br.readLine();
        line = br.readLine();
        while (line != null) {
            metadata = line.split(Constants.CSV_SPLIT_REGEX);
            addPath(metadata[Constants.Distance.LOC_ID_1.ordinal()],
                    metadata[Constants.Distance.LOC_ID_2.ordinal()],
                    Integer.parseInt(metadata[Constants.Distance.LENGTH.ordinal()]));
            line = br.readLine();
        }
    }

    /**
     * Adds user to distribution network graph.
     *
     * @param locID  User Location ID
     * @param userID User ID
     * @param lat    User latitude
     * @param lng    User longitude
     * @throws InvalidParameterException if user ID doesn't start with C, E, or P.
     */
    public User createUser(String locID, String userID, double lat, double lng) throws InvalidParameterException {
        User user;
        switch (userID.charAt(0)) {
            case 'C' -> user = new Client(locID, userID, lat, lng);
            case 'E' -> user = new Firm(locID, userID, lat, lng);
            case 'P' -> user = new Producer(locID, userID, lat, lng);
            default -> throw new InvalidParameterException("Invalid user ID");
        }
        return user;
    }

    /**
     * Makes a connection between two users in graph.
     *
     * @param locID1 User location ID 1
     * @param locID2 User location ID 2
     * @param weight Distance between locations in meters
     * @throws InvalidParameterException if locID1 and locID2 are equal or dont exist
     */
    public void addPath(String locID1, String locID2, Integer weight) throws InvalidParameterException {
        if (locID1.equals(locID2))
            throw new InvalidParameterException("Location ID 1 and 2 cannot be equal");

        User user1 = graph.vertex(user -> user.getLocID().equals(locID1));
        User user2 = graph.vertex(user -> user.getLocID().equals(locID2));

        if (user1 == null) {
            if (user2 == null)
                throw new InvalidParameterException("Users with location ID 1 and 2 don't exist");
            throw new InvalidParameterException("User with location ID 1 doesn't exist");
        } else if (user2 == null)
            throw new InvalidParameterException("User with location ID 2 doesn't exist");

        graph.addEdge(user1, user2, weight);
    }

    /**
     * Finds and sets n nearest firms (E) on average to all network points (C, P and E) as HUBs.
     *
     * @param n number of desired hubs
     * @return returns TreeMap -> Key: average distance, Value: HUB
     * @throws InvalidParameterException if n lower than 0
     */
    public TreeMap<Double, Firm> setHUBs(int n) throws InvalidParameterException {
        if (n < 0) throw new InvalidParameterException("Number of hubs should be higher than 0");

        ArrayList<LinkedList<User>> paths = new ArrayList<>();
        ArrayList<Integer> dists = new ArrayList<>();
        TreeMap<Double, Firm> firmSet = new TreeMap<>();
        TreeMap<Double, Firm> hubSet = new TreeMap<>();
        int sum;
        double average;

        for (User v : graph.vertices()) {
            if (v instanceof Firm) {
                Algorithms.shortestPaths(graph, v, Integer::compare, Integer::sum, 0, paths, dists);
                sum = 0;
                for (Integer i : dists) sum += i;
                average = 1.0d * sum / dists.size() - 1;
                firmSet.put(average, (Firm) v);
            }
        }

        Iterator<Map.Entry<Double, Firm>> iterator = firmSet.entrySet().iterator();
        for (int i = 0; iterator.hasNext() && i < n; i++) {
            Map.Entry<Double, Firm> entry = iterator.next();
            entry.getValue().setHUB(true);
            hubSet.put(entry.getKey(), entry.getValue());
        }

        return hubSet;
    }

    /**
     * Sets all firms as not HUBs
     */
    public void clearHUBs() {
        for (User v : graph.vertices())
            if (v instanceof Firm) ((Firm) v).setHUB(false);
    }

    /**
     * Finds the n nearest neighbours of an object type, starting in a vertex, ordered by distance.
     * Requires paths and dists from Algorithms.shortestPaths.
     *
     * @param limit minimum neighbours
     * @param paths paths
     * @param dists distance
     * @param clazz object class type
     * @return paths, ordered by distance
     */
    public TreeSet<DistancePathPair> findNearestNeighboursByType(int limit, ArrayList<LinkedList<User>> paths,
                                                                 ArrayList<Integer> dists, Class<?> clazz) {
        TreeSet<DistancePathPair> set = new TreeSet<>();
        for (int i = 0; i < dists.size(); i++)
            if (paths.get(i).getLast().getClass().equals(clazz))
                set.add(new DistancePathPair(dists.get(i), paths.get(i)));

        return set.stream().limit(limit).collect(TreeSet::new, (m, e) -> m.add(new DistancePathPair(e.getDistance(), e.getPath())), Set::addAll);
    }


    /**
     * Finds the nearest HUB to a vertex. Requires paths and dists from Algorithms.shortestPaths.
     *
     * @param paths paths
     * @param dists distance
     * @return HUB
     */
    public Firm findNearestHUB(ArrayList<LinkedList<User>> paths, ArrayList<Integer> dists) {
        TreeSet<DistancePathPair> set = new TreeSet<>();
        for (int i = 0; i < dists.size(); i++)
            if (paths.get(i).getLast() instanceof Firm && ((Firm) paths.get(i).getLast()).isHUB())
                set.add(new DistancePathPair(dists.get(i), paths.get(i)));

        return (Firm) set.first().getPath().getLast();
    }

}
