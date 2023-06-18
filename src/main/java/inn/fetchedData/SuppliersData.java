package inn.fetchedData;

import java.sql.Date;

public class SuppliersData {

    private String supplierName, contactNumber, location;
    private int id, status;
    private Date date;


    public SuppliersData() {}
    public SuppliersData(int id, int status, String supplierName, String contactNumber, String location, Date date) {
        this.id  = id;
        this.status = status;
        this.supplierName = supplierName;
        this.contactNumber = contactNumber;
        this.location = location;
        this.date = date;
    }
    public SuppliersData(String supplierName, String contactNumber, String location) {
        this.supplierName = supplierName;
        this.contactNumber = contactNumber;
        this.location = location;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}//END OF CLASS...
