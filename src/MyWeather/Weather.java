package MyWeather;

import MyGMaps.Coordinate;
import MyGMaps.InvalidPlace;
import weatherproject.Config;

/**
 * Abstract class of weather api wrapper
 * Created by Simone on 15/11/2015.
 */
public abstract class Weather {

    public static Weather createWeather() {
        switch (Config.getInstance().getDataType()) {
            case Config.XML:
                return new XMLWeather();
            case Config.JSON:
                return new JSONWeather();
        }
        return null;
    }

    /**
     * Retrive the actual weather data
     * @param place Coordinate of the place
     * @param listener Listener of the data
     * @throws InvalidPlace Exception throwed in case of invalid coordinate
     */
    public abstract void getActualWeather (Coordinate place, WeatherResultListener listener) throws InvalidPlace;

    /**
     * Retrive the weatherforecast
     * @param place Coordinate of the place
     * @param listener Listener of the data
     * @throws InvalidPlace Exception throwed in case of invalid coordinate
     */
    public abstract void getForecast(Coordinate place, WeatherResultListener listener) throws InvalidPlace;
}
