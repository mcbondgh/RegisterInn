package inn.controllers;

import inn.StartInn;
import inn.controllers.configurations.FormatLocalDateTime;
import inn.controllers.configurations.SysActivator;
import inn.controllers.inventory.ManageInventory;
import inn.models.InnActivationModel;
import inn.models.UserLoginsModel;
import inn.multiStage.MultiStages;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXNotificationCenter;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Homepage extends UserLoginsModel implements Initializable{

    /******************************************> OBJECT INSTANTIATION FIELD <*******************************************/
    MultiStages multiStagesOBJ = new MultiStages();
    InnActivationModel modelOBJ = new InnActivationModel();
    UserAlerts  userAlert;

    UserNotification notify = new UserNotification();





    /******************************************> FXML OBJECTS  <*******************************************/
    @FXML private Button dashboardBtn, signoutBtn, inventoryBtn, bookingBtn, accountingBtn,newMemberButton;
    @FXML private MenuButton reportsBtn, settingsBtn;
    @FXML private MenuItem generalBtn, manageRolesBtn, updateBtn, humanResourceBtn, employeeProfileBtn, payRoleButton, manageRoomsButton, manageStocksButton;
    @FXML private Label businessNameLabel, activeUserLabel, dateLabel, counter,userFlag;
    @FXML private Label displayTimeLabel;
    @FXML private TextArea aboutField;
    @FXML private BorderPane displayContainer;
    @FXML private ImageView heroImageDisplay;
    @FXML private AnchorPane homepagePane;
    @FXML private MFXNotificationCenter notificationIcon;
    @FXML private Label showDate;
    @FXML private Pane displayUsernamePane, userProfilePane;

    @FXML private PasswordField newPasswordField, confirmPasswordField;
    @FXML private Label passwordStatusDisplay;
    @FXML private Button saveProfileUpdateButton, cancelProfileButton;

    @FXML private Circle beepIndicator;
    @FXML private Pane sidebarPane, borderPaneCenter;
    @FXML private MFXToggleButton togglerButton;

    public static String label;

    public static String activeUsername;
    public static int counterValue;





//    General general = new General();
    /*******************************************************************************************************************
     IMPLEMENTATION OF INITIALIZER METHOD*/
    public void initialize(URL location, ResourceBundle resourceBundle) {
        activeUserLabel.setText(activeUsername);userFlag.setText(activeUserLabel.getText());
        setDashboardVariables();
        beepActiveUser();
        displayCurrentTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        LocalDate currentDate = LocalDate.now();
        String date  = formatter.format(currentDate);
        showDate.setText(date);
        showUserProfilePane();
        hideUserProfilePane();

    }
    public void checkFLOW(){
//        timer.scheduleAtFixedRate(task,0,1000);
    }

    /*******************************************************************************************************************
     GETTER AND SETTER FIELD*/
    public void setAboutField(String about) {aboutField.setText(about);}
    public String getAboutField() {return aboutField.getText();}

    public void setActiveUserLabel(String activeUser) {activeUserLabel.setText(activeUser);}
    public String getActiveUserLabel() {return activeUserLabel.getText();}

    public void setBusinessNameLabel(String businessLabel) {businessNameLabel.setText(businessLabel);}
    public String getBusinessNameLabel() {return businessNameLabel.getText();}


    /*******************************************************************************************************************
     SPECIAL METHODS IMPLEMENTATION*/
    public void FlipView(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource(fxmlFileName));
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000),displayContainer);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1.0);
        displayContainer.setCenter(fxmlLoader.load());
        fadeTransition.play();
    }

    /*******************************************************************************************************************
     ACTION EVENT METHODS IMPLEMENTATION*/
    public void dashboardBtnAction() throws IOException {
        FlipView("Modules/dashboard/admin-dashboard.fxml");
    }
    public void reportsBtnAction() throws IOException {
        FlipView("Modules/report/report.fxml");
    }
    public void bookingBtnAction() throws IOException {
        FlipView("Modules/booking/booking.fxml");
    }
    public void accountingBtnAction() throws IOException {
        FlipView("Modules/accounting/accounts.fxml");
    }
    public void inventoryBtnAction() throws IOException {
        int month = LocalDate.now().getMonthValue();
        String year = String.valueOf(LocalDate.now().getYear()).substring(2,4);
        String nameValue = activeUsername.substring(0,3);
        String timeValue = String.valueOf(System.currentTimeMillis()).substring(6, 12);
        ManageInventory.transactionId = nameValue.concat( timeValue +"/"+ year +"/" + month).toLowerCase();
        FlipView("Modules/inventory/inventory.fxml");

    }

    public void generalBtnAction() throws IOException {
        FlipView("Modules/settings/general.fxml");
    }
    public void manageRolesBtnAction() throws IOException {
        FlipView("Modules/settings/roles&permission.fxml");
    }

    public void humanResourceBtnAction() throws IOException {
        FlipView("Modules/settings/humanResource.fxml");
    }

    public void employeeProfileBtnOnAction() throws IOException {
        FlipView("Modules/settings/employeeProfile.fxml");
    }

    public void manageRoomsBtnOnAction() throws IOException {
        FlipView("Modules/settings/manageRooms.fxml");
    }
    @FXML public void messageBoxButtonOnAction() throws IOException, InterruptedException {
        Thread executeThread = new Thread(()-> Platform.runLater(() -> {
            try {
                FlipView("Modules/messagebox/manageSms.fxml");
                Thread.sleep(1000);
            } catch (IOException ignored) {
//                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
        executeThread.start();
    }
    public void updateBtnAction() throws IOException {
        FlipView("Modules/settings/updateUserLogin.fxml");
//        multiStagesOBJ.UpdateLoginDetails();
    }
    @FXML void managePayrollButtonClick() throws IOException {
        FlipView("Modules/settings/payroll.fxml");
    }
    @FXML void manageStocksButtonOnAction() throws IOException {
        FlipView("Modules/settings/manageStocks.fxml");
    }

    //THIS METHODS RUNS A TimerTask WHICH IS RESPONSIBLE FOR BEEPING THE GREEN BUTTON BESIDE THE USER AVATOR.
    private void beepActiveUser() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int counter = 1;
            @Override
            public void run() {
                if (counter > 0){
                    beepIndicator.setStyle("-fx-fill:#beffc2");
                    counter--;
                }else {
                    beepIndicator.setStyle("-fx-fill:#09e618");counter = 1;
                }
                if (signoutBtn.isPressed()) {
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task,1,1000);
    }

    //THIS METHOD RUNS A TimerTask THAT IS RESPONSIBLE FOR RUNNING HE LIVE WATCH IN THE APPLICATION.
    private void displayCurrentTime() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            boolean startTime = true;
            @Override
            public void run() {
                if (startTime){
                    LocalDateTime current = LocalDateTime.now();
                    String time = FormatLocalDateTime.formatTimeWithTimeOfDate(current.toLocalTime());
                    Platform.runLater(()-> displayTimeLabel.setText(time));}
                if (signoutBtn.isDisabled()){
                    startTime = false;
                    timer.cancel();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000,1000);
    }
    public void signoutBtnAction() throws IOException {
        userAlert = new UserAlerts("LOGOUT", "ARE YOU SURE YOU WANT TO LOGOUT?", "please confirm your action to execute else cancel to abort.");
        if(userAlert.confirmationAlert()) {
            signoutBtn.setDisable(true);
            int returnedValue = modelOBJ.updateTrackerDateOnly();
            if (returnedValue > 0) {
                signoutBtn.getScene().getWindow().hide();
                Index.setUsernameFieldValue = activeUserLabel.getText();
                multiStagesOBJ.LoginForm();
            } else multiStagesOBJ.showFailedPrompt();
        }
    }

    /*******************************************************************************************************************
     OTHER METHODS IMPLEMENTATION*/
    public void setDashboardVariables () {
        try {
            Blob imageBlob = (Blob) fetchBusinessInfo().get(8);
            byte[] imageByte = imageBlob.getBytes(1, (int) imageBlob.length());
            OutputStream stream = new FileOutputStream("G:\\My Drive\\RegisterInn\\src\\main\\resources\\inn\\images\\placeholder.jpg");
            stream.write(imageByte);
            Image image = new Image("G:\\My Drive\\RegisterInn\\src\\main\\resources\\inn\\images\\placeholder.jpg");
            heroImageDisplay.setImage(image);
            String bsi_name = (String) fetchBusinessInfo().get(0);
            Date establishedDate = (Date) fetchBusinessInfo().get(6);
            LocalDate localDate = establishedDate.toLocalDate();
            dateLabel.setText(localDate.toString());
            Object about = fetchBusinessInfo().get(9);
            setBusinessNameLabel(bsi_name);
            setAboutField((String) about);
        }catch (NullPointerException ignored) {
            //e.printStackTrace();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }


    /****************************************************************
     * IMPLEMENTATION OF UPDATE LOGIN DETAILS.
     *****************************************************************/
    private void showUserProfilePane() {
        displayUsernamePane.setOnMouseClicked(event -> {
            if(userProfilePane.isVisible()) {
                event.consume();
            } else {
                newPasswordField.clear();
                confirmPasswordField.clear();
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000),userProfilePane);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1.0);
                userProfilePane.setVisible(true);
                fadeTransition.play();
            }
        });
    }
    private void hideUserProfilePane() {
        cancelProfileButton.setOnMouseClicked(event -> {
            Transition transition = new ScaleTransition(Duration.millis(1000), userProfilePane);
            transition.setInterpolator(Interpolator.EASE_BOTH);
            userProfilePane.setVisible(false);
            transition.play();
        });
    }
    @FXML void validatePasswordFields(KeyEvent event) {
        try {
            boolean emptyFields = newPasswordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty();
            boolean shortPassword = newPasswordField.getText().length() < 4 || confirmPasswordField.getText().length() < 4;
            boolean passwordMatch = Objects.equals(newPasswordField.getText(), confirmPasswordField.getText());
            saveProfileUpdateButton.setDisable(emptyFields || shortPassword || !passwordMatch);
            passwordStatusDisplay.setVisible(!passwordMatch);
        }catch (Exception ignored) {}
    }

    @FXML void updateProfileButtonClicked() {
        int userId = getUserIdByUsername(activeUserLabel.getText());
        String userPassword = newPasswordField.getText();
        String encryptedPassword = SysActivator.passwordEncryptor(userPassword);

        userAlert = new UserAlerts("UPDATE PASSWORD", "YOU HAVE REQUESTED TO CHANGE YOUR PASSWORD, DO YOU WANT TO PROCEED?",
                "please confirm your YES to change your password else CANCEL to abort");
        if(userAlert.confirmationAlert()) {
            int flag = updatePassword(userId, encryptedPassword, encryptedPassword);
            if (flag == 1) {
                notify.successNotification("PASSWORD UPDATE SUCCESSFUL", "Password updated successfully");
                newPasswordField.clear();
                confirmPasswordField.clear();
            } else {
                notify.errorNotification("UPDATE FAILED", "Your request to update your password failed, contact system administrator");
            }
        }
    }

}//END OF CLASS
