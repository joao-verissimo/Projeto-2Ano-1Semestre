package ui;

import controller.DispatchListController;
import domain.model.User;
import domain.store.DispatchList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DispatchListUI implements Runnable {

    @Override
    public void run() {
        DispatchListController controller = new DispatchListController();
        List<String> options = new ArrayList<>(Arrays.asList("Create dispatch list", "See dispatch lists"));
        System.out.println("\n||----------------------------------------||\n");
        int option = Utils.showAndSelectIndex(options, "Select Option:") + 1;
        switch (option) {
            case 1 -> {
                int day = Utils.readIntegerFromConsole("Input day:");
                List<String> options2 = new ArrayList<>(Arrays.asList("No restrictions", "N closest producers to HUB"));
                int option2 = Utils.showAndSelectIndex(options2, "Create dispatch list with:") + 1;
                switch (option2) {
                    case 1 -> {
                        try {
                            controller.createDispatchList(day);
                            System.out.println("Dispatch list generated with success");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    case 2 -> {
                        int limit = Utils.readIntegerFromConsole("Number of producers closest to HUB");
                        try {
                            controller.createDispatchListNearest(day, limit);
                            System.out.println("Dispatch list generated with success");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
            case 2 -> {
                int day = Utils.readIntegerFromConsole("Input day:");
                for (Map.Entry<User, ArrayList<DispatchList.Delivery>> entry : controller.getDispatchList().get(day).entrySet()) {
                    System.out.println("Client: " + entry.getKey());
                    System.out.println("Delivery HUB: " + " User ID: " + entry.getValue().get(0).getHub().getUserID() + " Location ID: " + entry.getValue().get(0).getHub().getLocID());
                    for (DispatchList.Delivery v : entry.getValue()) {
                        System.out.println("Product: " + v.getProduct());
                        System.out.println("Quantity: " + v.getQuantity());
                        if (v.getProducer() != null) {
                            System.out.println("Product Provider: " + " Location ID: " + v.getProducer().getLocID() + " User ID: " + v.getProducer().getUserID() + "\n");
                        } else {
                            System.out.println("Product Provider: no producer with product");
                        }
                    }
                }
            }
        }
    }

}
