package MyHTTP;

import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Simone on 14/11/2015.
 */
public class XMLRetriver implements DataRetriver {
    private static final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    private URL url;

    public XMLRetriver(URL url) {
        this.url = url;
    }

    @Override
    public void retriveResult(DataRetrivedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URLConnection conn = url.openConnection();
                    listener.onResult(docBuilderFactory.newDocumentBuilder().parse(conn.getInputStream()));
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        }).start();
    }
}
