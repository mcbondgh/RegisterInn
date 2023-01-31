package inn.tableViews;

public class RoomsCategoryTableView {

    public RoomsCategoryTableView() {}

    private int roomsCatId;
    private String roomsCateName;
    private Byte status;
    private double price;

    public RoomsCategoryTableView(int roomsCatId, String roomsCateName, byte status, double price) {
        this.roomsCatId = roomsCatId;
        this.roomsCateName = roomsCateName;
        this.status = status;
        this.price = price;
    }

    public RoomsCategoryTableView(int roomsCatId, String roomsCateName, double price) {
        this.roomsCatId = roomsCatId;
        this.roomsCateName = roomsCateName;

        this.price = price;
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

        public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
