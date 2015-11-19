package MyWeather;

import MyGMaps.InvalidPlace;
import MyHTTP.DataRetrivedListener;
import MyHTTP.JSONRetriver;
import org.json.JSONObject;

/**
 * Created by simone on 19/11/15.
 */
public class JSONWeather extends Weather {

    @Override
    public void getActualWeather(WeatherResultListener listener) throws InvalidPlace {
        JSONRetriver retriver = new JSONRetriver(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.ACTUAL_WEATHER, OpenWeatherMapURLGenerator.JSON));
        retriver.retriveResult(new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                try {
                    JSONObject json = (JSONObject) data;
                    JSONObject jsonWeather = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject jsonMain = json.getJSONObject("main");
                    WeatherState state = new WeatherState();
                    state.setDescription(jsonWeather.getString("description"));
                    Temperature temp = new Temperature(jsonMain.getDouble("temp"), jsonMain.getDouble("temp_min"), jsonMain.getDouble("temp_max"));
                    state.setTemperature(temp);
                    listener.onResult(new WeatherState[]{state});
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        });
    }

    @Override
    public void getForecast(WeatherResultListener listener) throws InvalidPlace {
        JSONRetriver retriver = new JSONRetriver(OpenWeatherMapURLGenerator.generateURL(place, OpenWeatherMapURLGenerator.ACTUAL_WEATHER, OpenWeatherMapURLGenerator.JSON));
        retriver.retriveResult(new DataRetrivedListener() {
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