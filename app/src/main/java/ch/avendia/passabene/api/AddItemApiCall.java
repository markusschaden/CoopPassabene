package ch.avendia.passabene.api;

import ch.avendia.passabene.api.json.DTO;
import ch.avendia.passabene.api.json.Session;

/**
 * Created by Markus on 22.01.2015.
 */
public class AddItemApiCall extends ApiCall {
    private Integer quantity;
    private String barcode;

    public AddItemApiCall(String barcode, Integer quantity) {
        this.barcode = barcode;
        this.quantity = quantity;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public DTO execute(Session session) {
        if(session == null) {
            return null;
        }

        String url = "AddItem?sessionId="+session.getSessionId()+"&barcode="+barcode+"&quantity="+quantity+"&linkedBarcode=";

        String json = sender.sendGet(BASE_URL + url);
        return stringToDTO(json);
    }
}
