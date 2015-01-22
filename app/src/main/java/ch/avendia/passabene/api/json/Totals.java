
package ch.avendia.passabene.api.json;

import com.google.gson.annotations.Expose;

public class Totals {

    @Expose
    private Integer Discount;
    @Expose
    private Integer Deposit;
    @Expose
    private Integer NumItems;
    @Expose
    private Integer Points;
    @Expose
    private Integer Subtotal;

    /**
     * 
     * @return
     *     The Discount
     */
    public Integer getDiscount() {
        return Discount;
    }

    /**
     * 
     * @param Discount
     *     The Discount
     */
    public void setDiscount(Integer Discount) {
        this.Discount = Discount;
    }

    /**
     * 
     * @return
     *     The Deposit
     */
    public Integer getDeposit() {
        return Deposit;
    }

    /**
     * 
     * @param Deposit
     *     The Deposit
     */
    public void setDeposit(Integer Deposit) {
        this.Deposit = Deposit;
    }

    /**
     * 
     * @return
     *     The NumItems
     */
    public Integer getNumItems() {
        return NumItems;
    }

    /**
     * 
     * @param NumItems
     *     The NumItems
     */
    public void setNumItems(Integer NumItems) {
        this.NumItems = NumItems;
    }

    /**
     * 
     * @return
     *     The Points
     */
    public Integer getPoints() {
        return Points;
    }

    /**
     * 
     * @param Points
     *     The Points
     */
    public void setPoints(Integer Points) {
        this.Points = Points;
    }

    /**
     * 
     * @return
     *     The Subtotal
     */
    public Integer getSubtotal() {
        return Subtotal;
    }

    /**
     * 
     * @param Subtotal
     *     The Subtotal
     */
    public void setSubtotal(Integer Subtotal) {
        this.Subtotal = Subtotal;
    }

}
