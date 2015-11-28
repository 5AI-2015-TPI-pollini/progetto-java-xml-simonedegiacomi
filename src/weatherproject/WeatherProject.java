package weatherproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;

/**
 * Main Class
 * Created by Degiacomi Simone on 15/11/2015.
 */
public class WeatherProject extends Application {

    public static void main (String[] args) {
        loadConfig(); // Load the configuaration
        launch(args); // Lanch the GUI App
    }
    /**
     * Load the file configuration
    **/
    private static void loadConfig () {
        try {
            Config.getInstance().loadConfig(new FileInputStream(Config.DEFAULT_CONFIG_FILE));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../res/window.fxml"));
        primaryStage.setTitle("WeatherProject");
        primaryStage.setScene(new Scene(root, 700, 300));
        primaryStage.show();
    }
}