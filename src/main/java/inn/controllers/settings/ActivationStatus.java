package inn.controllers.settings;

import inn.models.MainModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ActivationStatus extends MainModel implements Initializable {


    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillFields();
    }
    //PRIVATE FIELDS
    private String activationKey;
    private Date activationDate;
    private Date expiryDate;


    //NON-PARAMETERIZED CONSTRUCTOR
    public ActivationStatus() {}

    //PARAMETERIZED CONSTRUCTOR
    public ActivationStatus(String activationKey, Date activationDate, Date expiryDate) {
        this.activationKey = activationKey;
        this.activationDate = activationDate;
        this.expiryDate = expiryDate;
    }

    //FXML NODES EJECTION

    @FXML
    private TextField activationKeyField, startDateField, expiryDateField;

    @FXML private Label daysCounterLabel;


    private void fillFields() {
        activationKeyField.setText(getSystemActivationKey());

        Date startDate = fetchExpiryAndUpdatedDates().get(2);
        Date expiryDate = fetchExpiryAndUpdatedDates().get(0);
        Date dateTracker = fetchExpiryAndUpdatedDates().get(1);

        startDateField.setText(startDate.toLocalDate().toString());
        expiryDateField.setText(expiryDate.toString());

        //CALCULATE YEAR DIFFERENCE
        int expiryYear  = expiryDate.toLocalDate().getYear();
        int dateTrackerYear = dateTracker.toLocalDate().getYear();

        //CALCULATE MONTH DIFFERENCE
        int expiryMonth = expiryDate.toLocalDate().getMonthValue();
        int dateTrackerMonth = dateTracker.toLocalDate().getMonthValue();

        //CALCULATE FOR DAYS DIFFERENCE
        int expiryDay = expiryDate.toLocalDate().getDayOfMonth();
        int trackerDay = dateTracker.toLocalDate().getDayOfMonth();

        //OUTPUT RESULTS
        int yearDifference = (expiryYear - dateTrackerYear) * 365;
        int expiryDateDiff = (expiryMonth - dateTrackerMonth ) * (int)(30.45);
        int trackerDateDiff = (trackerDay - expiryDay);

        int outputResult = expiryDateDiff - trackerDateDiff;
        int finalDate = yearDifference - outputResult;

        if (!(yearDifference == 0)) {
            daysCounterLabel.setText(String.valueOf(finalDate));
        } else {
            daysCounterLabel.setText(String.valueOf(outputResult));
        }
    }


















}//END OF CLASS
