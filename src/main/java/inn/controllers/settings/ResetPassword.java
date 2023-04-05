package inn.Controllers.settings;

import inn.Controllers.configurations.SysActivator;
import inn.models.MainModel;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Objects;
import java.util.ResourceBundle;

public class ResetPassword extends MainModel implements Initializable {

    UpdateLogins updateLogins = new UpdateLogins();
    SysActivator activator = new SysActivator();
    UserNotification notify = new UserNotification();
    UserAlerts alerts;

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) { }

    //Default Constructor;
    public ResetPassword() { }

    /*************************************** FXML NODES ************************************************/
    @FXML private TextField usernameField;
    @FXML private PasswordField newPasswordField, retypePasswordField;
    @FXML private Label userLabel, passLabel, retypeLabel, resetPasswordSuccessfulLabel;
    @FXML private MFXButton resetButton, cancelButton;


    /********************************* IMPLEMENTATION OF TRUE OR FALSE STATEMENTS *************************************/




    /********************************* IMPLEMENTATION OF OTHER METHODS ************************************************/


    /********************************* ACTION EVENT METHOD IMPLEMENTATION *********************************************/
    @FXML void searchOnKeyTyped() {
        String username = usernameField.getText();
        for (String item : getActiveUsersOnly()) {
           if (Objects.equals(item.toLowerCase(), username.toLowerCase().trim())) {
              newPasswordField.setDisable(false);
              retypePasswordField.setDisable(false);
              break;
           } else{
               newPasswordField.setDisable(true);
               retypePasswordField.setDisable(true);
           }
        }
    }
    @FXML void validatePasswordFields() {
        String newPassword = newPasswordField.getText();
        String repeat = retypePasswordField.getText();
        if (!Objects.equals(newPassword, repeat)) {
            passLabel.setVisible(false);
            retypeLabel.setVisible(true);
            resetButton.setDisable(true);
        } else {
            resetButton.setDisable(false);
            passLabel.setVisible(false);
            retypeLabel.setVisible(false);
        }
    }
    @FXML void resetButtonClicked() throws InterruptedException {
        String username = usernameField.getText();
        String password = activator.passwordEncryptor(newPasswordField.getText());
        alerts = new UserAlerts("UPDATE PASSWORD", "ARE YOU SURE YOU WANT TO UPDATE YOUR PASSWORD?", "Please confirm your process with YES, else CANCEL to abort.");
        if (alerts.confirmationAlert()) {
            int flag = updateLogins.updatePassword(username, password, password);
            if (flag > 0) {
                resetButton.getScene().getWindow().hide();
            }
        }
    }
    @FXML void  cancelButtonClicked() {
        cancelButton.getScene().getWindow().hide();
    }










}//END OF CLASS
