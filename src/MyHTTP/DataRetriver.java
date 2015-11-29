package MyHTTP;

import java.net.URL;

/**
 * Generic HTTP Data retriver
 * Created by Simone on 14/11/2015.
 */
public interface DataRetriver {
    /**
     * Download the response and call the  specified listener onResult method
     * @param url  Url to connect
     * @param listener Listener of the result
     */
    public void retriveResult(URL url, DataRetrivedListener listener);
}
