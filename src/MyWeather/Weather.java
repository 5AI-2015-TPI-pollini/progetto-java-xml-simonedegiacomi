package MyWeather;

import MyGMaps.Coordinate;
import MyGMaps.InvalidPlace;

/**
 * Created by Simone on 15/11/2015.
 */
public interface Weather {
    public void getActualWeather (Coordinate place, WeatherResultListener listener) throws InvalidPlace;

    public void getForecast(Coordinate place, WeatherResultListener listener) throws InvalidPlace;
}
