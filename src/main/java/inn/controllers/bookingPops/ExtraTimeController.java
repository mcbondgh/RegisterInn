package inn.Controllers.bookingPops;

import inn.enumerators.PaymentMethods;
import inn.models.ExtraTimeModel;
import inn.tableViews.RoomPricesData;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ExtraTimeController extends ExtraTimeModel implements Initializable {

    public static String guestName, roomNumber;
    public static int bookingId;

    /*******************************************************************************************************************
     **********************************************  FXML FILE EJECTIONS ***********************************************/
    @FXML private TextField guestNameField, roomNoField, cashField, momoField, transactionIdField, bookingIdField;

    @FXML private Label payableDisplay, exitTimeDisplay;
    @FXML private MFXButton saveButton,cancelButton;
    @FXML private MFXLegacyComboBox<String> durationSelector, PayMethodSelector;


    public void initialize (URL url, ResourceBundle resourceBundle) {
        guestNameField.setText(guestName);
        roomNoField.setText(roomNumber);
        bookingIdField.setText(String.valueOf(bookingId));
        fillDurationSelector();
        fillPaymentMethodSelector();
    }

    @FXML void closeStage(){
        cancelButton.getScene().getWindow().hide();
    }


    /*******************************************************************************************************************
     **********************************************  IMPLEMENTATION OF TRUE OR FALSE METHODS  **************************/


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
        for (RoomPricesData item : fetchRoomPrices()) {
            String selectedItem = durationSelector.getValue();
            if (Objects.equals(item.getRoomsCateName(),selectedItem)) {
                payableDisplay.setText(String.valueOf(item.getPrice()));
            }
        }
    }


}//end of class
