package MyWeather;

import MyGMaps.Coordinate;
import MyGMaps.InvalidPlace;
import MyHTTP.DataRetrivedListener;
import MyHTTP.XMLRetriver;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import javax.lang.model.element.Name;
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

    private static final String QUERY_FORECTAST_TIME = "/weatherdata/forecast/time";
    private static final String QUERY_FORECTAST_DESCRIPTION = "/weatherdata/forecast/time/symbol";
    private static final String QUERY_FORECTAST_TEMPERATURE = "/weatherdata/forecast/time/temperature";

    @Override
    public void getActualWeather(Coordinate place, WeatherResultListener listener) throws InvalidPlace {
        XMLRetriver retriver = new XMLRetriver(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.ACTUAL_WEATHER, OpenWeatherMapURLGenerator.XML));
        retriver.retriveResult(new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                Document xml = (Document) data;
                XPath xpath = xpathFactory.newXPath();
                try {
                    XPathExpression currentState = xpath.compile(QUERY_CURRENT_STATE);
                    XPathExpression temperature = xpath.compile(QUERY_CURRENT_TEMP);
                    WeatherState states[] = new WeatherState[1];
                    WeatherState state = new WeatherState();
                    states[0] = state;
                    // Get the weather state
                    NamedNodeMap weatherAttributes = ((NodeList) (currentState.evaluate(xml, XPathConstants.NODESET))).item(0).getAttributes();
                    state.setDescription(weatherAttributes.getNamedItem("value").getNodeValue());
                    state.setIcon(weatherAttributes.getNamedItem("icon").getNodeValue());
                    // Get the temperature
                    NamedNodeMap temperatureAttributes = ((NodeList)(temperature.evaluate(xml, XPathConstants.NODESET))).item(0).getAttributes();
                    state.setTemperature(Double.parseDouble(temperatureAttributes.getNamedItem("value").getNodeValue()));
                    listener.onResult(states);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    listener.onResult(null);
                }
            }
        });
    }

    @Override
    public void getForecast(Coordinate place, WeatherResultListener listener) throws InvalidPlace {
        XMLRetriver retriver = new XMLRetriver(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.FORECAST, OpenWeatherMapURLGenerator.XML));
        retriver.retriveResult(new DataRetrivedListener() {
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
                        state.setDate(times.item(i).getAttributes().getNamedItem("from").getNodeValue());
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
