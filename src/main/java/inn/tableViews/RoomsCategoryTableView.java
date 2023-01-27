package inn.tableViews;

public class RoomsCategoryTableView {

    private int roomsCatId;
    private String roomsCateName;

    public RoomsCategoryTableView(int roomsCatId, String roomsCateName) {
        this.roomsCatId = roomsCatId;
        this.roomsCateName = roomsCateName;
    }

    public int getRoomsCatId() {
        return roomsCatId;
    }

    public void setRoomsCatId(int roomsCatId) {
        this.roomsCatId = roomsCatId;
    }

    public String getRoomsCateName() {
        return roomsCateName;
    }

    public void setRoomsCateName(String roomsCateName) {
        this.roomsCateName = roomsCateName;
    }
}
