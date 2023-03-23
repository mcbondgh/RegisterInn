package inn.tableViews;

import java.sql.Timestamp;

public class ProductPricesData {
    int productId;
    String productName;
    float purchasePrice;
    float sellingPrice;
    float profitPerItem;
    float previousPurchasePrice;
    float previousSellingPrice;
    float previousProfit;
    String username;
    Timestamp dateModified;

    public ProductPricesData(int productId, String productName, float purchasePrice, float sellingPrice, float profitPerItem, float previousPurchasePrice, float previousSellingPrice, float previousProfit, String username, Timestamp dateModified) {
        this.productId = productId;
        this.productName = productName;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.profitPerItem = profitPerItem;
        this.previousPurchasePrice = previousPurchasePrice;
        this.previousSellingPrice = previousSellingPrice;
        this.previousProfit = previousProfit;
        this.username = username;
        this.dateModified = dateModified;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public float getProfitPerItem() {
        return profitPerItem;
    }

    public void setProfitPerItem(float profitPerItem) {
        this.profitPerItem = profitPerItem;
    }

    public float getPreviousPurchasePrice() {
        return previousPurchasePrice;
    }

    public void setPreviousPurchasePrice(float previousPurchasePrice) {
        this.previousPurchasePrice = previousPurchasePrice;
    }

    public float getPreviousSellingPrice() {
        return previousSellingPrice;
    }

    public void setPreviousSellingPrice(float previousSellingPrice) {
        this.previousSellingPrice = previousSellingPrice;
    }

    public float getPreviousProfit() {
        return previousProfit;
    }

    public void setPreviousProfit(float previousProfit) {
        this.previousProfit = previousProfit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }
}
