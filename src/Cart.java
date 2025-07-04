import java.util.ArrayList;

public class Cart {
    private ArrayList cartItems;
    public ArrayList getCartItems() {
        return cartItems;
    }

    public Cart() {
        this.cartItems = new ArrayList<Product>();
    }
    public void addProduct(Product product) {
        this.cartItems.add(product);
    }
}
