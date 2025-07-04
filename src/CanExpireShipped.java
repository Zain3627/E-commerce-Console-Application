import java.sql.Date;

public class CanExpireShipped extends CanExpireProduct implements Shippable {
    private double weight;

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public CanExpireShipped(String name, double price, int quantity, Date expirationDate, double weight) {
        super(name, price, quantity, expirationDate);
        this.weight = weight;
    }
    public CanExpireShipped(Product product, int quantity, Date expirationDate, double weight) {
        super(product, quantity, expirationDate);
        this.weight = weight;
    }

    @Override
    public String toString() {
        return super.toString() +
                "weight per unit = " + getWeight() +"\n";
    }
}
