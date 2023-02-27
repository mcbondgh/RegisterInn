package inn.models;

import inn.ErrorLogger;
import inn.database.DbConnection;
import inn.tableViews.ProductsStockData;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

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
    public int insertProduct(String productName, String ProductType, String productBrand, String category, String supplier, String notes, Date expiryDate,  byte AddedBy) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO ProductStock(ProductName, ProductType, ProductBrand, Category, Supplier, Notes, ExpiryDate, AddedBy) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, productName);
            prepare.setString(2, ProductType);
            prepare.setString(3, productBrand);
            prepare.setString(4, category);
            prepare.setString(5, supplier);
            prepare.setString(6, notes);
            prepare.setDate(7, expiryDate);
            prepare.setByte(8, AddedBy);
            flag = prepare.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
    public int removeProduct(int productId) {
        int flag = 0;
        try {
            prepare = CONNECTOR().prepareStatement(" UPDATE ProductStock SET DeleteStatus = 1 WHERE( id = '"+ productId +"')");
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

    public void insertNewStockLevelItem(@NamedArg("Product Id")int productId, @NamedArg("Unit Qty") int unitQty, @NamedArg("PackQty") int packQty, @NamedArg("Qty Per Pack") int QtyPerPack, @NamedArg("Total Product") int total) {
        try {
            String insertQuery = "INSERT INTO stockLevels(ProductId, StockLevel, CurrentQty, PresentUnitQty, PresentPackQty, PresentPackPerQty) VALUES(?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setInt(1, productId);
            prepare.setInt(2, total);
            prepare.setInt(3, total);
            prepare.setInt(4, unitQty);
            prepare.setInt(5, packQty);
            prepare.setInt(6, QtyPerPack);
            prepare.execute();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public ObservableList<ProductsStockData> filterProductStockTable(String userInput) {
        ObservableList<ProductsStockData> products = FXCollections.observableArrayList();
        try {
            String searchQuery = "SELECT * FROM ProductStock WHERE(ProductName LIKE'%" + userInput + "%' OR ProductBrand LIKE'%" + userInput + "%' AND DeleteStatus = 0 );";

            stmt = CONNECTOR().createStatement();
            result  = stmt.executeQuery(searchQuery);

            while (result.next()) {
                int rowId = result.getInt(1);
                String productName = result.getString(2);
                String ProductType = result.getString(3);
                String productBrand = result.getString(4);
                String productCategory = result.getString(5);
                String productSupplier = result.getString(6);
                String notes = result.getString(7);
                Date expiryDate = result.getDate(8);
                byte storeId = result.getByte(9);
                byte activeStatus = result.getByte(10);
                byte deleteStatus = result.getByte(11);
                byte addedBy = result.getByte(12);
                Timestamp dateCreated = result.getTimestamp(13);

                Label statusValue = new Label();
                switch(activeStatus) {
                    case 0 -> {
                        statusValue.setText("Not Active");
                        statusValue.setStyle("-fx-text-fill:red");
                    }
                    case 1 -> {
                        statusValue.setStyle("-fx-text-fill:green");
                        statusValue.setText("Active");
                    }
                }
//                    String statusValue = activeStatus == 0 ? "Not Active" : "Active";
                products.add(new ProductsStockData(rowId, productName, ProductType, productBrand, productCategory, productSupplier, notes, expiryDate, storeId, statusValue, deleteStatus, addedBy, dateCreated));
            }
            stmt.close();
            result.close();
            CONNECTOR().close();
        }catch (SQLException ex) {
            ErrorLogger logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return products;
    }


}//END OF CLASS...
