package weatherproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;

/**
 * Created by simone on 19/11/15.
 */
public class PreferencesController implements Initializable {
    @FXML
    private CheckBox useProxy;
    @FXML
    private TextField proxyIP;
    @FXML
    private TextField proxyPort;
    @FXML
    private CheckBox authenticateProxy;
    @FXML
    private TextField proxyUser;
    @FXML
    private PasswordField proxyPassword;
    @FXML
    private TitledPane authenticationProxyPane;
    @FXML
    private Button save;

    private Config config;
    private Stage dialogStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bind some graphics element
        proxyIP.editableProperty().bind(useProxy.selectedProperty());
        proxyPort.editableProperty().bind(useProxy.selectedProperty());
        authenticationProxyPane.expandedProperty().bind(authenticateProxy.selectedProperty());
        config = Config.getInstance();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void applyChanges() {
        config.setBoolean("useProxy", useProxy.isSelected());
        config.setString("proxyIp", proxyIP.getText());
        config.setString("proxyPassword", proxyPassword.getText());
        config.setBoolean("authenticationProxy", authenticateProxy.isSelected());
        config.setString("proxyUser", proxyUser.getText());
        config.setString("proxyPassword", proxyPassword.getText());
        config.setDataType(Config.XML);
        EasyProxy.setProxyByConfig();
    }

    private void showErrorAlert() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Connection Error");
        error.setContentText("Check the new proxy configuration");
        error.showAndWait();
        dialogStage.show();
    }

    @FXML
    private void saveAndClose() {
        // Hide the dialog
        dialogStage.hide();
        // Change the configuration
        try {
            applyChanges();
        } catch (Exception ex) {
            showErrorAlert();
        }
        // Save the configuration
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Save the file
                    config.saveConfig(new FileOutputStream(Config.DEFAULT_CONFIG_FILE));
                    // And make a test connession
                    try {
                        URLConnection c = new URL("http://www.google.it/").openConnection();
                    } catch (Exception ex){
                        showErrorAlert();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }
}