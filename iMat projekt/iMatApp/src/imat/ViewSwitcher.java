package imat;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewSwitcher {

    public static <T> T switchTo(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(ViewSwitcher.class.getResource(fxml));
        Parent root = loader.load();

        Stage stage = iMatApp.getPrimaryStage();
        stage.setScene(new Scene(root));
        stage.show();

        return loader.getController();
    }
}
