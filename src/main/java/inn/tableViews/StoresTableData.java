package inn.tableViews;

import java.sql.Timestamp;

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

    // BRAND CLASS
    public static class BrandsTableData {
        int brandId;
        String BrandName;
        Timestamp dateAdded;

        public BrandsTableData(int brandId, String brandName, Timestamp dateAdded) {
            this.brandId = brandId;
            BrandName = brandName;
            this.dateAdded = dateAdded;
        }
        public int getBrandId() {
            return brandId;
        }
        public void setBrandId(int brandId) {
            this.brandId = brandId;
        }
        public String getBrandName() {
            return BrandName;
        }
        public void setBrandName(String brandName) {
            BrandName = brandName;
        }
        public Timestamp getDateAdded() {
            return dateAdded;
        }
        public void setDateAdded(Timestamp dateAdded) {
            this.dateAdded = dateAdded;
        }

    }//END OF CLASS
}

