package inn.Controllers.settings;

import inn.StartInn;
import inn.database.DbConnection;
import inn.models.AddRolesAndDepartmentModel;
import inn.prompts.UserNotification;
import inn.tableViews.DepartmentTableView;
import inn.tableViews.IdTypeTableView;
import inn.tableViews.RolesTypesTableView;
import inn.tableViews.RoomsCategoryTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddRolesAndDepartments extends AddRolesAndDepartmentModel implements Initializable {

    DbConnection dbConnectionOBJ = new DbConnection();
    RolesTypesTableView rolesTypesOBJ = new RolesTypesTableView();
    UserNotification notificationOBJ = new UserNotification();


    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateRolesTable();
            populateDepartmentTable();
            populateIdTypeTable();
            populateRoomsCategoryTable();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /******************************************* FXML NODE EJECTIONS ********************************/

    @FXML private TextField roleField, departmentField, idTypeField, roomsCategoryField,priceField;

    @FXML private BorderPane settingsPane;
    @FXML private ScrollPane scrollPane;
    @FXML private Pane infoPane, setRolePane, setDepartmentPane, setIdTypePane, setRoomsCatPannel;
    @FXML private Button deleteRoleButton, addRoleCatgButton, setPermissionBtn, addRoleButton;
    @FXML private Button addDepartmentButton, deleteDepartmentButton, addRoomCategoryBtn, deleteRoomsCategoryBtn;
    @FXML private Button addIdTypeButton, deleteIdTypeButton;
    @FXML private CheckBox roleCheckBox, departmentCheckBox, idTypeCheckBox, roomCategoriesCheckBox;
    @FXML private TableView<RolesTypesTableView> roleTableView;
    @FXML private TableColumn<RolesTypesTableView, String> roleName;
    @FXML private TableColumn<RolesTypesTableView, Integer> roleId;


    //FXML NODES FOR DEPARTMENT TABLE VIEW
    @FXML private TableView<DepartmentTableView> departmentsTableView;
    @FXML private TableColumn<DepartmentTableView, String> departmentName;
    @FXML private TableColumn<DepartmentTableView, Integer> departmentId;


    //FXML NODES FOR ID TYPE TABLE VIEW.
    @FXML private TableView<IdTypeTableView> idTypeTableView;
    @FXML private TableColumn<IdTypeTableView, String> idTypeID;
    @FXML private TableColumn<RolesTypesTableView, Integer> idTypeName;


    //FXML NODES FOR ROOMS CATEGORY TABLE VIEW
    @FXML private TableView<RoomsCategoryTableView> roomsCategoryTableView;
    @FXML private TableColumn<RoomsCategoryTableView, Integer> roomsCatId;
    @FXML private TableColumn<RoomsCategoryTableView, String> roomsCateName;
    @FXML private TableColumn<RolesTypesTableView, Double> priceColumn;


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
            setRoomsCatPannel.setDisable(true);
        } else if (departmentCheckBox.isSelected()) {
            setDepartmentPane.setDisable(false);
            setRolePane.setDisable(true);
            setIdTypePane.setDisable(true);
            setRoomsCatPannel.setDisable(true);
        } else if(idTypeCheckBox.isSelected()) {
            setIdTypePane.setDisable(false);
            setRolePane.setDisable(true);
            setDepartmentPane.setDisable(true);
            setRoomsCatPannel.setDisable(true);
        } else if(roomCategoriesCheckBox.isSelected()) {
            setRoomsCatPannel.setDisable(false);
            setIdTypePane.setDisable(true);
            setRolePane.setDisable(true);
            setDepartmentPane.setDisable(true);
        } else {
            setDepartmentPane.setDisable(true);
            setRolePane.setDisable(true);
            setIdTypePane.setDisable(true);
            setRoomsCatPannel.setDisable(true);
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
@FXML void validateRoomsCategoryField() {
        priceField.setOnKeyReleased(KeyEvent ->  {
            if(!(KeyEvent.getCode().isDigitKey() || KeyEvent.getCode().equals(KeyCode.BACK_SPACE) || KeyEvent.getCode().equals(KeyCode.PERIOD))) {
                priceField.clear();
            }
        });
        addRoomCategoryBtn.setDisable(checkRoomsCategoryField() || checkPriceField());
        deleteRoomsCategoryBtn.setDisable(checkRoomsCategoryField() || checkPriceField());

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

    @FXML void selectedRoomsCategoryNameValue() {
        String selectedCategoryName = roomsCategoryTableView.getSelectionModel().getSelectedItem().getRoomsCateName();
        double selectedPrice= roomsCategoryTableView.getSelectionModel().getSelectedItem().getPrice();

        roomsCategoryField.setText(selectedCategoryName);
        priceField.setText(String.valueOf(selectedPrice));
    }
    @FXML void addRoomsCategoryButtonOnAction() throws SQLException {
        String currentValue = roomsCategoryField.getText().trim();
        double currentPrice = Double.parseDouble(priceField.getText());

        if (checkIfRoomCategoryExist(currentValue)) {
            notificationOBJ.informationNotification("ALREADY EXIST", "Room Category name already exist.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "please confirm to save new category name.");
            alert.setTitle("CONFIRM TO SAVE");
            alert.setHeaderText("ARE YOU SURE YOU WANT TO SAVE " + currentValue + " AS A ROOM CATEGORY TYPE?");
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().remove(ButtonType.OK);
            if(alert.showAndWait().get().equals(ButtonType.YES)) {
                if (addNewRoomsCategory(currentValue, currentPrice) > 0) {
                    notificationOBJ.successNotification("SUCCESSFUL", "New Room Category successfully added.");
                    roomsCategoryTableView.getItems().clear();
                    roomsCategoryField.clear();
                    validateRoomsCategoryField();
                    populateRoomsCategoryTable();
                } else  notificationOBJ.errorNotification("INSERT FAILED", "Unable to add room category type " + currentValue);
            }
        }
    }

    @FXML void deleteRoomsCategoryButtonClicked() {
        String currentValue = roomsCategoryField.getText().trim();
        if (deleteRoomsCategory(currentValue) > 0) {
            notificationOBJ.successNotification("SUCCESSFUL", "Selected room category successfully deleted.");
            roomsCategoryField.clear();
            validateRoomsCategoryField();
            roomsCategoryTableView.getItems().clear();
            populateRoomsCategoryTable();
        } else notificationOBJ.errorNotification("DELETE FAILED", "Unable to delete " + currentValue);
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
       FlipView("Modules/Settings/setPermissions.fxml");
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
    boolean checkRoomsCategoryField() {return roomsCategoryField.getText().isBlank();}
    boolean checkPriceField() {return priceField.getText().isBlank();}
    boolean checkIfRoleExist(String textFieldValue) throws SQLException {
        boolean flag =  false;
        for (String item : dbConnectionOBJ.fetchUserRoles()) {
            if (Objects.equals(item.toLowerCase(), textFieldValue.toLowerCase())) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    boolean checkIfDepartmentExist(String textFieldValue) throws SQLException {
        boolean flag =  false;
        for (String item : dbConnectionOBJ.fetchDesignation()) {
            if (Objects.equals(item.toLowerCase(), textFieldValue.toLowerCase())) {
                flag = true;
                break;
            }
        }
        return flag;
    }


    boolean checkIfIdTypeExist(String textFieldValue) throws SQLException {
        boolean flag =  false;
        for (String item : dbConnectionOBJ.fetchIdTypes()) {
            if (Objects.equals(item.toLowerCase(), textFieldValue.toLowerCase())) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    boolean checkIfRoomCategoryExist(String textFieldValue) throws SQLException {
        boolean flag =  false;
        for (RoomsCategoryTableView item : dbConnectionOBJ.fetchCategories()) {
            if (Objects.equals(item.getRoomsCateName().toLowerCase(), textFieldValue.toLowerCase())) {
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
        ObservableList<RolesTypesTableView> roleValues = FXCollections.observableArrayList();

        for (int i = 0; i < dbConnectionOBJ.fetchUserRoles().size(); i++) {
            roleValues.add(new RolesTypesTableView(i + 1, dbConnectionOBJ.fetchUserRoles().get(i)));
            roleTableView.setItems(roleValues);
        }
    }

    void populateIdTypeTable() {
        idTypeName.setCellValueFactory( new PropertyValueFactory<>("idTypeName"));
        idTypeID.setCellValueFactory(new PropertyValueFactory<>("idTypeID"));
        ObservableList<IdTypeTableView> roleValues = FXCollections.observableArrayList();

        for (int i = 0; i < dbConnectionOBJ.fetchIdTypes().size(); i++) {
            roleValues.add(new IdTypeTableView(i + 1, dbConnectionOBJ.fetchIdTypes().get(i)));
            idTypeTableView.setItems(roleValues);
        }
    }

    void populateDepartmentTable() {
        departmentName.setCellValueFactory( new PropertyValueFactory<>("departmentName"));
        departmentId.setCellValueFactory(new PropertyValueFactory<>("departmentId"));
        ObservableList<DepartmentTableView> roleValues = FXCollections.observableArrayList();

        for (int i = 0; i < dbConnectionOBJ.fetchDesignation().size(); i++) {
            roleValues.add(new DepartmentTableView(i + 1, dbConnectionOBJ.fetchDesignation().get(i)));
            departmentsTableView.setItems(roleValues);
        }
    }

    void populateRoomsCategoryTable() {
        roomsCateName.setCellValueFactory( new PropertyValueFactory<>("roomsCateName"));
        roomsCatId.setCellValueFactory(new PropertyValueFactory<>("roomsCatId"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        ObservableList<RoomsCategoryTableView> roleValues = FXCollections.observableArrayList();

        for (RoomsCategoryTableView item : fetchCategories()) {
            roleValues.add(new RoomsCategoryTableView(item.getRoomsCatId(), item.getRoomsCateName(), item.getPrice()));
        }
        roomsCategoryTableView.setItems(roleValues);

    }








}//END OF CLASS
