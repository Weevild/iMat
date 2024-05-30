package imat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class landingPageViewController {

    ViewSwitcher viewSwitcher = new ViewSwitcher();

    // loginButton används TEMPORÄRT för att direkt komma till profilsidan, ändra ViewSwitcher också efter användning
    @FXML
    private Button loginButton;
    @FXML
    private Button cartButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button largeLoginButton;

    @FXML
    private void showRegisterView() throws IOException {
        ViewSwitcher.switchTo("/imat/iMat_login.fxml");
    }
}
