package MyGMaps;

import java.net.URL;
import java.net.URLEncoder;

/**
 * This class create the url to search the address information
 * Created by Simone on 14/11/2015.
 */
public class GeocodeURLGenerator {
    /**
     * XML Response
     */
    public static final int XML = 0;
    /**
     * JSON Response
     */
    public static final int JSON = 1;
    /**
     * Prefix of the url
     */
    private static final String geocodeURL = "http://maps.googleapis.com/maps/api/geocode/";
    /**
     * Flag to append in the url to specify the response type
     */
    private static final String RESULT_TYPE[] = {"xml", "json"};
    /**
     * Prefix to append in the url to specify the address ti find
     */
    private static final String ADDRESS_QUERY_PREFIX = "?address=";

    /**
     * Generate the Geocode url given the response type and a place
     * @param resultType Response type
     * @param placeToFind Place to find
     * @return The Geocode url
     * @throws Exception Exception throwed if the place is not valid
     */
    public static URL generateURL (int resultType, String placeToFind) throws InvalidPlace {
        StringBuilder url = new StringBuilder(geocodeURL);
        // Append the result type
        url.append(RESULT_TYPE[resultType]);
        url.append(ADDRESS_QUERY_PREFIX);
        try {
            url.append(URLEncoder.encode(placeToFind, "UTF-8"));
            return new URL(url.toString());
        } catch (Exception ex) {
            throw new InvalidPlace(placeToFind);
        }
    }
}
