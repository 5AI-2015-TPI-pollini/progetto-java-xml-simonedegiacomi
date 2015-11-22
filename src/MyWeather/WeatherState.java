package MyWeather;

/**
 * Created by Simone on 15/11/2015.
 */
public class WeatherState {
    private Double temperature;
    private String description;
    private String icon;
    private String date;
    private Double pressure, humidity;

    public String getDescription() {
        return description;
    }

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
