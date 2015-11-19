package weatherproject;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.net.URL;
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

    private Config config;
    private Stage dialogStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        config = Config.getInstance();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void applyChanges() {
        config.setBoolean("USE_PROXY", useProxy.isSelected());
        config.setString("PROXY_IP", proxyIP.getText());
        config.setString("PROXY_PASSWORD", proxyPassword.getText());
        config.setBoolean("AUTHENTICATE_PROXY", authenticateProxy.isSelected());
        config.setString("PROXY_USER", proxyUser.getText());
        config.setString("PROXY_PASSWORD", proxyPassword.getText());
        config.setDataType(Config.XML);
    }

    @FXML
    private void saveAndClose() {
        dialogStage.hide(); // Hide the dialog
        // Change the configuration
        applyChanges();
        // Save the configuration
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    config.saveConfig(new FileOutputStream(Config.DEFAULT_CONFIG_FILE));
                } catch (Exception ex) {

                }
            }
        }).start();
        // Close the dialog
        dialogStage.close();
    }
}