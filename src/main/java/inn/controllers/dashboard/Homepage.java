package inn.Controllers.dashboard;

import inn.Controllers.settings.General;
import inn.StartInn;
import inn.database.DbConnection;
import inn.multiStage.MultiStages;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Homepage implements Initializable {

    /******************************************> OBJECT INSTANTIATION FIELD <*******************************************/
    MultiStages multiStages = new MultiStages();
    DbConnection connector = new DbConnection();

    ButtonType YES = ButtonType.YES;
    ButtonType NO = ButtonType.NO;




    /******************************************> FXML OBJECTS  <*******************************************/
    @FXML private Button dashboardBtn, signoutBtn, inventoryBtn, bookingBtn, accountingBtn;
    @FXML private MenuButton reportsBtn, settingsBtn;
    @FXML private MenuItem generalBtn, manageRolesBtn, updateBtn, humanResourceBtn;
    @FXML private Label businessNameLabel, activeUserLabel;
    @FXML private TextArea aboutField;
    @FXML private BorderPane displayContainer;

    public static String label;


    General general = new General();

    /*******************************************************************************************************************
     IMPLEMENTATION OF INITIALIZER METHOD*/
    public void initialize(URL location, ResourceBundle resourceBundle) {
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
     ACTION EVENT METHODS IMPLEMENTATION*/
    public void dashboadBtnAction() throws IOException {
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
        FlipView("Modules/settings/user-roles.fxml");
    }
    public void updateBtnAction() throws IOException {
        multiStages.UpdateLoginDetails();
    }
    public void humanResourceBtnAction() throws IOException {
        FlipView("Modules/settings/humanResource.fxml");
    }



    /*******************************************************************************************************************
     SPECIAL METHODS IMPLEMENTATION*/
    public void FlipView(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource(fxmlFileName));
        displayContainer.setCenter(fxmlLoader.load());
    }

    public void signoutBtnAction() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("ARE YOU SURE YOU WANT TO LOGOUT?");
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().add(YES);
//        Optional<ButtonType> choose = alert.showAndWait();
        if(alert.showAndWait().get() == YES) {
            signoutBtn.getScene().getWindow().hide();
            multiStages.LoginForm();
        }
    }



    /*******************************************************************************************************************
     OTHER METHODS IMPLEMENTATION*/

    public void setDashboardVariables () {
        String bsi_name = (String) connector.fetchBusinessInfo().get(0);
        Object about = connector.fetchBusinessInfo().get(7);
        setBusinessNameLabel(bsi_name);
        setAboutField((String) about);
    }

}
