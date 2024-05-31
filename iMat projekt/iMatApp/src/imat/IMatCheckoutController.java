package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class IMatCheckoutController {

    private ProfileViewController profileViewController;

    ViewSwitcher viewSwitcher = new ViewSwitcher();
    VarukorgController varukorgController = new VarukorgController();
    IMatDataHandlerWrapper iMatDataHandlerWrapper = new IMatDataHandlerWrapper();

    @FXML
    private ScrollPane cartScrollPane;

    private VaruSidaController parentController;
    private ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();

    public void setParentController(VaruSidaController parentController) {
        this.parentController = parentController;
    }

    public void setProfileViewController(ProfileViewController profileViewController) {
        this.profileViewController = profileViewController;
    }

    @FXML
    public void initialize() {
        refreshCartItems();
    }

    public void refreshCartItems() {
        cartItemsContainer.getChildren().clear();
        List<ShoppingItem> items = shoppingCart.getItems();
        double total = 0;

        for (ShoppingItem item : items) {
            HBox cartItemBox = createCartItemBox(item);
            cartItemsContainer.getChildren().add(cartItemBox);
            total += item.getTotal();
        }

        totalLabel.setText("Totalt (" + items.size() + " varor)");
        totalPrice.setText(String.format("%.2f kr", total));
    }

    private HBox createCartItemBox(ShoppingItem item) {
        HBox cartItemBox = new HBox();
        cartItemBox.setSpacing(6.67);
        cartItemBox.getStyleClass().add("cart-item");
        cartItemBox.setMinHeight(60); // Increase the minimum height of the HBox

        ImageView itemImage = new ImageView();
        itemImage.setFitHeight(80.0); // Increase the height of the ImageView
        itemImage.setFitWidth(90.0); // Increase the width of the ImageView
        itemImage.setPreserveRatio(true);

        // Get the product from the ShoppingItem
        Product product = item.getProduct();

        // Directly set the image without checking for file existence
        String imagePath = "/images/" + product.getImageName();
        itemImage.setImage(new Image(imagePath));

        VBox itemDetails = new VBox();
        itemDetails.setSpacing(3.33);
        itemDetails.getStyleClass().add("cart-item-details");

        Label itemName = new Label(product.getName());
        itemName.getStyleClass().add("product-label");

        Label itemPrice = new Label(String.format("%.2f kr", item.getTotal()));
        itemPrice.setFont(new javafx.scene.text.Font(13.33));

        itemDetails.getChildren().addAll(itemName, itemPrice);

        HBox quantityControl = new HBox();
        quantityControl.setSpacing(3.33);

        Button subtractQuantityCartButton = new Button("-");
        subtractQuantityCartButton.getStyleClass().add("adjust-button");
        subtractQuantityCartButton.setOnAction(e -> decreaseQuantity(item));

        Label itemQuantity = new Label(String.valueOf(item.getAmount()));
        itemQuantity.getStyleClass().add("quantity-label");

        Button addQuantityCartButton = new Button("+");
        addQuantityCartButton.getStyleClass().add("adjust-button");
        addQuantityCartButton.setOnAction(e -> increaseQuantity(item));

        quantityControl.getChildren().addAll(subtractQuantityCartButton, itemQuantity, addQuantityCartButton);

        // Set alignment and padding for quantityControl
        quantityControl.setAlignment(Pos.CENTER_RIGHT);
        HBox.setMargin(quantityControl, new Insets(0, 10, 0, 0));

        cartItemBox.getChildren().addAll(itemImage, itemDetails, quantityControl);

        return cartItemBox;
    }

    private void increaseQuantity(ShoppingItem item) {
        item.setAmount(item.getAmount() + 1);
        shoppingCart.fireShoppingCartChanged(item, true); // Notify listeners of change
        refreshCartItems();
    }

    private void decreaseQuantity(ShoppingItem item) {
        if (item.getAmount() > 1) {
            item.setAmount(item.getAmount() - 1);
            shoppingCart.fireShoppingCartChanged(item, true); // Notify listeners of change
        } else {
            shoppingCart.removeItem(item);
        }
        refreshCartItems();
    }

    @FXML
    private void handleCloseCart() {
        parentController.handleCloseCart();  // Use parent controller to close cart
    }

    @FXML
    private VBox cartItemsContainer;

    @FXML
    private Label totalLabel;

    @FXML
    private Label totalPrice;

    @FXML
    private Label LeveransTid;

    @FXML
    private AnchorPane Checkout1;

    @FXML
    private AnchorPane Checkout2;

    @FXML
    private AnchorPane Checkout3;

    @FXML
    private AnchorPane Checkout4;

    @FXML
    private AnchorPane Checkout5;


    @FXML
    private void showHomePageView() throws IOException {
        ViewSwitcher.switchTo("/imat/VaruSida.fxml");
    }

    public void openCheckOut1() {
        Checkout1.toFront();
    }

    public void openCheckOut2() {
        Checkout2.toFront();
    }

    public void openCheckOut3() { Checkout3.toFront(); }

    public void openCheckOut4() {
        Checkout4.toFront();
    }

    public void openCheckOut5() {
        Checkout5.toFront();
    }

    public void confirmOrder() {
        IMatDataHandler dataHandler = IMatDataHandler.getInstance();
        Order newOrder = dataHandler.placeOrder();

        // Notify ProfileViewController to refresh the order history
        FXMLLoader profileLoader = new FXMLLoader(getClass().getResource("profile_page.fxml"));
        try {
            Parent profileRoot = profileLoader.load();
            ProfileViewController profileController = profileLoader.getController();
            profileController.loadOrderHistory(dataHandler.getOrders()); // Pass the orders list here
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (profileViewController != null) {
            profileViewController.refreshOrderHistory();
        }
        Checkout5.toFront();
    }

    public void SetTimeMonday8() {
        LeveransTid.setText("Måndag 2024-05-27 8:00");
    }

    public void SetTimeMonday12() {
        LeveransTid.setText("Måndag 2024-05-27 12:00");
    }

    public void SetTimeMonday16() {
        LeveransTid.setText("Måndag 2024-05-27 16:00");
    }

    public void SetTimeWednesday8() {
        LeveransTid.setText("Onsdag 2024-05-29 8:00");
    }

    public void SetTimeWednesday12() {
        LeveransTid.setText("Onsdag 2024-05-29 12:00");
    }

    public void SetTimeWednesday16() {
        LeveransTid.setText("Onsdag 2024-05-29 16:00");
    }

    public void SetTimeFridayy8() {
        LeveransTid.setText("Fredag 2024-05-31 8:00");
    }

    public void SetTimeFriday12() {
        LeveransTid.setText("Fredag 2024-05-31 12:00");
    }

    public void SetTimeFriday16() {
        LeveransTid.setText("Fredag 2024-05-31 16:00");
    }
}
