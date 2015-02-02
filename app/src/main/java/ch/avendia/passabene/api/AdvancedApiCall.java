package ch.avendia.passabene.api;

import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Session;
import ch.avendia.passabene.shopping.ShoppingCardHolder;

/**
 * Created by Markus on 22.01.2015.
 */
public abstract class AdvancedApiCall extends ApiCall {

    public abstract DTO execute(Session session);
    public abstract void doUpdate(DTO dto, boolean blockedMode);

    //todo: change implementation if catalog is available
    protected ShoppingCardHolder shoppingCardHolder = new ShoppingCardHolder();



}
