package imat;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Order;

public class ProfileViewController {

    ViewSwitcher viewSwitcher = new ViewSwitcher();

    @FXML
    private Button loginButton;
    @FXML
    private Button cartButton;
    @FXML
    private Button homeButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private Button nameChangeButton;
    @FXML
    private Label currentNameLabel;
    @FXML
    private TextField emailTextField;
    @FXML
    private Button emailChangeButton;
    @FXML
    private Label currentEmailLabel;
    @FXML
    private TextField phoneTextField;
    @FXML
    private Button phoneChangeButton;
    @FXML
    private Label currentPhoneLabel;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField apartmentNumberTextField;
    @FXML
    private Button addressChangeButton;
    @FXML
    private Label currentAddressLabel;
    @FXML
    private TextField cardHolderTextField;
    @FXML
    private TextField cardNumberTextField;
    @FXML
    private TextField securityCodeTextField;
    @FXML
    private DatePicker expirationDatePicker;
    @FXML
    private Button cardChangeButton;
    @FXML
    private FlowPane orderHistoryFlowPane;

    public FlowPane getOrderHistoryFlowPane() {
        return orderHistoryFlowPane;
    }

    @FXML
    private IMatCheckoutController checkoutController;

    User currentUser;

    @FXML
    public void initialize() {
        // Initialize text fields as uneditable
        makeFieldsUneditable();

        setupEditableTextField(nameTextField, nameChangeButton, this::changeName);
        setupEditableFieldsForAddress(addressChangeButton, addressTextField, postalCodeTextField, cityTextField, apartmentNumberTextField);
        setupEditableTextField(emailTextField, emailChangeButton, this::changeEmail);
        setupEditableTextField(phoneTextField, phoneChangeButton, this::changePhone);
        setupEditableFieldsForCard(cardChangeButton, expirationDatePicker,cardHolderTextField, cardNumberTextField, securityCodeTextField);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("IMatCheckout.fxml"));
            Parent checkoutRoot = loader.load();
            checkoutController = loader.getController();
            checkoutController.setProfileViewController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (checkoutController != null) {
            checkoutController.setProfileViewController(this);
        } else {
            System.out.println("checkoutController is null");
        }

