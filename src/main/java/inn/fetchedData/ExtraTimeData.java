package inn.fetchedData;

import javafx.scene.control.Button;

import java.sql.Timestamp;

public class ExtraTimeData {
    private int booking_id;
    private int room_id;
    private String roomNo;
    private Timestamp date_created;
    private String exit_time;
    private String bookingType;
    private Button checkout;

    public ExtraTimeData(int booking_id, int room_id, String roomNo, Timestamp date_created, String exit_time, String bookingType, Button checkout) {
        this.booking_id = booking_id;
        this.room_id = room_id;
        this.roomNo = roomNo;
        this.date_created = date_created;
        this.exit_time = exit_time;
        this.bookingType = bookingType;
        this.checkout = checkout;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

    public String getExit_time() {
        return exit_time;
    }

    public void setExit_time(String exit_time) {
        this.exit_time = exit_time;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public Button getCheckout() {
        return checkout;
    }

    public void setCheckout(Button checkout) {
        this.checkout = checkout;
    }
}//end of class
