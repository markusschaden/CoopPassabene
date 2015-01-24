package ch.avendia.passabene.api;

import ch.avendia.passabene.api.json.DTO;

/**
 * Created by Markus on 22.01.2015.
 */
public class GetStatusApiCall extends BasicApiCall {

    @Override
    public DTO execute() {

        String url = "GetStatus?appVersion=2.0.0";

        String json = sender.sendGet(BASE_URL + url);
        return stringToDTO(json);
    }
}
