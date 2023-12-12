package domain.model;

public class Product {
    private final String productID;

    public Product(String productID) {
        this.productID = productID;
    }

    public String getProductID() {
        return productID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return productID.equals(product.productID);
    }

    @Override
    public int hashCode() {
        return productID.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Product ID: ");
        sb.append(productID);
        return sb.toString();
    }
}
