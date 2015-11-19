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
    private static final String QUERY_CURRENT_TEMP = "/current/temperature[\"value\"]/text()";
    private static final String QUERY_CURRENT_MAX = "/current/temperature[\"max\"]/text()";
    private static final String QUERY_CURRENT_MIN = "/current/temperature[\"min\"]/text()";

    private static final String QUERY_FORECTAST_TIME = "";
    private static final String QUERY_FORECTAST_DESCRIPTION = "";
    private static final String QUERY_FORECTAST_TEMPERATURE = "";

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
                    XPathExpression temperature = xpath.compile(QUERY_CURRENT_TEMP);
                    XPathExpression temperatureMax = xpath.compile(QUERY_CURRENT_MAX);
                    XPathExpression temperatureMin = xpath.compile(QUERY_CURRENT_MIN);
                    WeatherState states[] = new WeatherState[1];
                    WeatherState state = new WeatherState();
                    states[0] = state;
                    state.setDescription(((NodeList) (currentState.evaluate(xml, XPathConstants.NODESET))).item(0).getNodeValue());
                    Temperature temp = new Temperature(((NodeList) (temperature.evaluate(xml, XPathConstants.NODESET))).item(0).getNodeValue(),
                            ((NodeList) (currentState.evaluate(xml, XPathConstants.NODESET))).item(0).getNodeValue(),
                            ((NodeList) (currentState.evaluate(xml, XPathConstants.NODESET))).item(0).getNodeValue());
                    state.setTemperature(temp);
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
                Document xml = (Document) data;
                XPath xpath = xpathFactory.newXPath();
                try {
                    NodeList times = xpath.compile(QUERY_FORECTAST_TIME).evaluate(xml, XPathConstants.NODESET);
                    NodeList descriptions = xpath.compile(QUERY_FORECTAST_DESCRIPTION).evaluate(xml, XPathConstants.NODESET);
                    NodeList temperatures = xpath.compile(QUERY_FORECTAST_TEMPERATURE).evaluate(xml, XPathConstants.NODESET);
                    WeatherState[] states = new WeatherState[times.getLength()];
                    for(int i = 0; i < states.length ; i++) {
                        WeatherState state = new WeatherState();
                        state.setDescription(descriptions.item(i).getNodeValue());
                        state.setTemperature(new Temperature(temperatures.item(i).getNodeValue()));
                        states[i] = state;
                    }
                    listener.onResult(states);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    listener.onResult(null);
                }
            }
        });
    }
}
