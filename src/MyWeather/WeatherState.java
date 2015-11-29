package MyWeather;

/**
 * Class used to rappresentate the weather state
 * Created by Degiacomi Simone on 15/11/2015.
 */
public class WeatherState {
    private Double temperature;
    private String description;
    private String icon;
    private String date;
    private Double pressure, humidity;

    /**
     * Get the description of the weather. The description is a string that indicate
     * the state of the weather expressed in 'human'
     * @return Description of the state
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the weather description
     * @param description The newweather state description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Return the icon of the weather, which is a code that of an image
     * to download from internet
     * @return Code of the icon
     */
    public String getIcon() {
        return icon;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}
