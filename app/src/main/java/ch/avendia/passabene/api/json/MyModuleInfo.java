
package ch.avendia.passabene.api.json;

import com.google.gson.annotations.Expose;

public class MyModuleInfo {

    @Expose
    private Boolean CouponsAvailable;
    @Expose
    private Boolean ShoppingListsAvailable;
    @Expose
    private Object ShoppingListNames;

    /**
     * 
     * @return
     *     The CouponsAvailable
     */
    public Boolean getCouponsAvailable() {
        return CouponsAvailable;
    }

    /**
     * 
     * @param CouponsAvailable
     *     The CouponsAvailable
     */
    public void setCouponsAvailable(Boolean CouponsAvailable) {
        this.CouponsAvailable = CouponsAvailable;
    }

    /**
     * 
     * @return
     *     The ShoppingListsAvailable
     */
    public Boolean getShoppingListsAvailable() {
        return ShoppingListsAvailable;
    }

    /**
     * 
     * @param ShoppingListsAvailable
     *     The ShoppingListsAvailable
     */
    public void setShoppingListsAvailable(Boolean ShoppingListsAvailable) {
        this.ShoppingListsAvailable = ShoppingListsAvailable;
    }

    /**
     * 
     * @return
     *     The ShoppingListNames
     */
    public Object getShoppingListNames() {
        return ShoppingListNames;
    }

    /**
     * 
     * @param ShoppingListNames
     *     The ShoppingListNames
     */
    public void setShoppingListNames(Object ShoppingListNames) {
        this.ShoppingListNames = ShoppingListNames;
    }

}
