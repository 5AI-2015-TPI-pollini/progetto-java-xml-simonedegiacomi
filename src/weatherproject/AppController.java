package weatherproject;

import MyGMaps.Address;
import MyGMaps.InvalidPlace;
import MyGMaps.ResultRetrivedListener;
import MyGMaps.XMLGMaps;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;

/**
 * Created by Simone on 15/11/2015.
 */
public class AppController {
    @FXML
    private TextField inputAddress;
    @FXML
    private ListView addressesList;

    @FXML
    private void inputHandler () {
        XMLGMaps maps = XMLGMaps.getInstance();
        try {
            maps.find(inputAddress.getText(), new ResultRetrivedListener() {
                @Override
                public void onResult(Address[] result) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            addressesList.getItems().clear();
                            addressesList.getItems().addAll(result);
                        }
                    });

                }
            });
        } catch (InvalidPlace invalidPlace) {
            invalidPlace.printStackTrace();
        }
    }
}
