package weatherproject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
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
    private CheckBox useProxy, authenticateProxy;
    @FXML
    private TextField proxyIP, proxyPort, proxyUser;
    @FXML
    private PasswordField proxyPassword;
    @FXML
    private TitledPane authenticationProxyPane;
    @FXML
    private Button save;
    @FXML
    private ChoiceBox dataType;

    private Config config;
    private Stage dialogStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bind some graphics element
        proxyIP.editableProperty().bind(useProxy.selectedProperty());
        proxyPort.editableProperty().bind(useProxy.selectedProperty());
        authenticationProxyPane.expandedProperty().bind(authenticateProxy.selectedProperty());
        config = Config.getInstance();
        // Show the actuasl config
        useProxy.setSelected(config.getBoolean("useProxy"));
        proxyIP.setText(config.getString("proxyIP"));
        proxyPort.setText(config.getString("proxyPort"));
        authenticateProxy.setSelected(config.getBoolean("authenticateProxy"));
        proxyUser.setText(config.getString("proxyUser"));
        proxyPassword.setText(config.getString("proxyPassword"));
        dataType.setItems(FXCollections.observableArrayList(new String[]{"xml", "json"}));
        dataType.getSelectionModel().select(0);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    private void applyChanges() {
        config.setBoolean("useProxy", useProxy.isSelected());
        config.setString("proxyIP", proxyIP.getText());
        config.setString("proxyPort", proxyPort.getText());
        config.setString("proxyPassword", proxyPassword.getText());
        config.setBoolean("authenticateProxy", authenticateProxy.isSelected());
        config.setString("proxyUser", proxyUser.getText());
        config.setString("proxyPassword", proxyPassword.getText());
        config.setDataType(dataType.getSelectionModel().getSelectedIndex());
        EasyProxy.setProxyByConfig();
    }

    @FXML
    private void saveAndClose() {
        // Hide the dialog
        dialogStage.hide();
        // Change the configuration
        try {
            applyChanges();
        } catch (Exception ex) {}
        // Save the configuration
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Save the file
                    config.saveConfig(new FileOutputStream(Config.DEFAULT_CONFIG_FILE));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}