package weatherproject;

import MyGMaps.Address;
import MyGMaps.GMaps;
import MyGMaps.InvalidPlace;
import MyGMaps.ResultRetrivedListener;
import MyWeather.Weather;
import MyWeather.WeatherResultListener;
import MyWeather.WeatherState;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Simone on 15/11/2015.
 */
public class AppController implements Initializable {
    private GMaps gmaps;
    private Weather weather;

    private Address selectedAddress;
    private Address[] retrivedAddesses;

    @FXML
    private TextField inputAddress;
    @FXML
    private ListView addressesList;
    @FXML
    private ImageView mainIcon;

    @FXML
    private void inputHandler() {
        try {
            gmaps.find(inputAddress.getText(), new ResultRetrivedListener() {
                @Override
                public void onResult(Address[] result) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            addressesList.getItems().clear();
                            retrivedAddesses = result;
                            addressesList.getItems().addAll(result);
                        }
                    });

                }
            });
        } catch (InvalidPlace invalidPlace) {
            invalidPlace.printStackTrace();
        }
    }
    @FXML
    private void close() {
        try {
            Config.getInstance().saveConfig(new FileOutputStream(Config.DEFAULT_CONFIG_FILE));
        } catch (Exception ex) {}
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gmaps = GMaps.createGMaps();
        weather = Weather.createWeather();
        addressesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedAddress = retrivedAddesses[addressesList.getSelectionModel().getSelectedIndex()];
                // Get weather informations
                try {
                    weather.getActualWeather(selectedAddress.getCoordinate(), new WeatherResultListener() {
                        @Override
                        public void onResult(WeatherState[] states) {
                            showActualWeather(states[0]);
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void showActualWeather(WeatherState state) {

    }

    @FXML
    private void showAbout() {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("WeatherProject");
        about.setContentText("WeatherProject is a open source project. You can find other informations on gitthub");
        about.show();
    }

    @FXML
    private void showPreferences () {
        try {
            FXMLLoader loader = new FXMLLoader(WeatherProject.class.getResource("../res/preferences.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Preferences");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            ((PreferencesController)loader.getController()).setDialogStage(dialogStage);
            dialogStage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
