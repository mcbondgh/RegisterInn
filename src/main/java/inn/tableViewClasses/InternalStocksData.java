package inn.tableViewClasses;

import java.sql.Timestamp;

public class InternalStocksData {
    int itemId;
    String internalItemName;
    String internalItemCategory;
    int remainingQuantity;
    int currentQuantity;
    int previousQuantity;
    double totalCostPrice;
    Timestamp dateCreated;
    String addedBy;
    Boolean isDeleted;
    Timestamp dateModified;

    public InternalStocksData(int itemId, String internalItemName, String internalItemCategory, int remainingQuantity, int currentQuantity, int previousQuantity, double totalCostPrice, Timestamp dateCreated, String addedBy, Boolean isDeleted, Timestamp dateModified) {
        this.itemId = itemId;
        this.internalItemName = internalItemName;
        this.internalItemCategory = internalItemCategory;
        this.remainingQuantity = remainingQuantity;
        this.currentQuantity = currentQuantity;
        this.previousQuantity = previousQuantity;
        this.totalCostPrice = totalCostPrice;
        this.dateCreated = dateCreated;
        this.addedBy = addedBy;
        this.isDeleted = isDeleted;
        this.dateModified = dateModified;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getInternalItemName() {
        return internalItemName;
    }

    public void setInternalItemName(String internalItemName) {
        this.internalItemName = internalItemName;
    }

    public String getInternalItemCategory() {
        return internalItemCategory;
    }

    public void setInternalItemCategory(String internalItemCategory) {
        this.internalItemCategory = internalItemCategory;
    }

    public int getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(int remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getPreviousQuantity() {
        return previousQuantity;
    }

    public void setPreviousQuantity(int previousQuantity) {
        this.previousQuantity = previousQuantity;
    }

    public double getTotalCostPrice() {
        return totalCostPrice;
    }

    public void setTotalCostPrice(double totalCostPrice) {
        this.totalCostPrice = totalCostPrice;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }
}//END OF CLASS
