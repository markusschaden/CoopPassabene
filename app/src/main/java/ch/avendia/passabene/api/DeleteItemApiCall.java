package ch.avendia.passabene.api;

import android.util.Log;

import ch.avendia.passabene.Constants;
import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Item;
import ch.avendia.passabene.api.json.Session;

/**
 * Created by Markus on 22.01.2015.
 */
public class DeleteItemApiCall extends AdvancedApiCall {

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

        Item item = shoppingCardHolder.getRemoteItemFromBarcode(barcode);

        if(item != null) {
            String url = "RemoveItem?sessionId=" + session.getSessionId() + "&guid=" + item.getGuid() + "&quantity=" + quantity + "&removeLinkedItem=false";

            String json = sender.sendGet(BASE_URL + url);
            return stringToDTO(json);
        } else {
            Log.e(Constants.TAG, "Can't find item in RemoteIndexedList, " + barcode);
            throw new RuntimeException("Can't find item in RemoteIndexedList, " + barcode);
        }
        //return null;
    }

    @Override
    public void doUpdate(DTO dto, boolean blockedMode) {
        if (dto != null && dto.getTicket() != null && dto.getTicket().getItems() != null) {
            shoppingCardHolder.setRemoteShoppingCart(dto.getTicket().getItems());
            if(blockedMode) {
                shoppingCardHolder.setShoppingCart(dto.getTicket().getItems());
            }
        }
    }
}
