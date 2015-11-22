package MyWeather;

import MyGMaps.Coordinate;
import MyGMaps.InvalidPlace;
import weatherproject.Config;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Simone on 15/11/2015.
 */
public class OpenWeatherMapURLGenerator {
    public static final int XML = 0;
    public static final int JSON = 1;

    public static final int ACTUAL_WEATHER = 0;
    public static final int FORECAST = 1;

    private static final String[] resultTypes = {"xml", "json"};
    private static final String[] forecastType = {"weather", "forecast"};
    private static final String openWeatherUrl = "http://api.openweathermap.org/data/2.5/";
    private static final String openWeatherIconUrl = "http://openweathermap.org/img/w/";


    public static URL generateURL (Coordinate coordinate, int request, int resultType) throws InvalidPlace {
        StringBuilder urlString = new StringBuilder(openWeatherUrl);
        urlString.append(forecastType[request]);
        urlString.append("?units=metric&mode=");
        urlString.append(resultTypes[resultType]);
        urlString.append("&lat=");
        urlString.append(coordinate.getLatitude());
        urlString.append("&lon=");
        urlString.append(coordinate.getLongitude());
        urlString.append("&appid=");
        urlString.append(Config.getInstance().getString("apiKey"));
        try {
            return new URL(urlString.toString());
        } catch (Exception ex) {
            throw new InvalidPlace(coordinate.toString());
        }
    }

    public static URL generateIconURL (String iconId) throws MalformedURLException {
        StringBuilder urlString = new StringBuilder(openWeatherIconUrl);
        urlString.append(iconId);
        urlString.append(".png");
        return new URL(urlString.toString());
    }
}
