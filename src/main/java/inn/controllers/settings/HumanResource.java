package inn.Controllers.settings;

import inn.Controllers.config.DefPassword;
import inn.models.ResourceModel;
import inn.multiStage.MultiStages;
import inn.tableViews.EmployeesTable;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class HumanResource extends ResourceModel implements Initializable {

    /******************************************> FXML NODES EJECTION <****************************************/

    @FXML private TextField firstNameField, lastNameField, numberField, emailField ;
    @FXML private ComboBox<String> idTypeBox, designationBox, genderBox, userRoleBox;
    @FXML private TextField idNumberField, addressField, salaryField;
    @FXML private DatePicker joinedDate;
    @FXML private CheckBox EnableField;
    @FXML private  Tab addEmployeeTab;
    @FXML private Button saveButton, cancelButton;
    @FXML private Label emailLabel;

    /************************************ EMPLOYEES TABLEVIEW COLUMN NAMES EJECTED FROM THE FXML FILE.*/
    @FXML private TableView<EmployeesTable> EmployeesTableView;
    @FXML private TableColumn<EmployeesTable, String>  fullnameColumn;
    @FXML private TableColumn<EmployeesTable, String>  numberColumn;
    @FXML private TableColumn<EmployeesTable, String>  addressColumn;
    @FXML private TableColumn<EmployeesTable, String> designationColumn;
    @FXML private TableColumn<EmployeesTable, String> dateJoinedColumn;
    @FXML private TableColumn<EmployeesTable, String> statusColumn;
    @FXML private TableColumn<EmployeesTable, String> salaryColumn;
    @FXML private TableColumn<EmployeesTable, String> removeColumn;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillGenderBox();
            fillUserRoleBox();
            fillIdTypeBox();
            fillDesignationBox();
            emptyDesignation();
            emptyIdType();
            emptyGender();
            emptyUserRoll();
            populateEmployeesTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /******************************************> CLASS INSTANTIATION FIELD. <****************************************/
    ButtonType YES = ButtonType.YES;
    ButtonType REMOVE = ButtonType.OK;
    MultiStages multiStages = new MultiStages();
    DefPassword defPassword = new DefPassword();


    /******************************************> SETTER AND GETTER METHODS <***********************************/

    public void setFirstNameField(String firstname) {firstNameField.setText(firstname);}
    public String getFirstNameField(){return firstNameField.getText();}

    public void setLastNameField( @NamedArg("lastname") String lastNameField) {this.firstNameField.setText(lastNameField);}
    public String getLastNameField() {return lastNameField.getText();}

    public void setEmailField(String email) {emailField.setText(email);}
    public String getEmailField() {return emailField.getText();}

    public void setNumberField(int numberField) {this.numberField.setText(String.valueOf(numberField));}
    public int getNumberField(){return Integer.parseInt(numberField.getText());}

    public void setIdNumberField(String idNumber) {idNumberField.setText(idNumber);}
    public String getIdNumberField() {return idNumberField.getText();}

    public void setAddressField(String address) {addressField.setText(address);}
    public String getAddressField(){return addressField.getText();}

    public void setSalaryField(double salary) { salaryField.setText(String.valueOf(salary));}
    public double getSalary() {return Double.parseDouble(salaryField.getText());}

    public void setIdTypeBox(String idType) { idTypeBox.setValue(idType);}
    public String getIdType() {return idTypeBox.getValue();}

    public void setDesignationBox(String desg) {designationBox.setValue(desg);}
    public String getDesignation() {return designationBox.getValue();}

    public void setGenderBox(ObservableList<String> gender) {genderBox.setItems(gender);}
    public String getGender() {return genderBox.getValue();}

    public void setJoinedDate(LocalDate joinedDate) {this.joinedDate.setValue(joinedDate);}
    public LocalDate getJoinedDate() {return joinedDate.getValue();}

    public void setEnableField(boolean enable){EnableField.setSelected(enable);}
    public boolean getEnableField() {return EnableField.isSelected();}


    /*********************************> ACTION EVENT METHODS IMPLEMENTATION <**************************/

    public void validateEmail() {
        if(invalidEmail()) {
            emailLabel.setStyle("-fx-text-fill:#ff0000;");
            emailLabel.setText("Email already exist");
            emailField.setStyle("-fx-border-color:#ff0000; -fx-border-width:2px;");
        } else emailLabel.setStyle(null);  emailField.setStyle(null);
    }

    public void CancelButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "confirm your action to execute.");
        alert.getButtonTypes().add(YES);
        alert.getButtonTypes().remove(REMOVE);
        alert.setHeaderText("ARE YOU SURE YOU WANT TO CLEAR ALL FIELDS?");
        if(alert.showAndWait().get() == YES) {
            resetAll();
        }
    }

    public void SaveButtonClicked() throws IOException {
        try {
            Date emp_date = Date.valueOf(getJoinedDate());

            if (EnableField.isSelected() && userRoleBox.getValue() == null) {
                Alert alert3 = new Alert( Alert.AlertType.ERROR);
                alert3.setTitle("Empty Role.");
                alert3.setHeaderText("PLEASE SELECT A USER ROLE FIRST.");
                alert3.showAndWait();
            } else if(!(EnableField.isSelected())) {
              Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION, "confirm your action to save employee, else cancel to abort.");
              alert1.getButtonTypes().add(YES);
              alert1.getButtonTypes().remove(REMOVE);
              alert1.setHeaderText("ARE YOU SURE YOU WANT TO SAVE " + getFirstNameField() + " AS A NEW EMPLOYEE? ");
              alert1.setTitle("SAVE NEW EMPLOYEE");
              if(alert1.showAndWait().get() == YES) {
                  registerNewEmployee(getFirstNameField(), getLastNameField(), getGender(), getEmailField(), numberField.getText(), getAddressField(), getIdType(), getIdNumberField(), emp_date, getDesignation(), getSalary() );
                  multiStages.showSuccessPrompt();
                  resetAll();
              }
          } else {
              Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION, "confirm your action to save as employee and a user, else cancel to abort.");
              alert2.getButtonTypes().add(YES);
              alert2.getButtonTypes().remove(REMOVE);
              alert2.setHeaderText("ARE YOU SURE YOU WANT TO SAVE '" + getFirstNameField() + "' AS A NEW EMPLOYEE AND A SYSTEM USER?");
              alert2.setTitle("SAVE NEW EMPLOYEE");
              if(alert2.showAndWait().get() == YES) {
                  registerNewEmployee(getFirstNameField(), getLastNameField(), getGender(), getEmailField(), numberField.getText(), getAddressField(), getIdType(), getIdNumberField(), emp_date, getDesignation(), getSalary() );
                  int userRole = fetchUserRoleID(userRoleBox.getValue());
                  registerNewUser(getEmailField(),defPassword.defaultPassword(), defPassword.defaultPassword(), userRole);//register a new user.
                  multiStages.showSuccessPrompt();
                  resetAll();
              }
          }
        }catch (Exception e) {
            multiStages.showFailedPrompt();
            Alert alert = new Alert(Alert.AlertType.ERROR, "execute of operation failed, nothing was saved.");
            alert.setTitle("FAILED TO ADD EMPLOYEE");
            alert.setHeaderText("OPERATION FAILED. KINDLY CHECK IF YOU FILLED ALL FIELDS WITH THE APPROPRIATE DETAILS.");
            alert.showAndWait();
            e.printStackTrace();
        }

    }

    //METHOD TO CHECK IF THE USER HAS AGREED TO SET THE CURRENT EMPLOYEE AS A USER, enable if true else false.
    public void EnableFieldsOnClick() {
        if (!(EnableField.isSelected())) {
            userRoleBox.setDisable(true);
        } else {
            userRoleBox.setDisable(false);
            userRoleBox.setValue(null);
            EnableField.setSelected(true);
        }
    }

    //AUTHENTICATE THE SALARY FIELD TO ACCEPT ONLY double/int values ONLY.
    public void CheckSalaryValue(@NotNull("not null") KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.PERIOD)) {
            salaryField.clear();
        }
    }

    //AUTHENTICATE THE PHONE NUMBER FIELD TO ACCEPT NUMERIC VALUES ONLY AND SET LIMIT TO 10 VALID NUMBERS.
    public void CheckPhoneNumberValue(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE )) {
            numberField.clear();
        } else if (numberField.getLength() >= 10) {
            numberField.deleteText(10, numberField.getLength());
        }
    }

    public void archiveSaveButtonClicked() {
        CheckBox result = EmployeesTableView.getSelectionModel().getSelectedItem().getCheckBox();



    }

    /************************************> OTHER METHODS IMPLEMENTATION <******************************/

    /**
     * FILL GENDER COMBOBOX WITH DATA.
     */
    private void fillGenderBox() {
        ObservableList<String> values = FXCollections.observableArrayList();
        values.add(0, "Male");
        values.add(1, "Female");
        setGenderBox(values);
    }

    /**
     * FILL USER_ROLE COMBOBOX WITH DATA.
     */
    public void fillUserRoleBox() throws SQLException {
        userRoleBox.setVisibleRowCount(4);
        userRoleBox.setItems(fetchUserRoles());
    }

    /**
     * FILL id_type COMBOBOX WITH DATA.
     */
    public void fillIdTypeBox() throws SQLException {
        idTypeBox.setItems(fetchIdTypes());
        idTypeBox.setVisibleRowCount(4);
    }

    /**
     * FILL designation COMBOBOX WITH DATA.
     */
    public void fillDesignationBox() {
        designationBox.setVisibleRowCount(4);
        designationBox.setItems(fetchDesignation());
    }

    public void resetAll() {
        firstNameField.clear();
        lastNameField.clear();
        addressField.clear();
        emailField.clear();
        salaryField.clear();
        idNumberField.clear();
        numberField.clear();
        designationBox.setValue(null);
        joinedDate.setValue(null);
        idTypeBox.setValue(null);
        genderBox.setValue(null);
        userRoleBox.setValue(null);
        userRoleBox.setDisable(true);
        EnableField.setSelected(false);
    }


    /************************************> TRUE OR FALSE METHODS IMPLEMENTATION FOR POSSIBLE ERRORS <******************************/

    /**
     * @return true if user did not pick a date.
     */
    public boolean emptyDate(){
        return joinedDate.getValue() == null;
    }

    /**
     * @return true if user did not pick a designation.
     */
    public boolean emptyDesignation() {
        return getDesignation() == null;
    }

    /**
     * @return true if user did not pick a gender.
     */
    public boolean emptyGender() {
        return getGender() == null;
    }

    /**
     * @return true if user did not pick an id Type.
     */
    public boolean emptyIdType() {
        return getIdType() == null;
    }

    /**
     * @return true if user did not select a user role.
     */
    public void emptyUserRoll() {
        userRoleBox.getValue();
    }

    /**
     * @return true if the provided email already exists.
     */
    public boolean invalidEmail() {
        boolean result = false;
        for (int i = 0; i < fetchUsernames().size(); i++) {
            if(Objects.equals(getEmailField().toLowerCase(), fetchUsernames().get(i))) {
               result = true;
            }
        }
        return result;
    }

    /**
     * THIS METHOD CHECKS IF THE USER HAS FILLED ALL INPUT FIELDS. IF EITHER OF THE REQUIRED INPUT FIELD IS EMPTY.
     * IF TRUE THEN DISABLE THE SAVE BUTTON ELSE ENABLE THE SAVE BUTTON.
     * */
    public void keyTypeEventAll() {
        if(emptyGender() || emptyDesignation() || emptyDate() || emptyIdType() || getFirstNameField().isEmpty() || getLastNameField().isEmpty() ||
        salaryField.getLength() <= 0 || numberField.getLength() <= 0 || getAddressField().isEmpty() || getEmailField().isEmpty() || getIdNumberField().isEmpty()) {
            saveButton.setDisable(true);
            EnableField.setDisable(true);
        }else {
            saveButton.setDisable(false);
            EnableField.setDisable(false);
        }
    }

    /************************************************ EMPLOYEES TABLEVIEW IMPLEMENTATION METHODS **********************/
    public void populateEmployeesTable() {
        fetchActiveEmployees();
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        dateJoinedColumn.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        designationColumn.setCellValueFactory(new PropertyValueFactory<>("designation"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        removeColumn.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        EmployeesTableView.setItems(activeEmployees);



    }






}//END OF CLASS
