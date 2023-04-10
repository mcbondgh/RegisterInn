package inn.models;

import inn.tableViews.InventoryRequestData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.SQLException;

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
                statusText.setStyle("-fx-text-fill: #ba5a00; -fx-alignment:center; -fx-font-family:poppins; -fx-font-size:14px");
                requestData.add(new InventoryRequestData(ItemId, itemName, quantity, button, statusText));
            }
            prepare.close();
            result.close();
            CONNECTOR().close();
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return requestData;
    }


}
