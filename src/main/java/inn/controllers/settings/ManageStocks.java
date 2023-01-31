package inn.Controllers.settings;

import inn.tableViews.StockProductsTableView;
import inn.tableViews.StocksCategoryTableView;
import inn.tableViews.SuppliersTableViewItems;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Date;

public class ManageStocks {

    /*********************************************************************************************************
     ****** >>                      FXML NODE EJECTIONS
     *********************************************************************************************************/
    @FXML private TabPane stocksControlTabPane;
    @FXML private TabPane stocksTab, stockLevelTab, internalSupplyTab, externalSupplyTab, addComponentsTab;


    //******************* >> ADD COMPONENTS TAB NODE ITEMS
    @FXML private TitledPane suppliersTab, stockCategoriesTab;
    @FXML private CheckBox stockCategoryCheckBox, suppliersCheckBox;
    @FXML private TextField supplierNameField, contactField, locationField, categoryField;
    @FXML private Button saveSuppliersButton, updateSuppliersButton, deleteSuppliersButton;
    @FXML private Button saveStockCategoryButton, updateStockCategoryButton, deleteStockCategoryButton;


    //******************* >> SUPPLIERS TABLE VIEW NODES
    @FXML private TableView<SuppliersTableViewItems> suppliersTable;
    @FXML private  TableColumn<SuppliersTableViewItems, Integer> supplierIdColumn;
    @FXML private  TableColumn<SuppliersTableViewItems, String> supplierNameColumn;
    @FXML private  TableColumn<SuppliersTableViewItems, String> locationColumn;
    @FXML private  TableColumn<SuppliersTableViewItems, String> contactColumn;


    //******************* >> STOCKS CATEGORY TABLE VIEW NODES
    @FXML private  TableView<StocksCategoryTableView> stocksCategoryTable;
    @FXML private  TableColumn<StocksCategoryTableView, Integer> idColumn;
    @FXML private  TableColumn<StocksCategoryTableView, Integer> categoryNameColumn;


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
    @FXML private  ComboBox<StocksCategoryTableView> categoryComboBox;
    @FXML private  DatePicker expiryDatePicker;
    @FXML private  Button saveStockButton, updateStockButton, deleteStockButton;




}//END OF CLASS
