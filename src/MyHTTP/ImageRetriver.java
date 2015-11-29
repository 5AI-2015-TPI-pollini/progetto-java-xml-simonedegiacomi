package MyHTTP;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * Image http retriver
 * Created by Degiacomi Simone on 19/11/2015.
 */
public class ImageRetriver implements DataRetriver {
    /**
     * Download and instanciate an Image
     * @param url URL of the image
     * @param listener Listener of the data
     */
    @Override
    public void retriveResult(URL url, DataRetrivedListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    listener.onResult(new Image(url.openConnection().getInputStream()));
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        }).start();
    }
}
