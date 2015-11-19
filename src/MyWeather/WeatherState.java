package MyWeather;

/**
 * Created by Simone on 15/11/2015.
 */
public class WeatherState {
    private Temperature temperature;
    private String description;

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
