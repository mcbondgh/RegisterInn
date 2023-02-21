package inn.models;

import inn.database.DbConnection;
import javafx.beans.NamedArg;

import java.sql.Connection;
import java.sql.Date;
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
            e.printStackTrace();
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


    //THIS METHOD WHEN INVOKED TAKES IN TWO PARAMETER i.e. stocksCategory DateType AS AN ARGUMENT AND UPDATES SAME IN THE StocksCategory TABLE..
    public int updateSupplier(String supplierName, String contact, String location, int itemId) {
        int flag = 0;
        try {
            String updateQuery = "UPDATE Suppliers SET Status = DEFAULT, SupplierName = ?, Contact = ?, Location = ? , DateCreated = DEFAULT WHERE(id = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setString(1, supplierName);
            prepare.setString(2, contact);
            prepare.setString(3, location);
            prepare.setInt(4, itemId);
            flag = prepare.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void deleteSelectedSupplier(@NamedArg("Item Id") int itemId) {
        try {
            String updateQuery = "DELETE FROM Suppliers  WHERE(id = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setInt(1, itemId);
            prepare.execute();
            prepare.close();
            CONNECTOR().close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addNewStore(String storeName, String desc) {
        int flag = 0;
        try {
            prepare = CONNECTOR().prepareStatement("INSERT INTO stores VALUES(DEFAULT, ?, ?, DEFAULT)");
            prepare.setString(1, storeName);
            prepare.setString(2, desc);
            flag = prepare.executeUpdate();
            prepare.close();
            CONNECTOR().close();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public int deleteStore(int id) {
        int flag = 0;
        try {
            prepare = CONNECTOR().prepareStatement("DELETE FROM stores WHERE(id= '"+ id +"')");
            flag = prepare.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    //THIS METHOD WHEN INVOKED SHALL TAKE A NUMBER OF ARGUMENTS AND INTERS SAME INTO THE ProductStock Table...
    public int insertProduct(String productName, String productDesc, String productBrand, String category, String supplier, String notes, Date expiryDate, int unitQty, int packQty, int qtyPerPack, int productQuantity, byte AddedBy) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO ProductStock(ProductName, ProductDescription, ProductBrand, Category, Supplier, Notes, ExpiryDate, UnitQuantity, PackQuantity, QtyPerPack, productQuantity, AddedBy) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, productName);
            prepare.setString(2, productDesc);
            prepare.setString(3, productBrand);
            prepare.setString(4, category);
            prepare.setString(5, supplier);
            prepare.setString(6, notes);
            prepare.setDate(7, expiryDate);
            prepare.setInt(8, unitQty);
            prepare.setInt(9, packQty);
            prepare.setInt(10, qtyPerPack);
            prepare.setInt(11, productQuantity);
            prepare.setByte(12, AddedBy);
            flag = prepare.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public boolean addNewBrand(String brandName) {
        boolean flag = true;
        try {
            String insertQuery = "INSERT INTO ProductBrand VALUES(DEFAULT, ?, DEFAULT)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, brandName);
            flag = prepare.execute();
        }catch (SQLException exception) {
            exception.printStackTrace();
        }

        return flag;
    }


}//END OF CLASS...
