package inn.controllers.booking;

import com.jfoenix.controls.JFXToggleNode;
import inn.config.database.SmsConfig;
import inn.controllers.Homepage;
import inn.controllers.bookingPops.CheckoutController;
import inn.controllers.bookingPops.ExtraTimeController;
import inn.controllers.configurations.FormatLocalDateTime;
import inn.enumerators.AlertTypesEnum;
import inn.enumerators.PaymentMethods;
import inn.fetchedData.*;
import inn.models.BookingModel;
import inn.multiStage.MultiStages;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import inn.tableViewClasses.CheckInData;
import inn.tableViewClasses.SummaryTableData;
import inn.threads.BookingTimeGenerator;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class Booking extends BookingModel implements Initializable {
    UserAlerts userAlerts;
    UserNotification notify = new UserNotification();
    MultiStages multiStages = new MultiStages();
    BookingTimeGenerator bookingTimeGenerator;

    ExtraTimeController extraTimeController = new ExtraTimeController();
    SmsConfig smsConfig;

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        fillPaymentMethodComboBox();
        fillDurationComBox();
        fillIdTypeComboBox();
        populateCheckInTable();
        setBookedAndFreeRoomsVariables();
        populateExtraTimeTable();
        setBookingAmountValue();
        startDatePicker.setValue(LocalDate.now());
        endDatePicker.setValue(LocalDate.now());
        displayExtraTimeCount.setText(String.valueOf(countExtraTimeRequests()));
//        System.out.println(countExtraTimeRequests() + " extra requests");
    }

    private void setBookedAndFreeRoomsVariables() {
        displayFreeRoomsLabel.setText(String.valueOf(countFreeRooms()));
        displayOccupiedRoomsLabel.setText(String.valueOf(countBookedRooms()));
    }
    private void setBookingAmountValue() {
        String activeUser = Homepage.activeUsername;
        int userId = getUserIdByUsername(activeUser);

        double amount = getTodayBookingAmountTotal(userId);
        DecimalFormat formatToDecimal = new DecimalFormat();
        bookingAmountLabel.setText(formatToDecimal.format(amount) + ".00" );
    }

    /*******************************************************************************************************************
     **********************************************  FXML NODE EJECTION  ***********************************************/
    @FXML private MFXLegacyComboBox<String> paymentMethodComboBox, idCombobox, roomNumberComboBox, durationComboBox;
    @FXML private MFXTextField guestNameField, idNumberField;
    @FXML private TextField guestNumberField;
    @FXML private Label changeField, displayBillField, displayFreeRoomsLabel, displayOccupiedRoomsLabel, bookingAmountLabel;
    @FXML private TextField cashField, momoPayField, transactionIdField, allocatedtimeField;
    @FXML private Pane cashPane, bookingAmountPane;
    @FXML private MFXButton saveBookingButton, cancelBookingField;
    @FXML private TextArea checkInCommentBox;
    @FXML private MFXCheckbox sendMessageCheckBox;
    @FXML private AnchorPane bookingAnchor;
    @FXML private TabPane bookingsTab;
    @FXML private MFXDatePicker startDatePicker;
    @FXML private MFXDatePicker endDatePicker;
    @FXML private JFXToggleNode viewExtraTimeButton;
    @FXML private ToggleGroup toggleExtraCheckOutTables;
    @FXML private Label displayExtraTimeCount;


    /*******************************************************************************************************************
     **********************************************  CHECK-IN TABLEVIEW ITEMS ******************************************/
    @FXML private MFXLegacyTableView<CheckInData> checkInTableView;
    @FXML private TableColumn<CheckInData, Integer> checkinID;
    @FXML private TableColumn<CheckInData, String> roomNumberColumn;
    @FXML private TableColumn<CheckInData, LocalDate> checkInDateColumn;
    @FXML private TableColumn<CheckInData, LocalTime> checkInTimeColumn;

    @FXML private TableColumn<CheckInData, LocalTime> dueTimeColumn;
    @FXML private  TableColumn<CheckInData, LocalDate> dueDateColumn;
    @FXML private TableColumn<CheckInData, Label> statusColumn;
    @FXML private  TableColumn<CheckInData, Button> actionColumnField;
    @FXML private  TableColumn<CheckInData, Integer> hoursColumn;
    @FXML private  TableColumn<CheckInData, Button> checkOutButtonColumn;


    /*******************************************************************************************************************
     ********************************************** CHECK OUT SUMMARY TABLEVIEW ITEMS ******************************************/
    @FXML private MFXLegacyTableView<SummaryTableData> summaryTabelView;
    @FXML private TableColumn<SummaryTableData, Integer> summaryIdColumn;
    @FXML private TableColumn<SummaryTableData, String> guestNameColumn;
    @FXML private TableColumn<SummaryTableData, String> mobileNumberColumn;
    @FXML private TableColumn<SummaryTableData, String> summaryRoomNoColumn;
    @FXML private TableColumn<SummaryTableData, String> bookingTypeColumn;
    @FXML private TableColumn<SummaryTableData, Timestamp> summaryBookedDateColumn;
    @FXML private TableColumn<SummaryTableData, Timestamp> summaryDueDateColumn;
    @FXML private TableColumn<SummaryTableData, Timestamp> summaryCheckoutDateColumn;
    @FXML private TableColumn<SummaryTableData, LocalTime> overtimeColumn;
    @FXML private TableColumn<SummaryTableData, String> notesColumn;
    @FXML private TableColumn<SummaryTableData, String> bookedByColumn;



    /*******************************************************************************************************************
     ********************************** EXTRA TIME TABLE NODE EJECTION. ***************************/
    @FXML private MFXLegacyTableView<ExtraTimeData> extraTimeTable;
    @FXML private TableColumn<ExtraTimeData, Integer> extraBookingIdColumn;
    @FXML private TableColumn<ExtraTimeData, String> extraRoomNoColumn;
    @FXML private TableColumn<ExtraTimeData, Timestamp> extraTimeColumn;
    @FXML private TableColumn<ExtraTimeData, String> ExitTimeColumn;
    @FXML private TableColumn<ExtraTimeData, String> extraBookingTypeColumn;
    @FXML private TableColumn<ExtraTimeData, String> extraCheckOutButtonColumn;

    void toggleTables(MFXLegacyTableView tableView) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500),tableView);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        tableView.setVisible(true);
        fadeTransition.play();
    }


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

    boolean isStartDateEmpty() {return startDatePicker.getValue() == null;}
    boolean isEndDateEmpty() {return endDatePicker.getValue() == null;}

    boolean isToggleActive() {
        return viewExtraTimeButton.isSelected();
    }

    boolean isTimeUp() {
        boolean isEqual = false;
        LocalDateTime currentTime = LocalDateTime.now();
        for (CheckInData item : checkInTableView.getItems()) {
            LocalDateTime dueTime = FormatLocalDateTime.formatToLocalDateTime(item.getDueDate());
            if (currentTime.isBefore(dueTime)) {
                isEqual = true;
            }
        }
        return isEqual;
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
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("check_in_status"));
        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("allotedTime"));
        checkOutButtonColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutButton"));
        actionColumnField.setCellValueFactory(new PropertyValueFactory<>("topupButton"));
        checkInTableView.setItems(fetchCheckInData());
    }

    private void populateSummaryTableView() {

        int userId = getUserIdByUsername(Homepage.activeUsername);
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        summaryIdColumn.setCellValueFactory(new PropertyValueFactory<>("checkinID"));
        summaryIdColumn.setStyle("-fx-text-fill:#ff0000; -fx-alignment:center;");
        guestNameColumn.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        mobileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("guestNumber"));
        summaryRoomNoColumn.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        bookingTypeColumn.setCellValueFactory( new PropertyValueFactory<>("bookingType"));
        summaryBookedDateColumn.setCellValueFactory(new  PropertyValueFactory<>("formattedCheckInDate"));
        summaryDueDateColumn.setCellValueFactory( new PropertyValueFactory<>("formattedDueDate"));
        summaryCheckoutDateColumn.setCellValueFactory( new PropertyValueFactory<>("formattedCheckOutDate"));
        overtimeColumn.setCellValueFactory(new PropertyValueFactory<>("overTime"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
        bookedByColumn.setCellValueFactory(new PropertyValueFactory<>("bookedBy"));

        summaryTabelView.setItems(getBookingSummary(startDate, endDate, userId));
    }
    void populateExtraTimeTable() {
        extraBookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("booking_id"));
        extraRoomNoColumn.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        extraTimeColumn.setCellValueFactory(new PropertyValueFactory<>("date_created"));
        ExitTimeColumn.setCellValueFactory(new PropertyValueFactory<>("exit_time"));
        extraBookingTypeColumn.setCellValueFactory(new PropertyValueFactory<>("bookingType"));
        extraCheckOutButtonColumn.setCellValueFactory(new PropertyValueFactory<>("checkout"));
        extraTimeTable.setItems(fetchExtraTimeDetails());
    }
    public void refreshCheckInTable() {
        checkInTableView.getItems().clear();
        setBookedAndFreeRoomsVariables();
        setBookingAmountValue();
        populateCheckInTable();
    }
    @FXML public void refreshBookingButtonClicked() {
        checkInTableView.getItems().clear();
        populateCheckInTable();
        refreshExtraTimeTable();
    }
    void sendMessageCheckBoxChecked() {
        String guestMobileNumber = guestNumberField.getText();
        String body = "";
            for (MessageTemplatesData item : fetchMessageTemplates()) {
                if (item.getTemplateTitle().contains("CHECK IN")) {
                    body = item.getTemplateBody();
                    break;
                }
            }
            if (body.contains("[NAME]")) {
                String newMessage  = body.replace("[NAME]", guestNameField.getText());
                smsConfig = new SmsConfig(guestMobileNumber, newMessage);
                smsConfig.submitMessage();
            }
    }

    /*******************************************************************************************************************
     ********************************** IMPLEMENTATION OF ACTION EVENT METHODS FOR CHECK-IN ***************************/

    @FXML void generateSummaryButtonClicked() {
        if (isStartDateEmpty()) {
            startDatePicker.setStyle("-fx-border-width:2; -fx-border-color:#ff0000");
            startDatePicker.setAnimated(true);
        } else if(isEndDateEmpty()) {
            endDatePicker.setStyle("-fx-border-width:2; -fx-border-color:#ff0000");
        } else {
            populateSummaryTableView();
            startDatePicker.setStyle(null);
            endDatePicker.setStyle(null);
        }

    }
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
            allocatedtimeField.deletePreviousChar();
        }
    }
    @FXML void validateGuestMobileNumber(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE )) {
            guestNumberField.deletePreviousChar();
        } else if (guestNumberField.getLength() > 10) {
            guestNumberField.deletePreviousChar();
        }
    }
    @FXML void validateCashField(@NotNull KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() ||  event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.PERIOD)) {
            cashField.deletePreviousChar();
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
        int flag;  int roomId = 0; int durationId = 0;
        String guestName = guestNameField.getText();
        String mobileNumber = guestNumberField.getText();
        String idType = idCombobox.getValue();
        String idNumber = idNumberField.getText();

        if(paymentMethodComboBox.getValue().equals("CASH") && Double.parseDouble(cashField.getText()) < Double.parseDouble(displayBillField.getText())) {
            userAlerts = new UserAlerts("INVALID AMOUNT", "CASH AMOUNT CANNOT BE LESSER THAN TOTAL BILL");
            userAlerts.chooseAlert(AlertTypesEnum.WARNING);
            return;
        }
        if(paymentMethodComboBox.getValue().equals("MOBILE MONEY") && Double.parseDouble(momoPayField.getText()) < Double.parseDouble(displayBillField.getText()) && transactionIdField.getText().length() < 5) {
            userAlerts = new UserAlerts("INVALID AMOUNT", "MOMO AMOUNT CANNOT BE LESSER THAN TOTAL BILL", "please make make sure transaction id is not less than 5 digits.");
            userAlerts.chooseAlert(AlertTypesEnum.WARNING);
            return;
        }

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

        LocalDateTime checkInDateTime = LocalDateTime.now();
        LocalDateTime dueDateTime= checkInDateTime.plusHours(allocatedTime);

        //checkin_id, room_id, duration_id, checkin_date, due_date, check_in_status, checkin_comment, booked_by
        userAlerts = new UserAlerts("SAVE BOOKING", "ARE YOU SURE YOU WANT TO SAVE YOUR CURRENT BOOKING?", "please confirm YES, else CANCEL to abort");
        if (userAlerts.confirmationAlert()) {
            if (isSendMessageChecked()) {
                sendMessageCheckBoxChecked();
            }
            flag = createNewCheckIn(roomId, durationId, checkInDateTime, dueDateTime, checkinComment, userId);
            flag = flag + createNewPayment(getLastCheckInId(), paymentMethod, cash, momo, transactionId,totalAmount, change, userId);
            flag = flag + createNewGuest(getLastCheckInId(), guestName, mobileNumber, idType, idNumber, userId);
            flag = flag + updateRoomStatus(roomId, 1);
            if (flag == 4) {
                notify.successNotification("SAVED", "BOOKING SAVED SUCCESSFULLY");
                resetFields();
                refreshCheckInTable();
            } else {
                notify.errorNotification("FAILED", "YOUR REQUEST TO SAVE CURRENT BOOKING FAILED.");
            }

        }
    }

    //THIS METHOD WHEN INVOKED SHALL TAKE THE TWO ARGUMENTS AND RETURN THE TIME DIFFERENCE AS AN OVER TIME.
    String computeOverTime(LocalDateTime dueTime) {
        String overTimeValue = "";
        LocalDateTime currentTime = LocalDateTime.now();

        // Calculate the time difference
         java.time.Duration duration = java.time.Duration.between(dueTime, currentTime);

        // Check if the current time is greater than the due time
         boolean isGreater = currentTime.isAfter(dueTime);
         if (isGreater) {
             // The current time is after the due time, so this is overtime
                long hour = duration.toHours();
                long min = duration.toMinutes() % 60;
                long seconds = duration.toSeconds() % 60;
                overTimeValue = hour + ":" + min + ":" + seconds;
         } else  {
             overTimeValue = "00:00:00";
         }
        return overTimeValue;
    }


    @FXML void checkInTableClicked() {
        displayExtraTimeCount.setText(String.valueOf(countExtraTimeRequests()));
        for (CheckInData items : checkInTableView.getItems()) {
            String bookedStatus = items.getCheck_in_status().getText();
            LocalDateTime dueTimeValue = FormatLocalDateTime.formatToLocalDateTime(items.getDueDate());
            LocalTime checkOutTime = LocalTime.now();
            items.getTopupButton().setDisable(isTimeUp());

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
                        ExtraTimeController.overTime = computeOverTime(dueTimeValue);

                        multiStages.extraTimeStage();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                items.getCheckOutButton().setOnMouseClicked(MouseEvent -> {
                    try {
                        int bookingId = items.getCheckin_id();
                        String clientName = getGuestNameByCheckInId(bookingId);
                        String roomNumber = items.getRoomNo();

                        CheckoutController.guestName = clientName;
                        CheckoutController.roomNo = roomNumber;
                        CheckoutController.bookingId = bookingId;
                        CheckoutController.checkoutTime = checkOutTime.withNano(0).toString();

                        CheckoutController.overTime = computeOverTime(dueTimeValue);
//                        long overtimeValue = dueTimeValue.compareTo(checkOutTime);
//                        if (overtimeValue <= 0) {
//
//                           int hour = checkOutTime.getHour() - dueTimeValue.getHour();
//                           int min = checkOutTime.getMinute() - dueTimeValue.getMinute();
//                           int sec = checkOutTime.getSecond() - dueTimeValue.getSecond();
//                           CheckoutController.overTime = hour + ":" + min + ":" + sec;
//                        } else {
//                            CheckoutController.overTime = "00:00:00";
//                        }

                        multiStages.checkOutStage();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }

        /******************************************** ********************************
         *EXTRA TIME TABLE BUTTON CLICKED IMPLEMENTATION
         **********************************************/
        for(ExtraTimeData timeData : extraTimeTable.getItems()) {
            int booking_id = timeData.getBooking_id();

            timeData.getCheckout().setOnAction(action ->  {
                userAlerts = new UserAlerts("EXTRA TIME CHECKOUT", "ARE YOU SURE YOU WANT TO CHECKOUT CLIENT WITH BOOKING ID "
                        + timeData.getBooking_id() + "?", "please confirm to finally terminate client's stay else CANCEL to abort.");
                if (userAlerts.confirmationAlert()) {
                    int flag = updateRoomStatus(timeData.getRoom_id(), 0);
                    flag = flag + updateExtraTimeStatus(booking_id);
                    if(flag == 2) {
                        notify.successNotification("UPDATE SUCCESSFUL", "You have successfully terminated client's booking");
                        refreshExtraTimeTable();
                        countExtraTimeRequests();
                    }
                }
            });
        }

    }



    /*******************************************************************************************************************
     ********************************** IMPLEMENTATION OF ACTION EVENT METHODS FOR EXTRA TIME NODE *********************/
    @FXML
    void extraTimeButtonToggled() {
        if(isToggleActive()) {
            toggleTables(extraTimeTable);
            checkInTableView.setVisible(false);
            viewExtraTimeButton.setText("View Check Out");
        } else {
            toggleTables(checkInTableView);
            extraTimeTable.setVisible(false);
            viewExtraTimeButton.setText("View Extra Time");
        }
    }
    @FXML void refreshExtraTimeTable() {
        extraTimeTable.getItems().clear();
        populateExtraTimeTable();
    }



//    public static void main(String[] args) {
//        String dateTimeString = "2023-05-16 18:00:00 a".substring(0, 19); // Replace with your formatted LocalDateTime string
//        String pattern = "yyyy-MM-dd HH:mm:ss"; // Replace with the pattern of your formatted string
//
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
//        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, formatter);
//
//
//        System.out.println("Parsed LocalDateTime: " + localDateTime);
//
//        Calendar tiem = Calendar.getInstance();
//        System.out.println("calender: " + tiem.toInstant());
//    }


}//end of class
