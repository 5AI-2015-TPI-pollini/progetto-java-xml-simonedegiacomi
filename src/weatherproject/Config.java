package weatherproject;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;


/**
 * Created by Simone on 18/11/2015.
 */
public class Config implements Serializable {
    public static final String DEFAULT_CONFIG_FILE = ".wpconfig";

    public static final int XML = 0;
    public static final int JSON = 1;

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
}
