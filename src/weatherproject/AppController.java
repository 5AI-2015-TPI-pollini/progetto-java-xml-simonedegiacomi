package weatherproject;

import MyGMaps.Address;
import MyGMaps.GMaps;
import MyGMaps.InvalidPlace;
import MyGMaps.ResultRetrivedListener;
	import MyHTTP.DataRetrivedListener;
import MyHTTP.DataRetriver;
import MyHTTP.ImageRetriver;
import MyWeather.OpenWeatherMapURLGenerator;
import MyWeather.Weather;
import MyWeather.WeatherResultListener;
import MyWeather.WeatherState;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * Created by Simone on 15/11/2015.
 */
public class AppController implements Initializable {
    /**
     * gmaps retriver
     */
    private GMaps gmaps;
    /**
     * weather retriver
     */
    private Weather weather;
    /**
     * Image retriver
     */
    private DataRetriver imagesRetriver = new ImageRetriver();
    /**
     * cache of the images
     */
    private HashMap<String, Image> images = new HashMap<>();
    /**
     * the address of the displayed weather
     */
    private Address selectedAddress;
    /**
     * displayed addresses list
     */
    private Address[] retrivedAddesses;

    // Graphic elements
    @FXML
    private TextField inputAddress;
    @FXML
    private ListView addressesList;
    @FXML
    private Label actualState, actualTemperature, actualHumidity, actualPressure, connectionStatus;
    @FXML
    private ImageView actualIcon;
    @FXML
    private TableView forecastTable;
    @FXML
    private TableColumn forecastDescriptionColumn, foresastDateColumn, forecastTemperatureColumn, forecastIconColumn;

    /**
     * AHandler off the input address field
     */
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

    /**
     * Close the application
     */
    @FXML
    private void close() {
        System.exit(0);
    }

    private void loadConfig() {
        EasyProxy.setProxyByConfig();
        gmaps = GMaps.createGMaps();
        weather = Weather.createWeather();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                connectionStatus.setText("Testing connection...");
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                // And make a test connession
                String result;
                try {
                    long startTime = System.currentTimeMillis();
                    URLConnection c = new URL("http://www.google.it/").openConnection();
                    c.connect();
                    c.getInputStream().close();
                    result = "Connection Ok (time: " + (System.currentTimeMillis() - startTime) + " ms)";
                } catch (Exception ex){
                    result = "Connection Error!!!";
                }
                final String finalResult = result;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        connectionStatus.setText(finalResult);
                    }
                });
            }
        }).start();
    }

    /**
     * Method used to initialize the graphics and logic objects
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadConfig();
        // Initialize the table
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
        // Initialize the icon cell factory
        forecastIconColumn.setCellFactory(column -> {
            return new TableCell<WeatherState, Image>() {
                @Override
                protected void updateItem(Image item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setGraphic(null); // Remove the image
                        setText("loading..."); // Set a loading label
                    } else {
                        setGraphic(new ImageView(item)); // Show the image
                        setText(null); // Remove the loading text
                    }
                }
            };
        });
        forecastIconColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<WeatherState, Image>, ObservableValue<Image>>() {
            public ObservableValue<Image> call(TableColumn.CellDataFeatures<WeatherState, Image> p) {
                return new SimpleObjectProperty<Image>(images.get(p.getValue().getIcon()));
            }
        });

        // Initialize the address list handler
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
                    weather.getForecast(selectedAddress.getCoordinate(), new WeatherResultListener() {
                        @Override
                        public void onResult(WeatherState[] states) {
                            showForecast(states);
                        }
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        // If the configuration changes during the execution, reload some objects
        Config.getInstance().addChangeCallback(new Runnable() {
            @Override
            public void run() {
                loadConfig();
            }
        });
}

    /**
     * Method that update the grapich elements to show the weather
     * informations
     * @param state Actual weather state
     */
    private void showActualWeather(WeatherState state) {
    Platform.runLater(new Runnable() {
        @Override
        public void run() {
            actualState.setText("State: " + state.getDescription());
            actualTemperature.setText("Temperature: " + state.getTemperature() + " °C");
            actualHumidity.setText("Humidity: " + state.getHumidity() + " %");
            actualPressure.setText("Pressure: " + state.getPressure() + " hPa");
            final String icon = state.getIcon();
            if(images.containsKey(state.getIcon()))
                actualIcon.setImage(images.get(icon));
            else
                try {
                    imagesRetriver.retriveResult(OpenWeatherMapURLGenerator.generateIconURL(state.getIcon()), new DataRetrivedListener() {
                        @Override
                        public void onResult(Object data) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    images.put(icon, (Image) data);
                                    actualIcon.setImage((Image) data);
                                }
                            });
                        }
                    });
                } catch (Exception ex) {}
        }
    });
}

private void showForecast(final WeatherState states[]) {
    final ObservableList observableStates = FXCollections.observableArrayList(states);
    // Before update the table download all the image
    ArrayList<String> imagesToDownload = new ArrayList<>();
    for(WeatherState state : states)
        if(!images.containsKey(state.getIcon()))
            imagesToDownload.add(state.getIcon());
    Iterator<String> it = imagesToDownload.iterator();
    while(it.hasNext()) {
        final String id = it.next();
        try {
            imagesRetriver.retriveResult(OpenWeatherMapURLGenerator.generateIconURL(id), new DataRetrivedListener() {
                @Override
                public void onResult(Object data) {
                    images.put(id, (Image) data);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            forecastTable.setItems(observableStates);
                        }
                    });
                }
            });
        } catch (Exception ex) {}
    }
    Platform.runLater(new Runnable() {
        @Override
        public void run() {
            forecastTable.setItems(observableStates);
        }
    });
}

	@FXML
private void showAbout() {
    Alert about = new Alert(Alert.AlertType.INFORMATION);
    about.setTitle("About");
    about.setHeaderText("WeatherProject");
    about.setContentText("WeatherProject is a open source project. You can find other informations on github");
    about.show();
}

@FXML
private void showPreferences() {
    try {
        FXMLLoader loader = new FXMLLoader(WeatherProject.class.getResource("../res/preferences.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Preferences");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        ((PreferencesController) loader.getController()).setDialogStage(dialogStage);
        dialogStage.showAndWait();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}
}