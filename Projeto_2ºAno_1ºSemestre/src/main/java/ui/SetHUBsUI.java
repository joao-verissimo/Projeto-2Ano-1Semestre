package ui;

import controller.SetHUBsController;
import domain.model.Firm;

import java.security.InvalidParameterException;
import java.util.*;

public class SetHUBsUI implements Runnable {

    @Override
    public void run() {
        SetHUBsController controller = new SetHUBsController();
        List<String> options = new ArrayList<>(Arrays.asList("Set HUBs", "Clear HUBs"));
        System.out.println("\n||----------------------------------------||\n");
        int option = Utils.showAndSelectIndex(options, "Select Option:") + 1;
        switch (option) {
            case 1 -> {
                try {
                    TreeMap<Double, Firm> firms = controller.setHUBs(Utils.readIntegerFromConsole("Number of Hubs: "));
                    int i = 1;
                    System.out.println("Hubs:");
                    for (Map.Entry<Double, Firm> entry : firms.entrySet())
                        System.out.println((i++) + ". User ID: " + entry.getValue().getUserID() + " Avg. Distance: " + entry.getKey().intValue() + "m");
                } catch (InvalidParameterException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            case 2 -> {
                controller.clearHUBs();
                System.out.println("Hubs cleared");
            }
        }
        System.out.println("\n||----------------------------------------||");
    }
}
