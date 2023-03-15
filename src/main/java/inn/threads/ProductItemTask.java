package inn.threads;

import inn.ErrorLogger;
import inn.tableViews.ProductPricesData;
import inn.tableViews.ProductsStockData;
import inn.tableViews.StockLevelData;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class ProductItemTask extends Task<Void> {

    ObservableList<ProductsStockData> productsStockData = FXCollections.observableArrayList();
    ObservableList<StockLevelData> stockLevelData = FXCollections.observableArrayList();
    ObservableList<ProductPricesData> productPricesData = FXCollections.observableArrayList();

    MFXLegacyTableView<ProductsStockData> productStockTableView;
    MFXLegacyTableView<ProductPricesData>productPriceTableView;
    MFXLegacyTableView<StockLevelData> stockLevelTableView;

    public ProductItemTask(ObservableList<ProductsStockData> productsStockData, ObservableList<StockLevelData> stockLevelData, ObservableList<ProductPricesData> productPricesData, MFXLegacyTableView<ProductsStockData> productStockTableView, MFXLegacyTableView<ProductPricesData> productPriceTableView, MFXLegacyTableView<StockLevelData> stockLevelTableView) {
        this.productsStockData = productsStockData;
        this.stockLevelData = stockLevelData;
        this.productPricesData = productPricesData;
        this.productStockTableView = productStockTableView;
        this.productPriceTableView = productPriceTableView;
        this.stockLevelTableView = stockLevelTableView;
    }

    @Override
    protected Void call() throws Exception {
        try{
            productStockTableView.setItems(productsStockData);
            productPriceTableView.setItems(productPricesData);
            stockLevelTableView.setItems(stockLevelData);
        }catch (IllegalStateException e) {
            ErrorLogger logger = new ErrorLogger();
            logger.log(e.getMessage());
        }

        return null;
    }
}
