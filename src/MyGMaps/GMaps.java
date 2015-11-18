package MyGMaps;

import weatherproject.Config;

/**
 * Created by Simone on 15/11/2015.
 */
public abstract class GMaps {
    public static GMaps getGMaps() {
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
