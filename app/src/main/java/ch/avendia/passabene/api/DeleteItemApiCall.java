package ch.avendia.passabene.api;

import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Item;
import ch.avendia.passabene.api.json.Session;

/**
 * Created by Markus on 22.01.2015.
 */
public class DeleteItemApiCall extends ApiCall {

    private final String barcode;
    private final Integer quantity;

    public DeleteItemApiCall(String barcode, Integer quantity) {
        this.barcode = barcode;
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    @Override
    public DTO execute(Session session) {
        if(session == null) {
            return null;
        }

        Item item = shoppingCardHolder.getItemFromBarcode(barcode);

        if(item != null) {
            String url = "RemoveItem?sessionId=" + session.getSessionId() + "&guid=" + item.getGuid() + "&quantity=" + quantity + "&removeLinkedItem=false";

            String json = sender.sendGet(BASE_URL + url);
            return stringToDTO(json);
        }
        return null;
    }
}
