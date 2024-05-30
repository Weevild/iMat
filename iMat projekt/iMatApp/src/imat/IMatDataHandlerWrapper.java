package imat;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IMatDataHandlerWrapper {

    // Categories
    private final Map<String, List<Product>> productItemMap = new HashMap<>();

    private static IMatDataHandlerWrapper instance = null;

    private final IMatDataHandler iMatDataHandler = IMatDataHandler.getInstance();

    IMatDataHandlerWrapper() {
        // No instantiation
    }


    // Singleton pattern
    public static IMatDataHandlerWrapper getInstance() {
        if (instance == null) {
            instance = new IMatDataHandlerWrapper();
        }
        return instance;
    }

    // Saves all information when exiting application
    public void shutDown() {
        iMatDataHandler.shutDown();
    }

    // Returns customer object: Setting / getting customer information
    public Customer getCustomer() {
        return iMatDataHandler.getCustomer();
    }

    // Returns true if its the first run.
    public boolean isFirstRun() {
        return iMatDataHandler.isFirstRun();
    }

    public void resetFirstRun() {
        iMatDataHandler.resetFirstRun();
    }

    // Resets everything to initial state
    public void reset() {
        iMatDataHandler.reset();
    }

    // Returns true if all information about the customer has been given.
    public boolean isCustomerComplete() {
        return iMatDataHandler.isCustomerComplete();
    }

    private boolean isLoggedIn;
    public void setIsLoogedIn(boolean isIn){
        isLoggedIn = isIn;
    }
    public boolean getIsLoggedIn(){
        return isLoggedIn;
    }

    public CreditCard getCreditCard() {
        return iMatDataHandler.getCreditCard();
    }

    public ShoppingCart getShoppingCart() {
        return iMatDataHandler.getShoppingCart();
    }

    /*
    Creates an order from the current contents of the shoppingcart.
     - Clears the shopping cart and adds the order to "orders" list
    */
    public Order placeOrder() {
        return iMatDataHandler.placeOrder();
    }

    // Returns a list of past orders (order history)
    public List<Order> getOrders() {
        return iMatDataHandler.getOrders();
    }

    // Returns the product with the given idNbr.
    public Product getProduct(int idNbr) {
        return iMatDataHandler.getProduct(idNbr);
    }

    // Returns all products
    public List<Product> getProducts() {
        return iMatDataHandler.getProducts();
    }

    // Returns products based on a category
    public List<Product> getProducts(ProductCategory pc) {
        return iMatDataHandler.getProducts(pc);
    }

    // Search for products
    public List<Product> findProducts(String s) {
        return iMatDataHandler.findProducts(s);
    }

    public void addProduct(Product p) {
        iMatDataHandler.addProduct(p);
    }

    public void removeProduct(Product p) {
        iMatDataHandler.removeProduct(p);
    }

}