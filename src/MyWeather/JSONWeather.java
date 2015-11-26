package MyWeather;

import MyGMaps.Coordinate;
import MyGMaps.InvalidPlace;
import MyHTTP.DataRetrivedListener;
import MyHTTP.JSONRetriver;
import org.json.JSONObject;

/**
 * API wrapper for OpenWeatherMap
 * Created by simone on 19/11/15.
 */
public class JSONWeather extends Weather {
    /**
     * JSON retrived used to download the json data
     */
    private JSONRetriver retriver = new JSONRetriver();

    @Override
    public void getActualWeather(Coordinate place, WeatherResultListener listener) throws InvalidPlace {
        retriver.retriveResult(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.ACTUAL_WEATHER, OpenWeatherMapURLGenerator.JSON), new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                try {
                    JSONObject json = (JSONObject) data;
                    JSONObject jsonWeather = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject jsonMain = json.getJSONObject("main");
                    WeatherState state = new WeatherState();
                    state.setDescription(jsonWeather.getString("description"));
                    state.setTemperature(jsonMain.getDouble("temp"));
                    listener.onResult(new WeatherState[]{state});
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        });
    }

    @Override
    public void getForecast(Coordinate place, WeatherResultListener listener) throws InvalidPlace {
        retriver.retriveResult(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.ACTUAL_WEATHER, OpenWeatherMapURLGenerator.JSON), new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                try {
                    JSONObject json = (JSONObject) data;
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        });
    }
}