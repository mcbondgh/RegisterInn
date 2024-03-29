package inn.controllers.settings;

import inn.StartInn;
import inn.fetchedData.DepartmentData;
import inn.fetchedData.IdTypesData;
import inn.fetchedData.RolesTypesData;
import inn.models.AddRolesAndDepartmentModel;
import inn.models.MainModel;
import inn.prompts.UserNotification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddRolesAndDepartments extends AddRolesAndDepartmentModel implements Initializable {

    MainModel mainModelOBJ = new MainModel();
    RolesTypesData rolesTypesOBJ = new RolesTypesData();
    UserNotification notificationOBJ = new UserNotification();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateRolesTable();
            populateDepartmentTable();
            populateIdTypeTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /******************************************* FXML NODE EJECTIONS ********************************/

    @FXML private TextField roleField, departmentField, idTypeField;

    @FXML private BorderPane settingsPane;
    @FXML private ScrollPane scrollPane;
    @FXML private Pane infoPane, setRolePane, setDepartmentPane, setIdTypePane;
    @FXML private Button deleteRoleButton, addRoleCatgButton, setPermissionBtn, addRoleButton;
    @FXML private Button addDepartmentButton, deleteDepartmentButton;
    @FXML private Button addIdTypeButton, deleteIdTypeButton;
    @FXML private CheckBox roleCheckBox, departmentCheckBox, idTypeCheckBox;
    @FXML private TableView<RolesTypesData> roleTableView;
    @FXML private TableColumn<RolesTypesData, String> roleName;
    @FXML private TableColumn<RolesTypesData, Integer> roleId;


    //FXML NODES FOR DEPARTMENT TABLE VIEW
    @FXML private TableView<DepartmentData> departmentsTableView;
    @FXML private TableColumn<DepartmentData, String> departmentName;
    @FXML private TableColumn<DepartmentData, Integer> departmentId;



    //FXML NODES FOR ID TYPE TABLE VIEW.
    @FXML private TableView<IdTypesData> idTypeTableView;
    @FXML private TableColumn<IdTypesData, String> idTypeID;
    @FXML private TableColumn<RolesTypesData, Integer> idTypeName;




    /*******************************************************************************************************************
     SPECIAL METHODS IMPLEMENTATION*/
    public void FlipView(String fxmlFileName) throws IOException {
              FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource(fxmlFileName));
              settingsPane.setCenter(fxmlLoader.load());
    }


    /******************************************* ACTION EVENT METHODS IMPLEMENTATION ********************************/
    //ENABLE OR DISABLE ROLES, DEPARTMENT AND ID_TYPE PANE IF CHECK BOXES ARE CHECKED. ELSE DISABLE
    @FXML void CheckedBoxAction() {
        if (roleCheckBox.isSelected()) {
            setRolePane.setDisable(false);
            setDepartmentPane.setDisable(true);
            setIdTypePane.setDisable(true);
        } else if (departmentCheckBox.isSelected()) {
            setDepartmentPane.setDisable(false);
            setRolePane.setDisable(true);
            setIdTypePane.setDisable(true);
        } else if(idTypeCheckBox.isSelected()) {
            setIdTypePane.setDisable(false);
            setRolePane.setDisable(true);
            setDepartmentPane.setDisable(true);
        } else {
            setDepartmentPane.setDisable(true);
            setRolePane.setDisable(true);
            setIdTypePane.setDisable(true);
        }
    }

    @FXML void validateRoleField() {
        addRoleButton.setDisable(checkRoleField());
        deleteRoleButton.setDisable(checkRoleField());
    }

    @FXML void validateDepartmentField() {
        addDepartmentButton.setDisable(checkDepartmentField());
        deleteDepartmentButton.setDisable(checkDepartmentField());
    }

    @FXML void validateIdTypeField() {
        addIdTypeButton.setDisable(checkIdTypeField());
        deleteIdTypeButton.setDisable(checkIdTypeField());
    }

    //FOR ROLE TABLE VIEW ONLY
    @FXML void deleteRoleButtonClicked() throws SQLException {
        String currentValue = roleField.getText().trim();
        if (deleteRoleType(currentValue) > 0) {
            notificationOBJ.successNotification("SUCCESSFUL", "Selected role type successfully deleted");
            roleField.clear();
            validateRoleField();
            roleTableView.getItems().clear();
            populateRolesTable();
        } else notificationOBJ.errorNotification("DELETE FAILED", "Unable to delete role type " + currentValue);
    }
    @FXML void selectedRoleNameValue() {
        String itemName = roleTableView.getSelectionModel().getSelectedItem().getRoleName();
        roleField.setText(itemName);
    }
    @FXML void addRoleButtonOnAction() throws SQLException {
        String currentValue = roleField.getText().trim();

        if (checkIfRoleExist(currentValue)) {
            notificationOBJ.informationNotification("ALREADY EXIST", "You can't add because role name already exist.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "please confirm to save new role.");
            alert.setTitle("CONFIRM TO SAVE");
            alert.setHeaderText("ARE YOU SURE YOU WANT TO SAVE " + currentValue + " as new role type?");
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().remove(ButtonType.OK);
            if(alert.showAndWait().get().equals(ButtonType.YES)) {
                if (addNewRoleType(currentValue) > 0) {
                    notificationOBJ.successNotification("SUCCESSFUL", "New role type successfully added.");
                    roleField.clear();
                    validateRoleField();
                    roleTableView.getItems().clear();
                    populateRolesTable();
                } else  notificationOBJ.errorNotification("INSERT FAILED", "Unable to add role type " + currentValue);
            }
        }
    }

    //FOR DEPARTMENT TABLE ONLY
    @FXML void refreshDepartmentTable() {
        populateDepartmentTable();
    }

    @FXML void deleteDepartmentButtonClicked() throws SQLException {
        String currentValue = departmentField.getText().trim();
        if (deleteDesignation(currentValue) > 0) {
            notificationOBJ.successNotification("SUCCESSFUL", "Selected department type successfully deleted.");
            departmentField.clear();
            validateDepartmentField();
            departmentsTableView.getItems().clear();
            refreshDepartmentTable();
        } else notificationOBJ.errorNotification("DELETE FAILED", "Unable to delete department type " + currentValue);
    }

    @FXML void selectedDepartmentNameValue() {
        String selectedName = departmentsTableView.getSelectionModel().getSelectedItem().getDepartmentName();
        departmentField.setText(selectedName);
    }

    @FXML void addDepartmentButtonOnAction() throws SQLException {
        String currentValue = departmentField.getText().trim();

        if (checkIfDepartmentExist(currentValue)) {
            notificationOBJ.informationNotification("ALREADY EXIST", "You can't add because department name already exist.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "please confirm to save new role.");
            alert.setTitle("CONFIRM TO SAVE");
            alert.setHeaderText("ARE YOU SURE YOU WANT TO SAVE " + currentValue + " as new department type?");
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().remove(ButtonType.OK);
            if(alert.showAndWait().get().equals(ButtonType.YES)) {
                if (addNewDepartment(currentValue) > 0) {
                    notificationOBJ.successNotification("SUCCESSFUL", "New Department successfully added.");
                    departmentField.clear();
                    validateDepartmentField();
                    departmentsTableView.getItems().clear();
                    populateDepartmentTable();
                } else  notificationOBJ.errorNotification("INSERT FAILED", "Unable to add department type " + currentValue);
            }
        }
    }

    //FOR ID TYPE TABLE ONLY
    @FXML void refreshIdTypeTable() {
        populateIdTypeTable();
    }
    @FXML void addIdTypeButtonOnAction() throws SQLException {
        String currentValue = idTypeField.getText().trim();

        if (checkIfIdTypeExist(currentValue)) {
            notificationOBJ.informationNotification("ALREADY EXIST", "You can't add because Id Type already exist.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "please confirm to save new role.");
            alert.setTitle("CONFIRM TO SAVE");
            alert.setHeaderText("ARE YOU SURE YOU WANT TO SAVE " + currentValue + " as new Id type?");
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().remove(ButtonType.OK);
            if(alert.showAndWait().get().equals(ButtonType.YES)) {
                if (addNewIdType(currentValue) > 0) {
                    notificationOBJ.successNotification("SUCCESSFUL", "New Id type successfully added.");
                    idTypeField.clear();
                    validateIdTypeField();
                    idTypeTableView.getItems().clear();
                    populateIdTypeTable();
                } else  notificationOBJ.errorNotification("INSERT FAILED", "Unable to add Id type " + currentValue);
            }
        }
    }

    @FXML void selectedIdTypeNameValue() {
        String selectedName = idTypeTableView.getSelectionModel().getSelectedItem().getIdTypeName();
        idTypeField.setText(selectedName);
    }

    @FXML void deleteIdTypeButtonClicked() throws SQLException {
        String currentValue = idTypeField.getText().trim();
        if (deleteIdType(currentValue) > 0) {
            notificationOBJ.successNotification("SUCCESSFUL", "Selected Id type successfully deleted.");
            idTypeField.clear();
            validateIdTypeField();
            idTypeTableView.getItems().clear();
            populateIdTypeTable();
        } else notificationOBJ.errorNotification("DELETE FAILED", "Unable to delete Id type " + currentValue);
    }


    @FXML void addCategoryBtnClicked() {
        settingsPane.setCenter(scrollPane);
    }

    @FXML void HoverEffectForCategoryBtn() {
        addRoleCatgButton.setStyle("-fx-background-color: #037dab; -fx-text-fill:#fff");
    }
    @FXML void mouseExitedForCategoryBtn() {
        addRoleCatgButton.setStyle("-fx-background-color:none; -fx-text-fill: #037dab; -fx-border-color: #037dab");
    }
    @FXML void setPermissionBtnClicked() throws IOException {
       FlipView("Modules/settings/setPermissions.fxml");
    }
    @FXML void hoverEffectForSetPermissionBtn() {
        setPermissionBtn.setStyle("-fx-background-color: #037dab; -fx-text-fill:#fff");
    }
    @FXML void mouseExitedForSetPermissionBtn() {
        setPermissionBtn.setStyle("-fx-background-color:none; -fx-text-fill: #037dab; -fx-border-color: #037dab");
    }


    /******************************************* TRUE OR FALSE METHODS ********************************/
    boolean checkRoleField() {
        return roleField.getText().isBlank();
    }
    boolean checkIdTypeField() {return idTypeField.getText().isBlank();}
    boolean checkDepartmentField() {return departmentField.getText().isBlank();}
    boolean checkIfRoleExist(String textFieldValue) throws SQLException {
        boolean flag =  false;
        for (String item : mainModelOBJ.fetchUserRoles()) {
            if (Objects.equals(item.toLowerCase(), textFieldValue.toLowerCase())) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    boolean checkIfDepartmentExist(String textFieldValue) throws SQLException {
        boolean flag =  false;
        for (String item : mainModelOBJ.fetchDesignation()) {
            if (Objects.equals(item.toLowerCase(), textFieldValue.toLowerCase())) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    boolean checkIfIdTypeExist(String textFieldValue) throws SQLException {
        boolean flag =  false;
        for (IdTypesData item : mainModelOBJ.fetchIdTypes()) {
            if (Objects.equals(item.getIdTypeName().toLowerCase(), textFieldValue.toLowerCase())) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    /******************************************* OTHER METHODS IMPLEMENTATION ********************************/
    void populateRolesTable() throws SQLException {
        roleName.setCellValueFactory( new PropertyValueFactory<>("roleName"));
        roleId.setCellValueFactory(new PropertyValueFactory<>("roleId"));
        ObservableList<RolesTypesData> roleValues = FXCollections.observableArrayList();

        for (int i = 0; i < mainModelOBJ.fetchUserRoles().size(); i++) {
            roleValues.add(new RolesTypesData(i + 1, mainModelOBJ.fetchUserRoles().get(i)));
            roleTableView.setItems(roleValues);
        }
    }

    void populateIdTypeTable() {
        idTypeName.setCellValueFactory( new PropertyValueFactory<>("idTypeName"));
        idTypeID.setCellValueFactory(new PropertyValueFactory<>("idTypeID"));
        idTypeTableView.setItems(fetchIdTypes());
    }
    void populateDepartmentTable() {
        departmentName.setCellValueFactory( new PropertyValueFactory<>("departmentName"));
        departmentId.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        ObservableList<DepartmentData> roleValues = FXCollections.observableArrayList();

        for (int i = 0; i < mainModelOBJ.fetchDesignation().size(); i++) {
            roleValues.add(new DepartmentData(i + 1, mainModelOBJ.fetchDesignation().get(i)));
            departmentsTableView.setItems(roleValues);
        }
    }








}//END OF CLASS
