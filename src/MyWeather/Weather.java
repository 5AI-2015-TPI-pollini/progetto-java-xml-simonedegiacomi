package MyWeather;

import MyGMaps.Coordinate;

/**
 * Created by Simone on 15/11/2015.
 */
public interface Weather {
    public void getActualWeather (WeatherResultListener listener);

    public void getFirecast (WeatherResultListener listener);
}
