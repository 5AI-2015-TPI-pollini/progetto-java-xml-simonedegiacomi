package MyWeather;

/**
 * Created by Degiacomi Simone on 15/11/2015.
 */
public interface WeatherResultListener {
    /**
     * Method called when the result is retrived
     * @param states Response
     */
    public void onResult (WeatherState[] states);
}
