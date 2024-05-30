package imat;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.util.List;

public class VarukorgController {

    ViewSwitcher viewSwitcher = new ViewSwitcher();

    @FXML
    private VBox cartItemsContainer;
    @FXML
    private Label totalLabel;
    @FXML
    private Label totalPrice;
    @FXML
    private ScrollPane cartScrollPane;

    private VaruSidaController parentController;
    private ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();

    public void setParentController(VaruSidaController parentController) {
        this.parentController = parentController;
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

        // Ensure the product has a valid image path
        String imagePath = product.getImageName() != null ? product.getImageName() : "placeholder.png";
        itemImage.setImage(new Image(getClass().getResource("/images/" + imagePath).toExternalForm()));

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

    public void toCheckOutButton() throws IOException {
        viewSwitcher.switchTo("IMatCheckout.fxml");
    }
}
