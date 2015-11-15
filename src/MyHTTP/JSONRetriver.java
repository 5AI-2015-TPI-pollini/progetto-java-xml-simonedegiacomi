package MyHTTP;

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

            }
        }).start();
    }
}
