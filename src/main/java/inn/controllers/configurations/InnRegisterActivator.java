package inn.Controllers.configurations;

import inn.models.MainModel;
import inn.models.InnActivationModel;
import inn.multiStage.MultiStages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class InnRegisterActivator extends InnActivationModel implements Initializable {


    public void initialize(URL url, ResourceBundle resourceBundle) {
        paneDisplay();

    }


    /******************************************************************************************************************
     *                                      FXML NODE EJECTIONS.
     * ****************************************************************************************************************/
    @FXML
     private AnchorPane parentPane;
    @FXML private Pane statusDisplayPane, authenticationPane, activationPane, keyStatusPane;

    @FXML private Label statusTextContent, statusHeaderContent, keyStatusText, activationPaneLabel;
    @FXML private Button closeButton, authenticationBtn, activationBtn, activateSoftwareBtn, insertButton;

    @FXML private PasswordField authenKeyField;
    @FXML private TextField activateKeyField;

    @FXML private DatePicker expiryDatePicker;

    //    CLASS INSTANTIATION SECTION.
    MultiStages multiStagesOBJ = new MultiStages();
    SysActivator sysActivatorOBJ = new SysActivator();
    MainModel modelOBJ = new MainModel();




    /************************************************** SETTER AND GETTER SECTIN ***************************************/
    public String getAuthenticationKeyValue() {return authenKeyField.getText();}
    public String getActivationKeyValue() {return activateKeyField.getText();}
    public Date getExpiryDateValue() {return Date.valueOf(expiryDatePicker.getValue());}



    /******************************************************************************************************************
     *                                      ACTION EVENT METHODS.
     * ****************************************************************************************************************/

    @FXML void closeButtonAction() {
        closeButton.setOnMouseClicked(MouseEvent -> closeButton.getScene().getWindow().hide());
    }

    @FXML void activateSoftwareBtnAction() throws IOException {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        String generatedKey = sysActivatorOBJ.activationKeyGenerator();
        Date expiryDate = modelOBJ.fetchExpiryAndUpdatedDates().get(0);
        Date dateChecker = modelOBJ.fetchExpiryAndUpdatedDates().get(1);

        //CHECK IF SELECTED DATE IS LESSER OR EQUAL TO THE ALREADY EXISTING EXPIRY DATE.
        if(expiryDate.equals(getExpiryDateValue()) || expiryDate.after(getExpiryDateValue())) {
            alert.setTitle("Invalid Date Selected.");
            alert.setHeaderText("SELECTED DATE CANNOT BE EQUAL OR BEHIND THE CURRENT EXPIRED DATE");
            alert.setContentText("you must select a valid date which is greater than the already expired date.");
            alert.showAndWait();
        }else {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "please confirm your action else cancel to abort.");
            alert1.getButtonTypes().add(ButtonType.YES);
            alert1.getButtonTypes().remove(ButtonType.OK);
            alert1.setTitle("Activate Inn Register");
            alert1.setHeaderText("YOU HAVE INITIATED TO UPDATE YOUR INN REGISTER. DO YOU WANT TO PROCEED?");
            if(alert1.showAndWait().get() == ButtonType.YES) {
                int returnedValue = updateSoftwareActivation(generatedKey, getExpiryDateValue());
                if (returnedValue > 0) {
                    multiStagesOBJ.systemUpdateAlertStage();
                    clearFields();
                    activateSoftwareBtn.getScene().getWindow().hide();
                }
            } else {
                multiStagesOBJ.showFailedPrompt();
            }
        }
//        multiStagesOBJ.systemUpdateAlertStage();
    }

    @FXML void authenticationBtnAction() {
        if(validateAuthenticationKeyPassword()) {
            keyStatusPane.setVisible(true);
            activationPane.setVisible(true);
            keyStatusPane.setStyle("-fx-background-color: lightgreen; -fx-background-radius:5px");
            keyStatusText.setStyle("-fx-text-fill:#017423");
            keyStatusText.setText("AUTHENTICATION KEY VERIFIED");
        } else {
            activationPane.setVisible(false);
            keyStatusPane.setVisible(true);
            keyStatusText.setText("INVALID AUTHENTICATION KEY");
            keyStatusPane.setStyle("-fx-background-color:#ff0000; -fx-background-radius:5px");
            keyStatusText.setStyle("-fx-text-fill:#fff");
        }
    }
    @FXML void activationBtnAction() {
        //EKDY-VPS-MPl-3MZ-k1U0
        if(!(activationBtn.isDisable())) {
            if(validateActivationKey()) {
                expiryDatePicker.setDisable(false);
                activationPaneLabel.setVisible(false);
            } else {
                activationPaneLabel.setVisible(true);
                activationPaneLabel.setText("INVALID ACTIVATION KEY");
                expiryDatePicker.setDisable(true);
                expiryDatePicker.setValue(null);
                activateSoftwareBtn.setDisable(true);
            }
        }
    }

