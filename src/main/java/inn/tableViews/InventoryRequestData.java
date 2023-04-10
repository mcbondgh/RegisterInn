package inn.tableViews;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Timestamp;

public class InventoryRequestData {

    private int internalStockId;
    private String internalStockItem;
    private int internalStockQty;
    private String internalRequestedStockQty;
    private Button makeRequestButton;
    private String requestStatus;
    private Timestamp requestDate;
    private Label statusText;
//
    public InventoryRequestData(int internalStockId, String internalStockItem, int internalStockQty, Button makeRequestButton, Label statusText) {
        this.internalStockId = internalStockId;
        this.internalStockItem = internalStockItem;
        this.internalStockQty = internalStockQty;
        this.makeRequestButton = makeRequestButton;
        this.statusText = statusText;
    }

    /**
     * get field
     *
     * @return internalStockId
     */
    public int getInternalStockId() {
        return this.internalStockId;
    }

    /**
     * set field
     *
     * @param internalStockId
     */
    public void setInternalStockId(int internalStockId) {
        this.internalStockId = internalStockId;
    }

    public Label getStatusText() {
        return statusText;
    }

    public void setStatusText(Label statusText) {
        this.statusText = statusText;
    }

    /**
     * get field
     *
     * @return internalStockItem
     */
    public String getInternalStockItem() {
        return this.internalStockItem;
    }

    /**
     * set field
     *
     * @param internalStockItem
     */
    public void setInternalStockItem(String internalStockItem) {
        this.internalStockItem = internalStockItem;
    }

    /**
     * get field
     *
     * @return internalStockQty
     */
    public int getInternalStockQty() {
        return this.internalStockQty;
    }

    /**
     * set field
     *
     * @param internalStockQty
     */
    public void setInternalStockQty(int internalStockQty) {
        this.internalStockQty = internalStockQty;
    }

    /**
     * get field
     *
     * @return internalRequestedStockQty
     */
    public String getInternalRequestedStockQty() {
        return this.internalRequestedStockQty;
    }

    /**
     * set field
     *
     * @param internalRequestedStockQty
     */
    public void setInternalRequestedStockQty(String internalRequestedStockQty) {
        this.internalRequestedStockQty = internalRequestedStockQty;
    }

    /**
     * get field
     *
     * @return makeRequestButton
     */
    public Button getMakeRequestButton() {
        return this.makeRequestButton;
    }

    /**
     * set field
     *
     * @param makeRequestButton
     */
    public void setMakeRequestButton(Button makeRequestButton) {
        this.makeRequestButton = makeRequestButton;
    }

    /**
     * get field
     *
     * @return requestStatus
     */
    public String getRequestStatus() {
        return this.requestStatus;
    }

    /**
     * set field
     *
     * @param requestStatus
     */
    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    /**
     * get field
     *
     * @return requestDate
     */
    public Timestamp getRequestDate() {
        return this.requestDate;
    }

    /**
     * set field
     *
     * @param requestDate
     */
    public void setRequestDate(Timestamp requestDate) {
        this.requestDate = requestDate;
    }
}//end of class
