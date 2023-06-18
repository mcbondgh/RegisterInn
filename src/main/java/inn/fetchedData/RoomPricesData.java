package inn.fetchedData;

public class RoomPricesData {

    public RoomPricesData() {}

    private int roomsCatId;
    private String roomsCateName;
    private Byte status;
    private double price;

    private String allotedTime;

    public RoomPricesData(int roomsCatId, String roomsCateName, byte status, double price, String allotedTime) {
        this.roomsCatId = roomsCatId;
        this.roomsCateName = roomsCateName;
        this.status = status;
        this.price = price;
        this.allotedTime = allotedTime;
    }

    public RoomPricesData(int roomsCatId, String roomsCateName, double price, String allotedTime) {
        this.roomsCatId = roomsCatId;
        this.roomsCateName = roomsCateName;
        this.allotedTime = allotedTime;
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

    public String getAllotedTime() {
        return allotedTime;
    }

    public void setAllotedTime(String allotedTime) {
        this.allotedTime = allotedTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
