package weatherproject;

import MyGMaps.*;
import MyWeather.Weather;
import MyWeather.WeatherResultListener;
import MyWeather.WeatherState;
import MyWeather.XMLWeather;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gmaps = GMaps.getGMaps();
        addressesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedAddress = retrivedAddesses[addressesList.getSelectionModel().getSelectedIndex()];
                // Get weather informations
                weather = Weather.getWeather(selectedAddress.getCoordinate());
                try {
                    weather.getActualWeather(new WeatherResultListener() {
                        @Override
                        public void onResult(WeatherState[] states) {
                            System.out.println(states[0]);
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
