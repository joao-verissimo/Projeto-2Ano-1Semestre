package domain.model;


import java.util.Arrays;
import java.util.Map;

public class Cabaz {
    private User user;
    private int day;
    private Map<Product, Float> products;

    public Cabaz(User user, int day, Map<Product, Float> products) {
        this.user = user;
        this.day = day;
        this.products = products;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Map<Product, Float> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Float> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cabaz{" +
                "id=" + user +
                ", day='" + day + '\'' +
                ", products=" + Arrays.toString(products.keySet().toArray(new Product[0])) +
                '}';
    }

}
