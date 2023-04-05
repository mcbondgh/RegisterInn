package inn.tableViews;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Timestamp;
import java.time.LocalTime;

public class CheckInData {
    private int checkin_id;
    private String roomNo;
    private int duration_id;
    private LocalTime checkin_time;
    private LocalTime due_time;
    private Label check_in_status;

    private int allotedTime;
    private int booked_by;
    private Button topupButton;
    private Timestamp date_created;

    public CheckInData(int checkin_id, String roomNo, LocalTime checkin_time, LocalTime due_time, Label check_in_status, int allotedTime, Button topupButton) {
        this.checkin_id = checkin_id;
        this.roomNo = roomNo;
        this.checkin_time = checkin_time;
        this.due_time = due_time;
        this.check_in_status = check_in_status;
        this.allotedTime = allotedTime;
        this.topupButton = topupButton;
    }

    public int getCheckin_id() {
        return checkin_id;
    }

    public void setCheckin_id(int checkin_id) {
        this.checkin_id = checkin_id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String room_id) {
        this.roomNo = room_id;
    }

    public int getDuration_id() {
        return duration_id;
    }

    public int getAllotedTime() {
        return allotedTime;
    }

    public void setAllotedTime(int allotedTime) {
        this.allotedTime = allotedTime;
    }

    public void setDuration_id(int duration_id) {
        this.duration_id = duration_id;
    }

    public LocalTime getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(LocalTime checkin_time) {
        this.checkin_time = checkin_time;
    }

    public LocalTime getDue_time() {
        return due_time;
    }

    public void setDue_time(LocalTime due_time) {
        this.due_time = due_time;
    }

    public Label getCheck_in_status() {
        return check_in_status;
    }

    public void setCheck_in_status(Label check_in_status) {
        this.check_in_status = check_in_status;
    }

    public int getBooked_by() {
        return booked_by;
    }

    public void setBooked_by(int booked_by) {
        this.booked_by = booked_by;
    }

    public Button getTopupButton() {
        return topupButton;
    }

    public void setTopupButton(Button button) {
        this.topupButton = button;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }
}//END OF CLASS
