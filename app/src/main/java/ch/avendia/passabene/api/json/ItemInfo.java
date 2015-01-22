
package ch.avendia.passabene.api.json;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ItemInfo {

    @Expose
    private Boolean IsAllowed;
    @Expose
    private Boolean AllowMultiply;
    @Expose
    private String Barcode;
    @Expose
    private String Description;
    @Expose
    private List<Object> AdditionalInformation = new ArrayList<Object>();
    @Expose
    private Integer Discount;
    @Expose
    private Integer Deposit;
    @Expose
    private Integer PiecePrice;
    @Expose
    private Integer Points;
    @Expose
    private Integer Price;
    @Expose
    private Integer Quantity;
    @Expose
    private String Guid;
    @Expose
    private Object LinkGuid;
    @Expose
    private List<Object> LinkedItems = new ArrayList<Object>();
    @Expose
    private Integer MultiPackCount;
    @Expose
    private Boolean IsVisible;

    /**
     * 
     * @return
     *     The IsAllowed
     */
    public Boolean getIsAllowed() {
        return IsAllowed;
    }

    /**
     * 
     * @param IsAllowed
     *     The IsAllowed
     */
    public void setIsAllowed(Boolean IsAllowed) {
        this.IsAllowed = IsAllowed;
    }

    /**
     * 
     * @return
     *     The AllowMultiply
     */
    public Boolean getAllowMultiply() {
        return AllowMultiply;
    }

    /**
     * 
     * @param AllowMultiply
     *     The AllowMultiply
     */
    public void setAllowMultiply(Boolean AllowMultiply) {
        this.AllowMultiply = AllowMultiply;
    }

    /**
     * 
     * @return
     *     The Barcode
     */
    public String getBarcode() {
        return Barcode;
    }

    /**
     * 
     * @param Barcode
     *     The Barcode
     */
    public void setBarcode(String Barcode) {
        this.Barcode = Barcode;
    }

    /**
     * 
     * @return
     *     The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * 
     * @param Description
     *     The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     * 
     * @return
     *     The AdditionalInformation
     */
    public List<Object> getAdditionalInformation() {
        return AdditionalInformation;
    }

    /**
     * 
     * @param AdditionalInformation
     *     The AdditionalInformation
     */
    public void setAdditionalInformation(List<Object> AdditionalInformation) {
        this.AdditionalInformation = AdditionalInformation;
    }

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
     *     The PiecePrice
     */
    public Integer getPiecePrice() {
        return PiecePrice;
    }

    /**
     * 
     * @param PiecePrice
     *     The PiecePrice
     */
    public void setPiecePrice(Integer PiecePrice) {
        this.PiecePrice = PiecePrice;
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
     *     The Price
     */
    public Integer getPrice() {
        return Price;
    }

    /**
     * 
     * @param Price
     *     The Price
     */
    public void setPrice(Integer Price) {
        this.Price = Price;
    }

    /**
     * 
     * @return
     *     The Quantity
     */
    public Integer getQuantity() {
        return Quantity;
    }

    /**
     * 
     * @param Quantity
     *     The Quantity
     */
    public void setQuantity(Integer Quantity) {
        this.Quantity = Quantity;
    }

    /**
     * 
     * @return
     *     The Guid
     */
    public String getGuid() {
        return Guid;
    }

    /**
     * 
     * @param Guid
     *     The Guid
     */
    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    /**
     * 
     * @return
     *     The LinkGuid
     */
    public Object getLinkGuid() {
        return LinkGuid;
    }

    /**
     * 
     * @param LinkGuid
     *     The LinkGuid
     */
    public void setLinkGuid(Object LinkGuid) {
        this.LinkGuid = LinkGuid;
    }

    /**
     * 
     * @return
     *     The LinkedItems
     */
    public List<Object> getLinkedItems() {
        return LinkedItems;
    }

    /**
     * 
     * @param LinkedItems
     *     The LinkedItems
     */
    public void setLinkedItems(List<Object> LinkedItems) {
        this.LinkedItems = LinkedItems;
    }

    /**
     * 
     * @return
     *     The MultiPackCount
     */
    public Integer getMultiPackCount() {
        return MultiPackCount;
    }

    /**
     * 
     * @param MultiPackCount
     *     The MultiPackCount
     */
    public void setMultiPackCount(Integer MultiPackCount) {
        this.MultiPackCount = MultiPackCount;
    }

    /**
     * 
     * @return
     *     The IsVisible
     */
    public Boolean getIsVisible() {
        return IsVisible;
    }

    /**
     * 
     * @param IsVisible
     *     The IsVisible
     */
    public void setIsVisible(Boolean IsVisible) {
        this.IsVisible = IsVisible;
    }

}
