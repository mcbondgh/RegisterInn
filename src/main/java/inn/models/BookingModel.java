package inn.models;

import inn.ErrorLogger;

import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

public class BookingModel extends MainModel{

    ErrorLogger logger;


    //THIS METHOD WHEN INVOKED SHALL INSERT NEW RECORDS INTO THE checkIn TABLE.

    protected  int createNewCheckIn(int room_id, int duration_id, LocalTime checkin_time, LocalTime due_time, Byte check_in_status, String chechin_comment, int booked_by ) {
        int flag =0;
        try {
            String insertQuery = "INSERT INTO checkIn(room_id, duration_id, checkin_time, due_time, check_in_status, checkin_comment, booked_by) VALUES(?, ?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setInt(1, room_id);
            prepare.setInt(2, duration_id);
            prepare.setTime(3, Time.valueOf(checkin_time));
            prepare.setTime(4, Time.valueOf(due_time));
            prepare.setByte(5, check_in_status);
            prepare.setString(6, chechin_comment);
            prepare.setInt(7, booked_by);

            flag = prepare.executeUpdate();

        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return  flag;
    }

    protected int createNewGuest(int checkin_id, String guest_name, String guest_number, String guest_id_type, String guest_id_number, int added_by) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO guests(checkin_id, guest_name, guest_number, guest_id_type, guest_id_number, added_by) VALUES(?, ?, ?, ?, ?, ?);";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setInt(1, checkin_id);
            prepare.setString(2, guest_name);
            prepare.setString(3, guest_number);
            prepare.setString(4, guest_id_type);
            prepare.setString(5, guest_id_number);
            prepare.setInt(6, added_by);
            flag = prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }


    protected int createNewPayment(int checkin_id, String payment_method, double cash_amount, double momo_amount, long transaction_ID, double total_bill, double client_change, int added_by) {
        int flag = 0;
        try {

            String insertQuery = "INSERT INTO payment_transaction(checkin_id, payment_method, cash_amount, momo_amount, transaction_ID, total_bill, client_change, added_by) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setInt(1, checkin_id);
            prepare.setString(2, payment_method);
            prepare.setDouble(3, cash_amount);
            prepare.setDouble(4, momo_amount);
            prepare.setLong(5, transaction_ID);
            prepare.setDouble(6, total_bill);
            prepare.setDouble(7, client_change);
            prepare.setInt(8, added_by);
            flag = prepare.executeUpdate();

        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
            ex.printStackTrace();
        }
        return flag;
    }

    protected  int createNewCheckout(int checkinId, String guestName, String roomNo, LocalTime checkOutTime, String overTime, int userId ) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO checkout(checkin_id, guestName, roomNo, checkout_time, overtime, checkedout_by) VALUES(?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setInt(1, checkinId);
            prepare.setString(2, guestName);
            prepare.setString(3, roomNo);
            prepare.setTime(4, Time.valueOf(checkOutTime));
            prepare.setString(5, overTime);
            prepare.setInt(6, userId);
            flag = prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }


    protected  int updateRoomStatus(int roomId, int statusValue) {
        int flag = 0;
        try {
            prepare = CONNECTOR().prepareStatement("UPDATE rooms SET isBooked = '"+ statusValue + "' WHERE (Id = '"+ roomId +"') ");
            flag = prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }

    protected  int updateCheckInStatus(int bookingId, int checkInValue) {
        int flag = 0;
        try {
            prepare = CONNECTOR().prepareStatement("UPDATE checkin SET check_in_status = '"+ checkInValue + "' WHERE (checkin_id = '"+ bookingId +"') ");
            flag = prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }






}
