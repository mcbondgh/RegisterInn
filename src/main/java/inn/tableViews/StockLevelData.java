package inn.tableViews;

import java.sql.Timestamp;

public class StockLevelData {

    int itemId;
    String productName;
    int stockLevel;
    int currentQty;
    int  PresentUnitQty;
    int PresentPackQty;
    int PresentPackPerQty;
    int PreviousUnitQty;
    int PreviousPackQty;
    int PreviousPackPerQty;
    int BeforeUnitQty;
    int BeforePackQty;
    int BeforePerPackQty;
    int StockGuage;
    String UpdatedBy;
    Timestamp UpdatedDate;


    public StockLevelData() {}

    public StockLevelData(int itemId, String productName, int stockLevel, int currentQty, int presentUnitQty, int presentPackQty, int presentPackPerQty, int previousUnitQty, int previousPackQty, int previousPackPerQty, int beforeUnitQty, int beforePackQty, int beforePerPackQty, int stockGuage, String updatedBy, Timestamp updatedDate) {
        this.itemId = itemId;
        this.productName = productName;
        this.stockLevel = stockLevel;
        this.currentQty = currentQty;
        PresentUnitQty = presentUnitQty;
        PresentPackQty = presentPackQty;
        PresentPackPerQty = presentPackPerQty;
        PreviousUnitQty = previousUnitQty;
        PreviousPackQty = previousPackQty;
        PreviousPackPerQty = previousPackPerQty;
        BeforeUnitQty = beforeUnitQty;
        BeforePackQty = beforePackQty;
        BeforePerPackQty = beforePerPackQty;
        StockGuage = stockGuage;
        UpdatedBy = updatedBy;
        UpdatedDate = updatedDate;
    }


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public int getCurrentQty() {
        return currentQty;
    }

    public void setCurrentQty(int currentQty) {
        this.currentQty = currentQty;
    }

    public int getPresentUnitQty() {
        return PresentUnitQty;
    }

    public void setPresentUnitQty(int presentUnitQty) {
        PresentUnitQty = presentUnitQty;
    }

    public int getPresentPackQty() {
        return PresentPackQty;
    }

    public void setPresentPackQty(int presentPackQty) {
        PresentPackQty = presentPackQty;
    }

    public int getPresentPackPerQty() {
        return PresentPackPerQty;
    }

    public void setPresentPackPerQty(int presentPackPerQty) {
        PresentPackPerQty = presentPackPerQty;
    }

    public int getPreviousUnitQty() {
        return PreviousUnitQty;
    }

    public void setPreviousUnitQty(int previousUnitQty) {
        PreviousUnitQty = previousUnitQty;
    }

    public int getPreviousPackQty() {
        return PreviousPackQty;
    }

    public void setPreviousPackQty(int previousPackQty) {
        PreviousPackQty = previousPackQty;
    }

    public int getPreviousPackPerQty() {
        return PreviousPackPerQty;
    }

    public void setPreviousPackPerQty(int previousPackPerQty) {
        PreviousPackPerQty = previousPackPerQty;
    }

    public int getBeforeUnitQty() {
        return BeforeUnitQty;
    }

    public void setBeforeUnitQty(int beforeUnitQty) {
        BeforeUnitQty = beforeUnitQty;
    }

    public int getBeforePackQty() {
        return BeforePackQty;
    }

    public void setBeforePackQty(int beforePackQty) {
        BeforePackQty = beforePackQty;
    }

    public int getBeforePerPackQty() {
        return BeforePerPackQty;
    }

    public void setBeforePerPackQty(int beforePerPackQty) {
        BeforePerPackQty = beforePerPackQty;
    }

    public int getStockGuage() {
        return StockGuage;
    }

    public void setStockGuage(int stockGuage) {
        StockGuage = stockGuage;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }

    public Timestamp getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        UpdatedDate = updatedDate;
    }
}//END OF CLASS.........
