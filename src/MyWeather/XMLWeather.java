package MyWeather;

import MyGMaps.Coordinate;
import MyHTTP.DataRetrivedListener;
import MyHTTP.XMLRetriver;
import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Simone on 15/11/2015.
 */
public class XMLWeather implements Weather {
    private static final XPathFactory xpathFactory = XPathFactory.newInstance();
    private XMLRetriver retriver;

    public XMLWeather (Coordinate place) {
        retriver = new XMLRetriver(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.XML));
    }

    @Override
    public void getActualWeather(WeatherResultListener listener) {
        retriver.retriveResult(new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                Document xml = (Document) data;
                XPath xpath = xpathFactory.newXPath();

                WeatherState states[] = new WeatherState[1];
                listener.onResult(states);
            }
        });
    }

    @Override
    public void getFirecast(WeatherResultListener listener) {
        retriver.retriveResult(new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                if (data == null)
                    listener.onResult(null);
                Document xml = (Document) data;
                WeatherState states[] = null;
                listener.onResult(states);
            }
        });
    }
}
