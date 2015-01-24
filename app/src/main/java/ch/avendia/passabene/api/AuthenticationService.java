package ch.avendia.passabene.api;

import com.google.zxing.common.StringUtils;

import ch.avendia.passabene.network.Sender;

/**
 * Created by Markus on 14.01.2015.
 */
public class AuthenticationService {

    private Sender sender = new Sender();

    public boolean authenticate(String username, String password) {

        String url = "https://soaentry.coop.ch/passabene/ws/authenticate";
        String values = "SupercardNumber="+username+"&SupercardPin="+password;

        String result = sender.sendPost(url, values);
        if(result != null && !result.equals("") && result.startsWith("<Return>")) {
            return true;
        }

        return false;
    }


}
