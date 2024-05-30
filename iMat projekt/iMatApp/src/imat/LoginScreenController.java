package imat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

public class LoginScreenController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button closeLoginButton;

    private List<User> userList;

    @FXML
    public void initialize() {
        UserDataHandler.loadUserList();
        userList = UserDataHandler.getUserList();

        loginButton.setOnAction(event -> {
            try {
                handleLogin();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        registerButton.setOnAction(event -> {
            try {
                handleRegister();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        closeLoginButton.setOnMouseClicked(event -> {
            try {
                handleClose();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void handleLogin() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = userList.stream()
                .filter(u -> u.getEmail().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user != null) {
            UserSession.setCurrentUser(user);
            VaruSidaController varuSidaController = ViewSwitcher.switchTo("/imat/VaruSida.fxml");
            varuSidaController.setCurrentUser(user);
        } else {
            System.out.println("Felaktigt användarnamn eller lösenord");
        }
    }

    private void handleRegister() throws IOException {
        System.out.println("Registreringsförsök");
        ViewSwitcher.switchTo("/imat/iMat_register.fxml");
    }

    private void handleClose() throws IOException {
        ViewSwitcher.switchTo("/imat/landing_page.fxml");
    }
}
