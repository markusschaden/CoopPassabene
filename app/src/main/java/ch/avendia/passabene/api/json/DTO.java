
package ch.avendia.passabene.api.json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class DTO {

    @Expose
    private ch.avendia.passabene.api.json.MyModuleInfo MyModuleInfo;
    @Expose
    private ch.avendia.passabene.api.json.Session Session;
    @Expose
    private Object Dialog;
    @Expose
    private ch.avendia.passabene.api.json.ItemInfo ItemInfo;
    @Expose
    private ch.avendia.passabene.api.json.Totals Totals;
    @Expose
    private ch.avendia.passabene.api.json.Ticket Ticket;
    @Expose
    private Object Coupons;
    @Expose
    private Object ShoppingList;
    @Expose
    private List<Object> EventInformation = new ArrayList<Object>();
    @Expose
    private List<ch.avendia.passabene.api.json.ParameterInformation> ParameterInformation = new ArrayList<ch.avendia.passabene.api.json.ParameterInformation>();
    @Expose
    private Boolean Result;
    @Expose
    private String Text;

    /**
     * 
     * @return
     *     The MyModuleInfo
     */
    public ch.avendia.passabene.api.json.MyModuleInfo getMyModuleInfo() {
        return MyModuleInfo;
    }

    /**
     * 
     * @param MyModuleInfo
     *     The MyModuleInfo
     */
    public void setMyModuleInfo(ch.avendia.passabene.api.json.MyModuleInfo MyModuleInfo) {
        this.MyModuleInfo = MyModuleInfo;
    }

    /**
     * 
     * @return
     *     The Session
     */
    public ch.avendia.passabene.api.json.Session getSession() {
        return Session;
    }

    /**
     * 
     * @param Session
     *     The Session
     */
    public void setSession(ch.avendia.passabene.api.json.Session Session) {
        this.Session = Session;
    }

    /**
     * 
     * @return
     *     The Dialog
     */
    public Object getDialog() {
        return Dialog;
    }

    /**
     * 
     * @param Dialog
     *     The Dialog
     */
    public void setDialog(Object Dialog) {
        this.Dialog = Dialog;
    }

    /**
     * 
     * @return
     *     The ItemInfo
     */
    public ch.avendia.passabene.api.json.ItemInfo getItemInfo() {
        return ItemInfo;
    }

    /**
     * 
     * @param ItemInfo
     *     The ItemInfo
     */
    public void setItemInfo(ch.avendia.passabene.api.json.ItemInfo ItemInfo) {
        this.ItemInfo = ItemInfo;
    }

    /**
     * 
     * @return
     *     The Totals
     */
    public ch.avendia.passabene.api.json.Totals getTotals() {
        return Totals;
    }

    /**
     * 
     * @param Totals
     *     The Totals
     */
    public void setTotals(ch.avendia.passabene.api.json.Totals Totals) {
        this.Totals = Totals;
    }

    /**
     * 
     * @return
     *     The Ticket
     */
    public ch.avendia.passabene.api.json.Ticket getTicket() {
        return Ticket;
    }

    /**
     * 
     * @param Ticket
     *     The Ticket
     */
    public void setTicket(ch.avendia.passabene.api.json.Ticket Ticket) {
        this.Ticket = Ticket;
    }

    /**
     * 
     * @return
     *     The Coupons
     */
    public Object getCoupons() {
        return Coupons;
    }

    /**
     * 
     * @param Coupons
     *     The Coupons
     */
    public void setCoupons(Object Coupons) {
        this.Coupons = Coupons;
    }

    /**
     * 
     * @return
     *     The ShoppingList
     */
    public Object getShoppingList() {
        return ShoppingList;
    }

    /**
     * 
     * @param ShoppingList
     *     The ShoppingList
     */
    public void setShoppingList(Object ShoppingList) {
        this.ShoppingList = ShoppingList;
    }

    /**
     * 
     * @return
     *     The EventInformation
     */
    public List<Object> getEventInformation() {
        return EventInformation;
    }

    /**
     * 
     * @param EventInformation
     *     The EventInformation
     */
    public void setEventInformation(List<Object> EventInformation) {
        this.EventInformation = EventInformation;
    }

    /**
     * 
     * @return
     *     The ParameterInformation
     */
    public List<ch.avendia.passabene.api.json.ParameterInformation> getParameterInformation() {
        return ParameterInformation;
    }

    /**
     * 
     * @param ParameterInformation
     *     The ParameterInformation
     */
    public void setParameterInformation(List<ch.avendia.passabene.api.json.ParameterInformation> ParameterInformation) {
        this.ParameterInformation = ParameterInformation;
    }

    /**
     * 
     * @return
     *     The Result
     */
    public Boolean getResult() {
        return Result;
    }

    /**
     * 
     * @param Result
     *     The Result
     */
    public void setResult(Boolean Result) {
        this.Result = Result;
    }

    /**
     * 
     * @return
     *     The Text
     */
    public String getText() {
        return Text;
    }

    /**
     * 
     * @param Text
     *     The Text
     */
    public void setText(String Text) {
        this.Text = Text;
    }

}
