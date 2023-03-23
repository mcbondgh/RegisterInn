package inn.Controllers.settings;


import inn.ErrorLogger;
import inn.enumerators.AlertTypesEnum;
import inn.models.ManageStocksModel;
import inn.multiStage.MultiStages;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import inn.tableViews.*;
import inn.threads.ProductItemTask;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageProductStocks extends ManageStocksModel implements Initializable {


    public Label addBrandPopup;
    /*********************************************************************************************************
     ****** >>                      FXML NODE EJECTIONS
     *********************************************************************************************************/
    @FXML private TabPane stocksControlTabPane;
    @FXML private Tab stocksTab, stockLevelTab, internalSupplyTab, externalSupplyTab, addComponentsTab;

    //******************** >>> ADD COMPONENTS TAB NODE ITEMS
    @FXML private TitledPane suppliersTab, stockCategoriesTab;
    @FXML private CheckBox stockCategoryCheckBox, suppliersCheckBox;
    @FXML private TextField supplierNameField, contactField, locationField, categoryField;
    @FXML private Button saveSuppliersButton, updateSuppliersButton, deleteSuppliersButton;
    @FXML private Button saveStockCategoryButton, updateStockCategoryButton, deleteStockCategoryButton;
    @FXML private MFXButton updateStockLevelButton;
    @FXML private Label requiredIndicator, selectedProductNameDisplay, selectedStockTypeDisplay;
    @FXML private TextField productNameField;



    //******************* >>> PRODUCT STOCKS NODES
    @FXML private DatePicker expiryDatePicker;
//    @FXML private TextField ;
    @FXML private MFXTextField  searchProductsTextField, singleItemQtyField, boxQuantityField, qtyPerBoxField, purchasedPriceField, sellingPriceField ;
    @FXML private Label totalProductDisplay, productProfitDisplay, stockLevelTotalProductDisplay;
    @FXML private TextArea productNoteField;
    @FXML private  Button saveProductButton, deleteProductButton;
    @FXML private MFXLegacyComboBox<String> stockTypeSelector;
    @FXML private MFXLegacyComboBox<String> productCategorySelector;
    @FXML private MFXLegacyComboBox<String> productSupplierSelector;
    @FXML private MFXLegacyComboBox<String> productBrandSelector;
    @FXML private MFXTextField stockLevelSingleQtyField, stockLevelBoxQuantityField, stockLevelQtyPerBoxField;


    //******************* >> STORES TABLE VIEW
    @FXML private TextField storesInputField, storeDescriptionField, productGate;
    @FXML private TitledPane storesTab;
    @FXML private CheckBox storesCheckBox;
    @FXML private Button saveStoreButton, deleteStoreButton;

    @FXML private TableView<StoresTableData> storeTypeTable;
    @FXML private TableColumn<Object, String> storeName;
    @FXML private TableColumn<Object, String> storeDescription;


    //******************* >> SUPPLIERS TABLE VIEW NODES
    @FXML private TableView<SuppliersData> suppliersTable;
    @FXML private  TableColumn<SuppliersData, Integer> supplierIdColumn;
    @FXML private  TableColumn<SuppliersData, String> supplierNameColumn;
    @FXML private  TableColumn<SuppliersData, String> locationColumn;
    @FXML private  TableColumn<SuppliersData, String> contactColumn;


    //******************* >> STOCKS CATEGORY TABLE VIEW NODES
    @FXML private  TableView<StocksCategoryData> stocksCategoryTable;
    @FXML private  TableColumn<StocksCategoryData, Integer> idColumn;
    @FXML private  TableColumn<StocksCategoryData, String> categoryNameColumn;


    //******************* >>PRODUCTS TABLE VIEW NODES
    @FXML private MFXLegacyTableView<ProductsStockData> productItemTableView;
    @FXML private TableColumn<ProductsStockData, Integer> productIdColumn;
    @FXML private TableColumn<ProductsStockData, String> productNameColumn;
    @FXML private TableColumn<ProductsStockData, String> supplyTypeField;
    @FXML private TableColumn<ProductsStockData, String> productCategoryColumn;
    @FXML private TableColumn<ProductsStockData, String> productBrandColumn;
    @FXML private TableColumn<ProductsStockData, String> productSupplierColumn;
    @FXML private TableColumn<ProductsStockData, String> productsStoreColumn;
    @FXML private TableColumn<ProductsStockData, String> productStatusColumn;
    @FXML private TableColumn<ProductsStockData, Date> productExpiryDateColumn;
    @FXML private TableColumn<ProductsStockData, String> productAddedByColumn;
    @FXML private TableColumn<ProductsStockData, Timestamp> productDateAddedColumn;

    //******************* >>STOCK LEVEL TABLE VIEW NODES
    @FXML private MFXLegacyTableView<StockLevelData> stockLevelTableView;
    @FXML private TableColumn<StockLevelData, Integer> stockIdField;
    @FXML private TableColumn<StockLevelData, String> stockNameColumn;
    @FXML private TableColumn<StockLevelData, Integer> stockLevelColumn;
    @FXML private TableColumn<StockLevelData, Integer> currentSingleColumn;
    @FXML private TableColumn<StockLevelData, Integer> currentBoxColumn;
    @FXML private TableColumn<StockLevelData, Integer> currentPerBoxColumn;
    @FXML private TableColumn<StockLevelData, Integer> oldStockLevelColumn;
    @FXML private TableColumn<StockLevelData, Integer> previousSingleColumn;
    @FXML private TableColumn<StockLevelData, Integer> previousBoxColumn;
    @FXML private TableColumn<StockLevelData, Integer> previousPerBoxColumn;
    @FXML private TableColumn<StockLevelData, Integer> stockGagaColumn;
    @FXML private TableColumn<StockLevelData, String> stockModifyColumn;
    @FXML private TableColumn<StockLevelData, Timestamp> stockLastUpdateColumn;


    //******************* >>PRODUCT PRICE TABLE VIEW NODES
    @FXML private MFXLegacyTableView<ProductPricesData> pricesTableView;
    @FXML private TableColumn<ProductPricesData, Integer> priceId;
    @FXML private TableColumn<ProductPricesData, String> priceProductNane;
    @FXML private TableColumn<ProductPricesData, Double> currentPurchasedPriceColumn;
    @FXML private TableColumn<ProductPricesData, Double> currentSellingPriceColumn;
    @FXML private TableColumn<ProductPricesData, Double> currentProfitColumn;
    @FXML private TableColumn<ProductPricesData, Double> previousPurchasedPriceColumn;
    @FXML private TableColumn<ProductPricesData, Double> previousSellingPriceColumn;
    @FXML private TableColumn<ProductPricesData, Double> previousProfitColumn;
    @FXML private TableColumn<ProductPricesData, String> priceUpdatedByColumn;
    @FXML private TableColumn<ProductPricesData, Timestamp> priceLastUpdatedColumn;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateStockCategoryTable();
        populateSuppliersTable();
        populateStoresTable();
        fillStockTypeSelector();
        fillProductBrandSelector();
        fillProductCategorySelector();
        fillProductSupplierSelector();

    }


    //CLASS INSTANTIATION FIELD...
    UserNotification notify = new UserNotification();
    StocksCategoryData categoryTableOBJ;
    SuppliersData suppliersTableOBJ;

    UserAlerts userAlertOBJ;
    MultiStages multiStages = new MultiStages();
    ErrorLogger logger;



    /*********************************************************************************************************
     ****** >>                    ACTION EVENT HANDLERS FOR STOCKS CATEGORY TABLE...
     *********************************************************************************************************/
    @FXML void stockCategoryCheckBoxClicked() {stockCategoriesTab.setDisable(disableStockCategoriesTab());}
    @FXML void suppliersCheckBoxClicked() {
        suppliersTab.setDisable(disableSuppliersTab());
    }
    @FXML void storeCheckBoxClicked() {
        storesTab.setDisable(storesCheckBox.isSelected());
    }

    //THIS ACTION EVENT IS ASSOCIATED WITH THE STOCKS BUTTON. WHEN CLICKED, BLOCK OF CODE SHALL BE EXECUTED...
    @FXML void saveStockCategoryButtonClicked() {
            if (checkIfStockCategoryAlreadyExist()) {
                notify.informationNotification("CATEGORY EXIST", "Category Name Already Exist");
            } else {
                int outputBit = insertNewStockCategory(categoryField.getText());
                if(outputBit > 0) {
                    notify.successNotification("ADD SUCCESSFUL", "New Stock Category Added" );
                    refreshStocksCategoryTable();
                    unsetStocksCategoryVariables();
                } else notify.errorNotification("FAILED TO SAVE", "Save Operation Filed.");
            }
    }

    //THIS METHOD WILL ENABLE delete AND update BUTTONS FROM THE StocksCategory TABLE WHEN A ROW IS SELECTED IN THE stocksCategory TABLE
    @FXML void stocksCategoryTableOnSelected() {
        updateStockCategoryButton.setDisable(false);
        deleteStockCategoryButton.setDisable(false);
    }
    @FXML void suppliersTableRowSelected() {
        updateSuppliersButton.setDisable(false);
        deleteSuppliersButton.setDisable(false);
    }


    //THIS METHOD IS RESPONSIBLE FOR ITERATING THROUGH THE STOCK CATEGORY TABLE AND SAVING ALL VALUES INTO THE StocksCategory TABLE..
    @FXML void updateStockCategoryButtonClicked() {
        int outputBit = 0;
        for (StocksCategoryData item : stocksCategoryTable.getItems()) {
             outputBit = updateStocksCategory(item.getCategoryName(), item.getId());
        }
        switch (outputBit) {
            case 1 -> notify.successNotification("UPDATE SUCCESSFUL", "Stock Category Table Values Successfully Updated");
            case 0 -> notify.errorNotification("FAILED OPERATION", "Failed To Update Table Items.");
        }
    }

    //THIS METHOD IS RESPONSIBLE FOR DELETING AN ITEM FROM THE StocksCategory TABLE WHEN THE deleteStockCategoryButton IS CLICKED...
    @FXML void deleteStockCategoryButtonClicked() {
        try {
            int itemId = stocksCategoryTable.getSelectionModel().getSelectedItem().getId();
            int outputBit = deleteStockCategory(itemId);
            if (outputBit == 1) {
                notify.successNotification("DELETE SUCCESSFUL", "Selected Item Successfully Deleted.");
                refreshStocksCategoryTable();
            }
        } catch (NullPointerException e) {
            notify.informationNotification("DELETE FAILED", "Please make a selection to delete");
        }

    }
    @FXML void saveSupplierButtonClicked() {
        if(checkIfSupplierAlreadyExist()) {
            notify.informationNotification("SUPPLIER EXIST", "Supplier with name " + supplierNameField.getText() + " already exist");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "please confirm to add new supplier, else cancel to abort");
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.setTitle("ADD NEW SUPPLIER");
            alert.setHeaderText("DO YOU WANT TO ADD " + supplierNameField.getText() + "?");
            if (alert.showAndWait().get().equals(ButtonType.YES)) {
                insertNewSupplier(supplierNameField.getText(), contactField.getText(), locationField.getText());
                notify.successNotification("ADD SUCCESSFUL", "New supplier successfully added");
                refreshSuppliersTable();
                unsetSuppliersVariables();
            }
        }
    }

    @FXML void updateSuppliersButtonClicked() {

        int outputBit = 0;
        for (SuppliersData items : suppliersTable.getItems()) {
            outputBit = updateSupplier(items.getSupplierName(), items.getContactNumber(), items.getLocation(), items.getId());
        }
        switch (outputBit) {
            case 1 -> notify.successNotification("UPDATE SUCCESSFUL", "Suppliers Table Items Successfully Updated.");
            case 0 -> notify.errorNotification("UPDATE FAILED", "Update Operation failed.");
        }
    }

    @FXML void deleteSupplierButtonClicked() {
        try {
            int itemId = suppliersTable.getSelectionModel().getSelectedItem().getId();
            deleteSelectedSupplier(itemId);
            notify.successNotification("DELETE SUCCESSFUL", "Selected Supplier Successfully Deleted.");
            refreshSuppliersTable();
        } catch (NullPointerException e) {
            notify.informationNotification("EMPTY SELECTION" , "Please Select A Supplier To Be Deleted.");
        }
    }

    @FXML void saveStoresButtonClicked() {
       int outputResult = addNewStore(storesInputField.getText(), storeDescriptionField.getText());
       switch (outputResult) {
           case 1 -> {notify.successNotification("SAVED SUCCESSFULLY", "New store successfully saved");
               storeTypeTable.getItems().clear();
                populateStoresTable();
                storesInputField.clear();
                storeDescriptionField.clear();
           }
           case 0 -> notify.errorNotification("FAILED TO SAVE", "Failed to save new store");
       }
    }
    @FXML void deleteStoreButtonClicked() {
        int storeId = 0;
        for (StoresTableData item : storeTypeTable.getSelectionModel().getSelectedItems()) {
            storeId = item.getId();
        }
        int outputResult = deleteStore(storeId);
        switch(outputResult) {
            case 1-> {
                notify.successNotification("DELETE SUCCESSFUL ", "Store successfully deleted");
                storeTypeTable.getItems().clear();
                populateStoresTable();
            }
            case 0-> notify.errorNotification("FAILED TO DELETE", "Attempt to delete failed.");
        }
    }

    @FXML void storesTableRowSelected() {
        String storeName = storeTypeTable.getSelectionModel().getSelectedItem().getStoreName();
        if (Objects.equals(storeName, "Store Room")) {
            deleteStoreButton.setDisable(true);
        } else {
            deleteStoreButton.setDisable(!storeTypeTable.isFocused());
        }
    }



    /*********************************************************************************************************
     ****** >>                   INPUT FIELDS VALIDATION OPERATION...
     *********************************************************************************************************/

    @FXML private void validateStoreInputField(){
        saveStoreButton.setDisable(checkStoreInputField());
    }

    @FXML private void validateForNumbersOnly(KeyEvent keyTyped) {
        if(!(keyTyped.getCode().isDigitKey())) {
            contactField.setStyle("-fx-border-color:#ff0000");
            contactField.clear();
        } else contactField.setStyle("-fx-border-color:null");
    }
    @FXML private void validateSupplierNameField() {
        saveSuppliersButton.setDisable(checkSupplierNameField() || checkContactField());
    }
    @FXML private void validateCategoryNameField() {
        saveStockCategoryButton.setDisable(checkCategoryField());
    }

    /*********************************************************************************************************
     ****** >>                     TRUE OR FALSE STATEMENTS FOR STOCKS CATEGORY
     *********************************************************************************************************/
    boolean disableSuppliersTab() {
        return suppliersCheckBox.isSelected();
    }
    boolean disableStockCategoriesTab() {return stockCategoryCheckBox.isSelected();}

    //CHECK IF THE STOCKS CATEGORY TABLE IS EMPTY. RETURN TRUE ELSE FALSE...

    boolean checkStocksCategoryTable() {
        return stocksCategoryTable.getItems().isEmpty();
    }
    boolean checkCategoryField() {
        return categoryField.getText().isBlank();
    }

    boolean checkSupplierNameField() {
        return supplierNameField.getText().isBlank();
    }

    boolean checkLocationField() {
        return locationField.getText().isBlank();
    }

    boolean checkContactField() {
        return contactField.getText().isBlank();
    }
    boolean checkStoreInputField(){return storesInputField.getText().isBlank();}

    boolean checkIfStockCategoryAlreadyExist() {
        boolean flag = false;
        for (StocksCategoryData item : fetchStockCategories()) {
           String itemName = categoryField.getText().toLowerCase().trim();
           if(Objects.equals(item.getCategoryName().trim().toLowerCase(), itemName)) {
               flag = true;
           }
        }
        return flag;
    }

    boolean checkIfSupplierAlreadyExist() {
        boolean flag = false;
        for (SuppliersData item : fetchSuppliers()) {
            String itemName = supplierNameField.getText().toLowerCase().trim();
            if(Objects.equals(item.getSupplierName().replaceAll("\\s","").toLowerCase(), itemName)) {
                flag = true;
            }
        }
        return flag;
    }




    /*********************************************************************************************************
     ****** >>                     TABLE VIEW FILL METHODS...
     *********************************************************************************************************/

    private void populateStockCategoryTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));

        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryNameColumn.setOnEditCommit(cellEditEvent -> {
            categoryTableOBJ = cellEditEvent.getRowValue();
            categoryTableOBJ.setCategoryName(cellEditEvent.getNewValue());
        });
        stocksCategoryTable.setItems(fetchStockCategories());
    }

    private void populateSuppliersTable() {
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        supplierNameColumn.setOnEditCommit(cellEditEvent -> {
            suppliersTableOBJ = cellEditEvent.getRowValue();
            suppliersTableOBJ.setSupplierName(cellEditEvent.getNewValue());
        });

        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        contactColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contactColumn.setOnEditCommit(cellEditEvent -> {
            suppliersTableOBJ = cellEditEvent.getRowValue();
            suppliersTableOBJ.setContactNumber(cellEditEvent.getNewValue());
        });

        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        locationColumn.setOnEditCommit(cellEditEvent -> {
            suppliersTableOBJ = cellEditEvent.getRowValue();
            suppliersTableOBJ.setLocation(cellEditEvent.getNewValue());
        });

        suppliersTable.setItems(fetchSuppliers());
    }

    private void populateStoresTable() {
        storeName.setCellValueFactory(new PropertyValueFactory<>("storeName"));
        storeDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        storeTypeTable.setItems(fetchStores());
    }

    private void populateProductItemsTable() {
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("rowId"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        supplyTypeField.setCellValueFactory(new PropertyValueFactory<>("ProductType"));
        productCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        productBrandColumn.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
        productSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("suppliers"));
        productsStoreColumn.setCellValueFactory(new PropertyValueFactory<>("storeId"));
        productStatusColumn.setCellValueFactory(new PropertyValueFactory<>("activeStatus"));
        productExpiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        productAddedByColumn.setCellValueFactory(new PropertyValueFactory<>("addedBy"));
        productDateAddedColumn.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        productItemTableView.setItems(fetchProductDetails());
    }
    private void populatePriceTableVIew() {

        pricesTableView.setItems(fetchProductPricesDetails());

    }

    private void populateStockLevelTaleView() {
        stockIdField.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        stockNameColumn.setCellValueFactory(new PropertyValueFactory<>("stockItemName"));
        stockLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stockLevel"));
        currentSingleColumn.setCellValueFactory(new PropertyValueFactory<>("currentStockLevel"));
        currentBoxColumn.setCellValueFactory(new PropertyValueFactory<>("currentBoxQuantity"));
        currentPerBoxColumn.setCellValueFactory(new PropertyValueFactory<>("currentQuantityPerBox"));
        oldStockLevelColumn.setCellValueFactory(new PropertyValueFactory<>("oldStockLevel"));
        previousSingleColumn.setCellValueFactory(new PropertyValueFactory<>("previousStockLevel"));
        previousBoxColumn.setCellValueFactory(new PropertyValueFactory<>("previousBoxQuantity"));
        previousPerBoxColumn.setCellValueFactory(new PropertyValueFactory<>("previousQuantityPerBox"));
        stockGagaColumn.setCellValueFactory(new PropertyValueFactory<>("gage"));
        stockModifyColumn.setCellValueFactory(new PropertyValueFactory<>("modifiedBy"));
        stockLastUpdateColumn.setCellValueFactory(new PropertyValueFactory<>("lastModified"));
    }


    /*********************************************************************************************************
     ****** >>                 FILTER TABLE VIEWS.....
     *********************************************************************************************************/


    /*********************************************************************************************************
     ****** >>                    REFRESH TABLE VALUES.....
     *********************************************************************************************************/
    private void refreshStocksCategoryTable() {
        stocksCategoryTable.getItems().clear();
        populateStockCategoryTable();
    }

    private void refreshSuppliersTable() {
        suppliersTable.getItems().clear();
        populateSuppliersTable();
    }
    private void refreshProductsTable() {
        productItemTableView.getItems().clear();
        loadTablesButtonClicked();
    }
    /*********************************************************************************************************
     >>  UNSET FIELDS AND BUTTONS TO DEFAULT
     *********************************************************************************************************/

    public void unsetStocksCategoryVariables() {
        categoryField.clear();
        saveStockCategoryButton.setDisable(true);
        updateStockCategoryButton.setDisable(true);
        deleteStockCategoryButton.setDisable(true);
    }
    void unsetSuppliersVariables() {
        supplierNameField.clear();
        contactField.clear();
        locationField.clear();
        saveSuppliersButton.setDisable(true);
        updateSuppliersButton.setDisable(true);
        deleteSuppliersButton.setDisable(true);
    }

    /*********************************************************************************************************
                 COMBOBOX FILL METHODS.
     *********************************************************************************************************/
    public void fillStockTypeSelector() {
        stockTypeSelector.getItems().add("All");
        stockTypeSelector.getItems().add("Single");
        stockTypeSelector.getItems().add("Box");
    }

    public void fillProductCategorySelector() {
        for (StocksCategoryData item : fetchStockCategories()) {
            productCategorySelector.getItems().add(item.getCategoryName());
        }
    }
    public void fillProductBrandSelector() {
        for (StoresTableData.BrandsTableData item : fetchProductBrands()) {
            productBrandSelector.getItems().add(item.getBrandName());
        }
    }
    public void fillProductSupplierSelector() {
       for (SuppliersData item : fetchSuppliers()) {
           productSupplierSelector.getItems().add(item.getSupplierName());
       }
    }


    /*********************************************************************************************************
     ****** >>                    ACTION EVENT HANDLERS FOR PRODUCT ITEMS TAB...
     *********************************************************************************************************/
    @FXML void reloadBrandSelector() {
        productBrandSelector.getItems().clear();
        fillProductBrandSelector();
    }
    @FXML void saveProductButtonClicked() {
        LocalDateTime generatedDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        String formatDate = formatter.format(generatedDate);
        Timestamp transactionDate = Timestamp.valueOf(formatDate);

        String productName = productNameField.getText();
        String stockType = stockTypeSelector.getValue();
        String proCategory = productCategorySelector.getValue();
        String proBrand = productBrandSelector.getValue();
        String proSupplier = productSupplierSelector.getValue();
        String note = productNoteField.getText();
        Date expiryDate = Date.valueOf(expiryDatePicker.getValue());

        int singleQuantity = Integer.parseInt(singleItemQtyField.getText());
        int boxQuantity = Integer.parseInt(boxQuantityField.getText());
        int qtyPerPack = Integer.parseInt(qtyPerBoxField.getText());
        int totalProduct = Integer.parseInt(totalProductDisplay.getText());

        double purchasePrice = Double.parseDouble(purchasedPriceField.getText());
        double sellingPrice = Double.parseDouble(sellingPriceField.getText());
        double profit = Double.parseDouble(productProfitDisplay.getText());

        int selectedCategoryId = 0;
        int selectedBrandId = 0;
        int selectedSupplierId = 0;

        //GET THE ASSOCIATED ID TO THE SELECTED BRAND.
        for (StoresTableData.BrandsTableData brandItem : fetchProductBrands()) {
           if(brandItem.getBrandName().equals(proBrand)) {
               selectedBrandId = brandItem.getBrandId();
               break;
           }
        }

        //GET THE ASSOCIATED ID TO THE SELECTED CATEGORY ITEM
        for (StocksCategoryData categoryItem : fetchStockCategories()) {
            if (categoryItem.getCategoryName().equals(proCategory)) {
                selectedCategoryId = categoryItem.getId();
                break;
            }
        }

        //GET THE ASSOCIATED ID TO THE SELECTED SUPPLIER
        for (SuppliersData suppliersData : fetchSuppliers()) {
            if (suppliersData.getSupplierName().equals(proSupplier)){
                selectedSupplierId = suppliersData.getId();
                break;
            }
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "please confirm your action to save else cancel to abort.");
        alert.setTitle("SAVE NEW PRODUCT.");
        alert.setHeaderText("ARE YOU SURE YOU WANT TO ADD " + productNameField.getText() + " TO YOUR PRODUCTS?");
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().remove(ButtonType.OK);
            if (alert.showAndWait().get().equals(ButtonType.YES)) {
                    int flag = 0;
                    flag = addNewProductItem(productName,stockType,selectedCategoryId, selectedSupplierId, selectedBrandId, expiryDate, note, 1, transactionDate);
                    ArrayList<Object> returnedProductId = getProductIdAndType(productName);
                    int productId = (int) returnedProductId.get(0);
                    flag += addNewStockLevel(productId, totalProduct, singleQuantity, boxQuantity, qtyPerPack, 1);
                    flag += addNewProductPrice(productId, purchasePrice, sellingPrice, profit, 1);
                    if (flag < 3) {
                        notify.errorNotification("FAILED TO SAVE", "Your operation to save product failed");
                    } else {
                        notify.successNotification("SUCCESSFUL", productName + " successfully added to product list");
                        refreshProductsTable();
                        clearProductsItemField();
                    }
            }
    }


    @FXML void deleteProductButtonClicked() {
        if (productItemTableView.getSelectionModel().isEmpty()) {
            userAlertOBJ = new UserAlerts("MAKE SELECTION", "SORRY! NO SELECTION DETECTED.", "please select the product to be remove.");
            userAlertOBJ.chooseAlert(AlertTypesEnum.WARNING);
            deleteProductButton.setDisable(true);
        } else {
            deleteProductButton.setDisable(false);
            int selectedItemID = productItemTableView.getSelectionModel().getSelectedItem().getRowId();
            String selectedItemName = productItemTableView.getSelectionModel().getSelectedItem().getProductName();

            userAlertOBJ = new UserAlerts("REMOVE PRODUCT", "ARE YOU SURE REMOVE ITEM '" + selectedItemName + "' FROM LIST?",  "please confirm to delete else cancel to abort.");
            userAlertOBJ.chooseAlert(AlertTypesEnum.CONFIRMATION);
            if (userAlertOBJ.confirmationAlert()) {
                int result = deleteSelectedProduct(selectedItemID);
                if (result == 1) {
                    notify.successNotification("REMOVED SUCCESSFUL", selectedItemName + " successfully remove.");
                    refreshProductsTable();
                }
            }
        }

    }
    @FXML void addNewBrandButton() throws IOException {
        multiStages.addBrandStage();
    }

    @FXML void validateProductNameOnKeyTyped() {
        if (checkIfProductExist()) {
            userAlertOBJ = new UserAlerts("PRODUCT EXIST", "'"+productNameField.getText() + "' ALREADY EXIST IN YOUR LIST OF PRODUCTS", "please make sure each product name is unique");
            userAlertOBJ.chooseAlert(AlertTypesEnum.INFORMATION);
            productNameField.clear();
        }
    }
    @FXML void validateAllProductFields() {
        try {
            saveProductButton.setDisable(isProductNameEmpty() || isProductCategoryEmpty() || isProductBrandEmpty() || isStockTypeEmpty() || isExpiryDateEmpty() || isSingleQtyEmpty() || isBoxQtyEmpty() || isPerPackEmpty() || isSellingPriceEmpty() || isPurchasedPriceEmpty());
            if (stockTypeSelector.getValue().equals("Single")) {
                singleItemQtyField.setDisable(false);
                boxQuantityField.setDisable(true);
                qtyPerBoxField.setDisable(true);
                boxQuantityField.setText(String.valueOf(0));
                qtyPerBoxField.setText(String.valueOf(0));
            } else if(stockTypeSelector.getValue().equals("Box")) {
                singleItemQtyField.setDisable(true);
                boxQuantityField.setDisable(false);
                qtyPerBoxField.setDisable(false);
                singleItemQtyField.setText(String.valueOf(0));
            } else {
                singleItemQtyField.setDisable(false);
                boxQuantityField.setDisable(false);
                qtyPerBoxField.setDisable(false);
            }
        }catch (Exception ignored){}
    }

    @FXML void validateGageField(KeyEvent keypressed) {
        if (!(keypressed.getCode().isDigitKey() || keypressed.getCode().isArrowKey() || keypressed.getCode() == KeyCode.BACK_SPACE )) {
            productGate.clear();
        }
    }

    /*********************************************************************************************************
     ****** >>                    KEY EVENT HANDLERS FOR PRODUCT ITEMS TAB............
     *********************************************************************************************************/
    @FXML void keyEventForSingleItemField(KeyEvent keypressed) {
        try {
            if (!(keypressed.getCode().isDigitKey() || keypressed.getCode().isArrowKey() || keypressed.getCode() == KeyCode.BACK_SPACE )) {
            singleItemQtyField.clear();
            } else {
                if (stockTypeSelector.getValue().equals("Single")) {
                    totalProductDisplay.setText(String.valueOf(singleItemQtyField.getText()));
                }
            }
        }catch (NumberFormatException e) {
            totalProductDisplay.setText(String.valueOf(0));
        }
    }
    @FXML void keyEventForBoxQtyField(KeyEvent keypressed) {
        try{
            if (!(keypressed.getCode().isDigitKey() || keypressed.getCode().isArrowKey() || keypressed.getCode() == KeyCode.BACK_SPACE )) {
                boxQuantityField.clear();
            }
        }catch (NumberFormatException e) {
            totalProductDisplay.setText(String.valueOf(0));
        }
    }
    @FXML void keyEventForPerBoxField(KeyEvent keypressed) {
        try {
            if (!(keypressed.getCode().isDigitKey() || keypressed.getCode().isArrowKey() || keypressed.getCode() == KeyCode.BACK_SPACE )) {
                qtyPerBoxField.clear();
            }  else {
                if (stockTypeSelector.getValue().equals("Box")) {
                    totalProductDisplay.setText(String.valueOf(computeTotalBoxQuantity()));
                } else totalProductDisplay.setText(String.valueOf(computeTotalProductQuantity()));
            }
        } catch (NumberFormatException e) {
            totalProductDisplay.setText(String.valueOf(0));
        }
    }
    @FXML void keyEventForPurchasePrice(KeyEvent keypressed) {
        try {
            if (!(keypressed.getCode().isDigitKey() || keypressed.getCode().isArrowKey() || keypressed.getCode() == KeyCode.BACK_SPACE || keypressed.getCode() == KeyCode.PERIOD)) {
                purchasedPriceField.clear();
            }
            productProfitDisplay.setText(String.valueOf(computeProductProfit()));
        } catch (NumberFormatException e) {
            productProfitDisplay.setText(String.valueOf(0));
        }
    }
    @FXML void keyEventForSellingPrice(KeyEvent keypressed) {
        try {
            if (!(keypressed.getCode().isDigitKey() || keypressed.getCode().isArrowKey() || keypressed.getCode() == KeyCode.BACK_SPACE || keypressed.getCode() == KeyCode.PERIOD)) {
                sellingPriceField.clear();
            }
            productProfitDisplay.setText(String.valueOf(computeProductProfit()));
        }catch (NumberFormatException e) {
            productProfitDisplay.setText(String.valueOf(0));
        }
    }

    @FXML void loadTablesButtonClicked() {
        populateProductItemsTable();
        populateStockLevelTaleView();
        populatePriceTableVIew();
        ProductItemTask fillTablesTask = new ProductItemTask(fetchProductDetails(), fetchStockLevelDetails(), fetchProductPricesDetails(), productItemTableView, pricesTableView, stockLevelTableView);
        Thread executeTask = new Thread(fillTablesTask);
        executeTask.start();
    }

    @FXML void productTableViewClicked() {
        deleteProductButton.setDisable(productItemTableView.getSelectionModel().isEmpty() && productItemTableView.isVisible());
    }
    @FXML void stockLevelTableClicked() throws SQLException {
        String[] constants = {"All", "Box", "Single"};
        boolean isNotSelected = stockLevelTableView.getSelectionModel().isEmpty();
        if (isNotSelected) {
            updateStockLevelButton.setDisable(true);
        } else {
            String selectedName = stockLevelTableView.getSelectionModel().getSelectedItem().getStockItemName().getText();
            String stockType = getProductTypeByName(selectedName);
            int gage =  stockLevelTableView.getSelectionModel().getSelectedItem().getGage();
            selectedProductNameDisplay.setText(selectedName); selectedStockTypeDisplay.setText(stockType); productGate.setText(String.valueOf(gage));

            if(Objects.equals(stockType, "Single")) {
                stockLevelSingleQtyField.setDisable(false);
                stockLevelBoxQuantityField.setDisable(true);
                stockLevelQtyPerBoxField.setDisable(true);
                stockLevelBoxQuantityField.setText(String.valueOf(0));
                stockLevelQtyPerBoxField.setText(String.valueOf(0));
            } else if (Objects.equals(stockType, "Box")) {
                stockLevelSingleQtyField.setDisable(true);
                stockLevelSingleQtyField.setText(String.valueOf(0));
                stockLevelBoxQuantityField.setDisable(false);
                stockLevelQtyPerBoxField.setDisable(false);
            } else {
                stockLevelSingleQtyField.setDisable(false);
                stockLevelBoxQuantityField.setDisable(false);
                stockLevelQtyPerBoxField.setDisable(false);
            }
        }
    }

    @FXML void updateStockLevelButtonClicked() {
        int selectedId = stockLevelTableView.getSelectionModel().getSelectedItem().getStockId();//where condition
        String productName = stockLevelTableView.getSelectionModel().getSelectedItem().getStockItemName().getText();
        int singleValue = Integer.parseInt(stockLevelSingleQtyField.getText());//currentStock
        int box = Integer.parseInt(stockLevelBoxQuantityField.getText());//currentBox
        int perBox = Integer.parseInt(stockLevelQtyPerBoxField.getText());//currentUnitPerPack
        int totalProducts = Integer.parseInt(stockLevelTotalProductDisplay.getText());//level
        int gage = Integer.parseInt(productGate.getText());//gage
        String userName = stockLevelTableView.getSelectionModel().getSelectedItem().getModifiedBy();

        int stockLevel = stockLevelTableView.getSelectionModel().getSelectedItem().getStockLevel();//map to oldStockLevel
        int currentLevel = stockLevelTableView.getSelectionModel().getSelectedItem().getCurrentStockLevel();//previous Level
        int currentBoxValue = stockLevelTableView.getSelectionModel().getSelectedItem().getCurrentBoxQuantity();//Previous Box Level
        int currentPerBox = stockLevelTableView.getSelectionModel().getSelectedItem().getCurrentQuantityPerBox(); //previous PerBox.

        int finalStockLeve = totalProducts + stockLevel;

//        String activeUsername = Homepage.activeUsername;
//        int activeUserId = getUserIdByUsername(activeUsername);
//

        userAlertOBJ = new UserAlerts("UPDATE STOCK LEVEL", "YOU HAVE REQUESTED TO UPDATE STOCK DETAILS OF (" + productName + ")", "please confirm your action to update else cancel to abort.");

        if (userAlertOBJ.confirmationAlert()) {

            int flag = updateStockLevel(selectedId, finalStockLeve, singleValue, box, perBox, stockLevel, currentLevel, currentBoxValue,currentPerBox, gage, 1);
            switch(flag) {
                case 1 -> {
                    notify.successNotification("UPDATE SUCCESSFUL","(" + productName + ") stock level successfully updated");
                    loadTablesButtonClicked();
                    clearStocksLevelFields();
                }
                case 0 -> notify.errorNotification("UPDATE FAILED", "Your request to update stock level for ("+ productName + ") failed");
            }
        }



    }

    @FXML void validateStockLevelSingleInput(KeyEvent keypressed) {
        try {
            if (!(keypressed.getCode().isDigitKey() || keypressed.getCode().isArrowKey() || keypressed.getCode() == KeyCode.BACK_SPACE )) {
                stockLevelSingleQtyField.setText(String.valueOf(0));
            } else {
                int singleValue = Integer.parseInt(stockLevelSingleQtyField.getText());
                stockLevelTotalProductDisplay.setText(String.valueOf(singleValue));
            }

        }catch (NumberFormatException e) {
            stockLevelTotalProductDisplay.setText(String.valueOf(0));
        }
    }

    @FXML void validateStockLevelBoxInputs(KeyEvent keypressed) {
        try {
            if (!(keypressed.getCode().isDigitKey() || keypressed.getCode().isArrowKey() || keypressed.getCode() == KeyCode.BACK_SPACE )) {
                stockLevelBoxQuantityField.setText(String.valueOf(0));
                stockLevelQtyPerBoxField.setText(String.valueOf(0));
            } else {
                int single = Integer.parseInt(stockLevelSingleQtyField.getText());
                int box = Integer.parseInt(stockLevelBoxQuantityField.getText());
                int perBox = Integer.parseInt(stockLevelQtyPerBoxField.getText());


                if (stockLevelQtyPerBoxField.isDisable()) {
                    stockLevelTotalProductDisplay.setText(String.valueOf(box * perBox));
                } else {
                    int total = single;
                    total += box * perBox;
                    stockLevelTotalProductDisplay.setText(String.valueOf(total));
                }
            }
        }catch (NumberFormatException e) {
            stockLevelTotalProductDisplay.setText(String.valueOf(0));
        }

    }

    @FXML void stockLevelMouseMovedEvent() {
        updateStockLevelButton.setDisable(stockLevelTotalProductDisplay.getText().equals(String.valueOf(0)));
    }

    /*********************************************************************************************************
     ******                   COMPUTATION FOR PRODUCT ITEM FIELDS
     *********************************************************************************************************/
    int computeTotalProductQuantity() {
        int single = Integer.parseInt(singleItemQtyField.getText());
        int boxQty = Integer.parseInt(boxQuantityField.getText());
        int perPack  = Integer.parseInt(qtyPerBoxField.getText());
        return single + (boxQty * perPack);
    }
    int computeTotalBoxQuantity() {
        int boxQty = Integer.parseInt(boxQuantityField.getText());
        int perPack  = Integer.parseInt(qtyPerBoxField.getText());
        return (boxQty * perPack);
    }
    double computeProductProfit() {
        Double purchase = Double.parseDouble(purchasedPriceField.getText());
        Double selling = Double.parseDouble(sellingPriceField.getText());
        Double result = selling - purchase;
        return Double.parseDouble(String.format("%.2f%n", result));
    }

    /*********************************************************************************************************
     ******>>>>>                     TRUE OR FALSE STATEMENTS FOR PRODUCT ITEM FIELDS
     *********************************************************************************************************/
    boolean isProductNameEmpty() {
        return productNameField.getText().isEmpty();
    }
    boolean isStockTypeEmpty() {
        return stockTypeSelector.getValue().isEmpty();
    }
    boolean isProductBrandEmpty() {
        return productBrandSelector.getValue().isEmpty();
    }
    boolean isProductCategoryEmpty() {
        return productCategorySelector.getValue().isEmpty();
    }
    boolean isExpiryDateEmpty() {
        return expiryDatePicker.getValue() == null;
    }
    boolean isSingleQtyEmpty() {
        return singleItemQtyField.getText().isBlank();
    }
    boolean isBoxQtyEmpty() {
        return boxQuantityField.getText().isBlank();
    }
    boolean isPerPackEmpty() {
        return qtyPerBoxField.getText().isEmpty();
    }
    boolean isPurchasedPriceEmpty() {
        return purchasedPriceField.getText().isBlank();
    }
    boolean isSellingPriceEmpty() {
        return sellingPriceField.getText().isBlank();
    }

    boolean checkIfProductExist() {
        boolean flag = false;
        String userInput = productNameField.getText().toLowerCase().replaceAll("\\s", "");
        for (ProductsStockData productName : fetchProductDetails()) {
            if ( productName.getProductName().toLowerCase().replaceAll("\\s", "").equals(userInput)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void clearProductsItemField() {
        productNameField.clear();
        productSupplierSelector.setValue(null);
        stockTypeSelector.setValue(null);
        productBrandSelector.setValue(null);
        productCategorySelector.setValue(null);
        expiryDatePicker.setValue(null);
        singleItemQtyField.clear();
        boxQuantityField.clear();
        qtyPerBoxField.clear();
        purchasedPriceField.clear();
        sellingPriceField.clear();
        productNoteField.clear();
        totalProductDisplay.setText(String.valueOf(0));
        productProfitDisplay.setText(String.valueOf(0));
    }

    void clearStocksLevelFields() {
        stockLevelSingleQtyField.clear();
        stockLevelBoxQuantityField.clear();
        stockLevelQtyPerBoxField.clear();
        stockLevelTotalProductDisplay.setText(String.valueOf(0));

    }
}//END OF CLASS
