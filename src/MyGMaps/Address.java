package MyGMaps;

/**
 * Created by Simone on 14/11/2015.
 */
public class Address {
    private String formattedName;
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
}
