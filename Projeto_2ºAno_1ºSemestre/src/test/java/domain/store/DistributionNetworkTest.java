package domain.store;

import data.graph.Algorithms;
import data.graph.Edge;
import domain.model.Client;
import domain.model.Firm;
import domain.model.Producer;
import domain.model.User;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DistributionNetworkTest {

    private static final DistributionNetwork distributionNetwork = new DistributionNetwork();

//    //SMALL
//    private static  final int VERTEX_SIZE = 17;
//    private static  final int EDGE_SIZE = 66;
//    private static  final String USER_PATH = "docs\\grafos\\Small\\clientes-produtores_small.csv";
//    private static  final String DISTANCE_PATH = "docs\\grafos\\Small\\distancias_small.csv";
//    //N=3
//    private static  final ArrayList<String> expectedHUBs = new ArrayList<>(Arrays.asList("E2", "E3", "E4"));

    //BIG
    private static final int VERTEX_SIZE = 323;
    private static final int EDGE_SIZE = 1566;
    private static final String USER_PATH = "docs\\grafos\\Big\\clientes-produtores_big.csv";
    private static final String DISTANCE_PATH = "docs\\grafos\\Big\\distancias_big.csv";
    //N=50
    private static final ArrayList<String> expectedHUBs = new ArrayList<>(Arrays.asList(
            "E69", "E2", "E3", "E4", "E54", "E71", "E90", "E8", "E9", "E73",
            "E11", "E72", "E65", "E14", "E60", "E78", "E17", "E76", "E19", "E67",
            "E87", "E88", "E23", "E24", "E25", "E86", "E56", "E28", "E29", "E30",
            "E84", "E32", "E68", "E52", "E35", "E36", "E55", "E38", "E39", "E40",
            "E41", "E77", "E43", "E79", "E53", "E57", "E47", "E48", "E49", "E82"));


    @BeforeAll
    static void setUp() throws IOException {
        distributionNetwork.importUserCSV(USER_PATH);
        distributionNetwork.importDistanceCSV(DISTANCE_PATH);
        distributionNetwork.setHUBs(expectedHUBs.size());
    }

    @Test
    @Order(1)
    void testImportUserCSV() {
        assertEquals(VERTEX_SIZE, distributionNetwork.getGraph().vertices().size(), "Wrong vertices count");
        assertTrue(Algorithms.isGraphConnected(distributionNetwork.getGraph()), "Graph is not connected");
    }

    @Test
    @Order(2)
    void testImportDistanceCSV() throws FileNotFoundException {
        assertEquals(EDGE_SIZE, distributionNetwork.getGraph().edges().size(), "Wrong vertices count");
        File file = new File(DISTANCE_PATH);
        Scanner reader = new Scanner(file);
        reader.nextLine();
        while (reader.hasNextLine()) {
            String[] metadata = reader.nextLine().split(Constants.CSV_SPLIT_REGEX);
            Edge<User, Integer> edge = distributionNetwork.getGraph().edge(
                    distributionNetwork.getGraph().vertex(user -> user.getLocID().equals(metadata[Constants.Distance.LOC_ID_1.ordinal()])),
                    distributionNetwork.getGraph().vertex(user -> user.getLocID().equals(metadata[Constants.Distance.LOC_ID_2.ordinal()])));
            assertNotNull(edge, "Edge doesnt exist");
            assertEquals(Integer.valueOf(metadata[Constants.Distance.LENGTH.ordinal()]), edge.getWeight(), "Wrong weight");
            edge = distributionNetwork.getGraph().edge(
                    distributionNetwork.getGraph().vertex(user -> user.getLocID().equals(metadata[Constants.Distance.LOC_ID_2.ordinal()])),
                    distributionNetwork.getGraph().vertex(user -> user.getLocID().equals(metadata[Constants.Distance.LOC_ID_1.ordinal()])));
            assertNotNull(edge, "Edge doesnt exist");
            assertEquals(Integer.valueOf(metadata[Constants.Distance.LENGTH.ordinal()]), edge.getWeight(), "Wrong weight");
        }
        reader.close();
    }

    @Test
    @Order(7)
    void testAddUser() {
        Exception exception = assertThrows(InvalidParameterException.class, () ->
                distributionNetwork.createUser("CT1", "D1", 25, 30));

        String expectedMessage = "Invalid user ID";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Order(8)
    void testAddPath() {
        Exception exception = assertThrows(InvalidParameterException.class, () ->
                distributionNetwork.addPath("CT1", "CT1", 6500));
        String expectedMessage = "Location ID 1 and 2 cannot be equal";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(InvalidParameterException.class, () ->
                distributionNetwork.addPath("CT0", "CT1", 6500));
        expectedMessage = "User with location ID 1 doesn't exist";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(InvalidParameterException.class, () ->
                distributionNetwork.addPath("CT1", "CT0", 6500));
        expectedMessage = "User with location ID 2 doesn't exist";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        exception = assertThrows(InvalidParameterException.class, () ->
                distributionNetwork.addPath("CT00", "CT0", 6500));
        expectedMessage = "Users with location ID 1 and 2 don't exist";
        actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @Order(3)
    void testSetHUBNetwork() {
        TreeMap<Double, Firm> actual = distributionNetwork.setHUBs(expectedHUBs.size());

        ArrayList<Firm> expected = new ArrayList<>();
        for (String hub : expectedHUBs)
            expected.add((Firm) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals(hub)));

        assertEquals(expected.size(), actual.size(), "wrong number of HUBs");
        for (Map.Entry<Double, Firm> entry : actual.entrySet()) {
            assertTrue(expected.contains(entry.getValue()), "Company " + entry.getValue() + "shouldn't be HUB");
            expected.remove(entry.getValue());
            assertTrue(entry.getValue().isHUB(), "Company should be HUB");
        }
    }

    @Test
    @Order(6)
    void testClearHUBs() {
        distributionNetwork.clearHUBs();
        for (User v : distributionNetwork.getGraph().vertices())
            if (v instanceof Firm) assertFalse(((Firm) v).isHUB());
    }

    @Test
    @Order(4)
    void testFindNearestNeighboursOfType() {
        Firm hub = (Firm) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("E4"));
        ArrayList<LinkedList<User>> paths = new ArrayList<>();
        ArrayList<Integer> dists = new ArrayList<>();
        Algorithms.shortestPaths(distributionNetwork.getGraph(), hub, Integer::compare, Integer::sum, 0, paths, dists);
        TreeSet<DistributionNetwork.DistancePathPair> actual = distributionNetwork.findNearestNeighboursByType(7, paths, dists, Producer.class);
        System.out.println(actual);
        System.out.println(distributionNetwork.findNearestNeighboursByType(10, paths, dists, Producer.class));
        TreeSet<DistributionNetwork.DistancePathPair> expected = new TreeSet<>();
        expected.add(new DistributionNetwork.DistancePathPair(8776, new LinkedList<>(Arrays.asList(hub, distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P35"))))));
        expected.add(new DistributionNetwork.DistancePathPair(11921, new LinkedList<>(Arrays.asList(hub, distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P29"))))));
        expected.add(new DistributionNetwork.DistancePathPair(16189, new LinkedList<>(Arrays.asList(hub, distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P44"))))));
        expected.add(new DistributionNetwork.DistancePathPair(18501, new LinkedList<>(Arrays.asList(hub, distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P42"))))));
        expected.add(new DistributionNetwork.DistancePathPair(23448, new LinkedList<>(Arrays.asList(hub, distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P3"))))));
        expected.add(new DistributionNetwork.DistancePathPair(49818, new LinkedList<>(Arrays.asList(hub, distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("C57")),
                distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("C16")),
                distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("E23")),
                distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P36"))))));
        expected.add(new DistributionNetwork.DistancePathPair(54395, new LinkedList<>(Arrays.asList(hub, distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P42")),
                distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("E36")),
                distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P4"))))));
        assertEquals(expected, actual);
    }

    @Test
    @Order(5)
    void testFindNearestHUB() {
        ArrayList<LinkedList<User>> paths = new ArrayList<>();
        ArrayList<Integer> dists = new ArrayList<>();
        Firm actual;
        Client c1 = (Client) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("C1"));
        Client c2 = (Client) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("C2"));
        Producer p1 = (Producer) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P1"));
        Producer p2 = (Producer) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P2"));
        Firm expectedC1 = (Firm) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("E49"));
        Firm expectedC2 = (Firm) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("E35"));
        Firm expectedP1 = (Firm) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("E39"));
        Firm expectedP2 = (Firm) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("E41"));

        Algorithms.shortestPaths(distributionNetwork.getGraph(), c1, Integer::compare, Integer::sum, 0, paths, dists);
        actual = distributionNetwork.findNearestHUB(paths, dists);
        assertTrue(actual.isHUB());
        assertEquals(expectedC1, actual);

        Algorithms.shortestPaths(distributionNetwork.getGraph(), c2, Integer::compare, Integer::sum, 0, paths, dists);
        actual = distributionNetwork.findNearestHUB(paths, dists);
        assertTrue(actual.isHUB());
        assertEquals(expectedC2, actual);

        Algorithms.shortestPaths(distributionNetwork.getGraph(), p1, Integer::compare, Integer::sum, 0, paths, dists);
        actual = distributionNetwork.findNearestHUB(paths, dists);
        assertTrue(actual.isHUB());
        assertEquals(expectedP1, actual);

        Algorithms.shortestPaths(distributionNetwork.getGraph(), p2, Integer::compare, Integer::sum, 0, paths, dists);
        actual = distributionNetwork.findNearestHUB(paths, dists);
        assertTrue(actual.isHUB());
        assertEquals(expectedP2, actual);
    }

}