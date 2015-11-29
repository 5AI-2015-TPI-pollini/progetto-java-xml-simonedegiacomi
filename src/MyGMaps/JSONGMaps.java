package MyGMaps;

import MyHTTP.DataRetrivedListener;
import MyHTTP.JSONRetriver;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * JSON API Wrapper implementation
 * Created by Degiacomi Simone on 16/11/2015.
 */
public class JSONGMaps extends GMaps{
    private JSONRetriver retriver = new JSONRetriver();

    /**
     * Find the address of a place.
     * @param place Place to find
     * @param listener Listener of the result
     * @throws InvalidPlace Throwed when the place is invalid
     */
    @Override
    public void find(String place, ResultRetrivedListener listener) throws InvalidPlace {
        // Retrive the result
        retriver.retriveResult(GeocodeURLGenerator.generateURL(GeocodeURLGenerator.JSON, place), new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                try {
                    // Get the result array
                    JSONArray jsonAddresses = ((JSONObject) data).getJSONArray("results");
                    // Create the array to contains the addresses
                    Address[] addresses = new Address[jsonAddresses.length()];
                    for(int i = 0; i < addresses.length; i++) {
                        JSONObject jsonAddress = jsonAddresses.getJSONObject(i); // Select the address
                        JSONObject jsonLocation = jsonAddress.getJSONObject("geometry").getJSONObject("location");
                        // Select the coordinate
                        Coordinate coordinate = new Coordinate(jsonLocation.getDouble("lng"), jsonLocation.getDouble("lat"));
                        addresses[i] = new Address(jsonAddress.getString("formatted_address"), coordinate);
                    }
                    // return the addresses
                    listener.onResult(addresses);
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        });
    }
}