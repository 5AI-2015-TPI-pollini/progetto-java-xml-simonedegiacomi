package weatherproject;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by Simone on 22/11/2015.
 */
public class EasyProxy {
    public static void setProxyByConfig() {
        Config config = Config.getInstance();
        if(!config.getBoolean("useProxy"))
            return ;
        if(config.getBoolean("authenticateProxy")) {
            Authenticator.setDefault(
                    new Authenticator() {
                        @Override
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(config.getString("proxyUser"), config.getString("proxyPassword").toCharArray());
                        }
                    }
            );
            System.setProperty("http.proxyUser", config.getString("proxyUser"));
            System.setProperty("http.proxyPassword", config.getString("proxyPassword"));
        }
        System.setProperty("http.proxyHost", config.getString("proxyIP"));
        System.setProperty("http.proxyPort", config.getString("proxyPort"));
    }
}