package ui.MainMenu;

import ui.*;

import java.util.ArrayList;
import java.util.List;

public class MainMenuUI {
    public void run() {
        List<MenuItem> options = new ArrayList<>();
        //Menu items
        options.add(new MenuItem("Add User", new AddUserUI()));
        options.add(new MenuItem("Add Connection", new AddConnectionUI()));
        options.add(new MenuItem("Set HUBs", new SetHUBsUI()));
        options.add(new MenuItem("Baskets Menu", new ImportBasketsUI()));
        options.add(new MenuItem("Dispatch List Menu", new DispatchListUI()));
        options.add(new MenuItem("Exit", () -> System.exit(0)));

        int option;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nMain Menu");

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        }
        while (option != -1);
    }
}
