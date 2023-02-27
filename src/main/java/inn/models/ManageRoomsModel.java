package inn.models;

import inn.database.DbConnection;
import inn.tableViews.ManageRoomsData;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

import java.sql.Date;

public class ManageRoomsModel extends DbConnection {


    //THIS METHOD WHEN INVOKED WILL TAKE 3 PARAMETERS AND INSERT INTO THE rooms TABLE.
    public int AddNewRoom(@NamedArg("ROOM NO.") String roomNo, @NamedArg("CATEGORY ID") String categoryId) {
        int flag = 0;
        try{
            String insertQuery = "INSERT INTO rooms(roomNo, categoryName) VALUES(?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, roomNo);
            prepare.setString(2, categoryId);
            //prepare.setDouble(3, standardPrice);
            flag = prepare.executeUpdate();
            prepare.close();
            CONNECTOR().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    //THIS METHOD WHEN INVOKED SHALL ACCEPT 5 PARAMETERS AND UPDATES THE rooms TABLE BASED ON THE ROOM ID PARSED.
    public int UpdateRoom(@NamedArg("roomId") int roomID, @NamedArg("RoomNo.") String roomNo, @NamedArg("RoomCategory") String categoryId, @NamedArg("StatusValue") byte statusValue) {
            int  flag = 0;
        try {
                String updateQuery = "UPDATE rooms SET roomNo = ?, categoryName = ?,status = ? WHERE(id = ?)";
                prepare = CONNECTOR().prepareStatement(updateQuery);
                prepare.setString(1, roomNo);
                prepare.setString(2, categoryId);
                prepare.setByte(3, statusValue);
                //prepare.setDouble(4, standardPrice);
                prepare.setInt(4, roomID);
                flag = prepare.executeUpdate();
                prepare.close();
                CONNECTOR().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        return flag;
    }

    public boolean deleteRoom(@NamedArg("room id") byte roomID) {
        boolean flag = false;
        try{
            String deleteQuery = "DELETE FROM rooms WHERE(id = ?)";
            prepare = CONNECTOR().prepareStatement(deleteQuery);
            prepare.setByte(1, roomID);
            int result = prepare.executeUpdate();
            if (result > 0) {
                flag = true;
            }
            prepare.close();
            CONNECTOR().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean UpdateRoomStatus(@NamedArg("Status") int statusValue, int roomId) {
        boolean flag = false;
        try {
            String updateQuery = "UPDATE rooms SET status = ? WHERE(id = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setInt(1, statusValue);
            prepare.setInt(2, roomId);
            int result = prepare.executeUpdate();
            if (result >0) {
                flag = true;
            }
            prepare.close();
            CONNECTOR().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public String getRoomCateById(int value) {
        String categoryName = "";
        try{
            String selectQuery = "SELECT name FROM roomsCategory WHERE(id = ?)";
            prepare = CONNECTOR().prepareStatement(selectQuery);
            prepare.setInt(1, value);
            result = prepare.executeQuery();

            if (result.next()) {
                categoryName = result.getString("name");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return  categoryName;
    }


    //THIS METHOD WHEN CALLED SHALL FETCH AND RETURN ALL ROWS IN rooms TABLE
    public ObservableList<ManageRoomsData> fetchAllRooms() {
        ObservableList<ManageRoomsData> roomItems = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT * FROM rooms";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);

            while(result.next()) {
                int roomId = result.getInt("id");// id
                String roomNo =  result.getString("roomNo");//
                String categoryId = result.getString("CategoryName");
                byte status =  result.getByte("status");
                byte isBooked = result.getByte("isBooked");
                double standardPrice = result.getDouble("standardPrice");
                Date dateAdded = result.getDate("dateAdded");
                CheckBox roomStatus = new CheckBox();
                roomStatus.setSelected(false);
                roomStatus.setStyle("-fx-font-family:poppins; -fx-font-size:14px; -fx-alignment:center; fx-text-fill: blue;");
                if(status == 1) {
                    roomStatus.setSelected(true);
                }
                roomItems.add(new ManageRoomsData(roomId, roomNo, categoryId, standardPrice, roomStatus));
                //roomItems.add(new ManageRoomsTableView(roomId, roomNo, categoryId, status, isBooked, dateAdded));
            }
            stmt.close();
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomItems;
    }

}
