package MyGMaps;

import MyHTTP.DataRetrivedListener;
import MyHTTP.JSONRetriver;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Simone on 16/11/2015.
 */
public class JSONGMaps extends GMaps{

    @Override
    public void find(String place, ResultRetrivedListener listener) throws InvalidPlace {
        JSONRetriver retriver = new JSONRetriver(GeocodeURLGenerator.generateURL(GeocodeURLGenerator.JSON, place));
        retriver.retriveResult(new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                try {
                    JSONArray jsonAddresses = ((JSONObject) data).getJSONArray("results");
                    Address[] addresses = new Address[jsonAddresses.length()];
                    for(int i = 0; i < addresses.length; i++) {
                        JSONObject jsonAddress = jsonAddresses.getJSONObject(i);
                        JSONObject jsonLocation = jsonAddress.getJSONObject("geometry").getJSONObject("location");
                        Coordinate coordinate = new Coordinate(jsonLocation.getDouble("lng"), jsonLocation.getDouble("lat"));
                        addresses[i] = new Address(jsonAddress.getString("formatted_address"), coordinate);
                    }
                    listener.onResult(addresses);
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        });
    }
}