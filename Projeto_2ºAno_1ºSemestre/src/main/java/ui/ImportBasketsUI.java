package ui;

import domain.model.*;
import domain.store.CabazStore;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ImportBasketsUI implements Runnable {
    Company company = Company.getInstance();
    CabazStore cabazStore = company.getCabazStore();

    @Override
    public void run() {
        List<String> options = new ArrayList<>(Arrays.asList("Import Baskets", "List Baskets"));

        System.out.println("\n||----------------------------------------||\n");

        int option = Utils.showAndSelectIndex(options, "Select Option:") + 1;
        switch (option) {
            case 1 -> {
                String path = Utils.readLineFromConsole("File Location: ");
                try {
                    cabazStore.importCabazFromCSV(path, company.getDistributionNetwork().getGraph());
                } catch (NoSuchFileException e) {
                    System.out.println("Error: file not found");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("File imported with success!");

            }
            case 2 -> {
                Map<Integer, Map<Producer, Cabaz>> cabazP = cabazStore.getCabazP();
                Map<Integer, Map<Firm, Cabaz>> cabazE = cabazStore.getCabazE();
                Map<Integer, Map<Client, Cabaz>> cabazC = cabazStore.getCabazC();
                if (cabazC.isEmpty() && cabazE.isEmpty() && cabazP.isEmpty()) {
                    System.out.println("No baskets to show!");
                } else {
                    printBasketsForType(cabazP, "Producer");
                    printBasketsForType(cabazC, "Client");
                    printBasketsForType(cabazE, "Firm");
                }
            }
        }
            System.out.println("\n||----------------------------------------||");
        }


    private <T> void printBasketsForType(Map<Integer, Map<T, Cabaz>> baskets, String customerType) {
        for (Integer dia : baskets.keySet()) {
            System.out.println(customerType + " Day: " + dia);
            for (T c : baskets.get(dia).keySet()) {
                System.out.println(baskets.get(dia).get(c));
            }
        }
    }
}
