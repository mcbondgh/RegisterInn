package inn.Controllers.booking;

import inn.enumerators.PaymentMethods;
import inn.models.MainModel;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import inn.tableViews.CheckInData;
import inn.tableViews.IdTypesData;
import inn.tableViews.RoomPricesData;
import inn.tableViews.RoomsData;
import inn.threads.BookingTimeGenerator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.ResourceBundle;

public class Booking extends MainModel implements Initializable {
    UserAlerts userAlerts;
    UserNotification notify;
    BookingTimeGenerator bookingTimeGenerator;

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        fillPaymentMethodComboBox();
        fillDurationComBox();
        fillRoomsComboBox();
        fillIdTypeComboBox();
    }

    /*******************************************************************************************************************
     **********************************************  FXML NODE EJECTION  ***********************************************/
    @FXML private MFXLegacyComboBox<String> paymentMethodComboBox, idCombobox, roomNumberComboBox, durationComboBox;
    @FXML private MFXTextField guestNameField, guestNumberField, idNumberField;
    @FXML private Label changeField, displayBillField;
    @FXML private TextField cashField, momoPayField, transactionIdField, allocatedtimeField;
    @FXML private Pane cashPane;
    @FXML private MFXButton saveBookingButton, cancelBookingField;


    /*******************************************************************************************************************
     **********************************************  CHECK-IN TABLEVIEW ITEMS ******************************************/
    @FXML private MFXLegacyTableView<CheckInData> checkInTableView;
    @FXML private TableColumn<CheckInData, String> roomNumberColumn;
    @FXML private TableColumn<CheckInData, String> checkInTimeColumn;
    @FXML private TableColumn<CheckInData, String> durationColumn;
    @FXML private TableColumn<CheckInData, String> statusColumn;


    /*******************************************************************************************************************
     **********************************************  TRUE OR FALSE STATEMENTS  *****************************************/
    boolean isCashFieldEmpty() {
        return cashField.getText().isBlank();
    }
    boolean isMomoFieldEmpty() {
        return momoPayField.getText().isBlank();
    }
    boolean isTransactionIdEmpty() {
        return transactionIdField.getText().isEmpty();
    }
    boolean isGuestNameFieldEmpty() {
        return guestNameField.getText().isEmpty();
    }
    boolean isGuestNumberFieldEmpty() {
        return guestNumberField.getText().isEmpty();
    }
    boolean isRoomNumEmpty() {
        return roomNumberComboBox.getValue().isEmpty();
    }
    boolean isDurationEmpty() {
        return durationComboBox.getValue().isEmpty();
    }
    boolean isTimeInputEmpty() {return allocatedtimeField.getText().isEmpty();}
    boolean isPaymentMethodEmpty() {
        return paymentMethodComboBox.getValue().isEmpty();
    }


    /*******************************************************************************************************************
     **********************************************  IMPLEMENTATION OF OTHER METHODS  **********************************/
    void fillPaymentMethodComboBox() {
        String[] constants = new String[]{"ALL METHODS", "CASH", "MOBILE MONEY"};
        for (String items : constants) {
            paymentMethodComboBox.getItems().add(items);
        }
    }
    void fillDurationComBox() {
        for (RoomPricesData items : fetchRoomPrices()) {
            durationComboBox.getItems().add(items.getRoomsCateName());
        }
    }
    void fillRoomsComboBox() {
        for (RoomsData items : fetchRooms()) {
            byte isBooked = items.getIsBooked();
            if (isBooked == 0) {
                roomNumberComboBox.getItems().add(items.getRoomNo());
            }
        }
    }
    void fillIdTypeComboBox() {
        for (IdTypesData items : fetchIdTypes()) {
            idCombobox.getItems().add(items.getIdTypeName());
        }
    }
    double cashChangeValue() {
        double cashValue = Double.parseDouble(cashField.getText());
        double billValue = Double.parseDouble(displayBillField.getText());
        return cashValue - billValue;

    }
    double momoChangeValue() {
        double momoValue = Double.parseDouble(momoPayField.getText());
        double billValue = Double.parseDouble(displayBillField.getText());
        return momoValue - billValue;
    }
    double totalChangeValue() {
        double momoValue = Double.parseDouble(momoPayField.getText());
        double billValue = Double.parseDouble(displayBillField.getText());
        double cashValue = Double.parseDouble(cashField.getText());
        return (momoValue + cashValue) - billValue;
    }
    void resetFields() {
        guestNameField.clear();
        guestNumberField.clear();
        idNumberField.clear();
        idCombobox.setValue(null);
        roomNumberComboBox.setValue(null);
        durationComboBox.setValue(null);
        paymentMethodComboBox.setValue(null);
        allocatedtimeField.clear();
        cashField.clear();
        momoPayField.clear();
        transactionIdField.clear();
        changeField.setText(String.valueOf(0.00));
        displayBillField.setText(String.valueOf(0.00));
    }

    @FXML void inputValuesChanged() {
        try {
            if (selectedPaymentMethod() == PaymentMethods.MOMO) {
                saveBookingButton.setDisable(isGuestNameFieldEmpty() || isGuestNumberFieldEmpty() || isRoomNumEmpty() || isDurationEmpty() || isPaymentMethodEmpty() || isMomoFieldEmpty() || isTransactionIdEmpty() || isTimeInputEmpty());
            } else if (selectedPaymentMethod() == PaymentMethods.CASH) {
                saveBookingButton.setDisable(isGuestNameFieldEmpty() || isGuestNumberFieldEmpty() || isRoomNumEmpty() || isDurationEmpty() || isPaymentMethodEmpty() || isCashFieldEmpty() || isTimeInputEmpty());
            } else {
                saveBookingButton.setDisable(isGuestNameFieldEmpty() || isGuestNumberFieldEmpty() || isRoomNumEmpty() || isDurationEmpty() || isPaymentMethodEmpty() || isCashFieldEmpty() || isMomoFieldEmpty() || isTransactionIdEmpty() || isTimeInputEmpty());
            }
        }catch (NullPointerException ignored) {}

    }

    private void generateCheckInTime() {


    }


    /*******************************************************************************************************************
     ********************************************** IMPLEMENTATION OF ACTION EVENT METHODS *****************************/
    @FXML PaymentMethods selectedPaymentMethod() {
        PaymentMethods method = PaymentMethods.ALL;
        try {
            String selectedValue = paymentMethodComboBox.getValue();
            switch(selectedValue) {
                case "CASH" -> {
                    method =  PaymentMethods.CASH;
                    cashField.setVisible(true);
                    momoPayField.setVisible(false);
                    transactionIdField.setVisible(false);
                } case "MOBILE MONEY" -> {
                    method =  PaymentMethods.MOMO;
                    cashField.setVisible(false);
                    momoPayField.setVisible(true);
                    transactionIdField.setVisible(true);
                } case "ALL METHODS" -> {
                    cashField.setVisible(true);
                    momoPayField.setVisible(true);
                    transactionIdField.setVisible(true);
                }
            }
        }catch (NullPointerException ignored) {}

        return method;
    }
    @FXML void selectedRoomDuration() {
        String selectedItem = durationComboBox.getValue();
        for (RoomPricesData item : fetchRoomPrices()) {
            if (Objects.equals(selectedItem, item.getRoomsCateName())) {
                double selectedPrice = item.getPrice();
                String allocatedTime = item.getAllotedTime();

                displayBillField.setText(String.valueOf(selectedPrice));
                allocatedtimeField.setText(allocatedTime);
            }
        }
    }

    @FXML void validateTimeField(@NotNull KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE )) {
            guestNumberField.clear();
        }
    }
    @FXML void validateGuestMobileNumber(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE )) {
            guestNumberField.clear();
        } else if (guestNumberField.getLength() >= 10) {
            guestNumberField.deleteText(10, guestNumberField.getLength());
        }
    }
    @FXML void validateCashField(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() ||  event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.PERIOD)) {
            cashField.clear();
        }
    }
    @FXML void validateMomoField(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() ||  event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.PERIOD)) {
            momoPayField.clear();
        }
    }
    @FXML void validateTransactionIdField(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() ||  event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.PERIOD)) {
            transactionIdField.clear();
        } else if (transactionIdField.getLength() >= 12) {
            transactionIdField.deleteText(12, transactionIdField.getLength());
        }
    }
    @FXML void cashFieldInputChanged() {
        try {
            double result = Double.parseDouble(String.format("%.2f%n", cashChangeValue()));
            changeField.setText(String.valueOf(result));
        }catch (NumberFormatException ex) {
            changeField.setText(String.valueOf(0));
        }
    }
    @FXML void momoFieldInputChanged() {
        try {
            double result;
               String paymentMethod = paymentMethodComboBox.getValue();
                switch (paymentMethod) {
                    case "MOBILE MONEY" -> {
                        result = Double.parseDouble(String.format("%.2f%n", momoChangeValue()));
                        changeField.setText(String.valueOf(result));
                    }case "ALL METHODS"-> {
                        result = Double.parseDouble(String.format("%.2f%n", totalChangeValue()));
                        changeField.setText(String.valueOf(result));
                    }
                }
        }catch (NumberFormatException ex) {
            changeField.setText(String.valueOf(0));
        }
    }

    @FXML void cancelBookingButtonClicked() {
        userAlerts = new UserAlerts("CANCEL BOOKING", "YOU HAVE REQUESTED TO CANCEL CURRENT BOOKING PROCESS,DO YOU WANT TO PROCEED?", "please confirm YES, else CANCEL to abort.");
        if (userAlerts.confirmationAlert()) {
            resetFields();
        }
    }

    @FXML void saveBookingButtonClicked() {
        generateCheckInTime();
    }



























}//end of class
