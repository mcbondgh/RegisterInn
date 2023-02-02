package inn.tableViews;

import java.sql.Date;

public class StocksCategoryTableView {
    public StocksCategoryTableView() {}
    private int Id;
    private String categoryName;
    private Date dateAdded;

    public StocksCategoryTableView(int id, String categoryName, Date dateAdded) {
        Id = id;
        this.categoryName = categoryName;
        this.dateAdded = dateAdded;
    }
    public StocksCategoryTableView(int id, String categoryName) {
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
