package ch.avendia.passabene.shopping;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Markus on 12.01.2015.
 */
public class ShoppingCart {

    private List<ShoppingCartItem> items = new ArrayList<ShoppingCartItem>();

    public double getTotalPrice() {
        double totalPrice = 0;
        for(ShoppingCartItem item : items) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }
        return totalPrice;
    }

    public void addProduct(Product product) {
        items.add(new ShoppingCartItem(product));
    }

    public void setQuantity(Product product, int quantity) {
        if(quantity <= 0) {
            for(int i=0;i<items.size();i++) {
                if (items.get(i).equals(product)) {
                    items.remove(i);
                    break;
                }
            }
        } else {
            for (ShoppingCartItem item : items) {
                if (item.equals(product)) {
                    item.setQuantity(quantity);
                }

            }
        }
    }
}
