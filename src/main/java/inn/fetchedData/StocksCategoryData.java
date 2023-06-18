package inn.fetchedData;

import java.sql.Date;

public class StocksCategoryData {
    public StocksCategoryData() {}
    private int Id;
    private String categoryName;
    private Date dateAdded;

    public StocksCategoryData(int id, String categoryName, Date dateAdded) {
        Id = id;
        this.categoryName = categoryName;
        this.dateAdded = dateAdded;
    }
    public StocksCategoryData(int id, String categoryName) {
        Id = id;
        this.categoryName = categoryName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
