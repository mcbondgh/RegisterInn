package inn.controllers.settings;

import inn.models.MainModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SetPermissions extends MainModel implements Initializable {

    /*******************************************************************************************************************
     *                                  FXML NODE EJECTIONS
     *******************************************************************************************************************/
    @FXML private ScrollPane scrollPane;
    @FXML private AnchorPane innerAnchorPane;
    @FXML private Button saveButton;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private TitledPane settingsModule, reportsModule, bookingModule, accountingModule, inventoryModule, dashboardModule, messageBoxModule;
    @FXML private CheckBox settingsModuleChecker, bookingModuleChecker, accountingModuleChecker, reportsModuleChecker, inventoryModuleChecker;
    @FXML private CheckBox messageBoxModuleChecker, dashboardModuleChecker;
    @FXML private CheckBox generalViewControl, systemActivationViewControl, systemInfoViewControl,authenKeyViewControl, userLogsViewControl;
    @FXML private CheckBox rolesPermissionViewControl, setPermissionViewControl, addRolesViewControl;
    @FXML private CheckBox viewEmployees, addEmployeesViewControl, archivedViewControl;
    @FXML private CheckBox employeeProfileViewControl;
    @FXML private CheckBox updateLoginsViewControl;
    @FXML private  CheckBox payrollViewControl;
    @FXML private CheckBox stocksViewControl;
    @FXML private  CheckBox roomsViewControl, setRoomsViewControl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillRolesComboBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*******************************************************************************************************************
     *                                 TRUE OR FALSE STATEMENTS
     *******************************************************************************************************************/
    private boolean disableSettingsModule() {
        return settingsModuleChecker.isSelected();
    }
    private boolean disableReportsModule() {
        return reportsModuleChecker.isSelected();
    }
    private boolean disableAccountingModule() {
        return accountingModuleChecker.isSelected();
    }
    private boolean disableInventoryModule() {
        return inventoryModuleChecker.isSelected();
    }
    private boolean disableDashboardModule() {
        return dashboardModuleChecker.isSelected();
    }
    private boolean disableBookingModule() {
        return bookingModuleChecker.isSelected();
    }
    private boolean disableMessageBoxModule() {
        return messageBoxModuleChecker.isSelected();
    }



    /*******************************************************************************************************************
     *                                 ACTION EVENTS METHODS IMPLEMENTATION
     *******************************************************************************************************************/
    @FXML void settingsCheckerClicked() {
        settingsModule.setDisable(disableSettingsModule());
      }
    @FXML void reportsCheckerClicked() {
        reportsModule.setDisable(disableReportsModule());
    }
    @FXML void bookingCheckerClicked() {
        bookingModule.setDisable(disableBookingModule());
    }
    @FXML void accountingCheckerClicked() {
        accountingModule.setDisable(disableAccountingModule());
    }
    @FXML void inventoryCheckerClicked() {
        inventoryModule.setDisable(disableInventoryModule());
    }
    @FXML void dashboardCheckerClicked() {
        dashboardModule.setDisable(disableDashboardModule());
    }
    @FXML void messageBoxCheckerClicked() {
        messageBoxModule.setDisable(disableMessageBoxModule());
    }

    void fillRolesComboBox() throws SQLException {
        roleComboBox.setItems(fetchUserRoles());
    }





}//END OF CLASS
