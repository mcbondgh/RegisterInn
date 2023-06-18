package inn.controllers.accounting;

import inn.models.MainModel;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import javax.print.DocFlavor;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Accounting extends MainModel implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int day = LocalDate.now().getDayOfMonth();
        startDatePicker.setValue(LocalDate.of(year, month, 1));
        endDatePicker.setValue(LocalDate.of(year, month, day));
        actionEventMethod();
        fillCategoryComBox();
    }




    /**********************************************************************************************************
     *                  FXML FILE EJECTIONS.
     ***********************************************************************************************************/
    @FXML private MFXDatePicker startDatePicker, endDatePicker;
    @FXML private MFXButton generateSummaryButton;
    @FXML private Label bookingAccountBalanceLabel, bookingMomoAmountLabel,bookingCashAmountLabel, bookingWithdrawalAmountLabel, bookingDateAndTimeLabel;
    @FXML private Label salesAccountBalanceLabel, salesMomoAmountLabel, salesCashAmountLabel, salesWithdrawalAmountLabel, salesDateAndTimeLabel;
    @FXML private Label expenditureAmountLabel, lastExpenditureLabel;
    @FXML private Label bookSalesLabel, salesExpenditureLabel,bookExpenditureLabel, bookSalesExpenditureLabel;
    @FXML private CheckBox bookSalesChecker, salesExpenditureChecker, bookSalesExpenditureChecker, bookExpenditureChecker;

    @FXML private TextField titleField, amountField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<String> categorySelector;
    @FXML private DatePicker dateSelector;
    @FXML private MFXButton addButton, cancelButton;
    @FXML private Pane expenditurePane;



    /**********************************************************************************************************
     *                  IMPLEMENTATION OF TRUE OR FALSE METHODS.
     ***********************************************************************************************************/

    boolean isTitleFieldEmpty() { return titleField.getText().isEmpty(); }
    boolean isAmountFieldEmpty() { return amountField.getText().isEmpty();}
    boolean isCategoryEmpty() { return categorySelector.getValue() == null; }
    boolean isDateEmpty() {return dateSelector.getValue() == null;}
    boolean isDescriptionEmpty(){return descriptionField.getText().isEmpty();}


    /**********************************************************************************************************
     *                  IMPLEMENTATION OF OTHER METHODS.
     ***********************************************************************************************************/
    void resetField() {
        titleField.clear();
        descriptionField.clear();
        amountField.clear();
        categorySelector.setValue(null);
        dateSelector.setValue(null);
        addButton.setDisable(true);
    }

    void fillCategoryComBox() {
        String[] values = {"Allowance", "Item Purchase", "Repairs", "Salary Payment", "Tax Payment", "Utility Bill"};
        for(String x : values) {
            categorySelector.getItems().add(x);
        }
    }




    /**********************************************************************************************************
     *                  IMPLEMENTATION OF ACTION EVENT METHODS.
     ***********************************************************************************************************/
    @FXML void generateAccountDetailsOnClick() {
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            double totalBooking = computeTotalBookingAmount(startDate, endDate);
            double cashBookingAmount = computeCashBookingAmount(startDate, endDate);
            double momoBookingAmount = computeMomoBookingAmount(startDate, endDate);
            double totalSales = computeTotalSalesAmount(startDate, endDate);
            double momoSalesAmount = computeSalesMomoAmount(startDate, endDate);
            double cashSalesAmount = computeSalesCashAmount(startDate, endDate);

            bookingAccountBalanceLabel.setText(String.valueOf(totalBooking));
            bookingCashAmountLabel.setText(String.valueOf(cashBookingAmount));
            bookingMomoAmountLabel.setText(String.valueOf(momoBookingAmount));

            salesAccountBalanceLabel.setText(String.valueOf(totalSales));
            salesMomoAmountLabel.setText(String.valueOf(momoSalesAmount));
            salesCashAmountLabel.setText(String.valueOf(cashSalesAmount));
    }

    void actionEventMethod() {

        expenditurePane.setOnMouseMoved(event -> {
            addButton.setDisable(isTitleFieldEmpty() || isAmountFieldEmpty() || isDateEmpty() || isCategoryEmpty() || isDescriptionEmpty());
        });

        cancelButton.setOnAction(event -> {
            resetField();
        });

        addButton.setOnAction(event -> {

        });

    }



}//end of class...
