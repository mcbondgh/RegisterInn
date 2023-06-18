package inn.tableViewClasses;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.util.converter.DateTimeStringConverter;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class ViewSalesSummaryData extends RecursiveTreeObject<ViewSalesSummaryData> {
    private int id;
    private String transactionId;
    private String itemName;
    private int quantitySold;
    private double totalCost;
    private Timestamp soldDate;
    private String soldBy;

    String formattedDate;
    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

    public ViewSalesSummaryData(Integer id, String transactionId, String itemName, Integer quantitySold, Double totalCost, Timestamp soldDate, String soldBy) {
        this.id = id;
        this.transactionId = transactionId;
        this.itemName = itemName;
        this.quantitySold = quantitySold;
        this.totalCost = totalCost;
        this.soldDate = soldDate;
        this.soldBy = soldBy;
        this.formattedDate = formatter.format(soldDate.toLocalDateTime());
    }

    public Integer getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Timestamp getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Timestamp soldDate) {
        this.soldDate = soldDate;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getSoldBy() {
        return soldBy;
    }

    public void setSoldBy(String soldBy) {
        this.soldBy = soldBy;
    }
}
