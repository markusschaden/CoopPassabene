package ch.avendia.passabene.shopping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.avendia.passabene.api.json.Item;
import ch.avendia.passabene.api.json.ItemInfo;

/**
 * Created by Markus on 14.01.2015.
 */
public class ShoppingCardHolder {

    private static List<Item> shoppingCart = new ArrayList<Item>();
    private static Map<String, Item> indexedCart = new HashMap<String, Item>();

    private static List<Item> remoteShoppingCart = new ArrayList<Item>();
    private static Map<String, Item> remoteIndexedCart = new HashMap<String, Item>();
    private static Item lastItem = null;

    private static boolean first = true;

    public ShoppingCardHolder() {
        if(first) {
            first = false;
            addLocalItem(new Item("Test Produkt 1", 100));
            addLocalItem(new Item("Test Produkt 2", 5500));
            addLocalItem(new Item("Test Produkt 3", 32000));
            addLocalItem(new Item("Test Produkt 10", 32000));

        }
    }

    public static Item getLastItem() {
        return lastItem;
    }

    public static void setLastItem(Item lastItem) {
        ShoppingCardHolder.lastItem = lastItem;
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

    public void setRemoteShoppingCart(List<Item> shoppingCart) {
        synchronized (ShoppingCardHolder.class) {
            this.remoteShoppingCart = shoppingCart;
            remoteIndexedCart = new HashMap<String, Item>();
            for(Item item : shoppingCart) {
                remoteIndexedCart.put(item.getBarcode(), item);
            }
        }
    }


    public Item getLocalItemFromBarcode(String id) {
        if(indexedCart.containsKey(id)) {
            return indexedCart.get(id);
        }
        return null;
    }

    public Item getRemoteItemFromBarcode(String id) {
        if(remoteIndexedCart.containsKey(id)) {
            return remoteIndexedCart.get(id);
        }
        return null;
    }

    public Item getLocalItemFromId(int index) {
        return shoppingCart.get(index);
    }

    public void removeLocalItem(Item item) {
        synchronized (ShoppingCardHolder.class) {
            indexedCart.remove(item.getBarcode());
            shoppingCart.remove(item);
        }
    }

    public void addLocalItem(Item item) {
        synchronized (ShoppingCardHolder.class) {
            indexedCart.put(item.getBarcode(), item);
            shoppingCart.add(item);
        }
        lastItem = item;
    }

    public void setLastItem(ItemInfo itemInfo) {
        if(itemInfo == null) {
            return;
        }

        synchronized (ShoppingCardHolder.class) {
            lastItem = new Item();
            lastItem.setQuantity(itemInfo.getQuantity());
            lastItem.setDescription(itemInfo.getDescription());
            lastItem.setPrice(itemInfo.getPrice());
            lastItem.setAdditionalInformation(itemInfo.getAdditionalInformation());
            lastItem.setBarcode(itemInfo.getBarcode());
            lastItem.setAllowMultiply(itemInfo.getAllowMultiply());
            lastItem.setDeposit(itemInfo.getDeposit());
            lastItem.setDiscount(itemInfo.getDiscount());
            lastItem.setGuid(itemInfo.getGuid());
            lastItem.setLinkGuid(itemInfo.getLinkGuid());
            lastItem.setLinkedItems(itemInfo.getLinkedItems());
            lastItem.setPoints(itemInfo.getPoints());
            lastItem.setPiecePrice(itemInfo.getPiecePrice());
            lastItem.setIsVisible(itemInfo.getIsVisible());
            lastItem.setIsAllowed(itemInfo.getIsAllowed());
        }
    }
}
