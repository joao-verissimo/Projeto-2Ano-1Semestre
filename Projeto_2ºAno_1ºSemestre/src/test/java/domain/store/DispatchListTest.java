package domain.store;

import data.graph.Algorithms;
import domain.model.Firm;
import domain.model.Producer;
import domain.model.Product;
import domain.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class DispatchListTest {

    private static final DistributionNetwork distributionNetwork = new DistributionNetwork();
    private static final CabazStore cabazStore = new CabazStore();
    private static DispatchList dispatchList;

    //SMALL
//    private static final String USER_PATH = "docs\\grafos\\Small\\clientes-produtores_small.csv";
//    private static final String DISTANCE_PATH = "docs\\grafos\\Small\\distancias_small.csv";
//    private static final String BASKET_PATH = "docs\\grafos\\Small\\cabazes_small.csv";

    //BIG
    private static final String USER_PATH = "docs\\grafos\\Big\\clientes-produtores_big.csv";
    private static final String DISTANCE_PATH = "docs\\grafos\\Big\\distancias_big.csv";
    private static final String BASKET_PATH = "docs\\grafos\\Big\\cabazes_big.csv";

    @BeforeAll
    static void setUp() throws IOException {
        dispatchList = new DispatchList(cabazStore, distributionNetwork);
        distributionNetwork.importUserCSV(USER_PATH);
        distributionNetwork.importDistanceCSV(DISTANCE_PATH);
        distributionNetwork.setHUBs(50);
        cabazStore.importCabazFromCSV(BASKET_PATH, distributionNetwork.getGraph());
    }

    //example
    @Test
    void createDispatchListNearest() {
        dispatchList.createDispatchListNearest(3, 10);
    }

    @Test
    void testFindNearestProducerAvailableProduct() {
        Firm hub = (Firm) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("E4"));
        HashMap<Firm, TreeSet<DistributionNetwork.DistancePathPair>> closestProducersToHUB = new HashMap<>();
        ArrayList<LinkedList<User>> paths = new ArrayList<>();
        ArrayList<Integer> dists = new ArrayList<>();
        Algorithms.shortestPaths(distributionNetwork.getGraph(), hub, Integer::compare, Integer::sum, 0, paths, dists);
        closestProducersToHUB.put(hub, distributionNetwork.findNearestNeighboursByType(10, paths, dists, Producer.class));
        Producer actual;
        Producer expected;
        HashMap<Product, Float> product = new HashMap<>();

        product.put(new Product("Prod12"), 3.5F);
        actual = dispatchList.findNearestProducerAvailableProduct(hub, 2, product.entrySet().iterator().next(), closestProducersToHUB);
        expected = (Producer) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P35"));
        assertEquals(expected, actual);

        product.clear();
        product.put(new Product("Prod8"), 10F);
        actual = dispatchList.findNearestProducerAvailableProduct(hub, 2, product.entrySet().iterator().next(), closestProducersToHUB);
        expected = (Producer) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P37"));
        assertEquals(expected, actual);

        product.clear();
        product.put(new Product("Prod8"), 9.5F);
        actual = dispatchList.findNearestProducerAvailableProduct(hub, 2, product.entrySet().iterator().next(), closestProducersToHUB);
        expected = (Producer) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P37"));
        assertEquals(expected, actual);

        product.clear();
        product.put(new Product("Prod8"), 8F);
        actual = dispatchList.findNearestProducerAvailableProduct(hub, 2, product.entrySet().iterator().next(), closestProducersToHUB);
        expected = (Producer) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P42"));
        assertEquals(expected, actual);
    }

    @Test
    void testRemoveProducts() {
        Producer p1 = (Producer) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P1"));
        Producer p16 = (Producer) distributionNetwork.getGraph().vertex(user -> user.getUserID().equals("P16"));
        HashMap<Product, Float> product = new HashMap<>();
        product.put(new Product("Prod19"), 3.5F);
        dispatchList.removeProducts(p1, product.entrySet().iterator().next(), 1);
        assertEquals(4, cabazStore.getCabazP().get(1).get(p1).getProducts().get(product.entrySet().iterator().next().getKey()));

        product.clear();
        product.put(new Product("Prod7"), 15F);
        dispatchList.removeProducts(p16, product.entrySet().iterator().next(), 2);
        assertEquals(0, cabazStore.getCabazP().get(2).get(p16).getProducts().get(product.entrySet().iterator().next().getKey()));
        assertEquals(1, cabazStore.getCabazP().get(1).get(p16).getProducts().get(product.entrySet().iterator().next().getKey()));
    }

}
