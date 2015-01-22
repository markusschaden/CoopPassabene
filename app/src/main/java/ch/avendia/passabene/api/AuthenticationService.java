package ch.avendia.passabene.api;

import com.google.zxing.common.StringUtils;

/**
 * Created by Markus on 14.01.2015.
 */
public class AuthenticationService {

    private RequestHandler requestHandler;

    public AuthenticationService() {

    }

    public boolean authenticate(String username, String password) {

        requestHandler = new RequestHandler();
        String url = "https://soaentry.coop.ch/passabene/ws/authenticate";
        String values = "SupercardNumber={0}&SupercardPin={1}";

        String result = requestHandler.sendPostRequest(url, values);
        if(result != null && !result.equals("") && result.startsWith("<Return>")) {
            return true;
        }

        return false;
    }


}
