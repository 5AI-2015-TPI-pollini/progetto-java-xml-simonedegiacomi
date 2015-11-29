package MyHTTP;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * JSON http retriver
 * Created by Degiacomi Simone on 14/11/2015.
 */
public class JSONRetriver implements DataRetriver {

    @Override
    public void retriveResult(URL url, DataRetrivedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URLConnection conn = url.openConnection(); // Open the connection
                    // Create a streamReader
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder jsonString = new StringBuilder();
                    String temp;
                    // Read all the data
                    while ((temp = in.readLine()) != null)
                        jsonString.append(temp);
                    // Return the parsed xml
                    listener.onResult(new JSONObject(jsonString.toString()));
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        }).start();
    }
}
