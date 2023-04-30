package inn.models;

import inn.ErrorLogger;
import inn.tableViews.InventoryRequestData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class InventoryModel extends MainModel{

    protected ObservableList<InventoryRequestData> getAllRequestItemsByCategoryName(String categoryName) {
        ObservableList<InventoryRequestData> requestData = FXCollections.observableArrayList();
        try {

            String searchQuery = "SELECT itemId, internal_item_name, remaining_quantity FROM internal_stock_items " +
                    "WHERE(internal_item_category = ? AND remaining_quantity > 0); ";
            prepare = CONNECTOR().prepareStatement(searchQuery);
            prepare.setString(1, categoryName);
            result = prepare.executeQuery();
            while(result.next()) {

                int ItemId = result.getInt("itemId");
                String itemName = result.getString("internal_item_name");
                int quantity = result.getInt("remaining_quantity");

                Button button = new Button("Request");
                button.setStyle("-fx-font-size: 12px; -fx-background-color:#ff0000; -fx-text-fill:#fff; -fx-alignment:center; -fx-font-family:poppins");
                Label statusText = new Label("Unspecified");
                statusText.setStyle("-fx-text-fill: #ff0000; -fx-alignment:center; -fx-font-family:poppins; -fx-font-size:14px");
                TextField textField = new TextField();
                textField.setPromptText("Enter Quantity");
                LocalDateTime datetime = null;


                requestData.add(new InventoryRequestData(ItemId, itemName, quantity, textField, button, statusText, null));
            }
            prepare.close();
            result.close();
            CONNECTOR().close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return requestData;
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

}
