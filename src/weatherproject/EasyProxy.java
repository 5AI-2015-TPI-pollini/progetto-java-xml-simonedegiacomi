package weatherproject;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * This class expones a method used to set the
 * proxy configuration
 * Created by Degiacomi Simone on 22/11/2015.
 */
public class EasyProxy {
    /**
     * Apply the proxy configurationfrom the Config class.
    **/
    public static void setProxyByConfig() {
        // Get the config instance
        Config config = Config.getInstance();
        // Apply the configuration
        boolean useProxy = config.getBoolean("useProxy");
        System.setProperty("proxySet", String.valueOf(useProxy));
        if(!useProxy)
            return ;
        System.setProperty("http.proxyHost", config.getString("proxyIP"));
        System.setProperty("http.proxyPort", config.getString("proxyPort"));
        if(config.getBoolean("authenticateProxy"))
            Authenticator.setDefault(
                    new Authenticator() {
                        @Override
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(config.getString("proxyUser"), config.getString("proxyPassword").toCharArray());
                        }
                    }
            );
    }
}