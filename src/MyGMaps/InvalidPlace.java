package MyGMaps;

/**
 * Created by Simone on 15/11/2015.
 */
public class InvalidPlace extends Exception {
    private String place;

    public InvalidPlace(String place) {
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "InvalidPlace{" +
                "place='" + place + '\'' +
                '}';
    }
}
