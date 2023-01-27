package inn.models;

import inn.database.DbConnection;
import inn.tableViews.ManageRoomsTableView;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

import java.sql.Date;

public class ManageRoomsModel extends DbConnection {


    //THIS METHOD WHEN INVOKED WILL TAKE 3 PARAMETERS AND INSERT INTO THE rooms TABLE.
    public int AddNewRoom(@NamedArg("ROOM NO.") String roomNo, @NamedArg("ROOM CATEGORY") String roomCategory, @NamedArg("ADDED BY")String addedBy) {
        int flag = 0;
        try{
            String insertQuery = "INSERT INTO rooms(roomNo, roomCategory, addedBy) VALUES(?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, roomNo);
            prepare.setString(2, roomCategory);
            prepare.setString(3, addedBy);
            flag = prepare.executeUpdate();
            prepare.close();
            CONNECTOR().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    //THIS METHOD WHEN INVOKED SHALL ACCEPT 5 PARAMETERS AND UPDATES THE rooms TABLE BASED ON THE ROOM ID PARSED.
    public int UpdateRoom(@NamedArg("roomId") int roomID, @NamedArg("RoomNo.") String roomNo, @NamedArg("RoomCategory")String roomCatg, @NamedArg("StatusValue") byte statusValue,  String addedBy) {
            int  flag = 0;
        try {
                String updateQuery = "UPDATE rooms SET roomNo = ?, roomCategory = ?,status = ?, addedBy = ? WHERE(id = ?)";
                prepare = CONNECTOR().prepareStatement(updateQuery);
                prepare.setString(1, roomNo);
                prepare.setString(2, roomCatg);
                prepare.setByte(3, statusValue);
                prepare.setString(4, addedBy);
                prepare.setInt(5, roomID);
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

    //THIS METHOD WHEN CALLED SHALL FETCH AND RETURN ALL ROWS IN rooms TABLE
    public ObservableList<ManageRoomsTableView> fetchAllRooms() {
        ObservableList<ManageRoomsTableView> roomItems = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT * FROM rooms";
            stmt = CONNECTOR().createStatement();
            result = stmt.executeQuery(selectQuery);
            while(result.next()) {
                int roomId = result.getInt("id");// id
               String roomNo =  result.getString("roomNo");//
               String roomCat = result.getString("roomCategory");
                byte status =  result.getByte("status");
                String addedBy = result.getString("addedBy");
                Date dateAdded = result.getDate("dateAdded");
                CheckBox roomStatus = new CheckBox();
                roomStatus.setSelected(false);
                roomStatus.setStyle("-fx-font-family:poppins; -fx-font-size:14px; -fx-alignment:center");
                if(status == 1) {
                    roomStatus.setSelected(true);
                }
                roomItems.add(new ManageRoomsTableView(roomId, roomNo, roomCat, addedBy, roomStatus));
            }
            stmt.close();
            result.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return roomItems;
    }

}
