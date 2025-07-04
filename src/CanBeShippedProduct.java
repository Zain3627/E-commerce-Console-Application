public class CanBeShippedProduct extends Product implements Shippable{
    private double weight;

    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public CanBeShippedProduct(String name, double price, int quantity, double weight) {
        super(name, price, quantity);
        this.weight = weight;
    }
    public CanBeShippedProduct(Product product, int quantity, double weight) {
        super(product, quantity);
        this.weight = weight;
    }

    @Override
    public String toString() {
        return super.toString() +
                "weight per unit = " + getWeight() +"\n";
    }
}
