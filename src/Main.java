import java.sql.Date;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("\n------------------------------Welcome to the e-commerce system------------------------------");

        ArrayList products = new ArrayList();
        Customer customer = null;
        //first step i want to add the available products to the system
        System.out.println("\n\nYou are now entering the products available in the system"  + "\n" +
                            "-------------------------------------------------------------" + "\n");
        int i = 1;
        String isFinish = "N";
        boolean isError = true;
        do {

            try {
                System.out.println("You are entering the product number " + i + " :");
                System.out.println("-------------------------------------------------------------");

                Scanner scanner = new Scanner(System.in);

                System.out.println("Please enter the product name:");
                String name = scanner.nextLine();
                if(name.isEmpty()) {
                    throw new ImproperInputException("Please enter a valid product name");
                }
                System.out.println("Please enter the product price:");
                double price = scanner.nextDouble();
                if(price <= 0) {
                    throw new NegativeValueException("Please enter a positive value");
                }
                System.out.println("Please enter the product quantity:");
                int quantity = scanner.nextInt();
                if(quantity < 0) {
                    throw new NegativeValueException("Please do not enter a negative value");
                }
                System.out.println("Does this product have an expiry date? (Y/N)");
                String hasExpiry = scanner.next();
                if(!hasExpiry.equalsIgnoreCase("Y") && !hasExpiry.equalsIgnoreCase("N")) {
                    throw new ImproperInputException("Please enter Y or N");
                }
                Date expirationDate = null;
                if(hasExpiry.equalsIgnoreCase("Y")){
                    System.out.println("Please enter the product expiration date (YYYY-MM-DD):");
                    String dateInput = scanner.next();
                    expirationDate = Date.valueOf(dateInput);

                }

                System.out.println("Does this product require shipping? (Y/N)");
                String reqShip = scanner.next();
                if(!reqShip.equalsIgnoreCase("Y") && !reqShip.equalsIgnoreCase("N")) {
                    throw new ImproperInputException("Please enter Y or N");
                }
                double weight = 0;
                if(reqShip.equalsIgnoreCase("Y")){
                    System.out.println("Please enter the product weight in Kilo grams:");
                    weight = scanner.nextDouble();
                    if(weight <= 0) {
                        throw new NegativeValueException("Please enter a positive value");
                    }
                }

                if(hasExpiry.equalsIgnoreCase("Y") && reqShip.equalsIgnoreCase("Y")){
                    products.add(new CanExpireShipped(name, price, quantity, expirationDate, weight));
                }
                else if (hasExpiry.equalsIgnoreCase("Y")){
                    products.add(new CanExpireProduct(name, price, quantity, expirationDate));
                }
                else if (reqShip.equalsIgnoreCase("Y")){
                    products.add(new CanBeShippedProduct(name, price, quantity, weight));
                }
                else {
                    products.add(new Product(name, price, quantity));
                }

                System.out.println("Product added successfully!");
                System.out.println(products.get(i-1).toString());
                System.out.println("Do you want to add more products? (Y/N)");
                isFinish = scanner.next();
                if(!isFinish.equalsIgnoreCase("Y") && !isFinish.equalsIgnoreCase("N")) {
                    throw new ImproperInputException("Please enter Y or N");
                }
                i++;
                isError = false;
            } catch (ImproperInputException e){
              System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Please enter values of proper format");
            } catch (NegativeValueException e) {
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }

        }   while(isFinish.equalsIgnoreCase("Y") || isError);


        System.out.println("\n\nYou have finished entering the products available in the system"  + "\n" +
                            "-------------------------------------------------------------" + "\n");

        //second step I want to define customer
        isError = true;
        do{
            try {
                System.out.println("Please enter the name of the customer:");
                Scanner scanner = new Scanner(System.in);
                String name = scanner.nextLine();
                System.out.println("Please enter the customer balance:");
                double balance = scanner.nextDouble();
                if(balance < 0) {
                    throw new NegativeValueException("Please do not enter a negative value");
                }
                customer = new Customer(name, balance);
                isError = false;
            } catch (NegativeValueException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Please enter values of proper format");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }   while(isError);

        //make customer buy products
        System.out.println("\n------------------------------Welcome " + customer.getName() + "------------------------------/n");
        int whatAction=0;
        do{

            try {
                System.out.println("Please choose the action you want. Choose a number from the following list.\n" +
                        "1 Add item to the cart\n" +
                        "2 Remove item from the cart\n" +
                        "3 Checkout ");
                Scanner scanner = new Scanner(System.in);
                whatAction = scanner.nextInt();
                if(whatAction < 1 || whatAction > 3) {
                    throw new ImproperInputException("Please enter a valid number between 1 and 4");
                }
                switch (whatAction) {
                    case 1:
                        customer.addItemToCart(products);
                        break;
                    case 2:
                        customer.removeItemCart(products);
                        break;
                    case 3:
                        customer.checkOut();
                        System.exit(0);
                }
            } catch (ImproperInputException e){
                System.out.println(e.getMessage());
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("Please enter values of proper format");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }   while(true);

    }



}

