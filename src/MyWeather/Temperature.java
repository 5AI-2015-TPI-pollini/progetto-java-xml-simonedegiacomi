package MyWeather;

/**
 * Created by simone on 19/11/15.
 */
public class Temperature {
    private double temperature, min, max;

    public Temperature(double temperature, double min, double max) {
        this.temperature = temperature;
        this.min = min;
        this.max = max;
    }

    public Temperature(String temperature, String min, String max) {
        this.temperature = Double.parseDouble(temperature);
        this.min = Double.parseDouble(min);
        this.max = Double.parseDouble(max);
    }

    public Temperature (String temperature) {
        this.temperature = Double.parseDouble(temperature);
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
}
