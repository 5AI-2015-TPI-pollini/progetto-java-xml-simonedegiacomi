package MyGMaps;

/**
 * Listener to implement, used as callback for the retrived result
 * Created by Degiacomi Simone on 15/11/2015.
 */
public interface ResultRetrivedListener {
    /**
     * Method called when the result is retrived
     * @param result Arrays of finded addresses
     */
    public void onResult(Address[] result);
}