//    ************************************************ VALIDATE INPUT FIELDS ON KEY TYPED ******************************
    @FXML void validateAuthenField() {
       authenticationBtn.setDisable(checkAuthenticationKeyField());
    }
    @FXML void validateActivationField() {
        activationBtn.setDisable(checkActivationKeyField() );

    }
    @FXML void validateDatePicker() {
        try {
            activateSoftwareBtn.setDisable(checkExpiryDatePicker());
        } catch (NullPointerException ignored) {

        }
    }


/***************************************> OTHER METHODS IMPLEMENTATION <************************************************/
    void clearFields() {
        expiryDatePicker.setValue(null);
        authenKeyField.clear();
        activateKeyField.clear();
    }

    /**CHECK FOR THE RETURNED VALUE OF THE DIFFERENCE IN MONTHS SO DECIDE WHICH PANE TO SHOW.
        IF TRUE, DISPLAY ACTIVATION PANE ELSE DISPLAY STATUS PANE */
    void paneDisplay() {
        //GET RESPECTIVE DATES FORM DATABASE...
        Date expiryDate = fetchExpiryAndUpdatedDates().get(0);
        Date expiryCheckerDate = fetchExpiryAndUpdatedDates().get(1);

        //GET MONTH VALUES FROM RESPECTIVE DATES RETURNED.
        int expiryMonthValue = expiryDate.toLocalDate().getMonthValue();
        int expiryCheckerMonthValue = expiryCheckerDate.toLocalDate().getMonthValue();

        int differenceInMonth = expiryMonthValue - expiryCheckerMonthValue;

        //GET DAY OF MONTH FROM EACH RETURNED MONTH
        int expiryMonth = expiryDate.toLocalDate().getDayOfMonth();
        int expiryCheckerMonth = expiryCheckerDate.toLocalDate().getDayOfMonth();

        int differenceInDays = expiryMonth - expiryCheckerMonth;

        if (differenceInMonth <= 1) {
            authenticationPane.setVisible(true);
        } else {
            statusDisplayPane.setVisible(true);
        }
    }




    /***********************************> RETURN TRUE OR FALSE RESULT FOR EMPTY FIELDS. <**********************************/
    protected boolean checkAuthenticationKeyField() {
        return getAuthenticationKeyValue().isBlank() || authenKeyField.getText().length() < 4;
    }
    protected boolean checkActivationKeyField() {
        return getActivationKeyValue().isBlank() || activateKeyField.getText().length() < 21;
    }
    protected boolean checkExpiryDatePicker() {
        return getExpiryDateValue() == null;
    }

    protected boolean validateAuthenticationKeyPassword() {
        String output = null;
        for (String item: getSystemActivationPassword()) {
            output = item;
        }
       return sysActivatorOBJ.passwordVerify(output, getAuthenticationKeyValue());
    }

    protected boolean validateActivationKey() {
        String keyValue = getSystemActivationKey();
        return keyValue.equals(getActivationKeyValue());
    }









}//END OF CLASS
