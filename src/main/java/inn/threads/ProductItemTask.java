package inn.threads;

import inn.ErrorLogger;
import inn.tableViewClasses.InternalStocksData;
import inn.tableViewClasses.ProductPricesData;
import inn.tableViewClasses.ProductsStockData;
import inn.fetchedData.StockLevelData;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class ProductItemTask extends Task<Void> {

    ObservableList<ProductsStockData> productsStockData = FXCollections.observableArrayList();
    ObservableList<StockLevelData> stockLevelData = FXCollections.observableArrayList();
    ObservableList<ProductPricesData> productPricesData = FXCollections.observableArrayList();
    ObservableList<InternalStocksData> internalStocksData = FXCollections.observableArrayList();
    MFXLegacyTableView<ProductsStockData> productStockTableView;
    MFXLegacyTableView<ProductPricesData>productPriceTableView;
    MFXLegacyTableView<StockLevelData> stockLevelTableView;
    MFXLegacyTableView<InternalStocksData>internalStocksTableView;

    public ProductItemTask(ObservableList<ProductsStockData> productsStockData, ObservableList<StockLevelData> stockLevelData, ObservableList<ProductPricesData> productPricesData, ObservableList<InternalStocksData> internalStocksData,  MFXLegacyTableView<ProductsStockData> productStockTableView,  MFXLegacyTableView<ProductPricesData> productPriceTableView, MFXLegacyTableView<StockLevelData> stockLevelTableView, MFXLegacyTableView<InternalStocksData> internalStocksTableView ) {
        this.productsStockData = productsStockData;
        this.stockLevelData = stockLevelData;
        this.productPricesData = productPricesData;
        this.productStockTableView = productStockTableView;
        this.productPriceTableView = productPriceTableView;
        this.stockLevelTableView = stockLevelTableView;
        this.internalStocksData = internalStocksData;
    }
    @Override
    protected Void call() throws Exception {
        try{
            productStockTableView.setItems(productsStockData);
            productPriceTableView.setItems(productPricesData);
            stockLevelTableView.setItems(stockLevelData);
            internalStocksTableView.setItems(internalStocksData);
        }catch (IllegalStateException e) {
            ErrorLogger logger = new ErrorLogger();
            logger.log(e.getMessage());
        }
        return null;
    }
}
