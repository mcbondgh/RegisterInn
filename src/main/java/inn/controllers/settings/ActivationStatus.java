package inn.controllers.settings;

import inn.models.InnActivationModel;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

public class ActivationStatus extends InnActivationModel implements Initializable {


    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillFields();
        fillApiFields();
    }



    //NON-PARAMETERIZED CONSTRUCTOR
    public ActivationStatus() {}

    UserNotification NOTIFY = new UserNotification();
    UserAlerts ALERTS;

    //PARAMETERIZED CONSTRUCTOR
    public ActivationStatus(String activationKey, Date activationDate, Date expiryDate) {
        //PRIVATE FIELDS
    }

    //FXML NODES EJECTION
    @FXML
    private TextField activationKeyField, startDateField, expiryDateField;
    @FXML
    private TextField apiKeyField, senderIdField;
    @FXML private MFXButton saveApiButton;
    @FXML private Label daysCounterLabel;


    private void fillFields() {
        activationKeyField.setText(getSystemActivationKey());

        Date startDate = fetchExpiryAndUpdatedDates().get(2);
        Date expiryDate = fetchExpiryAndUpdatedDates().get(0);
        Date dateTracker = fetchExpiryAndUpdatedDates().get(1);

        startDateField.setText(startDate.toLocalDate().toString());
        expiryDateField.setText(expiryDate.toString());
        long remainingDays = ChronoUnit.DAYS.between(dateTracker.toLocalDate(), expiryDate.toLocalDate());

        if (remainingDays <= 30) {
            daysCounterLabel.setStyle("-fx-text-fill: red");
            daysCounterLabel.setText(String.valueOf(remainingDays));
        } else daysCounterLabel.setText(String.valueOf(remainingDays));

//
//        //CALCULATE YEAR DIFFERENCE
//        int expiryYear  = expiryDate.toLocalDate().getYear();
//        int dateTrackerYear = dateTracker.toLocalDate().getYear();
//
//        //CALCULATE MONTH DIFFERENCE
//        int expiryMonth = expiryDate.toLocalDate().getMonthValue();
//        int dateTrackerMonth = dateTracker.toLocalDate().getMonthValue();
//
//        //CALCULATE FOR DAYS DIFFERENCE
//        int expiryDay = expiryDate.toLocalDate().getDayOfMonth();
//        int trackerDay = dateTracker.toLocalDate().getDayOfMonth();
//
//        //OUTPUT RESULTS
//        int yearDifference = (expiryYear - dateTrackerYear) * 365;
//        int expiryDateDiff = (expiryMonth - dateTrackerMonth ) * (int)(30.45);
//        int trackerDateDiff = (trackerDay - expiryDay);
//
//        int outputResult = expiryDateDiff - trackerDateDiff;
//        int finalDate = yearDifference - outputResult;
//
//        if (!(yearDifference == 0)) {
//            daysCounterLabel.setText(String.valueOf(finalDate));
//        } else {
//            if (outputResult <= 30) {
//                daysCounterLabel.setStyle("-fx-text-fill: red");
//            }
//            daysCounterLabel.setText(String.valueOf(outputResult));
//        }
    }

    private void fillApiFields() {

            String key = (String) fetchSmsApiInfo().get(1);
            String name = fetchSmsApiInfo().get(2).toString();

            apiKeyField.setText(key);
            senderIdField.setText(name);
    }


    @FXML void checkSenderIdLength() {
        if (senderIdField.getLength() > 11) {
            senderIdField.deleteText(11, senderIdField.getLength());
        }
    }

    @FXML void saveApiButtonClick() throws SQLException {
        if (apiKeyField.getText().isEmpty() || senderIdField.getText().isEmpty()) {
            NOTIFY.errorNotification("EMPTY FIELDS", "Please provide a valid SMS API KEY and and SENDER ID of your choice.");
        } else {
//          5c926b098c1087dac3f6
            ALERTS = new UserAlerts("SAVE SMS CONFIGURATION", "Please confirm your SMS configuration to save else CANCEL to abort.");
            if(ALERTS.confirmationAlert()) {
                int flag = updateSMSAPI(apiKeyField.getText(), senderIdField.getText());
                if(flag > 0) {
                    NOTIFY.successNotification("UPDATE SUCCESSFUL", "SMS API and Sender Id updated successfully");
                }
            }
        }
    }

















}//END OF CLASS
