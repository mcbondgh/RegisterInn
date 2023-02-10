package inn.tableViews;

public class StoresTableData {
    int id;
    String storeName;
    String description;

    public StoresTableData(int id, String storeName, String description) {
        this.id = id;
        this.storeName = storeName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
