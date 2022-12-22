package inn.Controllers.settings;

import inn.models.EmpProfileModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class EmployeeProfile extends EmpProfileModel implements Initializable {

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillGenderBox();
            fillIdTypeBox();
            fillEmployeeBox();
            fillUserRoleBox();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*******************************************************************************************************************
     FXML NODE EJECTION
     *******************************************************************************************************************/
    @FXML
    private ImageView uploadProfile;
    @FXML
    private ComboBox<String> employeeBox, genderBox, idTypeBox, userRoleBox;
    @FXML
    private DatePicker dateField;
    @FXML
    private Label userStatusLabel;
    @FXML
    private TextField firstnameField, lastnameField, emailField, numberField, imageNameField;
    @FXML
    private TextField addressField, idNumberField, designationField, salaryField, addedByField, updatedDate, idField;
    @FXML
    private Button updateProfileBtn, uploadImageBtn, deleteProfileBtn;


    /*******************************************************************************************************************
                                            ACTION EVENT METHODS IMPLEMENTATION
     *******************************************************************************************************************/
    @FXML
    private void UploadProfileImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Employee Image");
        fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = new File(fileChooser.showOpenDialog(updateProfileBtn.getScene().getWindow()).toURI().toString());
        String fileName  = file.getName();
        imageNameField.setText(fileName);
        Image image = new Image(file.getAbsolutePath());
        uploadProfile.setImage(image);
        FileInputStream inputStream;

    }
    @FXML
    void updateButtonClicked() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "confirm if you want to execute update employee operation.");
            ButtonType YES = ButtonType.YES;
            alert.getButtonTypes().add(YES);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.setTitle("UPDATE RECORDS");
            alert.setHeaderText("ARE YOU SURE YOU WANT TO UPDATE EMPLOYEE'S RECORDS?");
            if (alert.showAndWait().get() == YES) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void selectEmployeeOnAction() {
        ArrayList<Object> result = fetchFullEmployeeDetails(employeeBox.getValue());
        for (int i = 0; i < result.size(); i++) {
            idField.setText("00" +result.get(0).toString());
            firstnameField.setText(result.get(1).toString());
            lastnameField.setText(result.get(2).toString());
            genderBox.setValue(result.get(3).toString());
            emailField.setText(result.get(4).toString());
            numberField.setText(result.get(5).toString());
            addressField.setText(result.get(6).toString());
            idTypeBox.setValue(result.get(7).toString());
            idNumberField.setText(result.get(8).toString());
            dateField.setValue(LocalDate.parse(result.get(9).toString()));
            designationField.setText(result.get(10).toString());
            salaryField.setText(result.get(12).toString());
            addedByField.setText(result.get(13).toString());
            updatedDate.setText(result.get(14).toString());
        }
        //CHECK IF THE SELECTED EMPLOYEE IS ALREADY A USER. IF true Enable UserRole ComboBox else Disable UserRole ComboBox.
        for (int i = 0; i < fetchUsernames().size(); i++) {
            if(Objects.equals(emailField.getText().toLowerCase(), fetchUsernames().get(i))) {
               userStatusLabel.setText("System User");
               userStatusLabel.setStyle("-fx-text-fill: #00bf09");
               userRoleBox.setDisable(true);
            } else {
                userRoleBox.setDisable(false);
                userRoleBox.setValue(null);
                userStatusLabel.setStyle("-fx-text-fill: #be0000");
                userStatusLabel.setText("Not A User");
            }
        }
    }


    @FXML
    void deleteButtonClicked() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "confirm if you want to execute delete employee operation.");
            ButtonType YES = ButtonType.YES;
            alert.getButtonTypes().add(YES);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.setTitle("DELETE RECORDS");
            alert.setHeaderText("ARE YOU SURE YOU WANT TO UPDATE EMPLOYEE'S RECORDS?");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //AUTHENTICATE THE SALARY FIELD TO ACCEPT ONLY double/int values.
    @FXML
    public void CheckSalaryValue(@NotNull("not null") KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.PERIOD)) {
            salaryField.clear();
        }
    }

    //AUTHENTICATE THE PHONE NUMBER FIELD TO ACCEPT NUMERIC VALUES ONLY AND SET LIMIT TO 10 VALID NUMBERS.
    @FXML
    public void CheckPhoneNumberValue(@NotNull KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE )) {
            numberField.clear();
        } else if (numberField.getLength() >= 10) {
            numberField.deleteText(10, numberField.getLength());
        }
    }

    //THIS METHOD LISTENS TO A mouseMovedEvent AND WILL DISABLE THE
    // SAVE BUTTON AS LONG IF AT LEAST ONE INPUT FIELD IS EMPTY.
    @FXML
    boolean checkForEmptyFields() {
        try {
            if (genderBox.getValue().isEmpty() || idTypeBox.getValue().isEmpty() || firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || emailField.getText().isEmpty() ||
            addressField.getText().isEmpty() || designationField.getText().isEmpty() || salaryField.getText().isEmpty() || numberField.getText().isEmpty() || idNumberField.getText().isEmpty()) {
                updateProfileBtn.setDisable(true);
                deleteProfileBtn.setDisable(true);
            }else  {
                updateProfileBtn.setDisable(false);
                deleteProfileBtn.setDisable(false);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*******************************************************************************************************************
                                                   OTHER METHODS IMPLEMENTATION
     *******************************************************************************************************************/

    void fillEmployeeBox() {
        employeeBox.setItems(fetchEmployeeFullnames());
    }

    void fillIdTypeBox() {
        idTypeBox.setItems(fetchIdTypes());
    }

    void fillGenderBox() {
        genderBox.getItems().add(0, "Male");
        genderBox.getItems().add(1,"Female");
    }
    void fillUserRoleBox() throws SQLException {
        userRoleBox.setItems(fetchUserRoles());
    }


    /*******************************************************************************************************************
                                   TRUE OR FALSE METHOD IMPLEMENTATIONS FOR VALIDATING COMBO BOXES
    ********************************************************************************************************************/


    //CHECK IF THE USER HAS SELECTED AN EMPLOYEE FROM THE DROPDOWN BOX. IF YES RETURN true ELSE RETURN false
    public boolean checkEmployeeBox() {
        boolean selected = false;
        if(employeeBox.getValue() == null) {
            selected = true;
        }
        return selected;
    }

    //CHECK IF THE USER HAS SELECTED AN ID TYPE FROM THE DROPDOWN BOX. IF YES RETURN true ELSE RETURN false
    public boolean checkIdTypeBox() {
        boolean selected = false;
        if(idTypeBox.getValue() == null) {
            selected = true;
        }
        return selected;
    }

    //CHECK IF THE USER HAS SELECTED A GENDER TYPE FROM THE DROPDOWN BOX. IF YES RETURN true ELSE RETURN false
    public boolean checkGenderBox() {
        boolean selected = false;
        if(genderBox.getValue() == null) {
            selected = true;
        }
        return selected;
    }

    //CHECK IF THE USER HAS UPLOADED AN IMAGE OR NOT. IF YES RETURN true ELSE RETURN false
    public boolean checkImageName() {
        boolean selected = false;
        if(imageNameField.getText() == null) {
            selected = true;
        }
        return selected;
    }

    //CHECK IF THE USER HAS SELECTED A DATE FROM THE DATE PICKER BOX. IF YES RETURN true ELSE RETURN false
    public boolean checkDatePicker() {
        boolean selected = false;
        if(dateField.getValue() == null) {
            selected = true;
        } else {
            System.out.println(dateField.getValue());
        }
        return selected;
    }











}//END OF CLASS