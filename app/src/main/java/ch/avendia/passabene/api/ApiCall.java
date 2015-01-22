package ch.avendia.passabene.api;

import com.google.gson.Gson;

import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Session;
import ch.avendia.passabene.network.Sender;
import ch.avendia.passabene.shopping.ShoppingCardHolder;

/**
 * Created by Markus on 22.01.2015.
 */
public abstract class ApiCall {

    protected final String BASE_URL = "http://smartphopb.coop.ch/SelfScanWebService/SelfScanAPI.mdc/";

    protected Sender sender = new Sender();
    private static Gson gson  = new Gson();
    protected ShoppingCardHolder shoppingCardHolder = new ShoppingCardHolder();

    public DTO stringToDTO(String  string) {
        return gson.fromJson(string, DTO.class);
    }

    public abstract DTO execute(Session session);
}
