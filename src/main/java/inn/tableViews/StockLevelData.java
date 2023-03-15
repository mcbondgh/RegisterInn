package inn.tableViews;

import javafx.scene.control.Label;

import java.sql.Timestamp;

public class StockLevelData {
    int stockId;
    Label stockItemName;
    int stockLevel;
    int currentStockLevel;
    int currentBoxQuantity;
    int currentQuantityPerBox;
    int oldStockLevel;
    int previousStockLevel;
    int previousBoxQuantity;
    int previousQuantityPerBox;
    int gage;
    String modifiedBy;
    Timestamp lastModified;



    public StockLevelData(int stockId, Label stockItemName, int stockLevel, int currentStockLevel, int currentBoxQuantity, int currentQuantityPerBox, int oldStockLevel, int previousStockLevel, int previousBoxQuantity, int previousQuantityPerBox, int gage, String modifiedBy, Timestamp lastModified) {
        this.stockId = stockId;
        this.stockItemName = stockItemName;
        this.stockLevel = stockLevel;
        this.currentStockLevel = currentStockLevel;
        this.currentBoxQuantity = currentBoxQuantity;
        this.currentQuantityPerBox = currentQuantityPerBox;
        this.oldStockLevel = oldStockLevel;
        this.previousStockLevel = previousStockLevel;
        this.previousBoxQuantity = previousBoxQuantity;
        this.previousQuantityPerBox = previousQuantityPerBox;
        this.gage = gage;
        this.modifiedBy = modifiedBy;
        this.lastModified = lastModified;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public Label getStockItemName() {
        return stockItemName;
    }

    public void setStockItemName(Label stockItemName) {
        this.stockItemName = stockItemName;
    }

    public int getOldStockLevel() {
        return oldStockLevel;
    }

    public void setOldStockLevel(int oldStockLevel) {
        this.oldStockLevel = oldStockLevel;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public int getCurrentStockLevel() {
        return currentStockLevel;
    }

    public void setCurrentStockLevel(int currentStockLevel) {
        this.currentStockLevel = currentStockLevel;
    }

    public int getCurrentBoxQuantity() {
        return currentBoxQuantity;
    }

    public void setCurrentBoxQuantity(int currentBoxQuantity) {
        this.currentBoxQuantity = currentBoxQuantity;
    }

    public int getCurrentQuantityPerBox() {
        return currentQuantityPerBox;
    }

    public void setCurrentQuantityPerBox(int currentQuantityPerBox) {
        this.currentQuantityPerBox = currentQuantityPerBox;
    }

    public int getPreviousStockLevel() {
        return previousStockLevel;
    }

    public void setPreviousStockLevel(int previousStockLevel) {
        this.previousStockLevel = previousStockLevel;
    }

    public int getPreviousBoxQuantity() {
        return previousBoxQuantity;
    }

    public void setPreviousBoxQuantity(int previousBoxQuantity) {
        this.previousBoxQuantity = previousBoxQuantity;
    }

    public int getPreviousQuantityPerBox() {
        return previousQuantityPerBox;
    }

    public void setPreviousQuantityPerBox(int previousQuantityPerBox) {
        this.previousQuantityPerBox = previousQuantityPerBox;
    }

    public int getGage() {
        return gage;
    }

    public void setGage(int gage) {
        this.gage = gage;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public void setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
    }
}//END OF CLASS.........
