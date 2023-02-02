package inn.Controllers.settings;

import inn.Controllers.config.SysActivator;
import inn.StartInn;
import inn.database.DbConnection;
import inn.models.InnActivationModel;
import inn.multiStage.MultiStages;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AuthenPasswordReset extends InnActivationModel implements Initializable {


    public void initialize(URL url, ResourceBundle resourceBundle) {
        checkEmptyFieldsOnKeyTyped();
    }

    /*******************************************************************************************************************
                                                CLASS INSTANTIATION FIELD
     ******************************************************************************************************************/
    SysActivator sysActivatorOBJ = new SysActivator();
    DbConnection modelOBJ = new DbConnection();
    MultiStages multiStagesOBJ =  new MultiStages();
    General generalOBJ;


    /*******************************************************************************************************************
                                                    FXLM NODE EJECTION
     ******************************************************************************************************************/
    @FXML private PasswordField currentPasswordField, newPasswordField, confirmPasswordField;
    @FXML private Button resetPasswordBtn;
    @FXML private Label wrongPasswordLabel, comparePasswordLabel;


    /*******************************************************************************************************************
                                             ON KEY TYPED ACTION EVENT
     ******************************************************************************************************************/
    @FXML void checkEmptyFieldsOnKeyTyped() {
        if(validateNewPasswordField() || validateConfirmPasswordField() || validateCurrentPasswordField()) {
            resetPasswordBtn.setDisable(true);
        } else {
            resetPasswordBtn.setDisable(false);
        }
    }
    @FXML void OnKeyTypedEvent() {
        if (!(verifyProvidedPassword())) {
            wrongPasswordLabel.setVisible(true);
            newPasswordField.setDisable(true);
            confirmPasswordField.setDisable(true);
            currentPasswordField.setStyle("-fx-border-color:#ff0000; -fx-border-width:2px");
        } else {
            newPasswordField.setDisable(false);
            confirmPasswordField.setDisable(false);
            currentPasswordField.setStyle("-fx-border-color:#009735; -fx-border-width:2px");
            wrongPasswordLabel.setVisible(false);
        }
    }


    /*******************************************************************************************************************
     ACTION EVENT METHOD IMPLEMENTATION FIELD
     ******************************************************************************************************************/

    //UPDATES THE activation_key TABLE
    @FXML
    private void resetPasswordBtnOnAction() throws SQLException, IOException {
        if (!(comparePasswords())) {
            comparePasswordLabel.setText("Password fields do not match, retype");
            newPasswordField.setStyle("-fx-border-color:#ff0000; -fx-border-width:2px");
            confirmPasswordField.setStyle("-fx-border-color:#ff0000; -fx-border-width:2px");

        } else {
            newPasswordField.setStyle(null);
            confirmPasswordField.setStyle(null);
            comparePasswordLabel.setVisible(false);

            String inputValue = sysActivatorOBJ.passwordEncryptor(newPasswordField.getText());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "please confirm to update authentication key else cancel to abort");
            alert.setHeaderText("ARE YOU SURE YOU WANT TO SAVE NEW AUTHENTICATION KEY PASSWORD?");
            alert.setTitle("CONFIRM KEY UPDATE");
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.YES);

            if (alert.showAndWait().get() == ButtonType.YES) {
                int flag = updateAuthenticationPassword(inputValue);
                if (flag > 0) {
                    multiStagesOBJ.showSuccessPrompt();
                    clearFields();
                }
            }
        }
    }


    /*******************************************************************************************************************
     TRUE OR FALSE INPUT FIELDS VALIDATION
     ******************************************************************************************************************/

    boolean validateCurrentPasswordField() {
        return currentPasswordField.getText().isBlank();
    }

    boolean validateNewPasswordField() {
       return newPasswordField.getText().isBlank();
    }

    boolean validateConfirmPasswordField() {
        return confirmPasswordField.getText().isBlank();
    }

    //THIS METHOD WHEN INVOKED SHALL RETURN TRUE IF THE PASSWORDS MATCH ELSE FALSE.
    boolean verifyProvidedPassword() {
        boolean flag = false;
        String encryptedPassword = "";
        for(String value : modelOBJ.getSystemActivationPassword()) {
            encryptedPassword = value;
        }
        return sysActivatorOBJ.passwordVerify(encryptedPassword, currentPasswordField.getText());
    }

    boolean comparePasswords() {
        return newPasswordField.getText().equalsIgnoreCase(confirmPasswordField.getText());
    }


    void clearFields() throws IOException {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
        resetPasswordBtn.setDisable(true);
//        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("Modules/dashboard/resetAuthenPassword.fxml"));
//        Parent root = fxmlLoader.load();
//        generalOBJ = fxmlLoader.getController();
    }







}//END OF CLASS
