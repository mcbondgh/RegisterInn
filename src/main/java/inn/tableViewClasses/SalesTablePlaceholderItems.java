package inn.tableViewClasses;

import javafx.scene.control.Button;

public class SalesTablePlaceholderItems {
    private int itemId;
    private String itemName;
    private int quantity;
    private double cost;
    private Button removeItemButton;


    public SalesTablePlaceholderItems() {}

    public SalesTablePlaceholderItems(int itemId, String itemName, int quantity, double cost, Button removeItemButton) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.cost = cost;
        this.removeItemButton = removeItemButton;

    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Button getRemoveItemButton() {
        return removeItemButton;
    }

    public void setRemoveItemButton(Button removeItemButton) {
        this.removeItemButton = removeItemButton;
    }
}
