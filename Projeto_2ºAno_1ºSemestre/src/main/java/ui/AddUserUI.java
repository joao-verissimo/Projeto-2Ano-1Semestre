package ui;

import controller.AddUserController;
import domain.model.Company;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddUserUI implements Runnable {

    AddUserController controller = new AddUserController();

    @Override
    public void run() {
        List<String> options = new ArrayList<>(Arrays.asList("Import Users from CSV", "Add User"));

        System.out.println("\n||----------------------------------------||\n");

        int option = Utils.showAndSelectIndex(options, "Select Option:") + 1;
        switch (option) {
            case 1 -> {
                String path = Utils.readLineFromConsole("File Location: ");
                try {
                    controller.importUserCSV(path);
                } catch (NoSuchFileException e) {
                    System.out.println("Error: file not found");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("File imported with success!");
            }
            case 2 -> {
                String locId = Utils.readLineFromConsole("Location ID: ");
                String userId = Utils.readLineFromConsole("User ID: ");
                double lat = Utils.readDoubleFromConsole("Latitude: ");
                double lng = Utils.readDoubleFromConsole("Longitude: ");
                try {
                    controller.addUser(locId, userId, lat, lng);
                } catch (InvalidParameterException e) {
                    System.out.println("Error: " + e.getMessage());
                }
                System.out.println("User added with success!");
            }
        }
        System.out.println("\n||----------------------------------------||");
    }
}
