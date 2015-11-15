package MyGMaps;

import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Simone on 14/11/2015.
 */
public class GeocodeURLGenerator {
    public static final int XML = 0;
    public static final int JSON = 1;
    private static final String geocodeURL = "http://maps.googleapis.com/maps/api/geocode/";
    private static final String RESULT_TYPE[] = {"xml", "json"};
    private static final String ADDRESS_QUERY_PREFIX = "?address=";


    public static URL generateURL (int resultType, String placeToFind) throws Exception {
        StringBuilder url = new StringBuilder(geocodeURL);
        // Append the result type
        url.append(RESULT_TYPE[resultType]);
        url.append(ADDRESS_QUERY_PREFIX);
        url.append(URLEncoder.encode(placeToFind, "UTF-8"));
        return new URL(url.toString());
    }
}
