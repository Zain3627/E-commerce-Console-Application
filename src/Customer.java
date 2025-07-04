import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Customer {
    private String name;
    private double balance;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.cart = new Cart();
    }

    private Cart cart;
    public Cart getCart() {
        return cart;
    }

    public void addItemToCart(ArrayList<Product> products) {
        System.out.println("Here are the available products");
        for(int i=0; i<products.size(); i++){
            System.out.println(products.get(i).toString());
        }
        System.out.println("Please enter the name of the product you want to add to the cart:");
        Scanner scanner = new Scanner(System.in);
        String productName = scanner.nextLine();

        Product selectedProduct = null;
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                selectedProduct = product;
                break;
            }
        }

        if (selectedProduct == null) {
            throw new NullPointerException("Product not found.");
        }
        if(selectedProduct instanceof CanExpireProduct){
            CanExpireProduct expireProduct = (CanExpireProduct) selectedProduct;
            if(expireProduct.getExpirationDate().before(new Date(System.currentTimeMillis()))){
                throw new ImproperInputException("Can not add item to cart because it has expired");
            }
        }

        System.out.println(selectedProduct.toString());
        System.out.println("Your current balance is : " + this.getBalance());
        System.out.println("Please enter the quantity you want to add:");
        int quantity = scanner.nextInt();
        if (quantity > selectedProduct.getQuantity()) {
            throw new ImproperInputException("Insufficient quantity available.");
        }
        if (quantity <= 0) {
            throw new NegativeValueException("Please enter a positive quantity.");
        }
        if(quantity* selectedProduct.getPrice() > this.getBalance()) {
            throw new ImproperInputException("Insufficient balance to add this item to the cart.");
        }
        selectedProduct.setQuantity(selectedProduct.getQuantity() - quantity);
        this.setBalance(this.getBalance() - (selectedProduct.getPrice() * quantity));

        if(selectedProduct instanceof CanBeShippedProduct){
            CanBeShippedProduct shipped = (CanBeShippedProduct) selectedProduct;
            this.getCart().addProduct(new CanBeShippedProduct(selectedProduct,quantity,shipped.getWeight()));
        }
        else if (selectedProduct instanceof CanExpireShipped){
            CanExpireShipped expireShipped = (CanExpireShipped) selectedProduct;
            this.getCart().addProduct(new CanExpireShipped(selectedProduct, quantity, expireShipped.getExpirationDate(), expireShipped.getWeight()));
        }
        else if (selectedProduct instanceof CanExpireProduct){
            CanExpireProduct expireProduct = (CanExpireProduct) selectedProduct;
            this.getCart().addProduct(new CanExpireProduct(selectedProduct, quantity, expireProduct.getExpirationDate()));
        }
        else {
            this.getCart().addProduct(new Product(selectedProduct, quantity));
        }
        System.out.println("Item added to cart successfully!");
    }


    public void removeItemCart(ArrayList<Product> products){
        if(this.getCart().getCartItems().isEmpty()){
            throw new NullPointerException("You can not edit your cart because it is still empty");
        }
        System.out.println("These are the items in your cart:");
        for(int i=0; i<this.getCart().getCartItems().size(); i++){
            System.out.println(this.getCart().getCartItems().get(i).toString());
        }

        System.out.println("Please enter the name of the product you want to remove:");
        Scanner scanner = new Scanner(System.in);
        String productName = scanner.nextLine();

        Product selectedProduct = null;
        for (Object object : this.getCart().getCartItems()) {
            Product product = (Product) object;
            if (product.getName().equalsIgnoreCase(productName)) {
                selectedProduct = product;
                break;
            }
        }

        if (selectedProduct == null) {
            throw new NullPointerException("Product not found in your cart.");
        }
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                product.setQuantity(product.getQuantity() + selectedProduct.getQuantity());
                this.setBalance(this.getBalance() + selectedProduct.getQuantity()* selectedProduct.getPrice());
                this.getCart().getCartItems().remove(selectedProduct);
                System.out.println("Item removed from cart successfully!");
                break;
            }
        }
    }

    public void checkOut (){
        System.out.println("Checking out your cart...");
        if(this.getCart().getCartItems().isEmpty()){
            throw new NullPointerException("You can not checkout because your cart is empty");
        }
        System.out.println("These are the items in your cart:");
        for(int i=0; i<this.getCart().getCartItems().size(); i++){
            System.out.println(this.getCart().getCartItems().get(i).toString());
        }

        ArrayList<Shippable> shippableItems = new ArrayList<Shippable>();
        for(int i=0; i<this.getCart().getCartItems().size(); i++){
            if(this.getCart().getCartItems().get(i) instanceof Shippable){
                shippableItems.add((Shippable)this.getCart().getCartItems().get(i));
            }
        }
        double shippingCost = shippableService(shippableItems);

        System.out.println("** Checkout Receipt **");
        double subTotal = 0;
        for(Object object : this.getCart().getCartItems()) {
            Product product = (Product) object;
            System.out.println(product.getQuantity() + "x " +
                                product.getName() + "\t" +
                                product.getPrice()* product.getQuantity());
            subTotal += product.getPrice()* product.getQuantity();
        }
        System.out.println("Subtotal " + subTotal);
        System.out.println("Shipping " + shippingCost);
        System.out.println("Total " + (subTotal + shippingCost));
        System.out.println("Your current balance after purchase is : " + this.getBalance());
    }

    public static double shippableService (ArrayList<Shippable> shippableItems){
        System.out.println("** Shipment Notice **");
        if(shippableItems.isEmpty()){
            System.out.println("There are no items that require shipping in your cart");
            return 0.0;
        }
        double totalWeight = 0;
        for(int i=0; i<shippableItems.size(); i++){
            Shippable product = shippableItems.get(i);
            System.out.println(product.getQuantity() + "x " +
                                product.getName() + "\t" +
                    Math.round(product.getWeight() * product.getQuantity() * 1000) / 1000.0 + "kg");
            totalWeight += Math.round(product.getWeight() * product.getQuantity() * 1000) / 1000.0;
        }
        System.out.println("Total package weight is " + totalWeight);
        return  Math.ceil(totalWeight) * 5.0;
    }

}
