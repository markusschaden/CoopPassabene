package ch.avendia.passabene.api;

import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Session;

/**
 * Created by Markus on 22.01.2015.
 */
public class GetStoreApiCall extends BasicApiCall {
    private double lat;
    private double lon;

    public GetStoreApiCall(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public DTO execute() {

        String url = "GetStoreNumber?geoLocation=%2B"+lat+"%3A%2B"+lon;

        String json = sender.sendGet(BASE_URL + url);
        return stringToDTO(json);
    }
}
