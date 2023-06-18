package inn.controllers.inventory;


import inn.ErrorLogger;
import inn.controllers.configurations.FormatLocalDateTime;
import inn.enumerators.AlertTypesEnum;
import inn.tableViewClasses.InstockProductsData;
import inn.tableViewClasses.InventoryRequestData;
import inn.fetchedData.StocksCategoryData;
import inn.models.InventoryModel;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import inn.tableViewClasses.SalesTablePlaceholderItems;
import inn.tableViewClasses.ViewSalesSummaryData;
import inn.threads.InternalRequestTask;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

import static inn.controllers.Homepage.activeUsername;

public class ManageInventory extends InventoryModel implements Initializable {

    InternalRequestTask requestTask;
    InventoryRequestData requestData;
    ErrorLogger logger = new ErrorLogger();

    UserAlerts userAlerts;
    UserNotification notify = new UserNotification();
    int activeUserId = getUserIdByUsername(activeUsername);
    public static String transactionId;
    private final ObservableList<SalesTablePlaceholderItems> SalesDataItems = FXCollections.observableArrayList();

    int userId = getUserIdByUsername(activeUsername);

    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        fillRequestComboBox();
        fillItemSelector();
        populateSalesTable();
        transactionIdLabel.setText(transactionId);

        refreshTotalSalesValue();
    }


    /*********************************************************************************************************
     ****** >>                    FXML NODE ITEM EJECTION
     *********************************************************************************************************/
    @FXML
    private MFXFilterComboBox<String> requestComboBox;
    @FXML private MFXFilterComboBox<String> salesItemSelector;
    @FXML private CheckBox cashCheckBox, momoCheckBox;
    @FXML private TextField purchaseQtyField, cashInputField, transactionIdField;
    @FXML private Label itemAvailableQtyLabel, itemSellingPriceLabel, salesAmountLabel;
    @FXML private Label displayBillField, displayChangeField, totalSalesLabel,transactionIdLabel;
    @FXML private MFXButton addToSalesTableButton, saveSalesButton;
    @FXML private Pane totalSalesPane;
    @FXML private TextArea salesCommentField;


    /*********************************************************************************************************
     ****** >>                    MFX REQUEST TABLEVIEW ITEMS...
     *********************************************************************************************************/
    @FXML private TableView<InventoryRequestData> requestsTableView;
    @FXML private TableColumn<InventoryRequestData, String> requestedItemNameColumn;
    @FXML private TableColumn<InventoryRequestData, String> availableQtyColumn;
    @FXML private TableColumn<InventoryRequestData, CheckBox> requestedQtyColumn;
    @FXML private TableColumn<InventoryRequestData, Button> makeRequestButtonColumn;
    @FXML private TableColumn<InventoryRequestData, String> requestStatusColumn;
    @FXML private TableColumn<InventoryRequestData, Timestamp> requestDateColumn;


    /*********************************************************************************************************
     ****** >>                    MFX SALES TABLEVIEW ITEMS...
     *********************************************************************************************************/
    @FXML private TableView<SalesTablePlaceholderItems> salesTable;
    @FXML private TableColumn<SalesTablePlaceholderItems, String> itemNameColumn;
    @FXML private TableColumn<SalesTablePlaceholderItems, Integer> salesIdColumn;
    @FXML private TableColumn<SalesTablePlaceholderItems, Integer> salesQuantityColumn;
    @FXML private TableColumn<SalesTablePlaceholderItems, Double> salesCostColumn;
    @FXML private TableColumn<SalesTablePlaceholderItems, Button> salesActionColumn;

    /*********************************************************************************************************
     ****** >>                    MFX VIEW SALES TABLEVIEW ITEMS...
     *********************************************************************************************************/
    @FXML private MFXDatePicker startDatePicker, endDatePicker;
    @FXML private MFXButton generateSummaryButton;
    @FXML private MFXLegacyTableView<ViewSalesSummaryData> viewSalesTable;
    @FXML private TableColumn<SalesTablePlaceholderItems, String> transactionIdColumn;
    @FXML private TableColumn<ViewSalesSummaryData, Integer> viewSalesNoColumn;
    @FXML private TableColumn<ViewSalesSummaryData, String> viewSalesItemNameColumn;
    @FXML private TableColumn<ViewSalesSummaryData, Integer> quantitySoldColumn;
    @FXML private TableColumn<ViewSalesSummaryData, Double> totalCostColumn;
    @FXML private TableColumn<ViewSalesSummaryData, Timestamp> viewSalesDateColumn;
    @FXML private TableColumn<ViewSalesSummaryData, String> viewSalesSoldByColumn;


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

    void populateSalesTable() {
        salesIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        salesQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        salesCostColumn.setCellValueFactory(new PropertyValueFactory<> ("cost"));
        salesActionColumn.setCellValueFactory(new PropertyValueFactory<>("removeItemButton"));
//        salesTable.setItems(SalesDataItems);
    }
    void populateViewSalesTable() {

        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        viewSalesNoColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
        viewSalesItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        quantitySoldColumn.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));
        totalCostColumn.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        viewSalesDateColumn.setCellValueFactory(new PropertyValueFactory<>("formattedDate"));
        viewSalesSoldByColumn.setCellValueFactory(new PropertyValueFactory<>("soldBy"));
        salesTable.getItems().clear();
        viewSalesTable.setItems(getSalesSummary(startDate, endDate, userId));
    }

    /*********************************************************************************************************
     ****** >>                   TRUE OR FALSE METHODS IMPLEMENTATION
     *********************************************************************************************************/
    boolean isRequestTableEmpty() {
        return requestsTableView.getSelectionModel().isEmpty();
    }
    boolean isItemSelectorEmpty() {
        return salesItemSelector.getValue().isEmpty();
    }
    boolean isPurchaseFieldEmpty() {
        return purchaseQtyField.getText().isBlank();
    }
    boolean isCashChecked() {
        return cashCheckBox.isSelected();
    }
    boolean isMomoChecked() {
        return momoCheckBox.isSelected();
    }
    boolean isSalesTableEmpty() {
        return salesTable.getItems().isEmpty();
    }
    boolean isCashFieldEmpty() {
        return cashInputField.getText().isBlank();
    }
    boolean isTransactionFieldEmpty() {
        return transactionIdField.getText().isBlank();
    }
    boolean isTotalBillGreater() {
        boolean result;
        try{
            double cashValue = Double.parseDouble(cashInputField.getText());
            double totalBill = Double.parseDouble(displayBillField.getText());
            result = totalBill > cashValue;
        }catch(Exception e){
            return true;
        }
        return result;
    }

    /*********************************************************************************************************
     ****** >>                   OTHER METHODS IMPLEMENTATION
     *********************************************************************************************************/
    void fillRequestComboBox() {
        for (StocksCategoryData item : fetchStockCategories()) {
            requestComboBox.getItems().add(item.getCategoryName());
        }
    }
    void fillItemSelector() {
        for(InstockProductsData item : fetchAvailableProductsOnly()) {
            salesItemSelector.getItems().add(item.getProductName());
        }
    }
    void resetSalesFields() {
        salesItemSelector.setValue(null);
        purchaseQtyField.clear();
        itemAvailableQtyLabel.setText("0");
        itemSellingPriceLabel.setText("0.00");
        salesAmountLabel.setText("0.00");
    }

    void resetSalesPaymentParameters() {
        resetSalesFields();
        salesTable.getItems().clear();
        cashCheckBox.setSelected(false);
        momoCheckBox.setSelected(false);
        salesCommentField.clear();
        cashInputField.clear();
        transactionIdField.clear();
        displayBillField.setText("0.00");
        displayChangeField.setText("0.00");
    }
    private double computeSalesTotalPrice() {
        double total = 0.0;
        for(SalesTablePlaceholderItems items : salesTable.getItems()) {
            total += items.getCost();
        }
        return total;
    }
    private int returnStockLeveByItemId(int itemId) {
        int stockLevel = 0;
        for(InstockProductsData items : fetchAvailableProductsOnly()) {
            if(items.getId() == itemId) {
                stockLevel =  items.getStockLevel();
                break;
            }
        }
        return stockLevel;
    }

    void refreshTotalSalesValue() {
        double totalSales = getTodayUserSales(userId);
        totalSalesLabel.setText(String.valueOf(totalSales));
    }

    //THIS METHOD WHEN CALLED SHALL TAKE IN AN ITEM/PRODUCT NAME AS AN ARGUMENT, ITERATE OVER THE OBSERVABLE LIST ITEMS OF AVAILABLE PRODUCTS
    // AND RETURN THE ITEM/PRODUCT ID ASSOCIATED TO THE PRODUCT.
    private int returnItemId(String itemName) {
        int itemId = 0;
        for(InstockProductsData item : fetchAvailableProductsOnly()) {
            if (Objects.equals(item.getProductName(), itemName)) {
                itemId = item.getId();
                break;
            }
        }
        return itemId;
    }

    /*********************************************************************************************************
     ****** >>                    ACTION EVENT HANDLERS AND METHODS IMPLEMENTATION.
     *********************************************************************************************************/
    @FXML
    void selectedCategoryValueOnAction() {
        populateRequestTableView();

    }
    @FXML
    private void requestTableOnMouseClicked() {
        if (!isRequestTableEmpty()) {
            for (InventoryRequestData item : requestsTableView.getItems()) {
                item.getMakeRequestButton().setOnMouseClicked(event -> {
                    try {
                        TextField textField = item.getInternalRequestedStockQty();
                        int item_id = item.getInternalStockId();
                        int currentQty = item.getInternalStockQty();
                        int requestedQty = Integer.parseInt(textField.getText());
                        if (requestedQty > currentQty)  {
                            userAlerts = new UserAlerts("VALUES NOT VALID", "Requested Quantity cannot be greater than current item quantity", "please adjust your request quantity");
                            userAlerts.chooseAlert(AlertTypesEnum.WARNING);
                        } else {
                            LocalDateTime localDateTime = LocalDateTime.now();
                            String formatResult = FormatLocalDateTime.formatDateTime(localDateTime);
                            item.setRequestDate(formatResult);
                            item.getMakeRequestButton().setDisable(true);
                            item.getMakeRequestButton().setText("In queue");
                            item.getStatusText().setText("request pending");
                            requestsTableView.getItems().remove(item);
                            item.getInternalRequestedStockQty().setDisable(true);
                            int flag = addNewStockRequest(item_id, requestedQty, formatResult, activeUserId);
                            flag = flag + updateInternalStockItemOnRequested(item_id);
                            if (flag > 0) {
                                notify.successNotification("ITEM REQUEST.", "Item request successfully queued.");
                            }
                        }
                    } catch (NumberFormatException ex) {
//                        ex.printStackTrace();
                        userAlerts = new UserAlerts("INVALID INPUT", "Please input digits only for request quantity", "parameter accepts number values only");
                        userAlerts.chooseAlert(AlertTypesEnum.INFORMATION);
                    }
                });
            }
        }
    }
    @FXML
    void addSalesButtonClicked(ActionEvent event) {
        addToSalesTableButton.setDisable(isRequestTableEmpty());
        int status = 0;
        try {

            String itemName = salesItemSelector.getValue();
            int itemId = returnItemId(itemName);
            int purchaseQuantity = Integer.parseInt(purchaseQtyField.getText());
            double itemCost = Double.parseDouble(salesAmountLabel.getText());
            Button removeItemButton = new Button("Remove");
            removeItemButton.setStyle("-fx-font-size: 10px; -fx-background-color:#ff0000;-fx-text-fill:#fff; -fx-alignment:center; -fx-font-family:poppins; -fx-width:59; -fx-height:22;");

            for(SalesTablePlaceholderItems item : salesTable.getItems()) {
                 int compare = item.getItemId();
                 if(Objects.equals(itemId, compare)) {
                    status = 1;
                     break;
                 }
            }
            switch(status) {
                case 0 -> {
                    SalesTablePlaceholderItems dataItems = new SalesTablePlaceholderItems(itemId, itemName, purchaseQuantity,itemCost, removeItemButton);
                    SalesDataItems.add(dataItems);
                    salesTable.setItems(SalesDataItems);
                    resetSalesFields();
                    displayBillField.setText(String.valueOf(computeSalesTotalPrice()));
                }
                case 1 -> {
                    userAlerts = new UserAlerts("DUPLICATE ENTRY", "YOU CAN ONLY ADD ONE ITEM AT A TIME. REMOVE AND ADJUST QUANTITY", "to increase item quantity, remove, adjust preferred item quantity and add to sales table.");
                    userAlerts.chooseAlert(AlertTypesEnum.INFORMATION);
                }
            }
        }catch (NumberFormatException ignored) {}

    }
    @FXML void itemSelectorOnAction(ActionEvent event) {
        try {
        purchaseQtyField.setDisable(isItemSelectorEmpty());
        String itemName = salesItemSelector.getValue();
        for(InstockProductsData item : fetchAvailableProductsOnly()) {
            if (Objects.equals(item.getProductName(), itemName)) {
                itemAvailableQtyLabel.setText(String.valueOf(item.getStockLevel()));
                itemSellingPriceLabel.setText(String.valueOf(item.getSellingPrice()));
                break;
            }
        }
        }catch (NullPointerException ignored) {}
    }
    @FXML
    void quantityInputChanged(KeyEvent event) {
        addToSalesTableButton.setDisable(isPurchaseFieldEmpty());

        if(!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode().equals(KeyCode.BACK_SPACE))) {
            purchaseQtyField.deletePreviousChar();
            return;
        }
        try {
            int itemQuantity = Integer.parseInt(itemAvailableQtyLabel.getText());
            int purchaseQuantity = Integer.parseInt(purchaseQtyField.getText());
            double sellingPrice = Double.parseDouble(itemSellingPriceLabel.getText());
            if(purchaseQuantity > itemQuantity) {
                purchaseQtyField.deletePreviousChar();
                notify = new UserNotification();
                notify.informationNotification("INVALID QUANTITY", "Purchase quantity cannot be greater than available quantity");
            } else if(purchaseQuantity == 0) {
                notify = new UserNotification();
                notify.informationNotification("INVALID QUANTITY", "Purchase quantity cannot be set to zero(0)");
                addToSalesTableButton.setDisable(true);
            }else {
                double result = purchaseQuantity * sellingPrice;
                salesAmountLabel.setText(String.valueOf(result));
            }
        }catch (NumberFormatException e) {
            salesAmountLabel.setText("0.00");
        }

    }
    @FXML
    void salesTableActive(MouseEvent event) {
        salesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for(SalesTablePlaceholderItems items : salesTable.getItems()) {
            items.getRemoveItemButton().setOnMouseClicked(event1 -> {
                    double amount = items.getCost();
                    double totalAmount = Double.parseDouble(displayBillField.getText());
                    double result = totalAmount - amount;
                    displayBillField.setText(String.valueOf(result));
                    salesTable.getItems().remove(items);
            });
        }
        saveSalesButton.setDisable(isSalesTableEmpty());
    }
    @FXML
    void cashCheckBoxSelected(ActionEvent event) {
        transactionIdField.setDisable(isCashChecked());
        momoCheckBox.setDisable(isCashChecked());
    }
    @FXML
    void momoCheckBoxSelected(ActionEvent event) {
        cashCheckBox.setDisable(isMomoChecked());
    }
    @FXML
    void validateCashField(KeyEvent event) {
        if(!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode().equals(KeyCode.PERIOD) || event.getCode().equals(KeyCode.BACK_SPACE))) {
            cashInputField.deletePreviousChar();
        }
    }
    @FXML void calculateForChangeOnInputChange() {
        try {
            double cashValue = Double.parseDouble(cashInputField.getText());
            double totalBill = Double.parseDouble(displayBillField.getText());
            double result = cashValue - totalBill;
            displayChangeField.setText(String.valueOf(result));

        }catch (NumberFormatException e) {
            logger = new ErrorLogger();
            logger.log(e.getMessage());
            displayChangeField.setText("0.00");
        }
    }
    @FXML
    void validateTransactionIdField(KeyEvent event) {
        if(!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode().equals(KeyCode.BACK_SPACE))) {
            transactionIdField.deletePreviousChar();
        }
        if(transactionIdField.getLength() > 12) {
            transactionIdField.deletePreviousChar();
        }
    }

    String generateTransactionId() {
        LocalDate date = LocalDate.now();
        String year = String.valueOf(date.getYear()).substring(2,4);
        int month = date.getMonthValue();
        String nameValue = activeUsername.substring(0,3);
        String timeValue = String.valueOf(System.currentTimeMillis()).substring(6, 12);
        return nameValue.concat( timeValue +"/"+ year +"/" + month).toLowerCase();
    }

    @FXML
    void SaveSalesButtonClicked() {
        int activeUserId = getUserIdByUsername(activeUsername);
        String salesTransId = transactionIdLabel.getText();
        String paymentMethod = isCashChecked() ? cashCheckBox.getText() : isMomoChecked() ? momoCheckBox.getText() : "false";
        String momoTransId = transactionIdField.getText();
        double totalBill = Double.parseDouble(displayBillField.getText());
        double changeValue = Double.parseDouble(displayChangeField.getText());
        String salesNote = salesCommentField.getText();

        if (!(isCashChecked() || isMomoChecked())) {
            userAlerts = new UserAlerts("PAYMENT METHOD", "PLEASE SELECT A PAYMENT METHOD TYPE ie. cash or momo", "you need to select buyer's mode of payment first.");
            userAlerts.chooseAlert(AlertTypesEnum.WARNING);
        } else if(isMomoChecked() && (isTransactionFieldEmpty() || isCashFieldEmpty())) {
            userAlerts = new UserAlerts("INVALID INPUT", "CASH OR TRANSACTION ID FIELDS CANNOT BE EMPTY", "please enter cash amount or transaction ID before you save.");
            userAlerts.chooseAlert(AlertTypesEnum.WARNING);
        } else if(isTotalBillGreater()) {
            userAlerts = new UserAlerts("INVALID CASH AMOUNT", "CASH INPUT VALUE CANNOT BE LESSER THAN THE TOTAL BILL GENERATED", "please check and enter the correct amount into the cash field.");
            userAlerts.chooseAlert(AlertTypesEnum.ERROR);
        } else {
            userAlerts = new UserAlerts("SAVE SALES ", "ARE YOU SURE YOU WANT TO SAVE SALES TRANSACTION? ", "Please confirm your action else CANCEL to abort transaction.");
            if (userAlerts.confirmationAlert()) {
                int flag = saveSalesPayment(salesTransId, paymentMethod, momoTransId, totalBill, changeValue, salesNote, activeUserId);
                try {
                    for(int i = 0; i < salesTable.getItems().size(); i++) {
                        int itemId = salesTable.getItems().get(i).getItemId();
                        int purchasedQuantity = salesTable.getItems().get(i).getQuantity();
                        double cashValue = salesTable.getItems().get(i).getCost();
                        int newStockLevel = returnStockLeveByItemId(itemId)  - purchasedQuantity;
                        int paymentId = getLastPaymentId();

                        flag = flag + saveSalesTransaction(itemId, purchasedQuantity, cashValue, salesTransId, paymentId);
                        flag = flag + updateStockLevel(newStockLevel, itemId);
                    }//end of for-loop
                    if(flag >= 3) {
                        notify = new UserNotification();
                        notify.successNotification("SALES TRANSACTION SAVED", "Perfect, sales transaction successfully saved.");
                        resetSalesPaymentParameters();
                        populateViewSalesTable();
                        refreshTotalSalesValue();
                        transactionIdLabel.setText(generateTransactionId());
                    } else {
                        notify.successNotification("SALES TRANSACTION FAILED", "Sales transaction was unsuccessful, please retry or contact system admin for assistance.");
                    }
                }catch (SQLException ex) {
                    logger = new ErrorLogger();
                    logger.log(ex.getMessage());
                }//end of catch block
            }//end of if-statement
        }//end of else statement
    }//end of save sales block


    /*********************************************************************************************************
     ****** >>                    ACTION EVENT HANDLERS FOR VIEW SALES TABLE VIEW IMPLEMENTATION
     *********************************************************************************************************/
    @FXML void generateSummaryButtonClicked() {
        populateViewSalesTable();
    }
}//end of class
