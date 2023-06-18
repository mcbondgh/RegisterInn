package inn.fetchedData;

import java.sql.Timestamp;

public class RoomsData {
    private int roomId;
    private String roomNo;
    private String categoryName;
    private byte status;
    private byte isBooked;
    private double standardPrice;

    private Timestamp dateAdded;

    public RoomsData(int roomId, String roomNo, String categoryName, byte status, byte isBooked, double standardPrice, Timestamp dateAdded) {
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.categoryName = categoryName;
        this.status = status;
        this.isBooked = isBooked;
        this.standardPrice = standardPrice;
        this.dateAdded = dateAdded;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public byte getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(byte isBooked) {
        this.isBooked = isBooked;
    }

    public double getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(double standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }


}//END OF CLASS
