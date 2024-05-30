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
    registerController registerController = new registerController();

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
    private Button addressChangeButton;
    @FXML
    private Label currentAddressLabel;
    @FXML
    private TextField postalCodeTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField apartmentNumberTextField;
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
        setupEditableTextField(phoneTextField, phoneChangeButton, this::changePhone);
        setupEditableTextField(emailTextField, emailChangeButton, this::changeEmail);
        setupEditableTextField(addressTextField, addressChangeButton, this::changeAddress);
        setupEditableTextField(postalCodeTextField, addressChangeButton, this::changePostalCode);
        setupEditableTextField(cityTextField, addressChangeButton, this::changeCity);
        setupEditableTextField(apartmentNumberTextField, addressChangeButton, this::changeApartmentNumber);
        setupEditableTextField(cardHolderTextField, cardChangeButton, this::changeCardHolder);
        setupEditableTextField(cardNumberTextField, cardChangeButton, this::changeCardNumber);
        setupEditableTextField(securityCodeTextField, cardChangeButton, this::changeSecurityCode);
        setupEditableDatePicker(expirationDatePicker, cardChangeButton, this::changeExpirationDate);

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
            updateProfileLabels();
        } else {
            System.out.println("Current user is null in setCurrentUser.");
        }
    }

    private void updateProfileLabels() {
        if (currentUser != null) {
            System.out.println("Updating profile labels for user: " + currentUser.getFirstName() + " " + currentUser.getLastName());
            currentNameLabel.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
            currentEmailLabel.setText(currentUser.getEmail());
            currentPhoneLabel.setText(currentUser.getPhone());
            currentAddressLabel.setText(currentUser.getAddress() + " " + currentUser.getPostalCode() + " " + currentUser.getCity() + " " + currentUser.getApartmentNumber());
            cardHolderTextField.setText(currentUser.getHoldersName());
            cardNumberTextField.setText(currentUser.getCardNumber());
            securityCodeTextField.setText(String.valueOf(currentUser.getSecurityCode()));
        } else {
            System.out.println("Current user is null in updateProfileLabels.");
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

    private void setupEditableTextField(TextField textField, Button changeButton, Runnable changeMethod) {
        changeButton.setOnMouseClicked(event -> {
            makeEditable(textField);
            textField.setStyle(""); // Clear previous error styles
            textField.requestFocus();
        });
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (validateTextField(textField)) {
                    makeUneditable(textField);
                    changeMethod.run();
                    updateProfileLabels(); // Update labels immediately after changing
                }
            }
        });
    }

    private boolean validateTextField(TextField textField) {
        if (textField == emailTextField && !textField.getText().contains("@")) {
            displayError(textField, "Please enter a valid email.");
            return false;
        }
        return true;
    }

    private void setupEditableDatePicker(DatePicker datePicker, Button changeButton, Runnable changeMethod) {
        changeButton.setOnMouseClicked(event -> {
            datePicker.setEditable(true);
            datePicker.setStyle(""); // Clear previous error styles
            datePicker.requestFocus();
        });

        datePicker.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                if (validateDatePicker(datePicker)) {
                    datePicker.setEditable(false);
                    changeMethod.run();
                    updateProfileLabels(); // Update labels immediately after changing
                }
            }
        });
    }

    private boolean validateDatePicker(DatePicker datePicker) {
        if (datePicker.getValue() == null) {
            displayError(datePicker, "Expiration date cannot be empty.");
            return false;
        }
        return true;
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

    @FXML
    private void changeEmail() {
        String newEmail = emailTextField.getText();
        if (newEmail.trim().isEmpty() || !newEmail.contains("@")) {
            displayError(emailTextField, "Please enter a valid email.");
            return;
        }

        if (currentUser != null) {
            currentUser.setEmail(newEmail);
            UserDataHandler.saveUserList();
            clearError(emailTextField);
            System.out.println("Email changed to: " + newEmail);
        }
    }

    @FXML
    private void changePhone() {
        String newPhone = phoneTextField.getText();
        if (newPhone.trim().isEmpty()) {
            displayError(phoneTextField, "Phone number cannot be empty.");
            return;
        }

        if (currentUser != null) {
            currentUser.setPhone(newPhone);
            UserDataHandler.saveUserList();
            clearError(phoneTextField);
        }
    }

    @FXML
    private void changeAddress() {
        String newAddress = addressTextField.getText();
        if (newAddress.trim().isEmpty()) {
            displayError(addressTextField, "Address cannot be empty.");
            return;
        }

        if (currentUser != null) {
            currentUser.setAddress(newAddress);
            UserDataHandler.saveUserList();
            clearError(addressTextField);
        }
    }

    @FXML
    private void changePostalCode() {
        String newPostalCode = postalCodeTextField.getText();
        if (newPostalCode.trim().isEmpty()) {
            displayError(postalCodeTextField, "Postal code cannot be empty.");
            return;
        }

        if (currentUser != null) {
            currentUser.setPostalCode(newPostalCode);
            UserDataHandler.saveUserList();
            clearError(postalCodeTextField);
        }
    }

    @FXML
    private void changeCity() {
        String newCity = cityTextField.getText();
        if (newCity.trim().isEmpty()) {
            displayError(cityTextField, "City cannot be empty.");
            return;
        }

        if (currentUser != null) {
            currentUser.setCity(newCity);
            UserDataHandler.saveUserList();
            clearError(cityTextField);
        }
    }

    @FXML
    private void changeApartmentNumber() {
        String newApartmentNumber = apartmentNumberTextField.getText();
        if (newApartmentNumber.trim().isEmpty()) {
            displayError(apartmentNumberTextField, "Apartment number cannot be empty.");
            return;
        }

        if (currentUser != null) {
            currentUser.setApartmentNumber(newApartmentNumber);
            UserDataHandler.saveUserList();
            clearError(apartmentNumberTextField);
        }
    }

    @FXML
    private void changeCardHolder() {
        String newCardHolder = cardHolderTextField.getText();
        if (newCardHolder.trim().isEmpty()) {
            displayError(cardHolderTextField, "Card holder name cannot be empty.");
            return;
        }

        if (currentUser != null) {
            currentUser.setHoldersName(newCardHolder);
            UserDataHandler.saveUserList();
            clearError(cardHolderTextField);
        }
    }

    @FXML
    private void changeCardNumber() {
        String newCardNumber = cardNumberTextField.getText();
        if (newCardNumber.trim().isEmpty()) {
            displayError(cardNumberTextField, "Card number cannot be empty.");
            return;
        }

        if (currentUser != null) {
            currentUser.setCardNumber(newCardNumber);
            UserDataHandler.saveUserList();
            clearError(cardNumberTextField);
        }
    }

    @FXML
    private void changeSecurityCode() {
        String newSecurityCodeString = securityCodeTextField.getText();
        if (newSecurityCodeString.trim().isEmpty() || !newSecurityCodeString.matches("\\d+")) {
            displayError(securityCodeTextField, "Security code must be numeric.");
            return;
        }

        int newSecurityCode = Integer.parseInt(newSecurityCodeString);
        if (currentUser != null) {
            currentUser.setSecurityCode(newSecurityCode);
            UserDataHandler.saveUserList();
            clearError(securityCodeTextField);
        }
    }

    @FXML
    private void changeExpirationDate() {
        LocalDate newExpirationDate = expirationDatePicker.getValue();
        if (newExpirationDate == null) {
            displayError(expirationDatePicker, "Expiration date cannot be empty.");
            return;
        }

        if (currentUser != null) {
            currentUser.setExpirationDate(newExpirationDate);
            UserDataHandler.saveUserList();
            clearError(expirationDatePicker);
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

    private void displayError(TextField textField, String message) {
        textField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        textField.setPromptText(message);
    }

    private void clearError(TextField textField) {
        textField.setStyle("");
        textField.setPromptText("");
    }

    private void displayError(DatePicker datePicker, String message) {
        datePicker.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        datePicker.setPromptText(message);
    }

    private void clearError(DatePicker datePicker) {
        datePicker.setStyle("");
        datePicker.setPromptText("");
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
