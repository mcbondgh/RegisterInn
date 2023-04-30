package inn.controllers.booking;

import inn.controllers.bookingPops.CheckoutController;
import inn.controllers.bookingPops.ExtraTimeController;
import inn.controllers.dashboard.Homepage;
import inn.enumerators.PaymentMethods;
import inn.models.BookingModel;
import inn.multiStage.MultiStages;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import inn.smsApi.SendMessage;
import inn.tableViews.*;
import inn.threads.BookingTimeGenerator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class Booking extends BookingModel implements Initializable {
    UserAlerts userAlerts;
    UserNotification notify = new UserNotification();
    MultiStages multiStages = new MultiStages();
    BookingTimeGenerator bookingTimeGenerator;
    ExtraTimeController extraTimeController = new ExtraTimeController();
    SendMessage sendMessage;

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        fillPaymentMethodComboBox();
        fillDurationComBox();
        fillIdTypeComboBox();
        populateCheckInTable();
        displayFreeRoomsLabel.setText(String.valueOf(countFreeRooms()));
        displayOccupiedRoomsLabel.setText(String.valueOf(countBookedRooms()));
    }

    /*******************************************************************************************************************
     **********************************************  FXML NODE EJECTION  ***********************************************/
    @FXML private MFXLegacyComboBox<String> paymentMethodComboBox, idCombobox, roomNumberComboBox, durationComboBox;
    @FXML private MFXTextField guestNameField, guestNumberField, idNumberField;
    @FXML private Label changeField, displayBillField, displayFreeRoomsLabel, displayOccupiedRoomsLabel;
    @FXML private TextField cashField, momoPayField, transactionIdField, allocatedtimeField;
    @FXML private Pane cashPane;
    @FXML private MFXButton saveBookingButton, cancelBookingField;
    @FXML private TextArea checkInCommentBox;
    @FXML private MFXCheckbox sendMessageCheckBox;




    /*******************************************************************************************************************
     **********************************************  CHECK-IN TABLEVIEW ITEMS ******************************************/
    @FXML private MFXLegacyTableView<CheckInData> checkInTableView;
    @FXML private TableColumn<CheckInData, Integer> checkinID;
    @FXML private TableColumn<CheckInData, String> roomNumberColumn;
    @FXML private TableColumn<CheckInData, LocalTime> checkInTimeColumn;
    @FXML private  TableColumn<CheckInData, LocalTime> dueTimeColumn;
    @FXML private TableColumn<CheckInData, Label> statusColumn;
    @FXML private  TableColumn<CheckInData, Button> actionColumnField;
    @FXML private  TableColumn<CheckInData, Integer> hoursColumn;
    @FXML private  TableColumn<CheckInData, Button> checkOutButtonColumn;


    /*******************************************************************************************************************
     **********************************************  CHECK-IN TABLEVIEW ITEMS ******************************************/
    @FXML private MFXLegacyTableView<CheckInData> summaryTabelView;





    /*******************************************************************************************************************
     **********************************************  TRUE OR FALSE STATEMENTS FOR CHECKIN *****************************/
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
    boolean isSendMessageChecked() {
        return sendMessageCheckBox.isSelected();
    }


    /*******************************************************************************************************************
     **********************************************  IMPLEMENTATION OF OTHER METHODS FOR CHECK-IN ***********************/
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
        roomNumberComboBox.getItems().clear();
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
        cashField.setText(String.valueOf(0.00));
        momoPayField.setText(String.valueOf(0.00));
        transactionIdField.setText(String.valueOf(0));
        changeField.setText(String.valueOf(0.00));
        displayBillField.setText(String.valueOf(0.00));
        checkInCommentBox.clear();
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
    private void populateCheckInTable() {
        checkinID.setCellValueFactory(new PropertyValueFactory<>("checkin_id"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        checkInTimeColumn.setCellValueFactory(new PropertyValueFactory<>("checkin_time"));
        dueTimeColumn.setCellValueFactory(new PropertyValueFactory<>("due_time"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("check_in_status"));
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("allotedTime"));
        checkOutButtonColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutButton"));
        actionColumnField.setCellValueFactory(new PropertyValueFactory<>("topupButton"));
        checkInTableView.setItems(fetchCheckInData());
    }
    public void refreshCheckInTable() {
        checkInTableView.getItems().clear();
        populateCheckInTable();
    }

    public String formatDate(LocalTime localTime) {
        DateTimeFormatter checkInFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return localTime.format(checkInFormatter);
    }
    void sendMessageCheckBoxChecked() {
        String guestMobileNumber = guestNumberField.getText();
        String body = "";
            for (MessageTemplatesData item : fetchMessageTemplates()) {
                if (item.getTemplateTitle().equalsIgnoreCase("CHECK IN")) {
                    body = item.getTemplateBody();
                    break;
                }
            }
            if (body.contains("[NAME]")) {
                String newMessage  = body.replace("[NAME]", guestNameField.getText());
                sendMessage = new SendMessage(guestMobileNumber, newMessage);
                sendMessage.submitMessage();
            }
    }

    /*******************************************************************************************************************
     ********************************************** IMPLEMENTATION OF ACTION EVENT METHODS FOR CHECK-IN ****************/
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
            allocatedtimeField.clear();
        }
    }
    @FXML void validateGuestMobileNumber(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE )) {
            guestNumberField.clear();
        } else if (guestNumberField.getLength() >= 10) {
            guestNumberField.deleteText(10, guestNumberField.getLength());
        }
    }
    @FXML void validateCashField(@NotNull KeyEvent event) {
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
    @FXML void selectedRoomNumber() {
        fillRoomsComboBox();
    }
    @FXML void saveBookingButtonClicked() {
        int flag = 0;  int roomId = 0; int durationId = 0; int checkInCount = countCheckInList();
        String guestName = guestNameField.getText();
        String mobileNumber = guestNumberField.getText();
        String idType = idCombobox.getValue();
        String idNumber = idNumberField.getText();

        for (RoomsData item : fetchRooms()) {
            if (Objects.equals(item.getRoomNo(), roomNumberComboBox.getValue())) {
                roomId = item.getRoomId();
                break;
            }
        } // LOOP THROUGH ITEMS TO GET THE ROOM ID BASED ON THE SELECTED ROOM NO.
        for (RoomPricesData item : fetchRoomPrices()) {
            if (Objects.equals(item.getRoomsCateName(), durationComboBox.getValue())) {
                durationId = item.getRoomsCatId();
            }
        } //LOOP THROUGH TO GET THE PRICE ID BASED ON THE SELECTED PRICE...

        String paymentMethod = paymentMethodComboBox.getValue();

        double cash = Double.parseDouble(cashField.getText());
        double momo = Double.parseDouble(momoPayField.getText());
        long transactionId = Long.parseLong(transactionIdField.getText());
        int allocatedTime = Integer.parseInt(allocatedtimeField.getText());
        String checkinComment = checkInCommentBox.getText();

        double change = Double.parseDouble(changeField.getText());
        double totalAmount = Double.parseDouble(displayBillField.getText());

        int userId = getUserIdByUsername(Homepage.activeUsername);

        LocalTime checkInTime = LocalTime.now();
        LocalTime checkoutTime = checkInTime.plusHours(allocatedTime);

            userAlerts = new UserAlerts("SAVE BOOKING", "ARE YOU SURE YOU WANT TO SAVE YOUR CURRENT BOOKING?", "please confirm YES, else CANCEL to abort");
            if (userAlerts.confirmationAlert()) {
                if(selectedPaymentMethod() == PaymentMethods.CASH) {
                    flag = createNewCheckIn(roomId, durationId, checkInTime, checkoutTime,  (byte) 1, checkinComment ,userId);
                    flag = flag +  createNewGuest(countCheckInList(), guestName, mobileNumber, idType, idNumber, userId);
                    flag = flag + createNewPayment(countCheckInList(), paymentMethod, cash, 0.00, 0, totalAmount, change, userId);
                    flag = flag + updateRoomStatus(roomId, 1);
                    if (flag == 4) {
                        notify.successNotification("SAVED", "BOOKING SAVED SUCCESSFULLY");
                        resetFields();
                        refreshCheckInTable();
                    } else {
                        notify.errorNotification("FAILED", "YOUR REQUEST TO SAVE CURRENT BOOKING FAILED.");
                    }
                } else if(selectedPaymentMethod() == PaymentMethods.MOMO) {
                    flag = createNewCheckIn(roomId, durationId, checkInTime, checkoutTime, (byte) 1,checkinComment, userId);
                    flag = flag +  createNewGuest(countCheckInList(), guestName, mobileNumber, idType, idNumber, userId);
                    flag = flag + createNewPayment(countCheckInList(), paymentMethod, 0.00, momo, transactionId, totalAmount, change, userId);
                    flag = flag + updateRoomStatus(roomId, 1);
                    if (flag == 4) {
                        notify.successNotification("SAVED", "BOOKING SAVED SUCCESSFULLY");
                        resetFields();
                        refreshCheckInTable();
                    } else {
                        notify.errorNotification("FAILED", "YOUR REQUEST TO SAVE CURRENT BOOKING FAILED.");
                    }
                } else {
                    flag = createNewCheckIn(roomId, durationId, checkInTime, checkoutTime, (byte) 1, checkinComment, userId);
                    flag = flag +  createNewGuest(countCheckInList(), guestName, mobileNumber, idType, idNumber, userId);
                    flag = flag + createNewPayment(countCheckInList(), paymentMethod, cash, momo, transactionId, totalAmount, change, userId);
                    flag = flag + updateRoomStatus(roomId, 1);
                    if (flag == 4) {
                        notify.successNotification("SAVED", "BOOKING SAVED SUCCESSFULLY");
                        resetFields();
                        refreshCheckInTable();
                    } else {
                        notify.errorNotification("FAILED", "YOUR REQUEST TO SAVE CURRENT BOOKING FAILED.");
                    }
                }
                if (isSendMessageChecked()) {
                    sendMessageCheckBoxChecked();
                }
            }
    }
    @FXML void checkInTableClicked() {
        for (CheckInData items : checkInTableView.getItems()) {
            String bookedStatus = items.getCheck_in_status().getText();
            if (bookedStatus.equals("Checked Out")) {
                items.getCheckOutButton().setDisable(true);
                items.getTopupButton().setDisable(true);
            } else {
                items.getTopupButton().setOnMouseClicked(event -> {
                    try {
                        String roomNo = items.getRoomNo();
                        int bookingId = items.getCheckin_id();

                        String guestName = getGuestNameByCheckInId(bookingId);

                        ExtraTimeController.bookingId = bookingId;
                        ExtraTimeController.guestName = guestName;
                        ExtraTimeController.roomNumber = roomNo;

                        multiStages.extraTimeStage();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                items.getCheckOutButton().setOnMouseClicked(MouseEvent -> {
                    try {
                        int bookingId = checkInTableView.getSelectionModel().getSelectedItem().getCheckin_id();
                        String clientName = getGuestNameByCheckInId(bookingId);
                        String roomNumber = checkInTableView.getSelectionModel().getSelectedItem().getRoomNo();

                        LocalTime checkInTime = checkInTableView.getSelectionModel().getSelectedItem().getCheckin_time();
                        LocalTime dueTimeValue = checkInTableView.getSelectionModel().getSelectedItem().getDue_time();
                        LocalTime checkOutTime = LocalTime.now();

                        CheckoutController.guestName = clientName;
                        CheckoutController.roomNo = roomNumber;
                        CheckoutController.checkinTime = formatDate(checkInTime);
                        CheckoutController.dueTime = formatDate(dueTimeValue);
                        CheckoutController.bookingId = bookingId;
                        CheckoutController.checkoutTime = checkOutTime.withNano(0).toString();

                        int overtimeValue = dueTimeValue.compareTo(checkOutTime);
                        if (overtimeValue <= 0) {
                           int hour = checkOutTime.getHour() - dueTimeValue.getHour();
                           int min = checkOutTime.getMinute() - dueTimeValue.getMinute();
                           int sec = checkOutTime.getSecond() - dueTimeValue.getSecond();
                           CheckoutController.overTime = hour + ":" + min + ":" + sec;
                        } else {
                            CheckoutController.overTime = "00:00:00";
                        }

                        multiStages.checkOutStage();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }







}//end of class
