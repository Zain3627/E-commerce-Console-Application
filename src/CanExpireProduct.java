import java.sql.Date;

public class CanExpireProduct extends Product{

    private Date expirationDate;

    public Date getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public CanExpireProduct(String name, double price, int quantity, Date expirationDate) {
        super(name, price, quantity);
        this.expirationDate = expirationDate;
    }
    public CanExpireProduct(Product product, int quantity, Date expirationDate) {
        super(product, quantity);
        this.expirationDate = expirationDate;
    }
    @Override
    public String toString() {
        return super.toString() +
                "expirationDate : '" + getExpirationDate() + "'\n";
    }
}
