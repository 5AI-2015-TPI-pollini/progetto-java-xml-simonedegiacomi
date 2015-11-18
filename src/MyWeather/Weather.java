package MyWeather;

import MyGMaps.Coordinate;
import MyGMaps.InvalidPlace;
import weatherproject.Config;

/**
 * Created by Simone on 15/11/2015.
 */
public abstract class Weather {

    protected Coordinate place;

    public static Weather getWeather(Coordinate place) {
        switch (Config.getInstance().getDataType()) {
            case Config.XML:
                return new XMLWeather(place);
            //case Config.JSON:
            //    return new JSONWeather(place);
        }
        return null;
    }

    public Weather (Coordinate place) {
        this.place = place;
    }

    public abstract void getActualWeather (WeatherResultListener listener) throws InvalidPlace;

    public abstract void getForecast(WeatherResultListener listener) throws InvalidPlace;
}
