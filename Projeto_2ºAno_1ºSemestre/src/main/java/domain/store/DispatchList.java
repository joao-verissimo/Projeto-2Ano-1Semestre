package domain.store;

import data.graph.Algorithms;
import data.graph.map.MapGraph;
import data.graph.matrix.MatrixGraph;
import domain.model.*;

import java.util.*;

public class DispatchList {

    private final CabazStore cabazes;
    private final DistributionNetwork distributionNetwork;

    //public inner class
    public static class Delivery {
        private final Product product;
        private final float quantity;
        private final Producer producer;
        private final Firm hub;

        private Delivery(Product product, float quantity, Producer producer, Firm hub) {
            this.product = product;
            this.quantity = quantity;
            this.producer = producer;
            this.hub = hub;
        }

        public Product getProduct() {
            return product;
        }

        public float getQuantity() {
            return quantity;
        }

        public Producer getProducer() {
            return producer;
        }

        public Firm getHub() {
            return hub;
        }
    }//

    private final Map<Integer, Map<User, ArrayList<Delivery>>> dispatchList = new HashMap<>();

    public DispatchList(CabazStore cabazes, DistributionNetwork distributionNetwork) {
        this.cabazes = cabazes;
        this.distributionNetwork = distributionNetwork;
    }

    public Map<Integer, Map<User, ArrayList<Delivery>>> getDispatchList() {
        return dispatchList;
    }

    /**
     * Creates a dispatch list for a selected day for all clients with no restrictions.
     *
     * @param day day
     */
    public void createDispatchList(int day) {
        Firm hub;
        Producer producer;
        //iterate clients basket order
        for (Map.Entry<Client, Cabaz> order : cabazes.getCabazC().get(day).entrySet()) {
            //get nearest HUB
            ArrayList<LinkedList<User>> paths = new ArrayList<>();
            ArrayList<Integer> dists = new ArrayList<>();
            Algorithms.shortestPaths(distributionNetwork.getGraph(), order.getKey(), Integer::compare, Integer::sum, 0, paths, dists);
            hub = distributionNetwork.findNearestHUB(paths, dists);
            //iterate through basket products
            for (Map.Entry<Product, Float> orderProduct : order.getValue().getProducts().entrySet()) {
                producer = findProducerAvailableProduct(day, orderProduct);
                Delivery delivery = new Delivery(orderProduct.getKey(), orderProduct.getValue(), producer, hub);
                addToMap(day, order.getKey(), delivery);
                removeProducts(producer, orderProduct, day);
            }
        }
    }

    /**
     * Creates a dispatch list for a selected day for all clients.
     * Only n nearest producers to HUB are considered.
     *
     * @param day   day
     * @param limit maximum number of producers to search
     */
    public void createDispatchListNearest(int day, int limit) {
        Firm hub;
        Producer producer;
        HashMap<Firm, TreeSet<DistributionNetwork.DistancePathPair>> closestProducersToHUB = new HashMap<>();
        //iterate clients basket order
        for (Map.Entry<Client, Cabaz> order : cabazes.getCabazC().get(day).entrySet()) {
            //get nearest HUB
            ArrayList<LinkedList<User>> paths = new ArrayList<>();
            ArrayList<Integer> dists = new ArrayList<>();
            Algorithms.shortestPaths(distributionNetwork.getGraph(), order.getKey(), Integer::compare, Integer::sum, 0, paths, dists);
            hub = distributionNetwork.findNearestHUB(paths, dists);
            //get the nearest producers to HUB
            if (closestProducersToHUB.get(hub) == null) {
                closestProducersToHUB.put(hub, distributionNetwork.findNearestNeighboursByType(limit, paths, dists, Producer.class));
            }
            //iterate through basket products
            for (Map.Entry<Product, Float> orderProduct : order.getValue().getProducts().entrySet()) {
                producer = findNearestProducerAvailableProduct(hub, day, orderProduct, closestProducersToHUB);
                Delivery delivery = new Delivery(orderProduct.getKey(), orderProduct.getValue(), producer, hub);
                addToMap(day, order.getKey(), delivery);
                removeProducts(producer, orderProduct, day);
            }
        }
    }


