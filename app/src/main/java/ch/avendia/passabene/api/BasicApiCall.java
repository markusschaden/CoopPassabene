package ch.avendia.passabene.api;

import com.google.gson.Gson;

import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Session;
import ch.avendia.passabene.network.Sender;
import ch.avendia.passabene.shopping.ShoppingCardHolder;

/**
 * Created by Markus on 22.01.2015.
 */
public abstract class BasicApiCall extends ApiCall {

    public DTO execute(Session session) {
        throw new RuntimeException("operation not permitted");
    }

    public abstract DTO execute();
}
