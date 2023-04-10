package inn.Controllers;


import inn.Controllers.dashboard.Homepage;
import inn.ErrorLogger;
import inn.models.MainModel;
import inn.multiStage.MultiStages;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Index extends Homepage implements Initializable {

    /*******************************************> CLASS INSTANTIATION FIELD. <*************************************/
        MultiStages multiStages = new MultiStages();
        MainModel connector = new MainModel();
        private Parent root;


    /*******************************************************************************************************************
     INITIALIZER METHOD */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setBusinessHero();
        }catch (Exception e){
            try {
                multiStages.wrongDateTimeStage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ErrorLogger errorLogger = new ErrorLogger();
            errorLogger.log(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    /*******************************************************************************************************************
                                                        FXML OBJECTS */
    @FXML private Button signinButton, cancelButton;
    @FXML public Hyperlink resetPassword, activateBtn;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label businessNameLabel;

    /*******************************************************************************************************************
                                                    GETTER AND SETTER FIELDS. */
    public void setUsernameField(String username) {usernameField.setText(username);}
    public String getUsernameField() {return usernameField.getText();}

    /*******************************************************************************************************************
     ACTION EVENT METHODS IMPLEMENTATION*/
    public void signinButtonAction() throws IOException {
        Homepage.activeUsername = getUsernameField().isBlank() ?  "Active User" :getUsernameField();
        multiStages.Homepage();
        signinButton.getScene().getWindow().hide();

    }

    public void passwordResetLink() throws IOException {
        multiStages.resetPassword();
    }

    public void cancelButtonAction() throws IOException {
        Platform.exit();
    }

    /*******************************************> OTHER METHODS IMPLEMENTATION. <*************************************/
    public void setBusinessHero() {
        String name = (String) connector.fetchBusinessInfo().get(0);
        setBusinessNameLabel(name);
    }

    public void resetInnRegister() throws IOException {
        multiStages.innRegisterActivationStage();
    }




}//END OF CLAS
