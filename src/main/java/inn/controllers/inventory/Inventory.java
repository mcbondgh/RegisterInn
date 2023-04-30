package inn.controllers.inventory;

import inn.controllers.configurations.FormatLocalDateTime;
import inn.controllers.dashboard.Homepage;
import inn.enumerators.AlertTypesEnum;
import inn.models.InventoryModel;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import inn.tableViews.InventoryRequestData;
import inn.tableViews.StocksCategoryData;
import inn.threads.InternalRequestTask;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Inventory  extends InventoryModel implements Initializable{

    InternalRequestTask requestTask;
    InventoryRequestData requestData;

    UserAlerts userAlerts;
    UserNotification notify;

    int activeUserId = getUserIdByUsername(Homepage.activeUsername);



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
    @FXML private TableColumn<InventoryRequestData, CheckBox> requestedQtyColumn;
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
        if (!isRequestTableEmpty()) {
            for (InventoryRequestData item : requestsTableView.getItems()) {
                item.getMakeRequestButton().setOnMouseClicked(event -> {
                    try {
                        TextField textField = requestsTableView.getSelectionModel().getSelectedItem().getInternalRequestedStockQty();
                        int item_id = requestsTableView.getSelectionModel().getSelectedItem().getInternalStockId();
                        int currentQty = requestsTableView.getSelectionModel().getSelectedItem().getInternalStockQty();
                        int requestedQty = Integer.parseInt(textField.getText());

                        if (requestedQty > currentQty)  {
                            userAlerts = new UserAlerts("VALUES NOT VALID", "Requested Quantity cannot be greater than current item quantity", "please adjust your request quantity");
                            userAlerts.chooseAlert(AlertTypesEnum.WARNING);
                        } else {
                            LocalDateTime localDateTime = LocalDateTime.now();
                            String formatResult = FormatLocalDateTime.formatDateTime(localDateTime);
                            requestsTableView.getSelectionModel().getSelectedItem().setRequestDate(formatResult);
                            requestsTableView.getSelectionModel().getSelectedItem().getMakeRequestButton().setDisable(true);
                            requestsTableView.getSelectionModel().getSelectedItem().getMakeRequestButton().setText("In queue");
                            requestsTableView.getSelectionModel().getSelectedItem().getStatusText().setText("request pending");
                            requestsTableView.getSelectionModel().getSelectedItem().getInternalRequestedStockQty().setDisable(true);
                            int flag = addNewStockRequest(item_id, requestedQty, formatResult, activeUserId);
                            if (flag > 0) {
                                notify = new UserNotification();
                                notify.successNotification("ITEM REQUEST.", "Item request successfully queued.");
                            }

                        }

                    } catch (NumberFormatException ex) {
                            ex.printStackTrace();
                            userAlerts = new UserAlerts("INVALID INPUT", "Please input digits only for request quantity", "parameter accepts number values only");
                            userAlerts.chooseAlert(AlertTypesEnum.INFORMATION);
                    }
                });
            }
        }
    }


}//end of class
