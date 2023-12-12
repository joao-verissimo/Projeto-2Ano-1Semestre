package controller;

import domain.model.Company;
import domain.store.DistributionNetwork;

import java.io.IOException;

public class AddConnectionController {

    private final DistributionNetwork distributionNetwork;


    public AddConnectionController() {
        this.distributionNetwork = Company.getInstance().getDistributionNetwork();
    }

    public void importDistanceCSV(String path) throws IOException {
        distributionNetwork.importDistanceCSV(path);
    }

    public void addPath(String locID1, String locID2, Integer weight) {
        distributionNetwork.addPath(locID1, locID2, weight);
    }
}
