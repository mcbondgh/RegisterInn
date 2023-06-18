package inn.tableViewClasses;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class CheckInData {
    private int checkin_id;
    private String roomNo;
    private String checkInDate;
    private String dueDate;
    private Label check_in_status;
    private int allotedTime;
    private Button topupButton;
    private Button checkOutButton;

    public CheckInData(int checkin_id, String roomNo, String checkInDate, String dueDate, Label check_in_status, int allotedTime, Button topupButton, Button checkOutButton) {
        this.checkin_id = checkin_id;
        this.roomNo = roomNo;
        this.checkInDate = checkInDate;
        this.dueDate = dueDate;
        this.check_in_status = check_in_status;
        this.allotedTime = allotedTime;
        this.topupButton = topupButton;
        this.checkOutButton = checkOutButton;
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

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }


    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }


    public Label getCheck_in_status() {
        return check_in_status;
    }

    public void setCheck_in_status(Label check_in_status) {
        this.check_in_status = check_in_status;
    }

    public int getAllotedTime() {
        return allotedTime;
    }

    public void setAllotedTime(int allotedTime) {
        this.allotedTime = allotedTime;
    }

    public Button getTopupButton() {
        return topupButton;
    }

    public void setTopupButton(Button topupButton) {
        this.topupButton = topupButton;
    }

    public Button getCheckOutButton() {
        return checkOutButton;
    }

    public void setCheckOutButton(Button checkOutButton) {
        this.checkOutButton = checkOutButton;
    }
}//END OF CLASS
