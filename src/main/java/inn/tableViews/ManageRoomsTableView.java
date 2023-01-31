package inn.tableViews;

import javafx.scene.control.CheckBox;

public class ManageRoomsTableView {
    private int roomId;
    private String roomNo;
    private String roomCategory;
    private double roomPrice;

    private byte isBooked;

    private byte status;
    private CheckBox statusAction;

    public ManageRoomsTableView(){}
    public ManageRoomsTableView(int roomId, String roomNo, String roomCategory, double roomPrice, CheckBox statusAction) {
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.roomCategory = roomCategory;
        this.roomPrice = roomPrice;
        this.statusAction = statusAction;
    }
    public ManageRoomsTableView(int roomId, String roomNo, byte status, byte isBooked) {
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.isBooked = isBooked;
        this.status = status;
    }

    public byte getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(byte isBooked) {
        this.isBooked = isBooked;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
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

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public CheckBox getStatusAction() {
        return statusAction;
    }

    public void setStatusAction(CheckBox statusAction) {
        this.statusAction = statusAction;
    }
}//END OF CLASS
