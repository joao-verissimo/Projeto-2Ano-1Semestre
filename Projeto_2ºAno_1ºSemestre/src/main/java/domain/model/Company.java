package domain.model;

import domain.store.CabazStore;
import domain.store.DispatchList;
import domain.store.DistributionNetwork;

public class Company {

    private final DistributionNetwork distributionNetwork;
    private final DispatchList dispatchList;
    private final CabazStore cabazStore;

    private Company() {
        this.distributionNetwork = new DistributionNetwork();
        this.cabazStore = new CabazStore();
        this.dispatchList = new DispatchList(cabazStore, distributionNetwork);
    }

    public DistributionNetwork getDistributionNetwork() {
        return distributionNetwork;
    }

    public DispatchList getDispatchList() {
        return dispatchList;
    }

    public CabazStore getCabazStore() {
        return cabazStore;
    }

    private static Company singleton = null;

    public static Company getInstance() {
        if (singleton == null) {
            synchronized (Company.class) {
                singleton = new Company();
            }
        }
        return singleton;
    }
}
