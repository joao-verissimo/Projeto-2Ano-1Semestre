package controller;

import domain.model.Company;
import domain.model.Firm;
import domain.store.DistributionNetwork;

import java.util.TreeMap;

public class SetHUBsController {

    private final DistributionNetwork distributionNetwork;

    public SetHUBsController() {
        this.distributionNetwork = Company.getInstance().getDistributionNetwork();
    }

    public TreeMap<Double, Firm> setHUBs(int n) {
        return distributionNetwork.setHUBs(n);
    }

    public void clearHUBs() {
        distributionNetwork.clearHUBs();
    }


}
