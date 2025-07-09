# E-Commerce System
This is a simple Java-based console application that simulates an e-commerce system. It allows users to manage products, add items to a shopping cart, remove items, and perform checkout operations.  
## Features
* Add products to the system with attributes like name, price, quantity, expiration date, and weight.
* Manage a shopping cart:  
1- Add items to the cart.  
2- Remove items from the cart.
3- Check for product expiration and shipping requirements.
* Checkout process with shipping cost calculation based on total weight.
* Input validation and error handling for user inputs.

## How to Run
1- Clone the repository to your local machine.
2- Open the project in IntelliJ IDEA.
3- Run the Main class located in src/Main.java.
4- Follow the console prompts to interact with the system.  

## Project Structure
* src/Main.java: Entry point of the application.
* src/Customer.java: Handles customer details and cart operations.
* src/Cart.java: Manages cart items.
* src/Product.java and its subclasses: Represent different types of products.
* Custom exceptions for input validation.
