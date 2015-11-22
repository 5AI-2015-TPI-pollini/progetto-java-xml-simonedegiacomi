package weatherproject;

import MyGMaps.*;
import MyHTTP.DataRetrivedListener;
import MyHTTP.ImageRetriver;
import MyWeather.OpenWeatherMapURLGenerator;
import MyWeather.Weather;
import MyWeather.WeatherResultListener;
import MyWeather.WeatherState;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.FileOutputStream;
import java.lang.reflect.Array;
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
    private Label actualState, actualTemperature;

    @FXML
    private ImageView actualIcon;

    @FXML
    private TableView forecastTable;

    @FXML
    private TableColumn forecastDescriptionColumn, foresastDateColumn, forecastTemperatureColumn, forecastIconColumn;


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
        //Initialize the table
        forecastDescriptionColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<WeatherState, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<WeatherState, String> p) {
                return new SimpleStringProperty(p.getValue().getDescription());
            }
        });
        foresastDateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<WeatherState, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<WeatherState, String> p) {
                return new SimpleStringProperty(p.getValue().getDate());
            }
        });
        forecastTemperatureColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<WeatherState, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<WeatherState, String> p) {
                return new SimpleStringProperty(p.getValue().getTemperature() + " °C");
            }
        });

        final ImageView[] tempImage = new ImageView[1];
        try {
            new ImageRetriver(new URL("http://openweathermap.org/img/w/10d.png")).retriveResult(new DataRetrivedListener() {
                @Override
                public void onResult(Object data) {
                    tempImage[0] = (ImageView) data;
                    System.out.println("Retrived");
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }


//        forecastIconColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<WeatherState, ImageView>, ObservableValue<ImageView>>() {
          //  public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<WeatherState, ImageView> p) {

        //    }
        //});

        // Initialize the address list handler
        addressesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed (ObservableValue observable, Object oldValue, Object newValue){
            selectedAddress = retrivedAddesses[addressesList.getSelectionModel().getSelectedIndex()];
            // Get weather informations
            try {
                weather.getActualWeather(selectedAddress.getCoordinate(), new WeatherResultListener() {
                    @Override
                    public void onResult(WeatherState[] states) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                showActualWeather(states[0]);
                            }
                        });
                    }
                });
                weather.getForecast(selectedAddress.getCoordinate(), new WeatherResultListener() {
                    @Override
                    public void onResult(WeatherState[] states) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                // Update the table
                                forecastTable.setItems(FXCollections.observableArrayList(states));
                            }
                        });
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    );
}

    private void showActualWeather(WeatherState state) {
        actualState.setText("State: " + state.getDescription());
        actualTemperature.setText("Temperature: " + state.getTemperature() + " °C");
        try {
            ImageRetriver retriver = new ImageRetriver(OpenWeatherMapURLGenerator.generateIconURL(state.getIcon()));
            retriver.retriveResult(new DataRetrivedListener() {
                @Override
                public void onResult(Object data) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            actualIcon.setImage((Image) data);
                        }
                    });
                }
            });
        } catch (Exception ex) {

        }
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
