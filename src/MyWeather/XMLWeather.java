package MyWeather;

import MyGMaps.Coordinate;
import MyGMaps.InvalidPlace;
import MyHTTP.DataRetrivedListener;
import MyHTTP.XMLRetriver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Simone on 15/11/2015.
 */
public class XMLWeather extends Weather {
    private static final XPathFactory xpathFactory = XPathFactory.newInstance();

    private static final String QUERY_CURRENT_STATE = "/current/weather[\"value\"]/text()";

    public XMLWeather(Coordinate place) {
        super(place);
    }


    @Override
    public void getActualWeather(WeatherResultListener listener) throws InvalidPlace {
        XMLRetriver retriver = new XMLRetriver(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.ACTUAL_WEATHER, OpenWeatherMapURLGenerator.XML));
        retriver.retriveResult(new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                Document xml = (Document) data;
                XPath xpath = xpathFactory.newXPath();
                try {
                    XPathExpression currentState = xpath.compile(QUERY_CURRENT_STATE);
                    WeatherState states[] = new WeatherState[1];
                    states[0] = new WeatherState();
                    states[0].setDescription(((NodeList) (currentState.evaluate(xml, XPathConstants.NODESET))).item(0).getNodeValue());
                    listener.onResult(states);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    listener.onResult(null);
                }
            }
        });
    }

    @Override
    public void getForecast(WeatherResultListener listener) throws InvalidPlace {
        XMLRetriver retriver = new XMLRetriver(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.FORECAST, OpenWeatherMapURLGenerator.XML));
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
