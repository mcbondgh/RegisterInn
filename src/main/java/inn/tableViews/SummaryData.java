package inn.tableViews;

import java.sql.Timestamp;
import java.time.LocalTime;

public class SummaryData {
    int checkin_id;
    String guest_name, guest_number, roomNo, name, payment_method, checkin_comment, booked_by;
    double total_bill;
    LocalTime checkin_time, checkout_time;
    Timestamp date_created;

    public SummaryData(int checkin_id, String guest_name, String guest_number, String roomNo, String name, String payment_method, String checkin_comment, String booked_by, double total_bill, LocalTime checkin_time, LocalTime checkout_time, Timestamp date_created) {
        this.checkin_id = checkin_id;
        this.guest_name = guest_name;
        this.guest_number = guest_number;
        this.roomNo = roomNo;
        this.name = name;
        this.payment_method = payment_method;
        this.checkin_comment = checkin_comment;
        this.booked_by = booked_by;
        this.total_bill = total_bill;
        this.checkin_time = checkin_time;
        this.checkout_time = checkout_time;
        this.date_created = date_created;
    }

    public int getCheckin_id() {
        return checkin_id;
    }

    public void setCheckin_id(int checkin_id) {
        this.checkin_id = checkin_id;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getGuest_number() {
        return guest_number;
    }

    public void setGuest_number(String guest_number) {
        this.guest_number = guest_number;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getCheckin_comment() {
        return checkin_comment;
    }

    public void setCheckin_comment(String checkin_comment) {
        this.checkin_comment = checkin_comment;
    }

    public String getBooked_by() {
        return booked_by;
    }

    public void setBooked_by(String booked_by) {
        this.booked_by = booked_by;
    }

    public double getTotal_bill() {
        return total_bill;
    }

    public void setTotal_bill(double total_bill) {
        this.total_bill = total_bill;
    }

    public LocalTime getCheckin_time() {
        return checkin_time;
    }

    public void setCheckin_time(LocalTime checkin_time) {
        this.checkin_time = checkin_time;
    }

    public LocalTime getCheckout_time() {
        return checkout_time;
    }

    public void setCheckout_time(LocalTime checkout_time) {
        this.checkout_time = checkout_time;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }
}
