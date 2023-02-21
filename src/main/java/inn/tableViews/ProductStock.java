package inn.tableViews;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *  product stock
 *
 */
public class ProductStock {
    int rowId;
    String productName;
    String productDesc;
    String productBrand;
    String itemCategory;
    String suppliers;
    String notes;
    Date expiryDate;
    int unitQuantity;
    int packQuantity;
    int qtyPerPack;
    int totalQuantity;
    byte storeId;
    byte activeStatus;
    byte deleteStatus;
    byte addedBy;
    Timestamp dateAdded;

    public ProductStock(int rowId, String productName, String productDesc, String productBrand, String itemCategory, String suppliers, String notes, Date expiryDate, int unitQuantity, int packQuantity, int qtyPerPack, int totalQuantity, byte storeId, byte activeStatus, byte deleteStatus, byte addedBy, Timestamp dateAdded) {
        this.rowId = rowId;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productBrand = productBrand;
        this.itemCategory = itemCategory;
        this.suppliers = suppliers;
        this.notes = notes;
        this.expiryDate = expiryDate;
        this.unitQuantity = unitQuantity;
        this.packQuantity = packQuantity;
        this.qtyPerPack = qtyPerPack;
        this.totalQuantity = totalQuantity;
        this.storeId = storeId;
        this.activeStatus = activeStatus;
        this.deleteStatus = deleteStatus;
        this.addedBy = addedBy;
        this.dateAdded = dateAdded;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(String suppliers) {
        this.suppliers = suppliers;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(int unitQuantity) {
        this.unitQuantity = unitQuantity;
    }

    public int getPackQuantity() {
        return packQuantity;
    }

    public void setPackQuantity(int packQuantity) {
        this.packQuantity = packQuantity;
    }

    public int getQtyPerPack() {
        return qtyPerPack;
    }

    public void setQtyPerPack(int qtyPerPack) {
        this.qtyPerPack = qtyPerPack;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public byte getStoreId() {
        return storeId;
    }

    public void setStoreId(byte storeId) {
        this.storeId = storeId;
    }

    public byte getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(byte activeStatus) {
        this.activeStatus = activeStatus;
    }

    public byte getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(byte deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public byte getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(byte addedBy) {
        this.addedBy = addedBy;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }




}//END OF CLAS
