package imat;

import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class iMatApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage; // Save reference to primary stage

        // Load the landing_page.fxml

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("landing_page.fxml")));
        //Parent root = FXMLLoader.load(getClass().getResource("VaruSida.fxml"));

        Scene scene = new Scene(root, 1280, 720);

        stage.setScene(scene);
        stage.setTitle("iMatApp");
        stage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                IMatDataHandlerWrapper.getInstance().shutDown();
            }
        }));
    }
           


}
