package inn.threads;

import inn.ErrorLogger;
import inn.tableViewClasses.InventoryRequestData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TableView;

public class InternalRequestTask extends Task<Void> {
    private final TableView<InventoryRequestData> tableView;
    private ObservableList<InventoryRequestData> observableList = FXCollections.observableArrayList();

    public InternalRequestTask(TableView<InventoryRequestData> tableView, ObservableList<InventoryRequestData> observableList) {
        this.tableView = tableView;
        this.observableList = observableList;
    }

    @Override
    protected Void call() throws Exception {
        try {
            tableView.setItems(observableList);

            for (InventoryRequestData item : tableView.getItems()) {
                if (item.getIs_requested() == 1) {
                    item.getMakeRequestButton().setDisable(true);
                    item.getInternalRequestedStockQty().setDisable(true);
                    item.getStatusText().setStyle("-fx-text-fill:green;");
                    item.getStatusText().setText("Pending Approval.");
                    item.getMakeRequestButton().setStyle("-fx-background-color:green;");
                    item.getMakeRequestButton().setText("Requested");
                }
            }
        }catch (Exception ex) {
            ErrorLogger logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }

        return null;
    }
}
