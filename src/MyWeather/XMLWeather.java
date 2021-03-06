package MyWeather;

import MyGMaps.Coordinate;
import MyGMaps.InvalidPlace;
import MyHTTP.DataRetrivedListener;
import MyHTTP.DataRetriver;
import MyHTTP.XMLRetriver;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
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

    private static final String QUERY_CURRENT_STATE = "/current/weather";
    private static final String QUERY_CURRENT_TEMP = "/current/temperature";
    private static final String QUERY_CURRENT_HUMIDITY = "/current/humidity";
    private static final String QUERY_CURRENT_PRESSURE = "/current/pressure";

    private static final String QUERY_FORECTAST_TIME = "/weatherdata/forecast/time";
    private static final String QUERY_FORECTAST_DESCRIPTION = "/weatherdata/forecast/time/symbol";
    private static final String QUERY_FORECTAST_TEMPERATURE = "/weatherdata/forecast/time/temperature";

    private DataRetriver retriver = new XMLRetriver();

    @Override
    public void getActualWeather(Coordinate place, WeatherResultListener listener) throws InvalidPlace {
        retriver.retriveResult(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.ACTUAL_WEATHER, OpenWeatherMapURLGenerator.XML), new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                Document xml = (Document) data;
                XPath xpath = xpathFactory.newXPath();
                try {
                    XPathExpression currentState = xpath.compile(QUERY_CURRENT_STATE);
                    XPathExpression temperature = xpath.compile(QUERY_CURRENT_TEMP);
                    XPathExpression humidity = xpath.compile(QUERY_CURRENT_HUMIDITY);
                    XPathExpression pressure = xpath.compile(QUERY_CURRENT_PRESSURE);
                    WeatherState state = new WeatherState();
                    // Get the weather state
                    NamedNodeMap weatherAttributes = ((NodeList) (currentState.evaluate(xml, XPathConstants.NODESET))).item(0).getAttributes();
                    state.setDescription(weatherAttributes.getNamedItem("value").getNodeValue());
                    state.setIcon(weatherAttributes.getNamedItem("icon").getNodeValue());
                    // Get the humidity
                    NamedNodeMap humidityAttributes = ((NodeList) (humidity.evaluate(xml, XPathConstants.NODESET))).item(0).getAttributes();
                    state.setHumidity(Double.parseDouble(humidityAttributes.getNamedItem("value").getNodeValue()));
                    // Get the pressure
                    NamedNodeMap pressureAttributes = ((NodeList) (pressure.evaluate(xml, XPathConstants.NODESET))).item(0).getAttributes();
                    state.setPressure(Double.parseDouble(humidityAttributes.getNamedItem("value").getNodeValue()));
                    // Get the temperature
                    NamedNodeMap temperatureAttributes = ((NodeList)(temperature.evaluate(xml, XPathConstants.NODESET))).item(0).getAttributes();
                    state.setTemperature(Double.parseDouble(temperatureAttributes.getNamedItem("value").getNodeValue()));
                    listener.onResult(new WeatherState[]{state});
                } catch (Exception ex) {
                    ex.printStackTrace();
                    listener.onResult(null);
                }
            }
        });
    }

    @Override
    public void getForecast(Coordinate place, WeatherResultListener listener) throws InvalidPlace {
        retriver.retriveResult(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.FORECAST, OpenWeatherMapURLGenerator.XML), new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                Document xml = (Document) data;
                XPath xpath = xpathFactory.newXPath();
                try {
                    NodeList times = (NodeList) xpath.compile(QUERY_FORECTAST_TIME).evaluate(xml, XPathConstants.NODESET);
                    NodeList descriptions = (NodeList) xpath.compile(QUERY_FORECTAST_DESCRIPTION).evaluate(xml, XPathConstants.NODESET);
                    NodeList temperatures = (NodeList) xpath.compile(QUERY_FORECTAST_TEMPERATURE).evaluate(xml, XPathConstants.NODESET);
                    WeatherState[] states = new WeatherState[times.getLength()];
                    for(int i = 0; i < states.length ; i++) {
                        WeatherState state = new WeatherState();
                        // Get the time
                        state.setDate(times.item(i).getAttributes().getNamedItem("from").getNodeValue().replaceAll("T", " "));
                        // Get the description
                        NamedNodeMap weatherAttributes = descriptions.item(i).getAttributes();
                        state.setDescription(weatherAttributes.getNamedItem("name").getNodeValue());
                        state.setIcon(weatherAttributes.getNamedItem("var").getNodeValue());
                        // Get the temperature
                        NamedNodeMap temperaturesAttributes = temperatures.item(i).getAttributes();
                        state.setTemperature(Double.parseDouble(temperaturesAttributes.getNamedItem("value").getNodeValue()));
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
