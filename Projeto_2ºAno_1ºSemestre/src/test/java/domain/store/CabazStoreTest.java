package domain.store;

import domain.model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CabazStoreTest {
    DistributionNetwork distributionNetwork = new DistributionNetwork();
    CabazStore cabazStore = new CabazStore();
    String USER_PATH = "docs\\grafos\\Big\\clientes-produtores_big.csv";
    String DISTANCE_PATH = "docs\\grafos\\Big\\distancias_big.csv";


    @Test
    public void testImportCabazFromCSV() throws IOException {
        distributionNetwork.importUserCSV(USER_PATH);
        distributionNetwork.importDistanceCSV(DISTANCE_PATH);
        cabazStore.importCabazFromCSV("docs/grafos/Small/cabazes_small.csv", distributionNetwork.getGraph());
        Map<Integer, Map<Producer, Cabaz>> cabazP = cabazStore.getCabazP();
        Map<Integer, Map<Firm, Cabaz>> cabazE = cabazStore.getCabazE();
        Map<Integer, Map<Client, Cabaz>> cabazC = cabazStore.getCabazC();

        assertEquals(5,cabazC.size(),"There should be 5 days in CabazC");
        assertEquals(5,cabazP.size(),"There should be 5 days in CabazP");
        assertEquals(5,cabazE.size(),"There should be 5 days in CabazE");
        assertEquals(6,cabazC.get(1).size(),"There should be 9 Clients on DAY 1");
        assertEquals(3,cabazP.get(1).size(),"There should be 3 Producers on DAY 1");
        assertEquals(4,cabazE.get(1).size(),"There should be 5 Firms on DAY 1");

        for (Cabaz C:cabazC.get(1).values()) {
            assertEquals(1,C.getDay(),"The day should be 1");
        }
        for (Cabaz P:cabazP.get(1).values()) {
            assertEquals(1,P.getDay(),"The day should be 1");
        }
        for (Cabaz E:cabazE.get(1).values()) {
            assertEquals(1,E.getDay(),"The day should be 1");
        }


    }

    @Test
    public void testPrinting() throws IOException {
        distributionNetwork.importUserCSV(USER_PATH);
        distributionNetwork.importDistanceCSV(DISTANCE_PATH);
        cabazStore.importCabazFromCSV("docs/grafos/Small/cabazes_small.csv", distributionNetwork.getGraph());
        Map<Integer, Map<Producer, Cabaz>> cabazP = cabazStore.getCabazP();
        Map<Integer, Map<Firm, Cabaz>> cabazE = cabazStore.getCabazE();
        Map<Integer, Map<Client, Cabaz>> cabazC = cabazStore.getCabazC();
        for (Integer dia : cabazP.keySet()) {
            System.out.println("P "+dia);
            for (Producer c : cabazP.get(dia).keySet()) {
                System.out.println(c + " " + cabazP.get(dia).get(c));
            }
        }
        for (Integer dia : cabazC.keySet()) {
            System.out.println("C "+dia);
            for (Client c : cabazC.get(dia).keySet()) {
                System.out.println(c + " " + cabazC.get(dia).get(c));
            }
        }
        for (Integer dia : cabazE.keySet()) {
            System.out.println("F "+dia);
            for (Firm c : cabazE.get(dia).keySet()) {
                System.out.println(c + " " + cabazE.get(dia).get(c));
            }
        }
    }
    @Test
    public void testCreateCabazAndGets() {
        Client client = new Client("CT1", "C1", 40.6389, -8.6553);
        Map<Product, Float> products = new HashMap<>();
        products.put(new Product("Prod1"), 10.0F);
        products.put(new Product("Prod5"), 9.0F);
        products.put(new Product("Prod6"), 2.5F);
        products.put(new Product("Prod9"), 4.5F);
        Cabaz cabaz = new Cabaz(client, 1, products);
        assertEquals(cabaz.getDay(), 1);
        assertEquals(cabaz.getUser(), client);
        assertEquals(cabaz.getProducts(), products);
    }

    @Test
    public void testgetTotalQuantityOfProducts() {
        CabazStore cabazStore = new CabazStore();

        Client client = new Client("CT1", "C1", 40.6389, -8.6553);
        Map<Product, Float> products = new HashMap<>();
        products.put(new Product("Prod1"), 10.0F);
        products.put(new Product("Prod5"), 9.0F);
        products.put(new Product("Prod6"), 2.5F);
        products.put(new Product("Prod9"), 4.5F);
        Cabaz cabaz1 = new Cabaz(client, 1, products);
        Map<Client, Cabaz> cabazes = new HashMap<>();
        cabazes.put((Client) cabaz1.getUser(), cabaz1);
        cabazStore.getCabazC().put(1, cabazes);

        float totalCost = cabazStore.getTotalQuantityOfProducts();
        assertEquals(26.0F, totalCost, "The total cost should be 26.0"); // 10+9+2.5+4.5 = 26
    }

}