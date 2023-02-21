package inn.Controllers.settings;
import inn.multiStage.MultiStages;
import inn.tableViews.*;

import inn.models.ManageStocksModel;

import inn.prompts.UserNotification;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
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
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageStocks extends ManageStocksModel implements Initializable {


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


    //******************* >>> PRODUCT STOCKS NODES
    @FXML private TextField productNameField, totalProductQuantityField, unitQuantityField,packQuantityField, qtyPerPackField;
    @FXML private TextArea notesField;
    @FXML private ComboBox<String> itemDescBox, itemCategoryBox, itemSupplierBox, selectStoreBox;
    @FXML private MFXLegacyComboBox<String> itemBrandBox;
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
    @FXML private TableView<SuppliersTableViewItems> suppliersTable;
    @FXML private  TableColumn<SuppliersTableViewItems, Integer> supplierIdColumn;
    @FXML private  TableColumn<SuppliersTableViewItems, String> supplierNameColumn;
    @FXML private  TableColumn<SuppliersTableViewItems, String> locationColumn;
    @FXML private  TableColumn<SuppliersTableViewItems, String> contactColumn;


    //******************* >> STOCKS CATEGORY TABLE VIEW NODES
    @FXML private  TableView<StocksCategoryTableView> stocksCategoryTable;
    @FXML private  TableColumn<StocksCategoryTableView, Integer> idColumn;
    @FXML private  TableColumn<StocksCategoryTableView, String> categoryNameColumn;

    //******************* >>PRODUCTS TABLE VIEW NODES
    @FXML private MFXLegacyTableView<ProductStock> ProductStocksTable;
    @FXML private TableColumn<ProductStock, String> itemNameColumn;
    @FXML private TableColumn<ProductStock, String> itemDescColumn;
    @FXML private TableColumn<ProductStock, String> itemBrandColumn;
    @FXML private TableColumn<ProductStock, String> itemCategoryColumn;
    @FXML private TableColumn<ProductStock, String> itemSupplierColumn;
    @FXML private TableColumn<ProductStock, Date> expiryDateColumn;
    @FXML private TableColumn<ProductStock, Integer> unitQuantityColumn;
    @FXML private TableColumn<ProductStock, Integer> packQuantityColumn;
    @FXML private TableColumn<ProductStock, Integer> qtyPerPackColumn;
    @FXML private TableColumn<ProductStock, Integer> totalQuantityColumn;
    @FXML private TableColumn<ProductStock, Byte> statusColumn;
    @FXML private TableColumn<ProductStock, Byte> dateCreatedColumn;
    @FXML private TableColumn<ProductStock, Byte> userNameColumn;
    @FXML private TableColumn<ProductStock, String> notesColumn;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateStockCategoryTable();
        populateSuppliersTable();
        populateStoresTable();
        populateProductsTableView();
        fillStoresComboBox();
        fillCategoryComboBox();
        fillItemDescriptionBox();
        fillSuppliersComboBox();

    }


    //CLASS INSTANTIATION FIELD...
    UserNotification notify = new UserNotification();
    StocksCategoryTableView categoryTableOBJ;
    SuppliersTableViewItems suppliersTableOBJ;
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
        for (StocksCategoryTableView item : stocksCategoryTable.getItems()) {
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
        for (SuppliersTableViewItems items : suppliersTable.getItems()) {
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
        int itemQuantity = Integer.parseInt(unitQuantityField.getText());
        int packQuantity = Integer.parseInt(packQuantityField.getText());
        int qtyPerPack = Integer.parseInt(qtyPerPackField.getText());
        int totalProductQuantity = Integer.parseInt(totalProductQuantityField.getText());

        if (alert.showAndWait().get().equals(ButtonType.YES)) {
            int outputResult = insertProduct(productNameField.getText(), itemDescBox.getValue(), brandComboBox.getValue(), itemCategoryBox.getValue(), itemSupplierBox.getValue(), notesField.getText(), expiryDate, itemQuantity, packQuantity, qtyPerPack, totalProductQuantity, (byte)1);
            switch (outputResult) {
                case 1 -> {
                    notify.successNotification("SUCCESSFULLY ADDED", "New product successfully added.");
                    clearFields();
                }
                case 0 -> notify.errorNotification("ADD PRODUCT FAILED", "Failed to add new product");
            }
        }
    }

    @FXML void loadBrandOnAction() {
        brandComboBox.getItems().clear();
        for(StoresTableData.BrandsTableData item : fetchProductBrands()) {
            brandComboBox.getItems().add(item.getBrandName());
        }
    }

    @FXML void addBrandPopupClicked() throws IOException {
        multiStages.addBrandStage();
    }

    @FXML void deleteProductButtonOnAction() {}

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

        }

    }
    @FXML void validateAllFields() {
        saveProductBtn.setDisable(checkItemNameField() || checkQtyPerPackField() || checkExpiryDatePicker() || checkPackQuantityField() || checkUnitQuantityField() || checkItemDescField() );
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
        for (StocksCategoryTableView item : fetchStockCategories()) {
           String itemName = categoryField.getText().toLowerCase().trim();
           if(Objects.equals(item.getCategoryName().trim().toLowerCase(), itemName)) {
               flag = true;
           }
        }
        return flag;
    }

    boolean checkIfSupplierAlreadyExist() {
        boolean flag = false;
        for (SuppliersTableViewItems item : fetchSuppliers()) {
            String itemName = supplierNameField.getText().toLowerCase().trim();
            if(Objects.equals(item.getSupplierName().trim().toLowerCase(), itemName)) {
                flag = true;
            }
        }
        return flag;
    }

    boolean checkIfProductNameExist() throws SQLException {
        boolean flag = false;
        for(ProductStock item : fetchProductDetails()) {
            String itemName = item.getProductName().toLowerCase().replaceAll("\\s", "");
          if (Objects.equals(itemName, productNameField.getText().toLowerCase().replaceAll("\\s", ""))){
              flag = true;
            }
        }
        return flag;
    }

    /*********************************************************************************************************
     ****** >>                     TABLE VIEW FILL METHODS...
     *********************************************************************************************************/

    private void populateProductsTableView() {
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        itemDescColumn.setCellValueFactory(new PropertyValueFactory<>("productDesc"));
        itemBrandColumn.setCellValueFactory(new PropertyValueFactory<>("productBrand"));
        itemCategoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        itemSupplierColumn.setCellValueFactory(new PropertyValueFactory<>("suppliers"));
        expiryDateColumn.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        unitQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("unitQuantity"));
        packQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("packQuantity"));
        qtyPerPackColumn.setCellValueFactory(new PropertyValueFactory<>("qtyPerPack"));
        totalQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("totalQuantity"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("activeStatus"));
        dateCreatedColumn.setCellValueFactory(new PropertyValueFactory<>("dateAdded"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("addedBy"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
        ProductStocksTable.setItems(fetchProductDetails());
    }

    private void populateStockCategoryTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));

        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        categoryNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        categoryNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<StocksCategoryTableView, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<StocksCategoryTableView, String> cellEditEvent) {
                categoryTableOBJ = cellEditEvent.getRowValue();
                categoryTableOBJ.setCategoryName(cellEditEvent.getNewValue());
            }
        });
        stocksCategoryTable.setItems(fetchStockCategories());
    }

    private void populateSuppliersTable() {
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        supplierNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        supplierNameColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SuppliersTableViewItems, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SuppliersTableViewItems, String> cellEditEvent) {
                suppliersTableOBJ = cellEditEvent.getRowValue();
                suppliersTableOBJ.setSupplierName(cellEditEvent.getNewValue());
            }
        });

        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        contactColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        contactColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SuppliersTableViewItems, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SuppliersTableViewItems, String> cellEditEvent) {
                suppliersTableOBJ = cellEditEvent.getRowValue();
                suppliersTableOBJ.setContactNumber(cellEditEvent.getNewValue());
            }
        });

        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        locationColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<SuppliersTableViewItems, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<SuppliersTableViewItems, String> cellEditEvent) {
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
     ****** >>                    REFRESH TABLE VALUES.....
     *********************************************************************************************************/
    private void refreshStocksCategoryTable() {
        stocksCategoryTable.getItems().clear();
        populateStockCategoryTable();
    }
    private void refreshProductsTable() throws SQLException {
        ProductStocksTable.getItems().clear();
        populateProductsTableView();
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
        for (StocksCategoryTableView item : fetchStockCategories()) {
            itemCategoryBox.getItems().add(item.getCategoryName());
        }
    }
    void fillSuppliersComboBox() {
        for (SuppliersTableViewItems item : fetchSuppliers()) {
            itemSupplierBox.getItems().add(item.getSupplierName());
        }
    }
    void fillItemDescriptionBox() {
        itemDescBox.getItems().add(0,"All");
        itemDescBox.getItems().add(1, "Unit Item");
        itemDescBox.getItems().add(2, "Pack Item");
    }

    void clearFields() {
            productNameField.clear();
            itemDescBox.setValue(null);
            brandComboBox.setValue(null);
            unitQuantityField.clear();
            packQuantityField.clear();
            itemCategoryBox.setValue(null);
            itemSupplierBox.setValue(null);
            expiryDatePicker.setValue(null);
            notesField.clear();
            saveProductBtn.setDisable(true);
            deleteProductBtn.setDisable(true);
            totalProductQuantityField.clear();
            qtyPerPackField.clear();
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


}//END OF CLASS
