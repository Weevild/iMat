package imat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.ShoppingCart;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.Objects;

public class ProductCardController {

    @FXML
    private ImageView productImage;
    @FXML
    private Label productName;
    @FXML
    private Label productPrice;
    @FXML
    private Label quantityLabel;
    @FXML
    private Button decreaseButton;
    @FXML
    private Button increaseButton;
    @FXML
    private Button buyProductButton;
    @FXML
    private AnchorPane ProductCard;

    private Produkter product;
    private int quantity = 1;
    private ShoppingCart shoppingCart = IMatDataHandler.getInstance().getShoppingCart();
    private VarukorgController varukorgController;

    public void setProduct(Produkter product) {
        this.product = product;
        String imagePath = "/images/" + product.getImagePath();
        try {
            Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
            productImage.setImage(image);
        } catch (Exception e) {
            System.err.println("Image not found: " + imagePath);
           
            productImage.setImage(new Image("/images/" + product.getImagePath()));
        }
        productName.setText(product.getName());
        productPrice.setText(product.getPrice() + " " + product.getUnit());
        quantityLabel.setText("1");
        decreaseButton.setOnAction(e -> decreaseQuantity());
        increaseButton.setOnAction(e -> increaseQuantity());
        buyProductButton.setOnAction(e -> addToCart());
    }

    public void setVarukorgController(VarukorgController varukorgController) {
        this.varukorgController = varukorgController;
    }

    private void decreaseQuantity() {
        if (quantity > 0) {
            quantity--;
            quantityLabel.setText(String.valueOf(quantity));
        }
    }

    private void increaseQuantity() {
        quantity++;
        quantityLabel.setText(String.valueOf(quantity));
    }

    private void addToCart() {
        if (quantity > 0) {
            ShoppingItem item = new ShoppingItem(product.toProduct(), quantity);
            shoppingCart.addItem(item);
            quantity = 1;
            quantityLabel.setText("1");
            if (varukorgController != null) {
                varukorgController.refreshCartItems();
            }
        }
    }
}
