package weatherproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Simone on 15/11/2015.
 */
public class WeatherProject extends Application {
    private static final String DEFAULT_CONFIG_FILE = ".wcconf";

    public static void main (String[] args) {
        loadConfig();
        launch(args);
    }

    private static void loadConfig () {
        try {
            Config.loadConfig(new FileInputStream(DEFAULT_CONFIG_FILE));
        } catch (Exception ex) {
            Config.getInstance().setString("API_KEY", "");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("window.fxml"));
        primaryStage.setTitle("WeatherProject");
        primaryStage.setScene(new Scene(root, 700, 300));
        primaryStage.show();

    }


}
