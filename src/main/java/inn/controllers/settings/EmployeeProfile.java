package inn.Controllers.settings;

import inn.Controllers.config.DefPassword;
import inn.models.EmpProfileModel;
import inn.models.ResourceModel;
import inn.multiStage.MultiStages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class EmployeeProfile extends EmpProfileModel implements Initializable {

    MultiStages multiStagesOBJ = new MultiStages();
    ResourceModel resourceModelOBJ = new ResourceModel();
    DefPassword defPasswordOBJ = new DefPassword();

    File imageFile = null;
    FileInputStream inputStream = null;
    String fileName = null;
    String filePath = null;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillGenderBox();
            fillIdTypeBox();
            fillEmployeeBox();
            fillUserRoleBox();
            fillDesignationBox();
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
    private ComboBox<String> employeeBox, genderBox, idTypeBox, userRoleBox, designationBox;
    @FXML
    private DatePicker dateField;
    @FXML
    private Label userStatusLabel;
    @FXML
    private TextField firstnameField, lastnameField, emailField, numberField, imageNameField;
    @FXML
    private TextField addressField, idNumberField, salaryField, addedByField, updatedDate, idField;
    @FXML
    private Button updateProfileBtn, uploadImageBtn, deleteProfileBtn;


    /*******************************************************************************************************************
                                            ACTION EVENT METHODS IMPLEMENTATION
     *******************************************************************************************************************/
    @FXML
    private void UploadProfileImage() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose Employee Image");
            fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            imageFile = fileChooser.showOpenDialog(updateProfileBtn.getScene().getWindow());
            fileName = imageFile.getName();
            filePath = imageFile.getAbsolutePath();
            Image image = new Image(filePath);
            uploadProfile.setImage(image);
            imageNameField.setText(fileName);
        } catch (NullPointerException e) {
            System.out.println("No file selected.");
            Logger.getAnonymousLogger();
        }
    }

    //THIS METHOD WILL CONVERTER THE IMAGE OF THE USER UPLOADED INTO THE IMAGE VIEWER. THIS METHOD IS WHAT WILL BE SAVED INTO THE DATABASE.
    public InputStream userImage() throws IOException {
        try {
            inputStream = new FileInputStream(imageFile);
        } catch (NullPointerException | FileNotFoundException ignored) {

        }
        return inputStream;
    }
    @FXML
    void updateButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        try {
           if (checkEmployeeBox()) {
               alert.setAlertType(Alert.AlertType.WARNING);
               alert.setTitle("No Employee Selected");
               alert.setHeaderText("YOU HAVE NOT SELECTED AN EMPLOYEE, DO SO TO UPDATE RECORD");
               alert.setContentText("make sure you have selected an employee from the 'Select Employee Here' to specify a valid employee");
               alert.setResult(ButtonType.OK);
               alert.show();
            } if (numberField.getText().isBlank() || firstnameField.getText().isBlank() || lastnameField.getText().isBlank() || emailField.getText().isBlank() || idNumberField.getText().isBlank() || addressField.getText().isBlank()) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setTitle("Empty Field(s)");
                alert.setHeaderText("YOU HAVE ONE OR MORE FIELDS EMPTY, PLEASE FILL OUT ALL FIELDS.");
                alert.show();
                updateProfileBtn.setDisable(true);
                uploadImageBtn.setDisable(true);
                deleteProfileBtn.setDisable(true);
           }
           else {
               if (userRoleBox.isDisabled() || userRoleBox.getValue() == null) {
                   alert.setAlertType(Alert.AlertType.CONFIRMATION);
                   alert.getButtonTypes().add(ButtonType.YES);
                   alert.getButtonTypes().remove(ButtonType.OK);
                   alert.setTitle("UPDATE EMPLOYEE INFO");
                   alert.setHeaderText("ARE YOU SURE YOU WANT TO UPDATE EMPLOYEE'S DETAILS?");
                   alert.setContentText("please confirm your action to execute else cancel to abort.");
                   if (alert.showAndWait().get() == ButtonType.YES) {
                       updateEmployeeRecord(Integer.parseInt(idField.getText()), firstnameField.getText(), lastnameField.getText(), genderBox.getValue(), emailField.getText(), numberField.getText(), addressField.getText(), idTypeBox.getValue(), idNumberField.getText(), designationBox.getValue(), userImage(), Double.parseDouble(salaryField.getText()) );
                       multiStagesOBJ.showSuccessPrompt();
                       clearFields();
                       deleteProfileBtn.setDisable(true);
                       updateProfileBtn.setDisable(true);
                       userRoleBox.setDisable(true);
                       userStatusLabel.setText(null);
                   }
               } else if(!(userRoleBox.getValue().isEmpty())) {
                   //GET roleId from the users table based on the userRole name parsed as an argument to the below method.
                   int roleId = fetchUserRoleID(userRoleBox.getValue());
                   String defpass = defPasswordOBJ.defaultPassword();

                   alert.setAlertType(Alert.AlertType.CONFIRMATION);
                   alert.getButtonTypes().add(ButtonType.YES);
                   alert.getButtonTypes().remove(ButtonType.OK);
                   alert.setTitle("CREATE USER & UPDATE EMPLOYEE");
                   alert.setHeaderText("YOU ARE ABOUT TO UPDATE EMPLOYEE RECORDS AND SET AS A USER AT THE SAME TIME");
                   alert.setContentText("please confirm your action to execute else cancel to abort.");
                   if (alert.showAndWait().get() == ButtonType.YES) {
                       updateEmployeeRecord(Integer.parseInt(idField.getText()), firstnameField.getText(), lastnameField.getText(), genderBox.getValue(), emailField.getText(), numberField.getText(), addressField.getText(), idTypeBox.getValue(), idNumberField.getText(), designationBox.getValue(), userImage(), Double.parseDouble(salaryField.getText()) );
                       resourceModelOBJ.registerNewUser(emailField.getText(), defpass, defpass, Integer.parseInt(idField.getText()), roleId);
                       multiStagesOBJ.showSuccessPrompt();
                       clearFields();
                       updateProfileBtn.setDisable(true);
                       deleteProfileBtn.setDisable(true);
                       uploadImageBtn.setDisable(true);
                       userRoleBox.setDisable(true);
                   }
               } else {
                   multiStagesOBJ.showFailedPrompt();
               }
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void selectEmployeeOnAction() throws FileNotFoundException {
        ArrayList<Object> result = fetchFullEmployeeDetails(employeeBox.getValue());
        try {
                idField.setText(result.get(0).toString());
                firstnameField.setText(result.get(1).toString());
                lastnameField.setText(result.get(2).toString());
                genderBox.setValue(result.get(3).toString());
                emailField.setText(result.get(4).toString());
                numberField.setText(result.get(5).toString());
                addressField.setText(result.get(6).toString());
                idTypeBox.setValue(result.get(7).toString());
                idNumberField.setText(result.get(8).toString());
                dateField.setValue(LocalDate.parse(result.get(9).toString()));
                designationBox.setValue(result.get(10).toString());
                salaryField.setText(result.get(12).toString());
                addedByField.setText(result.get(13).toString());
                updatedDate.setText(result.get(14).toString());
                Blob imageBlob = (Blob) result.get(11);
                byte[] imageByte = imageBlob.getBytes(1, (int) imageBlob.length());
                OutputStream stream = new FileOutputStream("E:\\JAVA APPLICATIONS\\RegisterInn\\src\\main\\resources\\inn\\images\\imagexyz.jpg");
                stream.write(imageByte);
                Image profileImage = new Image("E:\\JAVA APPLICATIONS\\RegisterInn\\src\\main\\resources\\inn\\images\\imagexyz.jpg");
                uploadProfile.setImage(profileImage);
                stream.close();

        } catch (NullPointerException e) {
                Image defaultImage = new Image("E:\\JAVA APPLICATIONS\\RegisterInn\\src\\main\\resources\\inn\\images\\users.jpg");
                uploadProfile.setImage(defaultImage);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException index) {
            throw new IndexOutOfBoundsException();
        }

        //CHECK IF THE SELECTED EMPLOYEE IS ALREADY A USER. IF false Enable UserRole ComboBox else Disable UserRole ComboBox.
            if(validateUsername() || validateEmpId()){
               userStatusLabel.setText("VALID USER");
               userStatusLabel.setStyle("-fx-text-fill: #00bf09");
               userRoleBox.setDisable(true);
            } else {
                userRoleBox.setDisable(false);
                userRoleBox.setValue(null);
                userStatusLabel.setStyle("-fx-text-fill: #a30b0b");
                userStatusLabel.setText("NOT A USER");
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
            alert.setHeaderText("ARE YOU SURE YOU WANT TO PERMANENTLY DELETE EMPLOYEE'S RECORDS?");
            if (alert.showAndWait().get() == YES) {
                int emp_id = Integer.parseInt(idField.getText());
                deleteEmployeeRecord(emp_id);
                multiStagesOBJ.showSuccessPrompt();
                clearFields();
            }
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
            if (checkGenderBox() || checkIdTypeBox() || firstnameField.getText().isEmpty() || lastnameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                addressField.getText().isEmpty() || checkDesignationBox() || salaryField.getText().isEmpty() || numberField.getText().isEmpty() || idNumberField.getText().isEmpty()) {
                updateProfileBtn.setDisable(true);
                uploadImageBtn.setDisable(true);
                deleteProfileBtn.setDisable(true);
            }
            else  {
                updateProfileBtn.setDisable(false);
                uploadImageBtn.setDisable(false);
                deleteProfileBtn.setDisable(false);
                if (validateUsername() || validateEmpId()) {
                    userStatusLabel.setText("VALID USER");
                    userStatusLabel.setStyle("-fx-text-fill: #00bf09");
                    userRoleBox.setDisable(true);
                    userRoleBox.setValue(null);
                }else {
                    userStatusLabel.setText("NOT A USER");
                    userStatusLabel.setStyle("-fx-text-fill: #a30b0b");
                    userRoleBox.setDisable(false);
                }
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
    void fillDesignationBox() {
        designationBox.setItems(fetchDesignation());
    }


    /*******************************************************************************************************************
                                   TRUE OR FALSE METHOD IMPLEMENTATIONS FOR VALIDATING COMBO BOXES
    ********************************************************************************************************************/


    //CHECK IF THE USER HAS SELECTED AN EMPLOYEE FROM THE DROPDOWN BOX. IF YES RETURN true ELSE RETURN false
    public boolean checkEmployeeBox() {
        return employeeBox.getValue() == null;
    }

    //CHECK IF THE USER HAS SELECTED AN ID TYPE FROM THE DROPDOWN BOX. IF YES RETURN true ELSE RETURN false
    public boolean checkIdTypeBox() {
        return idTypeBox.getValue() == null;
    }

    //CHECK IF THE USER HAS SELECTED A GENDER TYPE FROM THE DROPDOWN BOX. IF YES RETURN true ELSE RETURN false
    public boolean checkGenderBox() {
        return genderBox.getValue() == null;
    }

    //CHECK IF THE USER HAS UPLOADED AN IMAGE OR NOT. IF YES RETURN true ELSE RETURN false
    public boolean checkImageName() {
        return imageNameField.getText() == null;
    }

    //CHECK IF THE USER SELECTED AN EMPLOYEE FROM THE EMPLOYEE DROPDOWN LIST. IF YES RETURN true ELSE RETURN false
    public boolean checkDesignationBox() {
        return designationBox.getValue() == null;
    }

    //CHECK IF THE USER HAS SELECTED A USER ROLE FROM THE DROPDOWN LIST. IF YES RETURN true ELSE RETURN false
    public boolean checkUserRoleBox() {
        return userRoleBox.getValue() == null;
    }

    //CHECK IF THE SELECTED USER IS AN VALID SYSTEM USER. IF YES RETURN true ELSE RETURN false
    boolean validateUsername() {
        boolean result = false;
        for (String item:fetchUsernames()) {
            if (item.equals(emailField.getText().toLowerCase())) {
                 result = true;
            }
        }
        return result;
    }

    boolean validateEmpId() {
        boolean result = false;
        for(int empIds: fetchEmployeeId()) {
            if(Integer.parseInt(idField.getText()) == empIds) {
                result = true;
            }
        }
        return result;
    }

    void clearFields() {
        firstnameField.clear();
        lastnameField.clear();
        genderBox.setValue(null);
        dateField.setValue(null);
        idTypeBox.setValue(null);
        emailField.clear();
        addressField.clear();
        designationBox.setValue(null);
        numberField.clear();
        salaryField.clear();
        idNumberField.clear();
        employeeBox.setAccessibleText("Select Employee Here.");
        idField.clear();
        updatedDate.clear();
        addedByField.clear();
        userRoleBox.setValue(null);
    }











}//END OF CLASS