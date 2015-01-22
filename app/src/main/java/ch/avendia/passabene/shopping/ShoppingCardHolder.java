package ch.avendia.passabene.shopping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.avendia.passabene.api.json.Item;

/**
 * Created by Markus on 14.01.2015.
 */
public class ShoppingCardHolder {

    private static List<Item> shoppingCart = new ArrayList<Item>();
    private static Map<String, Item> indexedCart = new HashMap<String, Item>();
    private static boolean first = true;

    public ShoppingCardHolder() {
        if(first) {
            first = false;
            addItem(new Item("Test Produkt 1", 100));
            addItem(new Item("Test Produkt 2", 5500));
            addItem(new Item("Test Produkt 3", 32000));
            addItem(new Item("Test Produkt 10", 32000));

        }
    }



    public List<Item> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List<Item> shoppingCart) {
        synchronized (ShoppingCardHolder.class) {
            this.shoppingCart = shoppingCart;
            indexedCart = new HashMap<String, Item>();
            for(Item item : shoppingCart) {
                indexedCart.put(item.getBarcode(), item);
            }
        }
    }


    public Item getItemFromBarcode(String id) {
        if(indexedCart.containsKey(id)) {
            return indexedCart.get(id);
        }
        return null;
    }

    public Item getItemFromId(int index) {
        return shoppingCart.get(index);
    }

    public void removeItem(Item item) {
        indexedCart.remove(item.getBarcode());
        shoppingCart.remove(item);
    }

    public void addItem(Item item) {
        indexedCart.put(item.getBarcode(), item);
        shoppingCart.add(item);
    }
}
