package imat;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCart;

import java.io.IOException;

public class Varukorg {

    IMatDataHandler dataHandler = IMatDataHandler.getInstance();
    ShoppingCart currentCart = dataHandler.getShoppingCart();

    Varukorg(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("productCard.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Button decreaseButton = (Button) anchorPane.lookup("#decreaseButton");
            Button increaseButton = (Button) anchorPane.lookup("#increaseButton");

            decreaseButton.setOnAction(e-> {

            });
        } catch (IOException e){
            e.printStackTrace();
        }


    }

}
