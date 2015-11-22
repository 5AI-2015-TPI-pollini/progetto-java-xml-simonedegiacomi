package MyHTTP;

import javafx.scene.image.Image;

import java.net.URL;

/**
 * Created by Simone on 19/11/2015.
 */
public class ImageRetriver implements DataRetriver {

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
