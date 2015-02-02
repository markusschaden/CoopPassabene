package ch.avendia.passabene.api;

import ch.avendia.passabene.Language;
import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Session;

/**
 * Created by Markus on 22.01.2015.
 */
public class StartSessionApiCall extends BasicApiCall {

    private String username;
    private String storeNumber;
    private Language language;

    public StartSessionApiCall(String username, String storeNumber, Language language) {
        this.username = username;
        this.storeNumber = storeNumber;
        this.language = language;
    }

    @Override
    public DTO execute() {

        String url = "StartSessionEx?cardNumber="+username+"&storeNumber="+storeNumber+"&language="+language.toString()+"&sdkVersion=2.5.2.4&originType=iPhone";

        String json = sender.sendGet(BASE_URL + url);
        return stringToDTO(json);
    }
}
