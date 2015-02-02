package ch.avendia.passabene.shopping;

import java.util.List;

import ch.avendia.passabene.api.json.Item;

/**
 * Created by Markus on 02.02.2015.
 */
public interface ShoppingCard {
    public List<Item> getShoppingCart();
    public void setShoppingCart(List<Item> shoppingCart);
    public Item getItemFromBarcode(String id);
    public Item getItemFromId(int index);
    public void removeItem(Item item);
    public void addItem(Item item);
}
