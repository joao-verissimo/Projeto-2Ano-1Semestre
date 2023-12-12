package controller;

import domain.model.Company;
import domain.model.User;
import domain.store.CabazStore;
import domain.store.DispatchList;
import domain.store.DistributionNetwork;

import java.util.ArrayList;
import java.util.Map;

public class DispatchListController {
    private final DistributionNetwork distributionNetwork;
    private final CabazStore cabazStore;
    private final DispatchList dispatchList;

    public DispatchListController() {
        this.distributionNetwork = Company.getInstance().getDistributionNetwork();
        this.cabazStore = Company.getInstance().getCabazStore();
        this.dispatchList = Company.getInstance().getDispatchList();
    }

    public void createDispatchListNearest(int day, int limit) {
        dispatchList.createDispatchListNearest(day, limit);
    }

    public void createDispatchList(int day) {
        dispatchList.createDispatchList(day);
    }

    public Map<Integer, Map<User, ArrayList<DispatchList.Delivery>>> getDispatchList() {
        return dispatchList.getDispatchList();
    }
}
