package inn.Controllers.settings;

import inn.models.ManageStocksModel;
import inn.prompts.UserNotification;
import inn.tableViews.StockProductsTableView;
import inn.tableViews.StocksCategoryTableView;
import inn.tableViews.SuppliersTableViewItems;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageStocks extends ManageStocksModel implements Initializable {

    /*********************************************************************************************************
     ****** >>                      FXML NODE EJECTIONS
     *********************************************************************************************************/
    @FXML private TabPane stocksControlTabPane;
    @FXML private Tab stocksTab, stockLevelTab, internalSupplyTab, externalSupplyTab, addComponentsTab;


    //******************* >> ADD COMPONENTS TAB NODE ITEMS
    @FXML private TitledPane suppliersTab, stockCategoriesTab;
    @FXML private CheckBox stockCategoryCheckBox, suppliersCheckBox;
    @FXML private TextField supplierNameField, contactField, locationField, categoryField;
    @FXML private Button saveSuppliersButton, updateSuppliersButton, deleteSuppliersButton;
    @FXML private Button saveStockCategoryButton, updateStockCategoryButton, deleteStockCategoryButton;
    @FXML private Label requiredIndicator;


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


    //******************* >> STOCK PRODUCTS TABLE VIEW NODES
    @FXML private TableView<StockProductsTableView> stocksTable;
    @FXML private TableColumn<StockProductsTableView, String> proNameColumn;
    @FXML private TableColumn<StockProductsTableView, String> productNameColumn;
    @FXML private TableColumn<StockProductsTableView, String> notesColumn;
    @FXML private TableColumn<StockProductsTableView, String> invoiceNoColumn;
    @FXML private TableColumn<StockProductsTableView, String> categoryColumn;
    @FXML private TableColumn<StockProductsTableView, String> supplierColumn;
    @FXML private TableColumn<StockProductsTableView, Integer> qtyColumn;
    @FXML private TableColumn<StockProductsTableView, Integer> costPriceColumn;
    @FXML private TableColumn<StockProductsTableView, Integer> sellingPriceColumn;
    @FXML private TableColumn<StockProductsTableView, Integer> totalColumn;
    @FXML private TableColumn<StockProductsTableView, Integer> profitColumn;
    @FXML private TableColumn<StockProductsTableView, Date> expDateColumn;
    @FXML private TableColumn<StockProductsTableView, Date> dateColumn;
    @FXML private TableColumn<StockProductsTableView, String> addedByColumn;

    //******************* >> STOCKS TAB NODE ITEMS
    @FXML private TextField searchField, displayProductId, productNameField, invoiceNoField, quantityField, costPriceField, amountField,  profitField;
    @FXML private TextArea notesField;
    @FXML private  ComboBox<SuppliersTableViewItems> supplierComboBox ;
    @FXML private  ComboBox<String> categoryComboBox;
    @FXML private  DatePicker expiryDatePicker;
    @FXML private  Button saveStockButton, updateStockButton, deleteStockButton;
    
    //THESE CHANGES WERE MADE IN GITHUB...

    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateStockCategoryTable();
        fillStockCategoryComboBox();
        populateSuppliersTable();
    }


    //CLASS INSTANTIATION FIELD...
    UserNotification notify = new UserNotification();
    StocksCategoryTableView categoryTableOBJ;
    SuppliersTableViewItems suppliersTableOBJ;

    /*********************************************************************************************************
     ****** >>                   INPUT FIELDS VALIDATION OPERATION...
     *********************************************************************************************************/
    @FXML void validateContactFieldOnKeyTyped(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE)) {
            contactField.setText(String.valueOf(0));
        }
        if(contactField.getText().length() > 10) {contactField.deleteText(10, contactField.getLength());}

    }
    @FXML void validateStockCategoryOnKeyType() {
        saveStockCategoryButton.setDisable(checkCategoryField());
    }
    @FXML void validateAllSupplierFields() {
        saveSuppliersButton.setDisable(checkLocationField() || checkSupplierNameField() || checkContactField());
        saveSuppliersButton.setDisable(contactField.getText().length() <10);
    }



    /*********************************************************************************************************
     ****** >>                    ACTION EVENT HANDLERS FOR STOCKS CATEGORY TABLE...
     *********************************************************************************************************/
    @FXML void stockCategoryCheckBoxClicked() {stockCategoriesTab.setDisable(disableStockCategoriesTab());}
    @FXML void suppliersCheckBoxClicked() {suppliersTab.setDisable(disableSuppliersTab());}

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
        int itemId = stocksCategoryTable.getSelectionModel().getSelectedItem().getId();
        int outputBit = deleteStockCategory(itemId);
        switch (outputBit) {
            case 1 -> {
                notify.successNotification("DELETE SUCCESSFUL", "Selected Item Successfully Deleted.");
                refreshStocksCategoryTable();
            }
            case 0 -> notify.errorNotification("DELETE FAILED", "Failed To Delete Selected Item.");
        }
    }


    @FXML void saveSupplierButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "please confirm to add new supplier, else cancel to abort");
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.setTitle("ADD NEW SUPPLIER");
        alert.setHeaderText("DO YOU WANT TO ADD " + supplierNameField.getText() + "?");
        if (alert.showAndWait().get().equals(ButtonType.YES)) {
             insertNewSupplier(supplierNameField.getText(), contactField.getText(), locationColumn.getText());
             notify.successNotification("ADD SUCCESSFUL", "New supplier successfully added");
             unsetSuppliersVariables();
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



    /*********************************************************************************************************
     ****** >>                     TABLE VIEW FILL METHODS...
     *********************************************************************************************************/

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

    @FXML private void supplierNameColumnOnEditCommit(TableColumn.CellEditEvent<StocksCategoryTableView, String> cellEditEvent) {
        supplierNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        suppliersTableOBJ.setSupplierName(cellEditEvent.getNewValue());
    }
    private void populateSuppliersTable() {
        supplierNameColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        suppliersTable.setItems(fetchSuppliers());
    }


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



    /*********************************************************************************************************
     ****** >>                     UNSET FIELDS AND BUTTONS TO DEFAULT
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
     ****** >>                     COMBOBOX FILL METHODS.
     *********************************************************************************************************/
    void fillStockCategoryComboBox() {
       for(StocksCategoryTableView item : fetchStockCategories()) {
           categoryComboBox.getItems().add(item.getCategoryName());
       }
    }


}//END OF CLASS
