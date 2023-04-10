package inn.threads;

import inn.ErrorLogger;
import inn.tableViews.InventoryRequestData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;

public class InternalRequestTask extends Task<Void> {

    private TableView<InventoryRequestData> tableView;
    private ObservableList<InventoryRequestData> observableList = FXCollections.observableArrayList();

    public InternalRequestTask(TableView<InventoryRequestData> tableView, ObservableList<InventoryRequestData> observableList) {
        this.tableView = tableView;
        this.observableList = observableList;
    }

    @Override
    protected Void call() throws Exception {
        try {
            tableView.setItems(observableList);
        }catch (Exception ex) {
            ErrorLogger logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }

        return null;
    }
}
