package inn.tableViews;

import javafx.scene.control.Label;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *  product stock
 *
 */
public class ProductsStockData {
    int rowId;
    String productName;
    String ProductType;
    String productBrand;
    String itemCategory;
    String suppliers;
    String notes;
    Date expiryDate;
    byte storeId;
    Label activeStatus;
    byte deleteStatus;
    byte addedBy;
    Timestamp dateAdded;

    public ProductsStockData() {}
    public ProductsStockData(int rowId, String productName, String ProductType, String productBrand, String itemCategory, String suppliers, String notes, Date expiryDate,  byte storeId, Label activeStatus, byte deleteStatus, byte addedBy, Timestamp dateAdded) {
        this.rowId = rowId;
        this.productName = productName;
        this.ProductType = ProductType;
        this.productBrand = productBrand;
        this.itemCategory = itemCategory;
        this.suppliers = suppliers;
        this.notes = notes;
        this.expiryDate = expiryDate;
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

    public String getProductType() {
        return ProductType;
    }

    public void setProductType(String productType) {
        ProductType = productType;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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


    public byte getStoreId() {
        return storeId;
    }

    public void setStoreId(byte storeId) {
        this.storeId = storeId;
    }

    public Label getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Label activeStatus) {
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
