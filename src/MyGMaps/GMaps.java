package MyGMaps;

/**
 * Created by Simone on 15/11/2015.
 */
public interface GMaps {
    /**
     * Find the address of a place.
     * @param place Place to find
     * @param listener Listener of the result
     * @throws InvalidPlace Throwed when the place is invalid
     */
    public void find(String place, ResultRetrivedListener listener) throws InvalidPlace;
}
