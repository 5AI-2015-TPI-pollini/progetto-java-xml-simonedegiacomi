package MyGMaps;

import weatherproject.Config;

/**
 * Abstact class of GMaps API Wrapper
 * Created by Simone on 15/11/2015.
 */
public abstract class GMaps {
    /**
     * Create a new GMaps, selecting the type reading the configuration
     * @return The new GMap instance
     */
    public static GMaps createGMaps() {
        switch(Config.getInstance().getDataType()) {
            case Config.XML:
                return new XMLGMaps();
            case Config.JSON:
                return new JSONGMaps();
        }
        return null;
    }

    /**
     * Find the address of a place.
     * @param place Place to find
     * @param listener Listener of the result
     * @throws InvalidPlace Throwed when the place is invalid
     */
    public abstract void find(String place, ResultRetrivedListener listener) throws InvalidPlace;
}
