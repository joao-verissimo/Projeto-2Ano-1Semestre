package domain.model;

public class User {
    private final String locID, userID;
    private final double lat, lng;

    protected User(String locID, String userID, double lat, double lng) {
        this.locID = locID;
        this.userID = userID;
        this.lat = lat;
        this.lng = lng;
    }

    public String getLocID() {
        return locID;
    }

    public String getUserID() {
        return userID;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userID.equals(user.userID);
    }

    @Override
    public int hashCode() {
        return userID.hashCode();
    }

    @Override
    public String toString() {
        return "Loc ID: " + locID + ' ' +
                "User ID: " + userID;
    }

}
