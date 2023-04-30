package inn.controllers.settings;

import inn.controllers.configurations.SysActivator;
import inn.models.MainModel;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ResetPassword extends MainModel implements Initializable {

    public AnchorPane updateLoginsForm;
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
        boolean validatePassword() {
            return newPasswordField.getText().length() < 4;
        }

    /********************************* IMPLEMENTATION OF OTHER METHODS ************************************************/
        void displaySuccessMessageWithTimer() {
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                int interval = 0;
                @Override
                public void run() {
                    if (interval < 3) {
                        Platform.runLater(() -> resetPasswordSuccessfulLabel.setVisible(true));
                        interval++;
                    }
                    if (interval == 2) {
                        Platform.runLater(() -> {
                            resetPasswordSuccessfulLabel.setVisible(false);
                            resetButton.getScene().getWindow().hide();
                        });
                        timer.cancel();
                    }
                }
            };
            timer.schedule(timerTask, 1, 2000);
        }


    /********************************* ACTION EVENT METHOD IMPLEMENTATION *********************************************/
    @FXML void searchOnKeyTyped() {
        String username = usernameField.getText();
        for (String item : getActiveUsersOnly()) {
           if (Objects.equals(item.toLowerCase(), username.toLowerCase().trim())) {
              newPasswordField.setDisable(false);
              retypePasswordField.setDisable(false);
               userLabel.setVisible(false);
              break;
           } else{
               userLabel.setVisible(true);
               newPasswordField.setDisable(true);
               retypePasswordField.setDisable(true);
           }
        }
    }

    @FXML void validatePasswordFields() {
        String newPassword = newPasswordField.getText();
        String repeat = retypePasswordField.getText();
        if (!validatePassword()) {
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

    }
    @FXML void resetButtonClicked() throws InterruptedException {
        String username = usernameField.getText();
        String password = activator.passwordEncryptor(newPasswordField.getText());
        alerts = new UserAlerts("UPDATE PASSWORD", "ARE YOU SURE YOU WANT TO UPDATE YOUR PASSWORD?", "Please confirm your process with YES, else CANCEL to abort.");
        if (alerts.confirmationAlert()) {
            int flag = updateLogins.updatePassword(username, password, password);
            if (flag > 0) {
               displaySuccessMessageWithTimer();
            }
        }
    }
    @FXML void  cancelButtonClicked() {
        cancelButton.getScene().getWindow().hide();
    }










}//END OF CLASS
