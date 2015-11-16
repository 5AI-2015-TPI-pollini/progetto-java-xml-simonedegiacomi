package MyWeather;

import MyGMaps.Coordinate;
import MyGMaps.InvalidPlace;

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


    public static URL generateURL (String apiKey, Coordinate coordinate, int request, int resultType) throws InvalidPlace {
        StringBuilder urlString = new StringBuilder(openWeatherUrl);
        urlString.append(forecastType[request]);
        urlString.append("?mode=");
        urlString.append(resultTypes[resultType]);
        urlString.append("&lat=");
        urlString.append(coordinate.getLatitude());
        urlString.append("&lon=");
        urlString.append(coordinate.getLongitude());
        urlString.append("&appid=");
        urlString.append(apiKey);
        System.out.println(urlString.toString());
        try {
            return new URL(urlString.toString());
        } catch (Exception ex) {
            throw new InvalidPlace(coordinate.toString());
        }
    }
}
