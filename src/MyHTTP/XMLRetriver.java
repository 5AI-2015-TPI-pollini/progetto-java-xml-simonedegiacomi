package MyHTTP;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;

/**
 * XML retriver used to download xml
 * Created by Degiacomi Simone on 14/11/2015.
 */
public class XMLRetriver implements DataRetriver {
    /**
     * DocumentBuilderFactory used to create the documentBuilder
     */
    private static final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();


    /**
     * Download the XML
     * @param listener Listener to 'call' when thw XML is donwloaded and parsed
     */
    @Override
    public void retriveResult(URL url, DataRetrivedListener listener) {
        // Create a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Open the http connection
                    URLConnection conn = url.openConnection();
                    // Parse the xml
                    listener.onResult(docBuilderFactory.newDocumentBuilder().parse(conn.getInputStream()));
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        }).start(); // Execute the thread
    }
}