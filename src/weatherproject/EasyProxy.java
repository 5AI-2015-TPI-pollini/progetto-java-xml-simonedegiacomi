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
        else
            System.setProperty("proxySet", "true");
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