    /**
     * Searches n nearest producers to HUB for a product and finds the producer that can provide the necessary quantity.
     * If no producer can provide the product fully, chooses the one with the bigger quantity.
     * Prioritizes same day product and closest producer.
     *
     * @param hub                   HUB
     * @param day                   day
     * @param product               product
     * @param closestProducersToHUB key -> firm, value -> set with n closest producers path, sorted by distance
     * @return producer
     */
    public Producer findNearestProducerAvailableProduct(Firm hub, int day, Map.Entry<Product, Float> product,
                                                        HashMap<Firm, TreeSet<DistributionNetwork.DistancePathPair>> closestProducersToHUB) {
        float tempQuantity;
        float highestQuantity = 0;
        Producer bestProducer = null;
        Producer p;
        for (DistributionNetwork.DistancePathPair distancePathPair : closestProducersToHUB.get(hub)) {//iterate producers by closest to HUB
            tempQuantity = 0;                                     //until limit or no more producers
            p = (Producer) distancePathPair.getPath().getLast();
            for (int i = 0; i <= 2 && day - i >= 1; i++) {//iterate available products
                if (cabazes.getCabazP().get(day - i).containsKey(p) &&
                        cabazes.getCabazP().get(day - i).get(p).getProducts().containsKey(product.getKey())) {
                    tempQuantity += cabazes.getCabazP().get(day - i).get(p).getProducts().get(product.getKey());
                    if (tempQuantity >= product.getValue()) {//found the closest producer with quantity
                        return p;
                    }
                    if (tempQuantity > highestQuantity) {//check for producer with higher quantity
                        highestQuantity = tempQuantity;
                        bestProducer = p;
                    }
                }
            }
        }
        return bestProducer;
    }

    /**
     * Searches producers with products with no restrictions.
     *
     * @param day     day
     * @param product product
     * @return producer
     */
    public Producer findProducerAvailableProduct(int day, Map.Entry<Product, Float> product) {
        float tempQuantity;
        float highestQuantity = 0;
        Producer bestProducer = null;
        for (User user : distributionNetwork.getGraph().vertices()) {//iterate producers by closest to HUB until limit or no more producers
            if (user instanceof Producer) {
                tempQuantity = 0;
                for (int i = 0; i <= 2 && day - i >= 1; i++) {//iterate available products
                    if (cabazes.getCabazP().get(day - i).containsKey(user) &&
                            cabazes.getCabazP().get(day - i).get(user).getProducts().containsKey(product.getKey())) {
                        tempQuantity += cabazes.getCabazP().get(day - i).get(user).getProducts().get(product.getKey());
                        if (tempQuantity >= product.getValue()) {//found the closest producer with quantity
                            return (Producer) user;
                        }
                        if (tempQuantity > highestQuantity) {//check for producer with higher quantity
                            highestQuantity = tempQuantity;
                            bestProducer = (Producer) user;
                        }
                    }
                }
            }
        }
        return bestProducer;
    }

    /**
     * Removes products from cabaz
     *
     * @param producer     producer
     * @param productOrder key -> product, value -> quantity
     * @param day          day
     */
    public void removeProducts(Producer producer, Map.Entry<Product, Float> productOrder, int day) {
        float orderQuantity = productOrder.getValue();
        for (int i = 0; i <= 2 && orderQuantity > 0 && day - i >= 1; i++) {
            if (cabazes.getCabazP().get(day - i).containsKey(producer) &&
                    cabazes.getCabazP().get(day - i).get(producer).getProducts().containsKey(productOrder.getKey())) {
                float pDayQuantity = cabazes.getCabazP().get(day - i).get(producer).getProducts().get(productOrder.getKey());
                cabazes.getCabazP().get(day - i).get(producer).getProducts().put(productOrder.getKey(), Math.max(0, pDayQuantity - orderQuantity));
                orderQuantity -= pDayQuantity;
            }
        }
    }

    /**
     * adds delivery information to map
     *
     * @param day      day
     * @param user     user who ordered
     * @param delivery delivery information
     */
    private void addToMap(int day, User user, Delivery delivery) {
        Map<User, ArrayList<Delivery>> map;
        if (!dispatchList.containsKey(day)) {
            map = new HashMap<>();
            map.put(user, new ArrayList<>());
            dispatchList.put(day, map);
        } else if (!dispatchList.get(day).containsKey(user)) {
            dispatchList.get(day).put(user, new ArrayList<>());
        }
        dispatchList.get(day).get(user).add(delivery);
    }

    public Set<Producer> getProducersForUser(User user) {
        Set<Producer> producers = new HashSet<>();
        for (Map<User, ArrayList<Delivery>> dispatch : dispatchList.values()) {
            ArrayList<Delivery> deliveries = dispatch.get(user);
            if (deliveries != null) {
                for (Delivery delivery : deliveries) {
                    producers.add(delivery.getProducer());
                }
            }
        }
        return producers;
    }

    public Set<Producer> getShortestPath(Set<Producer> producers) {

        MatrixGraph<Producer, Integer> graph = new MatrixGraph<>(false);
        Iterator<Producer> it1 = producers.iterator();
        MapGraph<User, Integer> g = distributionNetwork.getGraph();

        while (it1.hasNext()) {
            Producer producer1 = it1.next();
            Iterator<Producer> it2 = producers.iterator();
            graph.addVertex(producer1);
            while (it2.hasNext()) {
                Producer producer2 = it2.next();
                if (producer1 != producer2) {
                    graph.addEdge(producer1, producer2, g.edge(producer1, producer2).getWeight());
                }

            }
        }
        graph = (MatrixGraph<Producer, Integer>) Algorithms.kruskal(graph);

        Set<Producer> prods = (Set<Producer>) graph.vertices();
        return prods;
    }
}
