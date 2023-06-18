package inn.controllers.bookingPops;

import inn.controllers.Homepage;
import inn.controllers.configurations.FormatLocalDateTime;
import inn.enumerators.PaymentMethods;
import inn.fetchedData.RoomPricesData;
import inn.models.BookingModel;
import inn.multiStage.MultiStages;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class ExtraTimeController extends BookingModel implements Initializable {

    MultiStages multiStages = new MultiStages();
    UserNotification NOTIFY = new UserNotification();
    UserAlerts ALERTS;

    public static String guestName, roomNumber, overTime;
    public static int bookingId;

    /*******************************************************************************************************************
     **********************************************  FXML FILE EJECTIONS ***********************************************/
    @FXML private TextField guestNameField, roomNoField, cashField, momoField, transactionIdField, bookingIdField;

    @FXML private Label payableDisplay, exitTimeDisplay;
    @FXML private Label overTimeLabel;
    @FXML private MFXButton saveButton,cancelButton;
    @FXML private MFXLegacyComboBox<String> durationSelector, PayMethodSelector;
    @FXML private AnchorPane extraTimeAnchorPane;



    public void initialize (URL url, ResourceBundle resourceBundle) {
        guestNameField.setText(guestName);
        roomNoField.setText(roomNumber);
        overTimeLabel.setText(overTime);
        bookingIdField.setText(String.valueOf(bookingId));
        fillDurationSelector();
        fillPaymentMethodSelector();
        try {
            saveButtonClicked();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML void closeStage(){
        cancelButton.getScene().getWindow().hide();
    }



    /*******************************************************************************************************************
     **********************************************  IMPLEMENTATION OF TRUE OR FALSE METHODS  **************************/

    boolean isCashFieldEmpty() {
        return cashField.getText().isBlank();
    }
    boolean isMomoFieldEmpty(){
        return momoField.getText().isBlank();
    }
    boolean isTransIdEmpty() {
        return transactionIdField.getText().isBlank();
    }

    boolean isDurationFieldEmpty() {
        try {
            return durationSelector.getValue().isEmpty();
        }catch (NullPointerException e) {
            return true;
        }
    }
    boolean isPaymentFieldEmpty() {
        return PayMethodSelector.getValue().isEmpty();
    }


    /*******************************************************************************************************************
     **********************************************  IMPLEMENTATION OF OTHER METHODS  **********************************/
    private void fillDurationSelector() {
        for (RoomPricesData item : fetchRoomPrices()) {
            durationSelector.getItems().add(item.getRoomsCateName());
        }
    }
    private void fillPaymentMethodSelector() {
        String[] constants = new String[]{"ALL METHODS", "CASH", "MOBILE MONEY"};
        for (String items : constants) {
            PayMethodSelector.getItems().add(items);
        }
    }
    private PaymentMethods paymentType() {
        PaymentMethods method = PaymentMethods.ALL;
        try {
            String selectedValue = PayMethodSelector.getValue();
            switch(selectedValue) {
                case "CASH" -> {
                    method =  PaymentMethods.CASH;
                } case "MOBILE MONEY" -> {
                    method =  PaymentMethods.MOMO;
                }
            }
        }catch (NullPointerException ignored) {}
        return method;
    }

    void populateTable() {

    }

    void clearFields(){
        durationSelector.setValue(null);
        PayMethodSelector.setValue(null);
        cashField.clear();
        momoField.clear();
        transactionIdField.clear();
        payableDisplay.setText("0.00");
        exitTimeDisplay.setText("00:00:00");
        saveButton.setDisable(true);
    }

    /*******************************************************************************************************************
     **********************************************  IMPLEMENTATION OF INPUT KEY EVENT METHODS  ***************************/
    @FXML void validateAllInputFields(KeyEvent event) {
        if(!(isDurationFieldEmpty() || isPaymentFieldEmpty())) {
            switch(paymentType()) {
                case ALL -> saveButton.setDisable(isMomoFieldEmpty() || isCashFieldEmpty() || isTransIdEmpty());
                case CASH -> saveButton.setDisable(isCashFieldEmpty());
                case MOMO -> saveButton.setDisable(isMomoFieldEmpty() || isTransIdEmpty());
            }
        }

        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() ||  event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.PERIOD)) {
            cashField.deletePreviousChar();
            momoField.deletePreviousChar();
            transactionIdField.deletePreviousChar();
        } else if (transactionIdField.getLength() >= 12) {
        transactionIdField.deletePreviousChar();}
    }



    /*******************************************************************************************************************
     **********************************************  IMPLEMENTATION OF ACTION EVENT METHODS  ***************************/
    @FXML void paymentMethodSelected() {
        if (paymentType() == PaymentMethods.ALL) {
            cashField.setDisable(false);
            momoField.setDisable(false);
            transactionIdField.setDisable(false);
        } else if(paymentType() == PaymentMethods.CASH) {
            cashField.setDisable(false);
            momoField.setDisable(true);
            transactionIdField.setDisable(true);
        } else {
            cashField.setDisable(true);
            momoField.setDisable(false);
            transactionIdField.setDisable(false);
        }
    }
    @FXML void durationSelected() {
        int hourValue = 0;
        LocalDateTime localTime = LocalDateTime.now();
        for (RoomPricesData item : fetchRoomPrices()) {
            String selectedItem = durationSelector.getValue();
            if (Objects.equals(item.getRoomsCateName(),selectedItem)) {
                payableDisplay.setText(String.valueOf(item.getPrice()));
                hourValue = Integer.parseInt(item.getAllotedTime());
                break;
            }
        }
        String extraTime = FormatLocalDateTime.formatDateTime(localTime.plusHours(hourValue));
        exitTimeDisplay.setText(extraTime);
    }
    @FXML void saveButtonClicked() throws IOException {
        saveButton.setOnAction(ActionEvent -> {
            int duration_id = 0;
            String duration = durationSelector.getValue();
//            is_active, exit_tme, payment_method, cash, momo, momo_trans_id, booked_by, date_created

            for (RoomPricesData items : fetchRoomPrices()) {
                if(Objects.equals(items.getRoomsCateName(), duration)) {
                    duration_id = items.getRoomsCatId();
                    break;
                }
            }

            String paymentMethod = PayMethodSelector.getValue();
            int booking_id = Integer.parseInt(bookingIdField.getText());
            String guestName = guestNameField.getText();
            String roomNo = roomNoField.getText();
            LocalTime checkoutTime = LocalTime.now();
            double cashValue = Double.parseDouble(cashField.getText());
            double momoValue = Double.parseDouble(momoField.getText());
            String transValue = transactionIdField.getText();
            String extraTimeValue = exitTimeDisplay.getText();
            int userId = getUserIdByUsername(Homepage.activeUsername);
            ALERTS = new UserAlerts("ADD EXTRA TIME", "DO YOU AGREE TO ADD EXTRA TIME TO CLIENT'S BOOKING? ", "please confirm YES to extend client's stay else CANCEL to abort.");
            if(ALERTS.confirmationAlert()) {
                int flag = createNewCheckout(booking_id, guestName, roomNo, checkoutTime, overTime, userId);
                flag = flag + saveExtraTimeRequest(booking_id, duration_id,extraTimeValue, paymentMethod, cashValue, momoValue, transValue,userId);
                flag = flag + updateCheckInStatus(booking_id);
                if (flag> 0) {
                    NOTIFY.successNotification("EXTRA TIME SAVED", "EXTRA TIME FOR BOOKING ID(" + booking_id +") SUCCESSFULLY ADDED ");
                    clearFields();
                    saveButton.getScene().getWindow().hide();
                }
            }



        });
    }


}//end of class
