
package ch.avendia.passabene.api.json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Ticket {

    @Expose
    private List<Item> Items = new ArrayList<Item>();

    /**
     * 
     * @return
     *     The Items
     */
    public List<Item> getItems() {
        return Items;
    }

    /**
     * 
     * @param Items
     *     The Items
     */
    public void setItems(List<Item> Items) {
        this.Items = Items;
    }

}
