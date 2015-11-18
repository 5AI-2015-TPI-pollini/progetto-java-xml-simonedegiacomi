package weatherproject;

import java.io.*;
import java.util.HashMap;

/**
 * Created by Simone on 18/11/2015.
 */
public class Config implements Serializable {
    public static final int XML = 0;
    public static final int JSON = 1;

    private static Config ourInstance;

    public static Config getInstance() {
        if (ourInstance == null)
            ourInstance = new Config();
        return ourInstance;
    }

    private int dataType;
    private HashMap<String, String> strings;

    private Config() {
        dataType = XML;
        strings = new HashMap<>();
    }

    public static void loadConfig (InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream stream = new ObjectInputStream(in);
        ourInstance = (Config) stream.readObject();
        stream.close();
    }

    public void saveConfig (OutputStream out) throws IOException {
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(ourInstance);
        stream.close();
    }

    public int getDataType () {
        return dataType;
    }

    public void setDataType (int newDataType) {
        this.dataType = newDataType;
    }

    public String getString (String key) {
        return strings.get(key);
    }

    public void setString (String key, String value) {
        strings.put(key, value);
    }
}
