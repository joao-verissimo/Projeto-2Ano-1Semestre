package domain.model;

public class Firm extends User {

    private boolean isHUB;

    public Firm(String locId, String userId, double lat, double lng) {
        super(locId, userId, lat, lng);
        isHUB = false;
    }

    public boolean isHUB() {
        return isHUB;
    }

    public void setHUB(boolean HUB) {
        isHUB = HUB;
    }

    @Override
    public String toString() {
        return super.toString() + " Is HUB: " + isHUB;
    }

}
