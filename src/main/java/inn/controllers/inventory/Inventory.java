package inn.Controllers.inventory;

import inn.models.InventoryModel;
import inn.tableViews.InventoryRequestData;
import inn.tableViews.StocksCategoryData;
import inn.threads.InternalRequestTask;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class Inventory  extends InventoryModel implements Initializable{

    InternalRequestTask requestTask;
    InventoryRequestData requestData;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillRequestComboBox();
    }


    /*********************************************************************************************************
     ****** >>                    FXML NODE ITEM EJECTION
     *********************************************************************************************************/
    @FXML
    private MFXFilterComboBox<String> requestComboBox;



    /*********************************************************************************************************
     ****** >>                    MFX TABLEVIEW ITEMS...
     *********************************************************************************************************/
    @FXML private TableView<InventoryRequestData> requestsTableView;
    @FXML private TableColumn<InventoryRequestData, String> requestedItemNameColumn;
    @FXML private TableColumn<InventoryRequestData, String> availableQtyColumn;
    @FXML private TableColumn<InventoryRequestData, String> requestedQtyColumn;
    @FXML private TableColumn<InventoryRequestData, Button> makeRequestButtonColumn;
    @FXML private TableColumn<InventoryRequestData, String> requestStatusColumn;
    @FXML private TableColumn<InventoryRequestData, Timestamp> requestDateColumn;

    /*********************************************************************************************************
     ****** >>                   POPULATE REQUEST TABLEVIEW ON SORT
     *********************************************************************************************************/
    void populateRequestTableView() {
        requestedItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("internalStockItem"));
        availableQtyColumn.setCellValueFactory(new PropertyValueFactory<>("internalStockQty"));
        requestedQtyColumn.setCellValueFactory(new PropertyValueFactory<>("internalRequestedStockQty"));
        requestedQtyColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        requestedQtyColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<InventoryRequestData, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<InventoryRequestData, String> cellEditEvent) {
                requestData = cellEditEvent.getRowValue();
               requestData.setInternalRequestedStockQty(cellEditEvent.getNewValue());
            }
        });
        requestedQtyColumn.setStyle("-fx-text-fill: orange; -fx-alignment:center; -fx-font-weight:bold; fx-background-color:lightblue");

        makeRequestButtonColumn.setCellValueFactory(new PropertyValueFactory<>("makeRequestButton"));

        requestStatusColumn.setCellValueFactory(new PropertyValueFactory<>("statusText"));

        requestDateColumn.setCellValueFactory(new PropertyValueFactory<>("requestDate"));

        String selectedCategory = requestComboBox.getValue();


        requestTask = new InternalRequestTask(requestsTableView, getAllRequestItemsByCategoryName(selectedCategory));
        Thread runTask = new Thread(requestTask);
        runTask.start();

    }


    /*********************************************************************************************************
     ****** >>                   TRUE OR FALSE METHODS IMPLEMENTATION
     *********************************************************************************************************/
    boolean isRequestTableEmpty() {
        return requestsTableView.getSelectionModel().isEmpty();
    }


    /*********************************************************************************************************
     ****** >>                   OTHER METHODS IMPLEMENTATION
     *********************************************************************************************************/
    void fillRequestComboBox() {
        for (StocksCategoryData item : fetchStockCategories()) {
            requestComboBox.getItems().add(item.getCategoryName());
        }
    }



    /*********************************************************************************************************
     ****** >>                    ACTION EVENT HANDLERS AND METHODS IMPLEMENTATION.
     *********************************************************************************************************/
    @FXML void selectedCategoryValueOnAction() {
        populateRequestTableView();
    }
    @FXML void requestTableOnMouseClicked() {
        if (isRequestTableEmpty()) {
            System.out.println("table empty");
        } else {
               for(InventoryRequestData item : requestsTableView.getItems()) {
                       item.getMakeRequestButton().setOnMouseClicked(event -> {
                           int value = requestsTableView.getSelectionModel().getSelectedItem().getInternalStockId();
                           System.out.println(value);});
                   }

        }
    }





}//end of class
