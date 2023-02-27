package inn.Controllers.settings;
import inn.multiStage.MultiStages;
import inn.tableViews.*;

import inn.models.ManageStocksModel;

import inn.prompts.UserNotification;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.event.EventHandler;
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
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimerTask;

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
    @FXML private Label requiredIndicator;
    @FXML private TextField searchProductsTextField;


    //******************* >>> PRODUCT STOCKS NODES
    @FXML private TextField productNameField, totalProductQuantityField, unitQuantityField,packQuantityField, qtyPerPackField;
    @FXML private TextArea notesField;
    @FXML private ComboBox<String> itemDescBox, itemCategoryBox, itemSupplierBox, selectStoreBox;
//    @FXML private MFXLegacyComboBox<String> itemBrandBox;
    @FXML private DatePicker expiryDatePicker;
    @FXML private Button saveProductBtn, deleteProductBtn;
    @FXML private ComboBox<String> brandComboBox;


    //******************* >> STORES TABLE VIEW
    @FXML private TextField storesInputField, storeDescriptionField;
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
    @FXML private MFXLegacyTableView<ProductsStockData> productsTableView;
    @FXML private TableColumn<ProductsStockData, Integer> itemIdColumn;
    @FXML private TableColumn<ProductsStockData, String> itemNameColumn;
    @FXML private TableColumn<ProductsStockData, String> productTypeColumn;
    @FXML private TableColumn<ProductsStockData, String> brandColumn;
    @FXML private TableColumn<ProductsStockData, String> categoryColumn;
    @FXML private TableColumn<ProductsStockData, String> supplierColumn;
    @FXML private TableColumn<ProductsStockData, Date> expiryDateColumn;
    @FXML private TableColumn<ProductsStockData, Label> statusColumn;
    @FXML private TableColumn<ProductsStockData, Integer> addedByColumn;
    @FXML private TableColumn<ProductsStockData, Timestamp> dateCreatedColumn;


    //******************* >>STOCK LEVEL TABLE VIEW NODES
    @FXML private MFXLegacyTableView<StockLevelData> stocksLevelTableView;
    @FXML private TableColumn<StockLevelData, String> stockLevelProductNameColumn;
    @FXML private TableColumn<StockLevelData, Integer> stockLevelAvailableQtyColumn;
    @FXML private TableColumn<StockLevelData, Integer> stockLevelCurrentQtyColumn;
    @FXML private TableColumn<StockLevelData, Integer> presentQtyUnitColumn;
    @FXML private TableColumn<StockLevelData, Integer> presentQtyPackColumn;
    @FXML private TableColumn<StockLevelData, Integer> presentQtyPerPackColumn;
    @FXML private TableColumn<StockLevelData, Integer> previousQtyUnitColumn;
    @FXML private TableColumn<StockLevelData, Integer> previousQtyPackColumn;
    @FXML private TableColumn<StockLevelData, Integer> previousQtyPerPackColumn;
    @FXML private TableColumn<StockLevelData, Integer> beforeQtyUnitColumn;
    @FXML private TableColumn<StockLevelData, Integer> beforeQtyPackColumn;
    @FXML private TableColumn<StockLevelData, Integer> beforeQtyPerPackColumn;
    @FXML private TableColumn<StockLevelData, Integer> stockLevelGageColumn;
    @FXML private TableColumn<StockLevelData, Timestamp> stockLevelUpdatedDateColumn;
    @FXML private TableColumn<StockLevelData, String> stockLevelUpdatedByColumn;

    @FXML private MFXButton updateStockLevelBtn;
    @FXML private Label displayStockLevelProductName, stockLevelDisplayProductType;
    @FXML private MFXTextField stockLevelUnitQtyField, stockLevelPackQtyField, stockLevelPerPackField, stockGuageField;
    @FXML private TextField stockLevelTotalProduct;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateStockCategoryTable();
        populateSuppliersTable();
        populateStoresTable();
        fillStoresComboBox();
        fillCategoryComboBox();
        fillItemDescriptionBox();
        fillSuppliersComboBox();
        populateTableView.run();
    }


    //CLASS INSTANTIATION FIELD...
    UserNotification notify = new UserNotification();
    StocksCategoryData categoryTableOBJ;
    SuppliersData suppliersTableOBJ;
    MultiStages multiStages = new MultiStages();


    /*********************************************************************************************************
     ****** >>                   INPUT FIELDS VALIDATION OPERATION...
     *********************************************************************************************************/
    @FXML void validateUnitQuantityField(KeyEvent event) {
        if(!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE )) {
            unitQuantityField.clear();
        }
    }
    @FXML void validatePackQuantityField(KeyEvent event) {
        if(!( event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE)) {
            packQuantityField.clear();
        }
            try{
                if(Integer.parseInt(packQuantityField.getText()) == 0) {
                    totalProductQuantityField.setText(String.valueOf(0));
                }
            }catch (NumberFormatException ex) {
                totalProductQuantityField.setText(String.valueOf(0));
            }
    }
    @FXML private void validateQtyPerPackField(KeyEvent event) {
            if(!( event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE)) {
                qtyPerPackField.clear();
            }

    }

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
            case 1 -> {
                notify.successNotification("UPDATE SUCCESSFUL", "Suppliers Table Items Successfully Updated.");
            }
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

    @FXML void saveProductButtonOnAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Click YES to add product else cancel to abort process.");
        alert.setTitle("ADD NEW PRODUCT");
        alert.setHeaderText("ARE YOU SURE YOU WANT TO ADD '"+productNameField.getText()+"' TO YOUR PRODUCTS?");
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().remove(ButtonType.OK);

        Date expiryDate = Date.valueOf(expiryDatePicker.getValue());
        int unitQty = Integer.parseInt(unitQuantityField.getText());
        int packQty = Integer.parseInt(packQuantityField.getText());
        int qtyPerPac = Integer.parseInt(qtyPerPackField.getText());
        int totalQty = Integer.parseInt(totalProductQuantityField.getText());
        String productName = productNameField.getText();

        if (alert.showAndWait().get().equals(ButtonType.YES)) {
            int outputResult = insertProduct(productNameField.getText(), itemDescBox.getValue(),  brandComboBox.getValue(), itemCategoryBox.getValue(), itemSupplierBox.getValue(), notesField.getText(), expiryDate,  (byte)1);
            switch (outputResult) {
                case 1 -> {
                    ArrayList<Object> returnedValues = getProductIdAndType(productName);
                    int productId = (int) returnedValues.get(0);
                    insertNewStockLevelItem(productId, unitQty, packQty, qtyPerPac, totalQty);
                    notify.successNotification("SUCCESSFULLY ADDED", "New product successfully added.");
                    refreshProductsTable();
                    clearFields();
                }
                case 0 -> notify.errorNotification("ADD PRODUCT FAILED", "Failed to add new product");
            }
        }
    }

    @FXML void productsTableViewClicked() {
        deleteProductBtn.setDisable(checkProductsTableSelection());
    }

    @FXML void loadBrandOnAction() {
        brandComboBox.getItems().clear();
        try {
            for(StoresTableData.BrandsTableData item : fetchProductBrands()) {
                brandComboBox.getItems().add(item.getBrandName());
            }
        }catch (Exception ignored) {

        }

    }

    @FXML void addBrandPopupClicked() throws IOException {
        multiStages.addBrandStage();
    }

    @FXML void deleteProductButtonOnAction() {
        int itemId = productsTableView.getSelectionModel().getSelectedItem().getRowId();
        String selectedItemName = productsTableView.getSelectionModel().getSelectedItem().getProductName();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm to remove product, else cancel to abort.");
        alert.setTitle("REMOVE PRODUCT.");
        alert.setHeaderText("ARE YOU SURE YOU WANT REMOVE '" + selectedItemName + "' FROM PRODUCT LIST.");
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(ButtonType.YES);
        if(alert.showAndWait().get().equals(ButtonType.YES)) {
            int removeStatus = removeProduct(itemId);
            switch(removeStatus) {
                case 1 -> {
                    notify.successNotification("REMOVE SUCCESSFUL", "Selected Product successfully removed.");
                    refreshProductsTable();
                }
                case 0 -> notify.errorNotification("REMOVE FAILED", "Product Removal failed.");
            }
        }
    }

    @FXML void validateSelectedItemDesc() {
        try {
            String selectedDesc = itemDescBox.getValue();
            if(selectedDesc.equals("Unit Item")) {
                unitQuantityField.setDisable(false);
                packQuantityField.setDisable(true);
                qtyPerPackField.setDisable(true);
            } else if (selectedDesc.equals("Pack Item")) {
                unitQuantityField.setDisable(true);
                packQuantityField.setDisable(false);
                qtyPerPackField.setDisable(false);
            } else {
                unitQuantityField.setDisable(false);
                packQuantityField.setDisable(false);
                qtyPerPackField.setDisable(false);
            }
        } catch (NullPointerException ignored) {
                saveProductBtn.setDisable(true);
        }
    }

    @FXML void validateAllFields() {
        saveProductBtn.setDisable(checkItemNameField() || checkItemDescField() || checkExpiryDatePicker() || checkItemCategoryBox() || checkBrandComboBox());

        String selectedDesc = itemDescBox.getValue();
        try {
            if(selectedDesc.equals("Unit Item")) {
                packQuantityField.setText(String.valueOf(0));
                qtyPerPackField.setText(String.valueOf(0));
                totalProductQuantityField.setText(unitQuantityField.getText());
            } else if (selectedDesc.equals("Pack Item")) {
                unitQuantityField.setText(String.valueOf(0));
                totalProductQuantityField.setText(String.valueOf( computePackQuantityOnly()));
            } else {
                totalProductQuantityField.setText(String.valueOf(computeTotalQuantity()));
            }
        }catch (NumberFormatException ex) {
            totalProductQuantityField.setText(String.valueOf(0));
        } catch (NullPointerException ignored) {}

    }
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

    @FXML private void validateProductName() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Please provide a unique name for each product.");
        alert.setTitle("PRODUCT ALREADY EXIST");
        alert.setHeaderText("ITEM NAME PROVIDED ALREADY EXIST IN THE DATABASE");
        if (checkIfProductNameExist()) {
            alert.showAndWait();
            productNameField.clear();
        }
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
    boolean checkBrandComboBox() {
        return brandComboBox.getValue().isBlank();
    }
    boolean checkItemCategoryBox() {
        return itemCategoryBox.getValue().isBlank();
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

    boolean checkItemNameField() {
        return productNameField.getText().isBlank();
    }

    boolean checkUnitQuantityField() {
        return unitQuantityField.getText().isBlank();
    }
    boolean checkPackQuantityField(){
        return packQuantityField.getText().isBlank();
    }
    boolean checkItemDescField() {
        return itemDescBox.getValue() == null;
    }
    boolean checkSelectedStoreField() {
        return selectStoreBox.getValue() == null;
    }
    boolean checkQtyPerPackField () {
        return qtyPerPackField.getText().isBlank();
    }
    boolean checkExpiryDatePicker() {
        return expiryDatePicker.getValue() == null;
    }
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
            if(Objects.equals(item.getSupplierName().trim().toLowerCase(), itemName)) {
                flag = true;
            }
        }
        return flag;
    }

    boolean checkIfProductNameExist(){
        boolean flag = false;
        for(ProductsStockData item : fetchProductDetails()) {
            String itemName = item.getProductName().toLowerCase().replaceAll("\\s", "");
          if (Objects.equals(itemName, productNameField.getText().toLowerCase().replaceAll("\\s", ""))){
              flag = true;
            }
        }
        return flag;
    }

    boolean checkProductsTableSelection() {
        return productsTableView.getSelectionModel().isEmpty();
    }



    /*********************************************************************************************************
     ****** >>                     TABLE VIEW FILL METHODS...
     *********************************************************************************************************/

    public TimerTask populateTableView = new TimerTask() {
        @Override
        public void run() {
            productsTableView.getItems().clear();
            itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("rowId"));
            itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
            productTypeColumn.setCellValueFactory(new PropertyValueFactory<>("ProductType"));
            brandColumn.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
            supplierColumn.setCellValueFactory(new PropertyValueFactory<>("suppliers"));
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
            expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("activeStatus"));
            addedByColumn.setCellValueFactory(new PropertyValueFactory<>("addedBy"));
            dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
            productsTableView.setItems(fetchProductDetails());

            //stocksLevelTableView CELL POPULATION
            stocksLevelTableView.getItems().clear();
            stockLevelProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
            stockLevelAvailableQtyColumn.setCellValueFactory(new PropertyValueFactory<>("stockLevel"));
            stockLevelCurrentQtyColumn.setCellValueFactory(new PropertyValueFactory<>("currentQty"));
            presentQtyUnitColumn.setCellValueFactory(new PropertyValueFactory<>("PresentUnitQty"));
            presentQtyPackColumn.setCellValueFactory(new PropertyValueFactory<>("PresentPackQty"));
            presentQtyPerPackColumn.setCellValueFactory(new PropertyValueFactory<>("PresentPackPerQty"));
            previousQtyUnitColumn.setCellValueFactory(new PropertyValueFactory<>("PreviousUnitQty"));
            previousQtyPackColumn.setCellValueFactory(new PropertyValueFactory<>("PreviousPackQty"));
            previousQtyPerPackColumn.setCellValueFactory(new PropertyValueFactory<>("PreviousPackPerQty"));
            beforeQtyUnitColumn.setCellValueFactory(new PropertyValueFactory<>("BeforeUnitQty"));
            beforeQtyPackColumn.setCellValueFactory(new PropertyValueFactory<>("BeforePackQty"));
            beforeQtyPerPackColumn.setCellValueFactory(new PropertyValueFactory<>("BeforePerPackQty"));
            stockLevelGageColumn.setCellValueFactory(new PropertyValueFactory<>("StockGuage"));
            stockLevelUpdatedDateColumn.setCellValueFactory(new PropertyValueFactory<>("UpdatedDate"));
            stockLevelUpdatedByColumn.setCellValueFactory(new PropertyValueFactory<>("UpdatedBy"));
            stocksLevelTableView.setItems(fetchStockLevelDetails());
            populateTableView.cancel();

        }
    };


    private void populateStockCategoryTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));

        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<StocksCategoryData, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<StocksCategoryData, String> cellEditEvent) {
                categoryTableOBJ = cellEditEvent.getRowValue();
                categoryTableOBJ.setCategoryName(cellEditEvent.getNewValue());
            }
        });
        stocksCategoryTable.setItems(fetchStockCategories());
    }

    private void populateSuppliersTable() {
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        supplierNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SuppliersData, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SuppliersData, String> cellEditEvent) {
                suppliersTableOBJ = cellEditEvent.getRowValue();
                suppliersTableOBJ.setSupplierName(cellEditEvent.getNewValue());
            }
        });

        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        contactColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contactColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SuppliersData, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SuppliersData, String> cellEditEvent) {
                suppliersTableOBJ = cellEditEvent.getRowValue();
                suppliersTableOBJ.setContactNumber(cellEditEvent.getNewValue());
            }
        });

        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        locationColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SuppliersData, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SuppliersData, String> cellEditEvent) {
                suppliersTableOBJ = cellEditEvent.getRowValue();
                suppliersTableOBJ.setLocation(cellEditEvent.getNewValue());
            }
        });

        suppliersTable.setItems(fetchSuppliers());
    }

    private void populateStoresTable() {
        storeName.setCellValueFactory(new PropertyValueFactory<>("storeName"));
        storeDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        storeTypeTable.setItems(fetchStores());
    }

    /*********************************************************************************************************
     ****** >>                 FILTER TABLE VIEWS.....
     *********************************************************************************************************/
    @FXML void filterProductsTable() {
       if (searchProductsTextField.getText().isBlank()) {
           refreshProductsTable();
       } else {
           String userInput = searchProductsTextField.getText();

           productsTableView.setItems(filterProductStockTable(userInput));
       }
    }

    /*********************************************************************************************************
     ****** >>                    REFRESH TABLE VALUES.....
     *********************************************************************************************************/
    private void refreshStocksCategoryTable() {
        stocksCategoryTable.getItems().clear();
        populateStockCategoryTable();
    }
    private void refreshProductsTable(){
        populateTableView.run();
    }
    private void refreshSuppliersTable() {
        suppliersTable.getItems().clear();
        populateSuppliersTable();
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
    void fillStoresComboBox() {
        for (StoresTableData item : fetchStores()) {
            selectStoreBox.getItems().add(item.getStoreName());
        }
    }
    void fillCategoryComboBox() {
        for (StocksCategoryData item : fetchStockCategories()) {
            itemCategoryBox.getItems().add(item.getCategoryName());
        }
    }
    void fillSuppliersComboBox() {
        for (SuppliersData item : fetchSuppliers()) {
            itemSupplierBox.getItems().add(item.getSupplierName());
        }
    }
    void fillItemDescriptionBox() {
        itemDescBox.getItems().add(0,"All");
        itemDescBox.getItems().add(1, "Unit Item");
        itemDescBox.getItems().add(2, "Pack Item");
    }

    void clearFields() {
            try {
                productNameField.clear();
                itemDescBox.setValue(null);
//                brandComboBox.setValue(null);
                itemCategoryBox.setValue(null);
                itemSupplierBox.setValue(null);
                expiryDatePicker.setValue(null);
                notesField.clear();
                saveProductBtn.setDisable(true);
                unitQuantityField.setText(String.valueOf(0));
                packQuantityField.setText(String.valueOf(0));
                qtyPerPackField.setText(String.valueOf(0));
                totalProductQuantityField.setText(String.valueOf(0));

            }catch (NullPointerException ignored) {

            }
    }


//    INPUT FIELD COMPUTATIONS...
    int computePackQuantityOnly() {
        int result = 0;
        try {
            if (Integer.parseInt(packQuantityField.getText()) != 0 && Integer.parseInt(qtyPerPackField.getText()) != 0) {
                result = Integer.parseInt(packQuantityField.getText()) * Integer.parseInt(qtyPerPackField.getText());;
                totalProductQuantityField.setText(String.valueOf(result));
            } else {
                totalProductQuantityField.setText(String.valueOf(0));
            }
        }catch (NumberFormatException ex) {
            totalProductQuantityField.setText(String.valueOf(0));
        }
        return result;
    }

    int computeTotalQuantity() {
        int result = 0;
        try {
            if(unitQuantityField.getText().isBlank() || Integer.parseInt(unitQuantityField.getText()) == 0){
                totalProductQuantityField.setText(String.valueOf(result));
            } else {
                result = Integer.parseInt(unitQuantityField.getText()) + computePackQuantityOnly();
            }
        }catch (NumberFormatException ex) {

            totalProductQuantityField.setText(String.valueOf(0));
        }
        return result;
    }

    /*********************************************************************************************************
     ****** >>                    ACTION EVENT HANDLERS FOR STOCKS LEVELS TAB...
     *********************************************************************************************************/
    public void refreshStocksLeveTableButtonClicked() {
        populateTableView.run();
    }
    public void validateStockQuantityFields() {

    }

    public void dynamicStockFieldsComputation() {
        updateStockLevelBtn.setDisable(checkStockLevelPackQtyField() || checkStockUnitField() || checkStockLevelQtyPerPackField() );
    }

    public void stocksLevelTableViewClicked() {
        String fetchedProductType = "";
        String selectedItemName = stocksLevelTableView.getSelectionModel().getSelectedItem().getProductName();
        int selectedItemId =  stocksLevelTableView.getSelectionModel().getSelectedItem().getItemId();
        System.out.println(selectedItemId);

        if (!(stocksLevelTableView.getSelectionModel().isEmpty())) {
            ArrayList<String> fetchedValues = getProductTypeByName(selectedItemName);
            fetchedProductType = fetchedValues.get(0);

            displayStockLevelProductName.setText(selectedItemName);
            stockLevelDisplayProductType.setText(fetchedProductType);
        } else {
            System.out.println("null");
        }
    }
    /*********************************************************************************************************
     ******>>>>>                     TRUE OR FALSE STATEMENTS FOR STOCKS LEVEL FIELDS
     *********************************************************************************************************/
    boolean checkStockUnitField() {
        return stockLevelUnitQtyField.getText().isBlank();
    }
    boolean checkStockLevelPackQtyField() {
        return stockLevelPackQtyField.getText().isBlank();
    }
    boolean checkStockLevelQtyPerPackField() {
        return stockLevelPerPackField.getText().isBlank();
    }
    boolean checkStockGageField() {
        return stockGuageField.getText().isBlank();
    }

}//END OF CLASS
