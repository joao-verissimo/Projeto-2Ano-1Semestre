package controller;

import data.graph.Graph;
import domain.model.Company;
import domain.model.User;
import domain.store.DistributionNetwork;

import java.io.IOException;

public class AddUserController {

    private final DistributionNetwork distributionNetwork;

    public AddUserController() {
        this.distributionNetwork = Company.getInstance().getDistributionNetwork();
    }

    public void importUserCSV(String path) throws IOException {
        distributionNetwork.importUserCSV(path);
    }

    public void addUser(String locId, String userId, double lat, double lng) {
        User user = distributionNetwork.createUser(locId, userId, lat, lng);
        distributionNetwork.getGraph().addVertex(user);
    }

}
