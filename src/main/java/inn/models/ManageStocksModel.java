package inn.models;

import inn.database.DbConnection;
import javafx.beans.NamedArg;

import java.sql.Connection;
import java.sql.SQLException;

public class ManageStocksModel extends DbConnection {

    //THIS METHOD INSERTS INTO THE StocksCategory Table
    public int insertNewStockCategory(String categoryName) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO StocksCategory VALUES(DEFAULT, ?, DEFAULT)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, categoryName);
            flag = prepare.executeUpdate();
            prepare.close();
            CONNECTOR().close();
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return flag;
    }

    //THIS METHOD WHEN INVOKED TAKES IN TWO PARAMETER i.e. stocksCategory DateType AS AN ARGUMENT AND UPDATES SAME IN THE StocksCategory TABLE..
    public int updateStocksCategory(String categoryName, int itemId) {
        int flag = 0;
        try {
            String updateQuery = "UPDATE StocksCategory SET  CategoryName = ? WHERE(id = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setString(1, categoryName);
            prepare.setInt(2, itemId);
            flag = prepare.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();;
        }
        return flag;
    }

    //THIS METHOD WHEN INVOKED TAKES IN ONE PARAMETER i.e. stocksCategory DateType AS AN ARGUMENT AND DELETES SAME IN THE StocksCategory TABLE..
    public int deleteStockCategory(int itemId) {
        int flag = 0;
        try {
            String updateQuery = "DELETE FROM StocksCategory  WHERE(id = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setInt(1, itemId);
            flag = prepare.executeUpdate();
            prepare.close();
            CONNECTOR().close();
        }catch (SQLException e) {
            e.printStackTrace();;
        }
        return flag;
    }


    //THIS METHOD WHEN INVOKED SHALL INSERT A NEW RECORD INTO THE suppliers TABLE...
    public void insertNewSupplier(@NamedArg("Supplier Name") String name, @NamedArg("Contact") String contact, @NamedArg("Location") String location) {
        try {
            String insertQuery = "INSERT INTO Suppliers VALUES(DEFAULT, DEFAULT, ?, ?, ?, DEFAULT);";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, name);
            prepare.setString(2, contact);
            prepare.setString(3, location);
            prepare.execute();
            prepare.close();
            CONNECTOR().close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



}//END OF CLASS...
