package inn.tableViews;

import javafx.scene.control.CheckBox;

public class ManageRoomsTableView {
    private int roomId;
    private String roomNo, roomCategory, addedBy;
    private CheckBox statusAction;

    public ManageRoomsTableView(){}
    public ManageRoomsTableView(int roomId, String roomNo, String roomCategory, String addedBy, CheckBox statusAction) {
        this.roomId = roomId;
        this.roomNo = roomNo;
        this.roomCategory = roomCategory;
        this.addedBy = addedBy;
        this.statusAction = statusAction;
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

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public CheckBox getStatusAction() {
        return statusAction;
    }

    public void setStatusAction(CheckBox statusAction) {
        this.statusAction = statusAction;
    }
}//END OF CLASS
