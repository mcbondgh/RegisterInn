package inn.models;

import inn.ErrorLogger;
import inn.tableViewClasses.InstockProductsData;
import inn.tableViewClasses.InventoryRequestData;
import inn.tableViewClasses.ViewSalesSummaryData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InventoryModel extends MainModel{

    private ErrorLogger logger;

    protected ObservableList<InventoryRequestData> getAllRequestItemsByCategoryName(String categoryName) {
        ObservableList<InventoryRequestData> requestData = FXCollections.observableArrayList();
        try {

            String searchQuery = "SELECT itemId, internal_item_name, remaining_quantity, is_requested FROM internal_stock_items\n" +
                    " WHERE(internal_item_category = ? AND remaining_quantity > 0); ";
            prepare = CONNECTOR().prepareStatement(searchQuery);
            prepare.setString(1, categoryName);
            result = prepare.executeQuery();
            while(result.next()) {

                int ItemId = result.getInt("itemId");
                String itemName = result.getString("internal_item_name");
                int quantity = result.getInt("remaining_quantity");
                int is_requested = result.getInt("is_requested");

                Button button = new Button("Request");
                button.setStyle("-fx-font-size: 12px; -fx-background-color:#ff0000; -fx-text-fill:#fff; -fx-alignment:center; -fx-font-family:poppins");
                Label statusText = new Label("No request");
                statusText.setStyle("-fx-text-fill: #ff0000; -fx-alignment:center; -fx-font-family:poppins; -fx-font-size:12px");
                TextField textField = new TextField();
                textField.setPromptText("Enter Quantity");
                LocalDateTime datetime = null;

                requestData.add(new InventoryRequestData(ItemId, itemName, quantity, is_requested, textField, button, statusText, null));
            }
            prepare.close();
            result.close();
            CONNECTOR().close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return requestData;
    }

    protected int updateInternalStockItemOnRequested(int stockId) {
        int flag = 0;
        try {
            String updateQuery = "UPDATE internal_stock_items SET is_requested = 1 WHERE(itemId = ?);";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setInt(1, stockId);
            flag = prepare.executeUpdate();
        }catch (SQLException ignored) { }

        return flag;
    }

    protected int addNewStockRequest(int stock_id, int requested_quantity, String requested_date, int requested_by) {
        int flag = 0;
        try {
            String insert = "INSERT INTO internal_stock_request(stock_id, requested_quantity, requested_date, requested_by) VALUES(?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insert);
            prepare.setInt(1, stock_id);
            prepare.setInt(2, requested_quantity);
            prepare.setString(3, requested_date);
            prepare.setInt(4, requested_by);
            flag = prepare.executeUpdate();

        }catch (SQLException e) {
            logger = new ErrorLogger();
            logger.log(e.getMessage());
        }
        return  flag;
    }

    public ObservableList<InstockProductsData> fetchAvailableProductsOnly() {
        ObservableList<InstockProductsData> dataList = FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT pi.id, productName, stockLevel, sellingPrice FROM stocklevels AS sl\n" +
                    "    INNER JOIN productItems AS pi \n" +
                    "\t\tON sl.productId = pi.id\n" +
                    "\tINNER JOIN productprices AS pp\n" +
                    "\t\tON pi.id = pp.productid\n" +
                    "\tWHERE stockLevel > 0;";
            prepare = CONNECTOR().prepareStatement(selectQuery);
            result = prepare.executeQuery();
            while (result.next()) {
                int itemId = result.getInt("pi.id");
                String itemName = result.getString("productName");
                int stockLevel = result.getInt("stockLevel");
                double sellingPrice = result.getDouble("sellingPrice");
                dataList.add(new InstockProductsData(itemId, itemName, stockLevel, sellingPrice));
            }
            prepare.close();
            result.close();
            CONNECTOR().close();
        }catch(Exception e) {
            logger = new ErrorLogger();
            logger.log(e.getMessage());
        }
        return dataList;
    }

    protected int getLastPaymentId() throws SQLException {
        int lastPaymentId = -1;
        String selectQuery = "SELECT payment_id as pi FROM sales_payments ORDER BY pi DESC LIMIT 1;";
        prepare = CONNECTOR().prepareStatement(selectQuery);
        result = prepare.executeQuery();
        if (result.next()) {
            lastPaymentId = result.getInt(1);
        }
        return lastPaymentId;
    }

    protected double getTodayUserSales(int userId) {
        double value = 0.0;
        try{
            String selectQuery = "    SELECT SUM(total_bill) as total_sales FROM sales_payments WHERE (payment_added_by = ? AND DATE(payment_date) = CURRENT_DATE());";
            prepare = CONNECTOR().prepareStatement(selectQuery);
            prepare.setInt(1, userId);
            result = prepare.executeQuery();
            if (result.next()) {
                value = result.getDouble(1);
            }
            prepare.close();
            result.close();
            CONNECTOR().close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    protected int saveSalesPayment(String sales_trans_id, String payment_method, String momo_trans_id, double total_bill, double sales_change, String sales_note, int addedBy) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO sales_payments(sales_trans_id, payment_method, momo_trans_id, total_bill, sales_change, sales_note, payment_added_by) VALUES(?, ?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, sales_trans_id);
            prepare.setString(2, payment_method);
            prepare.setString(3, momo_trans_id);
            prepare.setDouble(4, total_bill);
            prepare.setDouble(5, sales_change);
            prepare.setString(6, sales_note);
            prepare.setInt(7, addedBy);
            flag = prepare.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }
    protected int saveSalesTransaction(int item_id, int purchased_quantity, double item_cost, String sales_trans_id, int payment_id) {
        int flag = 0;
        try{
            String insertStatement = "INSERT INTO sales_transaction(item_id, item_quantity, item_cost, sales_trans_id, payment_id) VALUES(?, ?, ?, ?, ?);";
            prepare = CONNECTOR().prepareStatement(insertStatement);
            prepare.setInt(1, item_id);
            prepare.setInt(2, purchased_quantity);
            prepare.setDouble(3, item_cost);
            prepare.setString(4, sales_trans_id);
            prepare.setInt(5, payment_id);
            flag = prepare.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return flag;
    }

    protected int updateStockLevel( int newStockLevelValue, int itemId) {
        int flag = 0;
        try{
            String updateQuery = "UPDATE stockLevels SET stockLevel = ? WHERE (productId = ?)";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setInt(1, newStockLevelValue);
            prepare.setInt(2, itemId);
            flag = prepare.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    protected ObservableList<ViewSalesSummaryData> getSalesSummary(LocalDate startDate, LocalDate endDate, int userId) {
        ObservableList<ViewSalesSummaryData> data= FXCollections.observableArrayList();
        try {
            String selectQuery = "SELECT DISTINCT(sales_id),s.sales_trans_id,  pi.productName, s.item_quantity, s.item_cost, s.sales_date, username FROM sales_transaction AS s\n" +
                    "    INNER JOIN productItems AS pi ON s.item_id = pi.id\n" +
                    "    INNER JOIN users AS u INNER JOIN sales_payments as sp\n" +
                    "    ON u.id = payment_added_by \n" +
                    "    WHERE (payment_added_by = '"+ userId + "' and s.sales_trans_id = sp.sales_trans_id AND DATE(s.sales_date) BETWEEN '"+startDate+"' AND '"+endDate+"')";
            prepare = CONNECTOR().prepareStatement(selectQuery);
            result = prepare.executeQuery();
            while (result.next()) {
                int itemId = result.getInt("sales_id");
                String transactionId = result.getString("s.sales_trans_id");
                String productName = result.getString("pi.productName");
                int quantity_sold = result.getInt("s.item_quantity");
                double totalCost = result.getDouble("s.item_cost");
                Timestamp sales_date = result.getTimestamp("s.sales_date");
                String username = result.getString("username");

                data.add(new ViewSalesSummaryData(itemId, transactionId, productName, quantity_sold, totalCost, sales_date, username));
            }
            prepare.close();
            result.close();
            CONNECTOR().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

}//END OF CLASS..
