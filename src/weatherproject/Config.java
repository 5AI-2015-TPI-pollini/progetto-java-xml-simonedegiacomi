package weatherproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;


/**
 * Created by Simone on 18/11/2015.
 */
public class Config implements Serializable {
    public static final String DEFAULT_CONFIG_FILE = ".wpconfig";

    public static final int XML = 0;
    public static final int JSON = 1;

    private ArrayList<Runnable> callbacks = new ArrayList<>();

    private static Config ourInstance = new Config();

    public static Config getInstance() {
        return ourInstance;
    }

    private Properties properties;

    private Config() {
        properties = new Properties();
    }

    public void loadConfig (InputStream in) throws IOException, ClassNotFoundException {
        properties.loadFromXML(in);
    }

    public void saveConfig (OutputStream out) throws IOException {
        properties.storeToXML(out, "Configuration");
        for(Runnable callback : callbacks)
            callback.run();
    }

    public int getDataType () {
        return Integer.parseInt(properties.getProperty("dataType"));
    }

    public void setDataType (int newDataType) {
        properties.put("dataType", "" + newDataType);
    }

    public String getString (String key) {
        return properties.getProperty(key);
    }

    public void setString (String key, String value) {
        properties.put(key, value);
    }

    public boolean getBoolean (String key) { return Boolean.parseBoolean((String) properties.get(key)); }

    public void setBoolean (String key, boolean value) { properties.put(key, "" + value); }

    public void addChangeCallback(Runnable callback) {
        callbacks.add(callback);
    }
}
