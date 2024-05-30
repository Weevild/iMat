package imat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class registerController {

    @FXML private TextField NamnText;
    @FXML private TextField EfternamnText;
    @FXML private TextField TelefonText;
    @FXML private TextField EmailText;
    @FXML private TextField AddressText;
    @FXML private TextField PostnummerText;
    @FXML private TextField OrtText;
    @FXML private TextField LghnummerText;
    @FXML private TextField LosenordText;
    @FXML private TextField ConfirmLosenordText;
    @FXML private AnchorPane cartPane;

    @FXML private AnchorPane RegistreringPane1;
    @FXML private AnchorPane RegistreringPane2;
    @FXML private AnchorPane RegistreringPane3;
    @FXML private AnchorPane RegistreringPane4;

    @FXML private Button NastaStegButton1;
    @FXML private Button NastaStegButton2;
    @FXML private Button NastaStegButton3;
    @FXML private Button TillProfilButton;
    @FXML private Button TillStartsidanButton;
    @FXML private Button EscapeButtonReg1;
    @FXML private Button EscapeButtonReg2;
    @FXML private Button EscapeButtonReg3;
    @FXML private Button EscapeButtonReg4;
    @FXML private Button TillbakaButton2;
    @FXML private Button TillbakaButton3;
    @FXML private Button homeButtonRegister;
    @FXML private Button LoggainButton;

    private static List<User> userList = new ArrayList<>();
    private VarukorgController varukorgController;
    private ViewSwitcher viewSwitcher;

    public static List<User> getUserList() {
        return userList;
    }

    public static void saveUserList() {
        UserDataHandler.saveUserList();
    }

    @FXML
    public void initialize() {
        UserDataHandler.loadUserList();
        userList = UserDataHandler.getUserList();

        NastaStegButton1.setOnAction(event -> {
            if (validateStep1()) {
                switchPane(RegistreringPane2);
            }
        });
        NastaStegButton2.setOnAction(event -> {
            if (validateStep2()) {
                switchPane(RegistreringPane3);
            }
        });
        NastaStegButton3.setOnAction(event -> {
            if (validateStep3()) {
                completeRegistration();
                switchPane(RegistreringPane4);
            }
        });

        TillbakaButton2.setOnAction(event -> switchPane(RegistreringPane1));
        TillbakaButton3.setOnAction(event -> switchPane(RegistreringPane2));
        LoggainButton.setOnAction(event -> {
            try {
                showLoginView();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        homeButtonRegister.setOnAction(event -> handleEscape());

        TillProfilButton.setOnAction(event -> handleProfileAfterRegister());
        TillStartsidanButton.setOnAction(event -> handleHomePageAfterRegister());
        EscapeButtonReg1.setOnAction(event -> handleEscape());
        EscapeButtonReg2.setOnAction(event -> handleEscape());
        EscapeButtonReg3.setOnAction(event -> handleEscape());
        EscapeButtonReg4.setOnAction(event -> handleEscape());
    }

    private void switchPane(AnchorPane nextPane) {
        RegistreringPane1.setVisible(false);
        RegistreringPane2.setVisible(false);
        RegistreringPane3.setVisible(false);
        RegistreringPane4.setVisible(false);
        nextPane.setVisible(true);
    }

    private boolean validateStep1() {
        boolean isValid = true;

        if (EmailText.getText().isEmpty() || !EmailText.getText().contains("@")) {
            displayError(EmailText, "E-postadress är obligatoriskt.");
            isValid = false;
        } else {
            clearError(EmailText);
        }

        return isValid;
    }

    private boolean validateStep2() {
        boolean isValid = true;

        if (AddressText.getText().isEmpty()) {
            displayError(AddressText, "Adress är obligatoriskt.");
            isValid = false;
        } else {
            clearError(AddressText);
        }

        if (PostnummerText.getText().isEmpty()) {
            displayError(PostnummerText, "Postnummer är obligatoriskt.");
            isValid = false;
        } else {
            clearError(PostnummerText);
        }

        if (OrtText.getText().isEmpty()) {
            displayError(OrtText, "Ort är obligatoriskt.");
            isValid = false;
        } else {
            clearError(OrtText);
        }

        return isValid;
    }

    private boolean validateStep3() {
        boolean isValid = true;

        if (LosenordText.getText().isEmpty()) {
            displayError(LosenordText, "Lösenord är obligatoriskt.");
            isValid = false;
        } else {
            clearError(LosenordText);
        }

        if (ConfirmLosenordText.getText().isEmpty()) {
            displayError(ConfirmLosenordText, "Bekräfta lösenord är obligatoriskt.");
            isValid = false;
        } else {
            clearError(ConfirmLosenordText);
        }

        if (!LosenordText.getText().equals(ConfirmLosenordText.getText())) {
            displayError(LosenordText, "Lösenorden matchar inte.");
            displayError(ConfirmLosenordText, "Lösenorden matchar inte.");
            isValid = false;
        }

        return isValid;
    }

    private void completeRegistration() {
        User newUser = new User(
                NamnText.getText(),
                EfternamnText.getText(),
                TelefonText.getText(),
                EmailText.getText(),
                AddressText.getText(),
                PostnummerText.getText(),
                OrtText.getText(),
                LghnummerText.getText(),
                LosenordText.getText()
        );
        userList.add(newUser);
        saveUserList();

        // Update current user session with the new user
        UserSession.setCurrentUser(newUser);

        // Print current user for troubleshooting
        System.out.println("Current user after registration: " + UserSession.getCurrentUser().getFirstName() + " " + UserSession.getCurrentUser().getLastName());

        // Clear errors after successful registration
        clearError(NamnText);
        clearError(EfternamnText);
        clearError(TelefonText);
        clearError(EmailText);
        clearError(AddressText);
        clearError(PostnummerText);
        clearError(OrtText);
        clearError(LosenordText);
        clearError(ConfirmLosenordText);

        System.out.println("Användare har skapats");
    }

    private void handleEscape() {
        try {
            ViewSwitcher.switchTo("/imat/landing_page.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleHomePageAfterRegister() {
        try {
            ViewSwitcher.switchTo("/imat/VaruSida.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleProfileAfterRegister() {
        try {
            ViewSwitcher.switchTo("/imat/profile_page.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public void showLoginView() throws IOException {
        viewSwitcher.switchTo("iMat_login.fxml");
    }
}
