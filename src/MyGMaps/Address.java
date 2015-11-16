package MyGMaps;

/**
 * Class used to contain the address informations retrived from Google Maps
 * Created by Simone on 14/11/2015.
 */
public class Address {
    /**
     * Formatted name of the 'place'
     */
    private String formattedName;
    /**
     * Coordinate of the 'place'
     */
    private Coordinate coordinate;

    public Address(String formattedName, Coordinate coordinate) {
        this.formattedName = formattedName;
        this.coordinate = coordinate;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public void setFormattedName(String formattedName) {
        this.formattedName = formattedName;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return formattedName;
    }
}
