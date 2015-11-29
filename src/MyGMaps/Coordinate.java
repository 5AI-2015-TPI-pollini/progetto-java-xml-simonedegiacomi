package MyGMaps;

/**
 * Created by Simone on 14/11/2015.
 */
public class Coordinate {
    private double longitude;
    private double latitude;

    /**
     * Create a coordinate object from two double insde a String
     * @param longitude Longitude
     * @param latitude Latitude
     */
    public Coordinate(String longitude, String latitude) {
        this.longitude = Double.parseDouble(longitude);
        this.latitude = Double.parseDouble(latitude);
    }

    /**
     * Create a new Coordinate from two double
     * @param longitude Longitude
     * @param latitude Latitude
     */
    public Coordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
