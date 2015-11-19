package MyWeather;

import MyGMaps.Coordinate;
import MyGMaps.InvalidPlace;
import weatherproject.Config;

/**
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

    public abstract void getActualWeather (Coordinate place, WeatherResultListener listener) throws InvalidPlace;

    public abstract void getForecast(Coordinate place, WeatherResultListener listener) throws InvalidPlace;
}
