package inn.controllers;


import inn.ErrorLogger;
import inn.StartInn;
import inn.config.database.DatabaseConfiguration;
import inn.models.MainModel;
import inn.multiStage.MultiStages;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Index extends Homepage implements Initializable {

    /*******************************************> CLASS INSTANTIATION FIELD. <*************************************/
        MultiStages multiStages = new MultiStages();
        MainModel connector = new MainModel();
        private Parent root;
        ErrorLogger logger;
        DatabaseConfiguration config = new DatabaseConfiguration();


    /*******************************************************************************************************************
     INITIALIZER METHOD */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setBusinessHero();
        }catch (Exception e){
            try {
                usernameField.setText(setUsernameFieldValue);
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
    @FXML private Pane loginPaneView;
    @FXML private BorderPane borderPaneView;
    public static String setUsernameFieldValue;




    /*******************************************************************************************************************
     SPECIAL METHODS IMPLEMENTATION*/
    public void FlipView(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource(fxmlFileName));
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000),borderPaneView);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        borderPaneView.setCenter(fxmlLoader.load());
        fadeTransition.play();
    }




    /*******************************************************************************************************************
                                                    GETTER AND SETTER FIELDS. */
    public void setUsernameField(String username) {usernameField.setText(username);}
    public String getUsernameField() {return usernameField.getText();}

    /*******************************************************************************************************************
     ACTION EVENT METHODS IMPLEMENTATION*/
    public void signinButtonAction() throws IOException {
        Homepage.activeUsername = getUsernameField().isBlank() ?  "Admin" :getUsernameField();
        multiStages.Homepage();
        signinButton.getScene().getWindow().hide();

    }

    public void passwordResetLink() throws IOException {
        try {
            multiStages.resetPassword();
        }catch(Exception e){
            logger = new ErrorLogger();
            logger.log(e.getMessage());
        }

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
