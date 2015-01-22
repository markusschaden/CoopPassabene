package ch.avendia.passabene.shopping;

/**
 * Created by Markus on 12.01.2015.
 */
public class ShoppingCartItem {

    private Product product;
    private int quantity;

    public ShoppingCartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
