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
    String storeId;
    Label activeStatus;
    byte deleteStatus;
    String addedBy;
    Timestamp dateAdded;

    public ProductsStockData() {}
    public ProductsStockData(int rowId, String productName, String ProductType, String itemCategory, String suppliers, String productBrand,  Date expiryDate, String notes, String storeId, Label activeStatus, byte deleteStatus, String addedBy, Timestamp dateAdded) {
        this.rowId = rowId;
        this.productName = productName;
        this.ProductType = ProductType;
        this.itemCategory = itemCategory;
        this.productBrand = productBrand;
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


    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
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

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }




}//END OF CLAS
