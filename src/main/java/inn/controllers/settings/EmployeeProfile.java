package inn.Controllers.settings;

import inn.models.EmpProfileModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeProfile extends EmpProfileModel implements Initializable {

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillGenderBox();
            fillIdTypeBox();
            fillEmployeeBox();
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
    private ComboBox employeeBox, genderBox, idTypeBox;
    @FXML
    private DatePicker dateField;

    @FXML
    private TextField firstnameField, lastnameField, emailField, mobileField, imageNameField;
    @FXML
    private TextField addressField, idNumberField, designationField, salaryField, addedByField, updatedDate, idField;
    @FXML
    private Button updateProfileBtn, uploadImageBtn, deleteProfileBtn;


    /*******************************************************************************************************************
                                            GETTER AND SETTER METHODS
     *******************************************************************************************************************/


    /*******************************************************************************************************************
                                            ACTION EVENT METHODS IMPLEMENTATION
     *******************************************************************************************************************/
    @FXML
    private void UploadProfileImage() {

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






}//END OF CLASS