package inn.tableViewClasses;

public class InstockProductsData {

    private int id;
    private String productName;
    private int stockLevel;
    double sellingPrice;

    public InstockProductsData(int id, String productName, int stockLevel, double sellingPrice) {
        this.id = id;
        this.productName = productName;
        this.stockLevel = stockLevel;
        this.sellingPrice = sellingPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}//END OF CLASS;
