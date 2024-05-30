package imat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.Calendar;
import java.util.Date;

public class historyCardController {

    @FXML
    private VBox productsVBox;

    @FXML
    private Label orderNumberLabel;

    @FXML
    private Label dateLabel;

    public static String getOrderDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return String.format("%d-%02d-%02d", year, month, dayOfMonth);
    }

    public void setOrderDetails(Order order) {
        if (order != null) {
            orderNumberLabel.setText("Ordernummer: " + order.getOrderNumber());
            dateLabel.setText("Datum: " + getOrderDate(order.getDate()));
            productsVBox.getChildren().clear();

            for (ShoppingItem item : order.getItems()) {
                HBox productHBox = new HBox();
                productHBox.setSpacing(10.0);
                Label productNameLabel = new Label(item.getProduct().getName());
                Label productQuantityLabel = new Label("x" + item.getAmount());
                HBox.setHgrow(productNameLabel, Priority.ALWAYS);
                productNameLabel.setMaxWidth(Double.MAX_VALUE);
                productHBox.getChildren().addAll(productNameLabel, productQuantityLabel);
                productsVBox.getChildren().add(productHBox);
            }
        }
    }
}
