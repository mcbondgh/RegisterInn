package inn.tableViews;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class InventoryRequestData {

    private int internalStockId;
    private String internalStockItem;
    private int internalStockQty;
    private TextField internalRequestedStockQty;
    private Button makeRequestButton;
    private Label statusText;
    private String requestDate;

    public InventoryRequestData(int internalStockId, String internalStockItem, int internalStockQty, TextField internalRequestedStockQty, Button makeRequestButton, Label statusText, String requestDate) {
        this.internalStockId = internalStockId;
        this.internalStockItem = internalStockItem;
        this.internalStockQty = internalStockQty;
        this.internalRequestedStockQty = internalRequestedStockQty;
        this.makeRequestButton = makeRequestButton;
        this.statusText = statusText;
        this.requestDate = requestDate;
    }

    public int getInternalStockId() {
        return internalStockId;
    }

    public void setInternalStockId(int internalStockId) {
        this.internalStockId = internalStockId;
    }

    public String getInternalStockItem() {
        return internalStockItem;
    }

    public void setInternalStockItem(String internalStockItem) {
        this.internalStockItem = internalStockItem;
    }

    public int getInternalStockQty() {
        return internalStockQty;
    }

    public void setInternalStockQty(int internalStockQty) {
        this.internalStockQty = internalStockQty;
    }

    public TextField getInternalRequestedStockQty() {
        return internalRequestedStockQty;
    }

    public void setInternalRequestedStockQty(TextField internalRequestedStockQty) {
        this.internalRequestedStockQty = internalRequestedStockQty;
    }

    public Button getMakeRequestButton() {
        return makeRequestButton;
    }

    public void setMakeRequestButton(Button makeRequestButton) {
        this.makeRequestButton = makeRequestButton;
    }

    public Label getStatusText() {
        return statusText;
    }

    public void setStatusText(Label statusText) {
        this.statusText = statusText;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }
}//end of class
