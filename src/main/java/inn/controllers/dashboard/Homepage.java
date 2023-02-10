package inn.Controllers.dashboard;

import inn.StartInn;
import inn.database.DbConnection;
import inn.models.InnActivationModel;
import inn.multiStage.MultiStages;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.Camera;
import javafx.scene.canvas.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Homepage extends DbConnection implements Initializable{

    /******************************************> OBJECT INSTANTIATION FIELD <*******************************************/
    MultiStages multiStagesOBJ = new MultiStages();
    InnActivationModel modelOBJ = new InnActivationModel();

    ButtonType YES = ButtonType.YES;


    /******************************************> FXML OBJECTS  <*******************************************/
    @FXML private Button dashboardBtn, signoutBtn, inventoryBtn, bookingBtn, accountingBtn;
    @FXML private MenuButton reportsBtn, settingsBtn;
    @FXML private MenuItem generalBtn, manageRolesBtn, updateBtn, humanResourceBtn, employeeProfileBtn, payRoleButton, manageRoomsButton, manageStocksButton;
    @FXML private Label businessNameLabel, activeUserLabel, dateLabel, counter;
    @FXML private TextArea aboutField;
    @FXML private BorderPane displayContainer;
    @FXML private ImageView heroImageDisplay;

    public static String label;

    public static String setActiveUserNane;
    public static int counterValue;

//    General general = new General();
    /*******************************************************************************************************************
     IMPLEMENTATION OF INITIALIZER METHOD*/
    public void initialize(URL location, ResourceBundle resourceBundle) {
        activeUserLabel.setText(setActiveUserNane);
        setDashboardVariables();
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
        displayContainer.setCenter(fxmlLoader.load());
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

    @FXML public void messageBoxButtonOnAction() throws IOException {
        FlipView("Modules/settings/manageRooms.fxml");
    }

    @FXML void managePayrollButtonClick() throws IOException {
        FlipView("Modules/settings/payroll.fxml");
    }
    @FXML void manageStocksButtonOnAction() throws IOException {
        FlipView("Modules/settings/manageStocks.fxml");
    }
    @FXML void countVALUE() {

    }

    public void signoutBtnAction() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("ARE YOU SURE YOU WANT TO LOGOUT?");
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(YES);
//        Optional<ButtonType> choose = alert.showAndWait();
        if(alert.showAndWait().get() == YES) {
            int returnedValue = modelOBJ.updateTrackerDateOnly();
            if (returnedValue > 0) {
                signoutBtn.getScene().getWindow().hide();
                multiStagesOBJ.LoginForm();
            } else multiStagesOBJ.showFailedPrompt();
        }
    }
    public void updateBtnAction() throws IOException {
        multiStagesOBJ.UpdateLoginDetails();
    }


    /*******************************************************************************************************************
     OTHER METHODS IMPLEMENTATION*/
    public void setDashboardVariables () {
        try {
            Blob imageBlob = (Blob) fetchBusinessInfo().get(8);
            byte[] imageByte = imageBlob.getBytes(1, (int) imageBlob.length());
            OutputStream stream = new FileOutputStream("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\placeholder.jpg");
            stream.write(imageByte);
            Image image = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\placeholder.jpg");
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


}
