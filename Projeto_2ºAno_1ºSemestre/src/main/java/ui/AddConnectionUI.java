package ui;

import controller.AddConnectionController;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddConnectionUI implements Runnable {

    @Override
    public void run() {
        AddConnectionController controller = new AddConnectionController();
        List<String> options = new ArrayList<>(Arrays.asList("Import connections from CSV", "Add connection"));

        System.out.println("\n||----------------------------------------||\n");

        int option = Utils.showAndSelectIndex(options, "Select Option:") + 1;
        switch (option) {
            case 1 -> {
                String path = Utils.readLineFromConsole("File Location: ");
                try {
                    controller.importDistanceCSV(path);
                } catch (NoSuchFileException e) {
                    System.out.println("Error: file not found");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("File imported with success!");
            }
            case 2 -> {
                String locID1 = Utils.readLineFromConsole("Location ID 1: ");
                String locID2 = Utils.readLineFromConsole("Location ID 2: ");
                Integer weight = Utils.readIntegerFromConsole("Distance: ");
                try {
                    controller.addPath(locID1, locID2, weight);
                } catch (InvalidParameterException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.out.println("Connection added with success!");
            }
        }
        System.out.println("\n||----------------------------------------||");
    }

}
