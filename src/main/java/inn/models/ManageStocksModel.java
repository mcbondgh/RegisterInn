package inn.models;

import inn.ErrorLogger;
import inn.fetchedData.RequestedItemsData;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

public class ManageStocksModel extends MainModel {

    ErrorLogger logger;

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

    //THIS METHOD WHEN INVOKED TAKES IN ONE PARAMETER i.e. stocksCategory DataType AS AN ARGUMENT AND DELETES SAME IN THE StocksCategory TABLE..
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

    //THIS METHOD WHEN INVOKED SHALL TAKE A NUMBER OF ARGUMENTS AND INTERS SAME INTO THE ProductItems Table...
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
    public int addNewProductItem(String ProductName, String supplyType, int categoryId, int supplierId, int brandID, Date ExpiryDate, String note, int addedBy, Timestamp dateCreated) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO ProductItems(ProductName, supplyType, categoryId, supplierId, brandID, ExpiryDate, note, addedBy, dateCreated) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, ProductName);
            prepare.setString(2, supplyType);
            prepare.setInt(3, categoryId);
            prepare.setInt(4, supplierId);
            prepare.setInt(5, brandID);
            prepare.setDate(6, ExpiryDate);
            prepare.setString(7, note);
            prepare.setInt(8, addedBy);
            prepare.setTimestamp(9, dateCreated);
            flag =  prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }
    public int updateStockLevel(int itemId, int stockLevel, int currentStockLevel, int currentBoxQuantity, int currentQuantityPerBox, int oldStockLevel, int previousStockLevel, int previousBoxQuantity, int previousQuantityPerBox, int gage, int modifiedBy) {
        int flag = 0;
         try {
             String updateQuery = "UPDATE stocklevels SET stockLevel = ?,  currentStockLevel = ?, currentBoxQuantity = ?, currentQuantityPerBox = ?, oldStockLevel = ?, previousStockLevel = ?, previousBoxQuantity = ?, previousQuantityPerBox = ?, gage = ?, modifiedBy = ? WHERE(id = ?)";
             prepare = CONNECTOR().prepareStatement(updateQuery);
             prepare.setInt(1, stockLevel);
             prepare.setInt(2, currentStockLevel);
             prepare.setInt(3, currentBoxQuantity);
             prepare.setInt(4, currentQuantityPerBox);
             prepare.setInt(5, oldStockLevel);
             prepare.setInt(6, previousStockLevel);
             prepare.setInt(7, previousBoxQuantity);
             prepare.setInt(8, previousQuantityPerBox);
             prepare.setInt(9, gage);
             prepare.setInt(10, modifiedBy);
             prepare.setInt(11, itemId);
             flag = prepare.executeUpdate();
         } catch (SQLException ex) {
             logger = new ErrorLogger();
             logger.log(ex.getMessage());
         }

        return flag;
    }

    public int addNewStockLevel(int productId, int stockLevel, int currentStockLevel, int currentBoxQuantity, int currentQuantityPerBox, int modifiedBy) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO stocklevels(productId, stockLevel, currentStockLevel, currentBoxQuantity, currentQuantityPerBox, modifiedBy) VALUES(?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setInt(1, productId );
            prepare.setInt(2,stockLevel );
            prepare.setInt(3,currentStockLevel );
            prepare.setInt(4, currentBoxQuantity );
            prepare.setInt(5, currentQuantityPerBox );
            prepare.setInt(6, modifiedBy );
            flag = prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getLocalizedMessage());
        }

        return flag;
    }
    public int addNewProductPrice(int productId, double purchasePrice, double sellingPrice, double profitPerItem, int modifiedBy) {
        int flag = 0;
            try {
                String insertQuery = "INSERT INTO ProductPrices(productId, purchasePrice, sellingPrice, profitPerItem, modifiedBy) VALUES(?, ?, ?, ?, ?)";
                prepare = CONNECTOR().prepareStatement(insertQuery);
                prepare.setInt(1, productId);
                prepare.setDouble(2, purchasePrice);
                prepare.setDouble(3, sellingPrice);
                prepare.setDouble(4, profitPerItem);
                prepare.setInt(5, modifiedBy);
                flag = prepare.executeUpdate();
            }catch (SQLException ex) {
                logger = new ErrorLogger();
                logger.log(Arrays.toString(ex.getStackTrace()));
            }
        return flag;
    }
    public int deleteSelectedProduct(int itemId)  {
        int flag = 0;
        try {
            String deleteStatement = "UPDATE ProductItems SET deleteStatus = 1 WHERE(id = '"+ itemId +"')";
            stmt = CONNECTOR().createStatement();
            flag = stmt.executeUpdate(deleteStatement);
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }
    protected int updateProductPrices(@NamedArg("productId")int productId, float purchasePrice, float sellingPrice, float profitPerItem, float previousPurchasePrice, float previousSellingPrice, float previousProfit, int modifiedBy) {
        int flag = 0;
        try {
            String updateQuery = "UPDATE productPrices SET purchasePrice = ?, sellingPrice = ?, profitPerItem = ?, previousPurchasePrice = ?, previousSellingPrice = ?, previousProfit = ?, modifiedBy = ?, dateModified = DEFAULT WHERE(productId = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setFloat(1, purchasePrice);
            prepare.setFloat(2, sellingPrice);
            prepare.setFloat(3, profitPerItem);
            prepare.setFloat(4, previousPurchasePrice);
            prepare.setFloat(5, previousSellingPrice);
            prepare.setFloat(6, previousProfit);
            prepare.setInt(7, modifiedBy);
            prepare.setInt(8, productId);
            flag = prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }
    protected int addNewInternalStockItem(String itemName, String itemCategory, int itemQuantity, double itemCost, byte addedBy) {
        int flag = 0;
        try {
            String intertQuery = "INSERT INTO internal_stock_items(internal_item_name, internal_item_category, remaining_quantity, current_quantity, total_cost_price, added_by) VALUES(?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(intertQuery);
            prepare.setString(1, itemName);
            prepare.setString(2, itemCategory);
            prepare.setInt(3, itemQuantity);
            prepare.setInt(4, itemQuantity);
            prepare.setDouble(5, itemCost);
            prepare.setByte(6, addedBy);
            flag = prepare.executeUpdate();
            prepare.close();
            CONNECTOR().close();
        } catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }
    protected int updateInternalStockItem(int itemId, String itemName, String itemCategory, int availableQty, int currentQty, int previousQty, double cost, int activeUserId) {
        int flag = 0;
        try {
            String updateQuery = "UPDATE internal_stock_items SET internal_item_name = ?, internal_item_category = ?,  remaining_quantity = ?, current_quantity = ?, previous_quantity = ?, total_cost_price = ?, added_by = ?, date_modified = DEFAULT WHERE(itemId = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setString(1, itemName);
            prepare.setString(2, itemCategory);
            prepare.setInt(3, availableQty);
            prepare.setInt(4, currentQty);
            prepare.setInt(5, previousQty);
            prepare.setDouble(6, cost);
            prepare.setInt(7, activeUserId);
            prepare.setInt(8, itemId);
            flag = prepare.executeUpdate();

            prepare.close();
            CONNECTOR().close();

        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return  flag;
    }

    protected ObservableList<RequestedItemsData> fetchAllRequestedItems() {
        ObservableList<RequestedItemsData> data = FXCollections.observableArrayList();
        try{
            String select = "SELECT request_id, stock_id, internal_item_name, request_status, requested_quantity, requested_date, username FROM internal_stock_request AS isr\n" +
                    "INNER JOIN internal_stock_items isi\n" +
                    "\tON itemId = stock_id\n" +
                    "INNER JOIN users AS u\n" +
                    "\tON requested_by = u.id\n" +
                    "WHERE request_status = 0;";
            prepare = CONNECTOR().prepareStatement(select);
            result = prepare.executeQuery();
            while (result.next()) {
                int item_id = result.getInt("request_id");
                int stock_id = result.getInt("stock_id");
                String itemName = result.getString("internal_item_name");
                byte request_status = result.getByte("request_status");
                int request_quantity = result.getInt("requested_quantity");
                String request_date = result.getString("requested_date");
                String username = result.getString("username");

                String itemStatus = "";
                switch (request_status) {
                    case 0 -> itemStatus = "Pending Approval";
                    case 1 -> itemStatus = "Approved";
                }
                data.addAll(new RequestedItemsData(item_id, stock_id, itemName, itemStatus, request_quantity, request_date, username));
            }
            prepare.close();
            result.close();
            CONNECTOR().close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }

    protected int updateInternalStockItem(int stockId, int remainingQuantity) {
        int flag = 0;
        try {
            String updateQuery = "UPDATE internal_stock_items SET remaining_quantity = ?, is_requested = 0, date_modified = DEFAULT WHERE(itemId = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setInt(1, remainingQuantity);
            prepare.setInt(2, stockId);
            flag = prepare.executeUpdate();

        }catch(Exception ignored){}
        return flag;
    }

    protected int updateInternalStockItemOnCancelled(int stockId) {
        int flag = 0;
        try {
            String updateQuery = "UPDATE internal_stock_items SET is_requested = 0, date_modified = DEFAULT WHERE(itemId = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setInt(1, stockId);
            flag = prepare.executeUpdate();

        }catch(Exception ignored){}
        return flag;
    }

    protected int updateRequestTable(int requestId, int userId, String approvedDate ) {
        int flag = 0;
        try {
            String updateQuery = "UPDATE internal_stock_request SET approved_date = ?, approved_by = ?, request_status = 1 WHERE(request_id = ?);";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setString(1, approvedDate);
            prepare.setInt(2, userId);
            prepare.setInt(3, requestId);
            flag = prepare.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }

    protected int updateRequestTableOnCancelled(int requestId, int userId) {
        int flag = 0;
        try {
            String updateQuery = "UPDATE internal_stock_request SET approved_date = 'cancelled', approved_by = ?, request_status = 2 WHERE(request_id = ?);";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setInt(1, userId);
            prepare.setInt(2, requestId);
            flag = prepare.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

}//END OF CLASS...
