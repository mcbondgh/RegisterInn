package inn.Controllers.settings;

import inn.Controllers.configurations.SysActivator;
import inn.models.UserLoginsModel;
import inn.multiStage.MultiStages;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateLogins extends UserLoginsModel implements Initializable {
    MultiStages multiStagesOBJ = new MultiStages();
    SysActivator sysActivatorOBJ = new SysActivator();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillUsernameBox();
            fillUserRoleBox();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*******************************************************************************************************************
                                            FXML NODE EJECTIONS
     ******************************************************************************************************/
    @FXML private ComboBox<String> userRoleBox, usernameBox;
    @FXML public TextField usernameField;
    @FXML private PasswordField passwordField, confirmField;
    @FXML private ToggleGroup user_status;
    @FXML private Button updateButton;
    @FXML private RadioButton activeStatus, inactiveStatus;



    /*******************************************************************************************************************
                                            ACTION EVENT METHODS IMPLEMENTATION
     ******************************************************************************************************/
    @FXML
    void updateButtonOnAction() throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING );
            if (checkUsernameBox()) {
                alert.setContentText("you have to select an existing username from the dropdown box above.");
                alert.setHeaderText("USERNAME IS NOT SET, PLEASE SELECT A USERNAME FIRST");
                alert.setTitle("No User Selected.");
                alert.showAndWait();
                usernameBox.setStyle("-fx-border-color:#ff0000; -fx-border-width: 2px");
            } else {
                usernameBox.setStyle(("-fx-border-color:null"));
                if(checkPasswordMatch()) {
                    alert.setTitle("PASSWORD MISMATCH");
                    alert.setHeaderText("SORRY, PASSWORD FIELDS DO NOT MATCH");
                    alert.setContentText("please make sure to provide a matching password");
                    alert.show();
                    passwordField.clear();
                    confirmField.clear();
                } else if(passwordLength()) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setTitle("Password Length");
                    alert.setHeaderText("YOUR DESIRED PASSWORD SHOULD BE AT LEASE 4 CHARACTERS LONG");
                    alert.setContentText("your desired password is too short.");
                    alert.show();
                } else {
                    alert.setAlertType(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Update User Logins");
                    alert.setHeaderText("ARE YOU SURE YOU WANT TO UPDATE USER'S LOGIN DETAILS?");
                    alert.setContentText("please confirm your decision to execute, else cancel to abort.");
                    alert.getButtonTypes().remove(ButtonType.OK);
                    alert.getButtonTypes().add(ButtonType.YES);
                    if(alert.showAndWait().get() == ButtonType.YES) {

                        int selectedRoleId = fetchUserRoleID(userRoleBox.getValue());
                        int statusId = 0;
                        if (selectedStatus().equals("Active")) {
                            statusId = 1;
                        }

                        //HASH USER PASSWORD BEFORE SAVING TO DATABASE.
                        String password1 = sysActivatorOBJ.passwordEncryptor(passwordField.getText());
                        String password2 = sysActivatorOBJ.passwordEncryptor(confirmField.getText());
                        int returnValue = updateUserLogins(usernameField.getText(), password1, password2, selectedRoleId, statusId, 1, usernameBox.getValue());
                        if (returnValue > 0) {
                            multiStagesOBJ.showSuccessPrompt();
                            clearFields();
                        } else multiStagesOBJ.showFailedPrompt();
                    }
                }
            }
    }
    @FXML void FillTextFields() throws SQLException {
           ArrayList<Object> userDetails = fetchUserLoginsDetails(usernameBox.getValue());
            usernameField.setText(userDetails.get(0).toString());
            passwordField.setText(userDetails.get(1).toString());
            confirmField.setText(userDetails.get(1).toString());
            String userRole = fetchUserRoleName((int) userDetails.get(2));
            userRoleBox.setValue(userRole);
            userStatusValue((int) userDetails.get(3));

    }

    @FXML void validateInputFields() {
        try {
            updateButton.setDisable(usernameField.getText().isBlank() || passwordField.getText().isBlank() || confirmField.getText().isBlank() || checkToggleButtons() || checkUserRole());

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /******************************************************************************************************************
                                                 OTHER METHODS IMPLEMENTATION
     ******************************************************************************************************/
    void fillUsernameBox() {
        for (Object item: fetchUsernames()) {
        usernameBox.getItems().add(item.toString());
        }
    }

    void fillUserRoleBox() throws SQLException {
        userRoleBox.setItems(fetchUserRoles());
    }

    //THIS METHOD WHEN INVOKED WILL RETURN THE SELECTED USER STATUS i.e Active or Inactive.
    String selectedStatus() {
        RadioButton selectedValue = (RadioButton) user_status.getSelectedToggle();
        return selectedValue.getText();
    }

    //THIS METHOD WHEN INVOKED WILL RETURN THE SPECIFIED status VALUE OF THE SELECTED USER. ie 1=active | 0=inactive.
    void userStatusValue(int statusValue) {
        switch (statusValue) {
            case 1 -> activeStatus.setSelected(true);
            case 0 -> inactiveStatus.setSelected(true);
        }
    }

    //THIS METHOD WHEN INVOKED WILL CHECK IF THE PROVIDED username in the usernameField already exist then returns true else return false..
    boolean validateUsername() {
        return false;
    }

    public boolean checkUserRole() {
        return userRoleBox.getValue() == null;
    }

    public boolean checkToggleButtons() {
        return user_status.getSelectedToggle() == null;
    }

    public boolean checkUsernameBox() {
        return usernameBox.getValue() == null;
    }

    boolean checkPasswordMatch() {
        return !Objects.equals(passwordField.getText(), confirmField.getText());
    }

    boolean passwordLength() {
        return passwordField.getLength() < 4 || confirmField.getLength() < 4;
    }

    void clearFields() {
//        usernameBox.setValue(null);
        usernameField.clear();
        passwordField.clear();
        confirmField.clear();
        userRoleBox.setValue(null);
        user_status.getSelectedToggle().setSelected(false);
    }



}//END OF CLASS
