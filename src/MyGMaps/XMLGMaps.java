package MyGMaps;

import MyHTTP.DataRetrivedListener;
import MyHTTP.XMLRetriver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.soap.Node;
import javax.xml.xpath.*;


/**
 * This class is used to call the Google Maps Geocode API using XML
 * Created by Simone on 15/11/2015.
 */
public class XMLGMaps extends GMaps {
    /**
     * XPath query to select the formatted address
     */
    private static final String QUERY_FORMATTED = "/GeocodeResponse/result/formatted_address/text()";
    private static final String QUERY_LATITUDE = "/GeocodeResponse/result/geometry/location/lat/text()";
    private static final String QUERY_LONGITUDE = "/GeocodeResponse/result/geometry/location/lng/text()";
    /**
     * XPath factory
     */
    private static final XPathFactory xpathFactory = XPathFactory.newInstance();

    public XMLGMaps() {}

    @Override
    public void find(String place, ResultRetrivedListener listener) throws InvalidPlace {
        XMLRetriver retriver = new XMLRetriver(GeocodeURLGenerator.generateURL(GeocodeURLGenerator.XML, place));
        // Retrive the XML
        retriver.retriveResult(new DataRetrivedListener() {
            @Override
            public void onResult(Object data) {
                Document xml = (Document) data;
                // Create xpath
                XPath xpath = xpathFactory.newXPath();
                try {
                    // Prepare the queries
                    XPathExpression formattedAddress = xpath.compile(QUERY_FORMATTED);
                    XPathExpression lat = xpath.compile(QUERY_LATITUDE);
                    XPathExpression lon = xpath.compile(QUERY_LONGITUDE);
                    // Execute the xpath query
                    NodeList formattedAddresses = (NodeList) formattedAddress.evaluate(xml, XPathConstants.NODESET);
                    NodeList lats = (NodeList) lat.evaluate(xml, XPathConstants.NODESET);
                    NodeList lons = (NodeList) lon.evaluate(xml, XPathConstants.NODESET);
                    Address[] addresses = new Address[formattedAddresses.getLength()];
                    for (int i = 0; i < addresses.length; i++) {
                        Coordinate coordinate = new Coordinate(lons.item(i).getNodeValue(), lats.item(i).getNodeValue());
                        addresses[i] = new Address(formattedAddresses.item(i).getNodeValue(), coordinate);
                    }
                    listener.onResult(addresses);
                } catch (Exception ex) {
                    listener.onResult(null);
                }
            }
        });
    }
}
