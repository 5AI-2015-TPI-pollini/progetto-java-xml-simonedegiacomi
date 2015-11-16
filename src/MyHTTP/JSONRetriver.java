package MyHTTP;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Simone on 14/11/2015.
 */
public class JSONRetriver implements DataRetriver {

    private URL url;

    public JSONRetriver(URL url) {
        this.url = url;
    }

    @Override
    public void retriveResult(DataRetrivedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URLConnection conn = url.openConnection();
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder jsonString = new StringBuilder();
                    String temp;
                    while ((temp = in.readLine()) != null)
                        jsonString.append(temp);
                    listener.onResult(new JSONObject(jsonString.toString()));
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        }).start();
    }
}
