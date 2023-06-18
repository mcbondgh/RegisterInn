package inn.models;

import inn.ErrorLogger;
import inn.fetchedData.ExtraTimeData;
import inn.tableViewClasses.SummaryTableData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;

import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BookingModel extends MainModel{

    ErrorLogger logger;


    //THIS METHOD WHEN INVOKED SHALL INSERT NEW RECORDS INTO THE checkIn TABLE.

    protected  int createNewCheckIn(int room_id, int duration_id, LocalDateTime checkin_date, LocalDateTime due_date, String checkin_comment, int booked_by) {
        int flag =0;
        try {
            String insertQuery = "INSERT INTO checkin(room_id, duration_id, checkin_date, due_date, checkin_comment, booked_by) VALUES (?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setInt(1, room_id);
            prepare.setInt(2, duration_id);
            prepare.setTimestamp(3, Timestamp.valueOf(checkin_date));
            prepare.setTimestamp(4, Timestamp.valueOf(due_date));
            prepare.setString(5, checkin_comment);
            prepare.setInt(6,booked_by);
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
    protected  int updateExtraTimeStatus(int booking_id) {
        int flag = 0;
        try {
            prepare = CONNECTOR().prepareStatement("UPDATE extra_time SET is_active = 0, exit_date = DEFAULT WHERE (booking_id = '"+ booking_id +"') ");
            flag = prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }
    protected  int updateCheckInStatus(int bookingId) {
        int flag = 0;
        try {
            prepare = CONNECTOR().prepareStatement("UPDATE checkin SET check_in_status = '"+ 0 + "' WHERE (checkin_id = '"+ bookingId +"') ");
            flag = prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }

    protected double getTodayBookingAmountTotal(int userId){
        double amount = 0;
        try{
            String selectStatement = "SELECT SUM(total_bill) AS amount FROM payment_transaction WHERE added_by = ? AND DATE(date_created) = CURRENT_DATE();";
            prepare = CONNECTOR().prepareStatement(selectStatement);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            while(result.next()){
                amount = result.getDouble("amount");
            }
            prepare.close();
            result.close();
            CONNECTOR().close();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return amount;
    }

    protected int saveExtraTimeRequest(int booking_id, int duration_id, String exit_time, String payment_method, double cash, double momo, String momo_trans_id, int booked_by) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO extra_time(booking_id, duration_id, extra_time_expiry, payment_method, cash, momo, momo_trans_id, booked_by) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setInt(1, booking_id);
            prepare.setInt(2, duration_id);
            prepare.setString(3, exit_time);
            prepare.setString(4, payment_method);
            prepare.setDouble(5, cash);
            prepare.setDouble(6, momo);
            prepare.setString(7, momo_trans_id);
            prepare.setInt(8, booked_by);
            flag = prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }

    protected int countExtraTimeRequests() {
        int value = 0;
        try {
            prepare = CONNECTOR().prepareStatement("SELECT COUNT(*) FROM extra_time WHERE is_active = 1");
            result = prepare.executeQuery();
            if (result.next()) {
                value = result.getInt(1);
            }
        }catch (Exception ignored) {}
        return value;
    }

    protected int getLastCheckInId() {
        int value = 0;
        try {
            String countQuery = "SELECT checkin_id FROM checkin ORDER BY checkin_id DESC LIMIT 1;";
            prepare = CONNECTOR().prepareStatement(countQuery);
            result = prepare.executeQuery();
            if (result.next()) {
                value = result.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return value;
    }

    protected ObservableList<ExtraTimeData> fetchExtraTimeDetails() {
        ObservableList<ExtraTimeData> data = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT booking_id, rm.id AS room_id, roomNo, ext.date_created AS booked_date, extra_time_expiry, name FROM extra_time AS ext\n" +
                    "    INNER JOIN roomPrices AS rp\n" +
                    "\t\tON duration_id = rp.id\n" +
                    "\tINNER JOIN rooms AS rm \n" +
                    "    INNER JOIN checkin AS ci\n" +
                    "\t\tON rm.id = ci.room_id\n" +
                    "\tWHERE booking_id = ci.checkin_id AND is_active = 1";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while (result.next()) {
                int booking_id = result.getInt("booking_id");
                int roomId = result.getInt("room_id");
                String roomNo = result.getString("roomNo");
                Timestamp booked_date = result.getTimestamp("booked_date");
                String exit_time = result.getString("extra_time_expiry");
                String bookType = result.getString("name");
                Button checkoutButton = new Button("Checkout");
                checkoutButton.setStyle("-fx-background-color:#ff0000; -fx-text-fill:#ffff; -fx-font-size:11; -fx-font-weight: normal");
               data.addAll(new ExtraTimeData(booking_id, roomId, roomNo, booked_date, exit_time, bookType, checkoutButton ));
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }



    public ObservableList<SummaryTableData> getBookingSummary(LocalDate startDate, LocalDate endDate, int userId) {
        ObservableList<SummaryTableData> dataItems = FXCollections.observableArrayList();
        try  {
            String select = "SELECT co.checkout_id, guestName, guest_number, r.roomNo, rp.name, checkin_date, due_date, co.date_created AS checked_out_date, overtime,\n" +
                    "   checkin_comment, username FROM checkout AS co\n" +
                    "   INNER JOIN checkin AS ci ON co.checkin_id = ci.checkin_id\n" +
                    "   INNER JOIN guests AS g ON co.checkin_id = g.checkin_id\n" +
                    "   INNER JOIN rooms AS r ON ci.room_id = r.id\n" +
                    "   INNER JOIN roomprices AS rp ON ci.duration_id = rp.id\n" +
                    "   INNER JOIN users AS u ON checkedout_by = u.id" +
                    "   WHERE ci.booked_by = '"+userId+"' AND DATE(co.date_created) BETWEEN '"+startDate+"' AND '"+endDate+"' ORDER BY co.checkout_id DESC LIMIT 5;";
            prepare = CONNECTOR().prepareStatement(select);
            result = prepare.executeQuery();
            while (result.next()) {
                int checkoutId = result.getInt("co.checkout_id");
                String guest_name = result.getString("guestName");
                String guest_number = result.getString("guest_number");
                String room_number = result.getString("roomNo");
                String duration = result.getString("rp.name");
                Timestamp checkin_date = result.getTimestamp("checkin_date");
                Timestamp due_date = result.getTimestamp("due_date");
                Timestamp date_created = result.getTimestamp("checked_out_date");
                LocalTime overtime = result.getTime("overtime").toLocalTime();
                String comment = result.getString("checkin_comment");
                String booked_by = result.getString("username");
                dataItems.addAll(new SummaryTableData(checkoutId, guest_name, guest_number, room_number, duration, checkin_date, due_date, date_created, overtime, comment, booked_by));
            }
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(select);
            while(result.next()) {

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return dataItems;
    }





}
