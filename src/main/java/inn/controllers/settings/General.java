package inn.Controllers.settings;

import inn.database.DbConnection;
import inn.multiStage.MultiStages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class General extends DbConnection implements Initializable {

/************************************ CLASS INSTANTIATION FIELD ***********************************************/
    MultiStages multiStagesOBJ = new MultiStages();




/************************************ FXML OBJECTS EJECTION ***********************************************/

    @FXML private TextField businessNameField, businessNumberField, otherNumberField, digitalAddressField, emailAddressField;
    @FXML private TextField managerNameField, managerEmailField, managerNumberField, totalWorkersField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker datePickerField;
    @FXML private Button updateButton;
    @FXML private Label dateLabel;



/************************************ INITIALIZER METHOD IMPLEMENTATION ***********************************************/
    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            fillAllFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/************************************ GETTER AND SETTER FIELDS ***********************************************/
    public void setBusinessNameField(String businessNameField) {
        this.businessNameField.setText(businessNameField);
    }
    public String getBusinessName() {return  businessNameField.getText();}

    public void setBusinessNumberField(int businessNumberField) {
        this.businessNumberField.setText(String.valueOf(businessNumberField));
    }
    public int getBusinessNumber() {return Integer.parseInt(businessNumberField.getText());}

    public void setOtherNumberField(int otherNumber) {otherNumberField.setText(String.valueOf(otherNumber));}
    public int getOtherNumberField() {return Integer.parseInt(otherNumberField.getText());}

    public void setDigitalAddressField(String address) {digitalAddressField.setText(address);}
    public String getAddress() {return digitalAddressField.getText();}

    public void setEmail(String email) { emailAddressField.setText(email);}
    public String getEmail() {return emailAddressField.getText();}

    public void setManagerEmailField(String email) {managerEmailField.setText(email);}
    public String getManagerEmailField() {return managerEmailField.getText();}

    public void setManagerNameField(String name) {managerNameField.setText(name);}
    public String getManagerName() {return managerNameField.getText();}

    public void setManagerNumberField(int number) {managerNumberField.setText(String.valueOf(number));}
    public int getManagerNumber() {return Integer.parseInt(managerNumberField.getText());}

    public void setTotalWorkersField(int total) {totalWorkersField.setText(String.valueOf(total));}
    public int getTotalWorkers() {return Integer.parseInt(totalWorkersField.getText());}

    public void setDescriptionField(String descrip) {descriptionField.setText(descrip);}
    public String getDescription () {return descriptionField.getText();}

    public LocalDate getDatePicker() {
        datePickerField.getValue();
        return null;
    }



    /************************************ ACTION METHOD IMPLEMENTATION FIELD ***********************************************/

    //THIS METHOD CHECKS IF THE ADMIN ENTERED NUMERIC VALUES ONLY INTO business_number_field, IF TRUE ALLOW, ELSE CLEAR FIELD.
    public void validateBusinessNumberField(KeyEvent event){
        if (!(event.getCode().isDigitKey() || event.getCode() == (KeyCode.BACK_SPACE)) ){
            businessNumberField.clear();
        }

    }
    //THIS METHOD CHECKS IF THE ADMIN ENTERED NUMERIC VALUES ONLY INTO other_number_field, IF TRUE ALLOW, ELSE CLEAR FIELD.
    public void validateOtherNumberField(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode() == (KeyCode.BACK_SPACE)) ){
            otherNumberField.clear();
        }
    }

    //THIS METHOD CHECKS IF THE ADMIN ENTERED NUMERIC VALUES ONLY INTO manager_number_field, IF TRUE ALLOW, ELSE CLEAR FIELD.
    public void validateManagerNumberField(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode() == (KeyCode.BACK_SPACE)) ){
            managerNumberField.setText(null);
        }
    }

    //THIS METHOD CHECKS IF THE ADMIN ENTERED NUMERIC VALUES ONLY INTO total_workers_field, IF TRUE ALLOW, ELSE CLEAR FIELD.
    public void validateWorkersField(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode() == (KeyCode.BACK_SPACE)) ){
            totalWorkersField.setText(null);
        }

    }

    //THIS METHOD DISABLES THE updateBtn UNTIL THE USER FILLS ALL THE TEXTFIELDS INCLUDING THE DATE PICKER.
    public void OnkeyPressed() {
        try {
            if (getBusinessName().isEmpty() || getAddress().isEmpty() || getEmail().isEmpty() || getDescription().isEmpty() ||
                    getManagerName().isEmpty() || getManagerEmailField().isEmpty()  || totalWorkersField.getText().isEmpty() ||
                    businessNumberField.getText().isEmpty() || otherNumberField.getText().isEmpty() || totalWorkersField.getText().isEmpty()) {
                updateButton.setDisable(true);
            }
            else updateButton.setDisable(false);
        } catch (Exception e) {
            dateLabel.setVisible(true);
            datePickerField.setStyle("-fx-border-width:4px; -fx-border-color:#ff0000");
            e.printStackTrace();
        }

    }

    public void updateButtonClicked() {
        try {
            getDatePicker();
            if(datePickerField.getValue() == null) {
                dateLabel.setVisible(true);
                datePickerField.setStyle("-fx-border-width:4px; -fx-border-color:#ff0000;");
            } else {
                dateLabel.setVisible(false);
                datePickerField.setStyle(null);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getButtonTypes().remove(ButtonType.OK);
                alert.getButtonTypes().add(ButtonType.YES);
                alert.setTitle("Confirm Update");
                alert.setHeaderText("ARE YOU SURE YOU WANT TO UPDATE CURRENT RECORDS?");
                if(!(alert.showAndWait().get() == ButtonType.CANCEL)) {
                    updateBusinessInfo(getBusinessName(), getEmail(), getAddress(), getBusinessNumber(), getOtherNumberField(), datePickerField.getValue(), getTotalWorkers(), getDescription(), getManagerName(), getManagerNumber(), getManagerEmailField());
//                    alert.setHeaderText("PERFECT, RECORDS UPDATED SUCCESSFULLY");
                    multiStagesOBJ.showSuccessPrompt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void checkNumberLength() {
        if(businessNumberField.getLength() == 10) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Mobile Number");
            alert.setHeaderText("MOBILE NUMBER MUST BE EXACTLY 10 DIGITS");
            alert.showAndWait();
        }
    }

    public void checkOtherNumberLength() {
        if(otherNumberField.getLength() == 10) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Mobile Number");
            alert.setHeaderText("MOBILE NUMBER MUST BE EXACTLY 10 DIGITS");
            alert.showAndWait();
        }
    }

    public void checkMangNumberLength() {
        if(managerNumberField.getLength() == 10) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Mobile Number");
            alert.setHeaderText("MOBILE NUMBER MUST BE EXACTLY 10 DIGITS");
            alert.showAndWait();
        }
    }



    /************************************ OTHER METHODS FIELD ***********************************************/


    //THIS METHOD FILLS THE TEXT FIELDS OF THE FORM WITH DATE RETURNED FROM THE DATABASE.
    public void fillAllFields() {
        setBusinessNameField((String) fetchBusinessInfo().get(0));
        setEmail((String) fetchBusinessInfo().get(1));
        setDigitalAddressField((String) fetchBusinessInfo().get(2));
        setBusinessNumberField((Integer) fetchBusinessInfo().get(3));
        setOtherNumberField((Integer) fetchBusinessInfo().get(4));
        Date date = (Date) fetchBusinessInfo().get(5);
        datePickerField.setValue((date.toLocalDate()));
        setTotalWorkersField((Integer) fetchBusinessInfo().get(6));
        setDescriptionField((String)fetchBusinessInfo().get(7));
        setManagerNameField((String) fetchBusinessInfo().get(8));
        setManagerNumberField((Integer) fetchBusinessInfo().get(9));
        setManagerEmailField((String) fetchBusinessInfo().get(10));
    }














}//END OF CLASS
