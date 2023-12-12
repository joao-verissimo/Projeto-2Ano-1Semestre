package domain.store;

import data.graph.map.MapGraph;
import domain.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CabazStore {

    Map<Integer, Map<Client,Cabaz>> cabazC = new HashMap<>();
    Map<Integer, Map<Firm,Cabaz>> cabazE = new HashMap<>();
    Map<Integer, Map<Producer,Cabaz>>  cabazP = new HashMap<>();

    /*
    * This method imports the Cabaz from a CSV file
    * @param path the path to the CSV file
    * @param graph the graph of the distribution network
    * */
    public void importCabazFromCSV(String filePath,MapGraph<User, Integer>  graph) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String id;
            reader.readLine();
            while ((line = reader.readLine()) != null) {

                String[] values = line.split(",");
                id = values[0].replace("\"", "");
                int dia = Integer.parseInt(values[1].replace("\"", ""));
                int numProdutos = values.length - 2;
                Map<Product, Float> produtos = new HashMap<>();
                for (int i = 0; i < numProdutos; i++) {
                    if (Float.parseFloat(values[i + 2].replace("\"", "")) != 0) {
                        produtos.put(new Product("Prod" + (i+1)), Float.parseFloat(values[i + 2].replace("\"", "")));
                    }
                }
                String finalId1 = id;
                User c = graph.vertex(user -> user.getUserID().equals(finalId1));
                if (!produtos.isEmpty()) {
                    Cabaz cabaz = new Cabaz(c, dia, produtos);
                    if (c != null) {
                        switch (id.charAt(0)) {
                            case 'C':
                                if (cabazC.containsKey(dia)) {
                                    cabazC.get(dia).put((Client) c, cabaz);
                                } else {
                                    Map<Client, Cabaz> cabazes = new HashMap<>();
                                    cabazes.put((Client) c, cabaz);
                                    cabazC.put(dia, cabazes);
                                }
                                break;
                            case 'E':
                                if (cabazE.containsKey(dia)) {
                                    cabazE.get(dia).put((Firm) c, cabaz);
                                } else {
                                    Map<Firm, Cabaz> cabazes = new HashMap<>();
                                    cabazes.put((Firm) c, cabaz);
                                    cabazE.put(dia, cabazes);
                                }
                                break;
                            case 'P':
                                if (cabazP.containsKey(dia)) {
                                    cabazP.get(dia).put((Producer) c, cabaz);
                                } else {
                                    Map<Producer, Cabaz> cabazes = new HashMap<>();
                                    cabazes.put((Producer) c, cabaz);
                                    cabazP.put(dia, cabazes);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }
    // getCabazC
    public Map<Integer, Map<Client,Cabaz>> getCabazC() {
        return cabazC;
    }
    // getCabazE
    public Map<Integer, Map<Firm,Cabaz>> getCabazE() {
        return cabazE;
    }

    public Map<Integer, Map<Producer,Cabaz>> getCabazP() {
        return cabazP;
    }
    public float getTotalQuantityOfProducts() {
        float total = 0;

        // Iterate through cabazP and add the quantity of products in each cabaz to the total
        for (Map<Producer, Cabaz> cabazMap : cabazP.values()) {
            for (Cabaz cabaz : cabazMap.values()) {
                for (Map.Entry<Product, Float> entry : cabaz.getProducts().entrySet()) {
                    total += entry.getValue();
                }
            }
        }

        // Iterate through cabazC and add the quantity of products in each cabaz to the total
        for (Map<Client, Cabaz> cabazMap : cabazC.values()) {
            for (Cabaz cabaz : cabazMap.values()) {
                for (Map.Entry<Product, Float> entry : cabaz.getProducts().entrySet()) {
                    total += entry.getValue();
                }
            }
        }

        // Iterate through cabazE and add the quantity of products in each cabaz to the total
        for (Map<Firm, Cabaz> cabazMap : cabazE.values()) {
            for (Cabaz cabaz : cabazMap.values()) {
                for (Map.Entry<Product, Float> entry : cabaz.getProducts().entrySet()) {
                    total += entry.getValue();
                }
            }
        }

        return total;
    }
}