        refreshOrderHistory();
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
        UserSession.setCurrentUser(user);
        System.out.println("Setting current user in ProfileViewController: " + (user != null ? user.getFirstName() + " " + user.getLastName() : "null"));
        if (user != null) {
            updateProfileTextFields();
        } else {
            System.out.println("Current user is null in setCurrentUser.");
        }
    }

    private void updateProfileTextFields() {
        if (currentUser != null) {
            nameTextField.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            emailTextField.setText(currentUser.getEmail());
            phoneTextField.setText(currentUser.getPhone());
            addressTextField.setText(currentUser.getAddress());
            postalCodeTextField.setText(currentUser.getPostalCode());
            cityTextField.setText(currentUser.getCity());
            apartmentNumberTextField.setText(currentUser.getApartmentNumber());
            cardHolderTextField.setText(currentUser.getHoldersName());
            cardNumberTextField.setText(currentUser.getCardNumber());
            securityCodeTextField.setText(String.valueOf(currentUser.getSecurityCode()));
            if (currentUser.getExpirationMonth() > 0 && currentUser.getExpirationYear() > 0) {
                expirationDatePicker.setValue(LocalDate.of(currentUser.getExpirationYear(), currentUser.getExpirationMonth(), 1));
            }
        } else {
            System.out.println("Current user is null in updateProfileTextFields.");
        }
    }

    private void makeFieldsUneditable() {
        makeUneditable(nameTextField);
        makeUneditable(emailTextField);
        makeUneditable(phoneTextField);
        makeUneditable(addressTextField);
        makeUneditable(postalCodeTextField);
        makeUneditable(cityTextField);
        makeUneditable(apartmentNumberTextField);
        makeUneditable(cardHolderTextField);
        makeUneditable(cardNumberTextField);
        makeUneditable(securityCodeTextField);
        expirationDatePicker.setEditable(false);
    }

    private void setupEditableFieldsForAddress(Button changeButton, TextField... textFields) {
        changeButton.setOnMouseClicked(event -> {
            for (TextField textField : textFields) {
                makeEditable(textField);
                textField.setStyle(""); // Clear previous error styles
            }
            textFields[0].requestFocus(); // Set focus to the first text field
        });
        for (TextField textField : textFields) {
            textField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    for (TextField tf : textFields) {
                        makeUneditable(tf);
                    }
                    changeAddress(); // Change address details when Enter is pressed
                }
            });
        }
    }

    private void setupEditableFieldsForCard(Button changeButton, DatePicker datePicker, TextField... textFields) {
        changeButton.setOnMouseClicked(event -> {
            for (TextField textField : textFields) {
                makeEditable(textField);
                textField.setStyle(""); // Clear previous error styles
            }
            datePicker.setEditable(true);
            datePicker.setStyle("");
            textFields[0].requestFocus(); // Set focus to the first text field
        });
        for (TextField textField : textFields) {
            textField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    for (TextField tf : textFields) {
                        makeUneditable(tf);
                    }
                    changeCardInformation(); // Change card details when Enter is pressed
                }
            });
        }
    }

    private void setupEditableTextField(TextField textField, Button changeButton, Runnable changeMethod) {
        changeButton.setOnMouseClicked(event -> {
            makeEditable(textField);
            textField.setStyle(""); // Clear previous error styles
            textField.requestFocus();
        });
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                makeUneditable(textField);
                changeMethod.run();
            }
        });
    }

    @FXML
    private void makeEditable(TextField textField) {
        textField.setEditable(true);
        textField.requestFocus();
    }

    private void makeUneditable(TextField textField) {
        textField.setEditable(false);
    }

    @FXML
    private void changeName() {
        String newName = nameTextField.getText();
        if (newName.trim().isEmpty() || !newName.contains(" ")) {
            displayError(nameTextField, "Please enter both first and last names.");
            return;
        }

        if (currentUser != null) {
            String[] nameParts = splitName(newName);
            currentUser.setFirstName(nameParts[0]);
            currentUser.setLastName(nameParts[1]);
            UserDataHandler.saveUserList();
            clearError(nameTextField);
        }
    }

    private String[] splitName(String fullName) {
        String[] nameParts = fullName.trim().split("\\s+", 2);
        if (nameParts.length < 2) {
            displayError(nameTextField, "Please enter both first and last name separated by a space.");
            return new String[]{"", ""};
        }
        return nameParts;
    }

    @FXML
    private void changeEmail() {
        String newEmail = emailTextField.getText();
        if (currentUser != null) {
            currentUser.setEmail(newEmail);
            UserDataHandler.saveUserList();
        }
    }

    @FXML
    private void changePhone() {
        String newPhone = phoneTextField.getText();
        if (currentUser != null) {
            currentUser.setPhone(newPhone);
            UserDataHandler.saveUserList();
        }
    }

    @FXML
    private void changeAddress() {
        if (currentUser != null) {
            currentUser.setAddress(addressTextField.getText());
            currentUser.setPostalCode(postalCodeTextField.getText());
            currentUser.setCity(cityTextField.getText());
            currentUser.setApartmentNumber(apartmentNumberTextField.getText());
            UserDataHandler.saveUserList();
        }
    }

    @FXML
    private void changeCardInformation() {
        LocalDate newExpirationDate = expirationDatePicker.getValue();
        if (currentUser != null) {
            currentUser.setHoldersName(cardHolderTextField.getText());
            currentUser.setCardNumber(cardNumberTextField.getText());
            currentUser.setSecurityCode(Integer.parseInt(securityCodeTextField.getText()));
            if(newExpirationDate != null){
                currentUser.setExpirationMonth(newExpirationDate.getMonthValue());
                currentUser.setExpirationYear(newExpirationDate.getYear());
            }
            UserDataHandler.saveUserList();
        }
    }

    private void displayError(TextField textField, String message) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        textField.setPromptText(message);
    }

    private void clearError(TextField textField) {
        textField.setStyle("");
        textField.setPromptText("");
    }

    @FXML
    private void showHomePageView() throws IOException {
        ViewSwitcher.switchTo("/imat/VaruSida.fxml");
    }

    public void refreshOrderHistory() {
        IMatDataHandler dataHandler = IMatDataHandler.getInstance();
        List<Order> orders = dataHandler.getOrders();
        loadOrderHistory(orders);
    }

    public void loadOrderHistory(List<Order> orders) {
        orderHistoryFlowPane.getChildren().clear();
        for (Order order : orders) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("history_card.fxml"));
                AnchorPane historyCard = loader.load();
                historyCardController controller = loader.getController();
                controller.setOrderDetails(order);
                orderHistoryFlowPane.getChildren().add(historyCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
