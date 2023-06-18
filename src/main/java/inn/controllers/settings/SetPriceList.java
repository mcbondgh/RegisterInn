package inn.controllers.settings;

import com.jfoenix.controls.JFXListView;
import inn.fetchedData.OvertimeData;
import inn.fetchedData.RoomPricesData;
import inn.models.ManageRoomsModel;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class SetPriceList extends ManageRoomsModel implements Initializable {
    public SetPriceList(){

    }
    UserNotification notificationOBJ = new UserNotification();
    UserAlerts alerts;


    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        populateRoomsCategoryTable();
        overtimeValues();
        populateOvertimeListView();
    }

    /************************************************************************************************
     *      EJECTION OF FXML FILE NODES
     */
    @FXML private CheckBox roomCategoriesCheckBox;
    @FXML private Button addRoomCategoryBtn, deleteRoomsCategoryBtn, addOverTimeButton;
    @FXML private Pane setRoomsCatPannel;
    @FXML private ComboBox<String> overtimeValueSelector;
    @FXML private JFXListView<String> overtimeListViewer;
    @FXML private TextField roomsCategoryField,priceField, timeField, overtimeAmount;


    //FXML NODES FOR ROOMS CATEGORY TABLE VIEW
    @FXML private TableView<RoomPricesData> roomsCategoryTableView;
    @FXML private TableColumn<RoomPricesData, Integer> roomsCatId;
    @FXML private TableColumn<RoomPricesData, String> roomsCateName;
    @FXML private TableColumn<RoomPricesData, Double> priceColumn;
    @FXML private TableColumn<RoomPricesData, Integer> allotedTimeColumn;


    /************************************************************************************************
     *   TRUE OR FALSE STATEMENTS
     */
    boolean checkRoomsCategoryField() {return roomsCategoryField.getText().isBlank();}
    boolean checkPriceField() {return priceField.getText().isBlank();}
    boolean checkTimeField() {
        return timeField.getText().isBlank();
    }
    boolean checkIfRoomCategoryExist(String textFieldValue) throws SQLException {
        boolean flag =  false;
        for (RoomPricesData item : fetchRoomPrices()) {
            if (Objects.equals(item.getRoomsCateName().toLowerCase(), textFieldValue.toLowerCase())) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    boolean isRoomsCategoryChecked() {
        return roomCategoriesCheckBox.isSelected();
    }




    void overtimeValues() {
        overtimeValueSelector.getItems().add("Between 0-1Hour");
        overtimeValueSelector.getItems().add("Between 1-3Hours");
        overtimeValueSelector.getItems().add("Above 3 Hours");
    }

    private void populateOvertimeListView() {
        overtimeListViewer.getItems().clear();
        for (OvertimeData item : getOvertimeValues()) {
            overtimeListViewer.getItems().add(item.getTitle());
        }
    }

    /************************************************************************************************
     *    ACTION EVENT METHODS IMPLEMENTATION
     */
    @FXML void validateRoomsCategoryField() {
        priceField.setOnKeyReleased(KeyEvent ->  {
            if(!(KeyEvent.getCode().isDigitKey() || KeyEvent.getCode().equals(KeyCode.BACK_SPACE) || KeyEvent.getCode().equals(KeyCode.PERIOD))) {
                priceField.deletePreviousChar();
            }
        });
        addRoomCategoryBtn.setDisable(checkRoomsCategoryField() || checkPriceField() || checkTimeField());
        deleteRoomsCategoryBtn.setDisable(checkRoomsCategoryField() || checkPriceField() || checkTimeField());
    }
    @FXML void validateTimeField() {
        timeField.setOnKeyReleased(KeyEvent ->  {
            if(!(KeyEvent.getCode().isDigitKey() || KeyEvent.getCode().equals(KeyCode.BACK_SPACE) || KeyEvent.getCode().equals(KeyCode.PERIOD))) {
                timeField.deletePreviousChar();
            }
        });
        addRoomCategoryBtn.setDisable(checkRoomsCategoryField() || checkPriceField() || checkTimeField());
        deleteRoomsCategoryBtn.setDisable(checkRoomsCategoryField() || checkPriceField() || checkTimeField());
    }
    @FXML void selectedRoomsCategoryNameValue() {
        try {
            String selectedCategoryName = roomsCategoryTableView.getSelectionModel().getSelectedItem().getRoomsCateName();
            double selectedPrice= roomsCategoryTableView.getSelectionModel().getSelectedItem().getPrice();
            int allotedTime = Integer.parseInt(roomsCategoryTableView.getSelectionModel().getSelectedItem().getAllotedTime());

            roomsCategoryField.setText(selectedCategoryName);
            priceField.setText(String.valueOf(selectedPrice));
            timeField.setText(String.valueOf(allotedTime));
        }catch (NumberFormatException ex) {
            notificationOBJ.informationNotification("FILL ALL", "Please fill all text fields before you save.");
        }
    }
    @FXML void addRoomsCategoryButtonOnAction() throws SQLException {
        try {
            String currentValue = roomsCategoryField.getText().trim();
            double currentPrice = Double.parseDouble(priceField.getText());
            int allotedTime = Integer.parseInt(timeField.getText());

            if (checkIfRoomCategoryExist(currentValue)) {
                notificationOBJ.informationNotification("ALREADY EXIST", "Room Category name already exist.");
            } else {
                alerts = new UserAlerts("CONFIRM TO SAVE", "ARE YOU SURE YOU WANT TO SAVE " + currentValue + " AS A ROOM CATEGORY TYPE?", "please confirm to save new category name.");
                if(alerts.confirmationAlert()) {
                    if (addNewRoomsCategory(currentValue, currentPrice, allotedTime) > 0) {
                        notificationOBJ.successNotification("SUCCESSFUL", "New Room Category successfully added.");
                        roomsCategoryTableView.getItems().clear();
                        roomsCategoryField.clear();
                        validateRoomsCategoryField();
                        populateRoomsCategoryTable();
                    } else  notificationOBJ.errorNotification("INSERT FAILED", "Unable to add room category type " + currentValue);
                }
            }
        }catch (NumberFormatException ex) {
            notificationOBJ.informationNotification("FILL ALL", "Please fill all text fields before you save.");
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
    @FXML private void CheckedBoxAction(ActionEvent event) {
        setRoomsCatPannel.setDisable(isRoomsCategoryChecked());
    }
    void populateRoomsCategoryTable() {
        roomsCateName.setCellValueFactory( new PropertyValueFactory<>("roomsCateName"));
        roomsCatId.setCellValueFactory(new PropertyValueFactory<>("roomsCatId"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        allotedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("allotedTime"));
        ObservableList<RoomPricesData> roleValues = FXCollections.observableArrayList();

        for (RoomPricesData item : fetchRoomPrices()) {
            roleValues.add(new RoomPricesData(item.getRoomsCatId(), item.getRoomsCateName(), item.getPrice(), item.getAllotedTime()));
        }
        roomsCategoryTableView.setItems(roleValues);
    }
    public void inputCashValue(KeyEvent event) {
        addOverTimeButton.setDisable(overtimeAmount.getText().isBlank());
        if(!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.PERIOD)) {
            overtimeAmount.deletePreviousChar();
        }
    }
    @FXML void addOvertimeButtonClicked() {
        String overtimeTitle = overtimeValueSelector.getValue();
        double overtimeCost = Double.parseDouble(overtimeAmount.getText());
        String buttonText = addOverTimeButton.getText();
        if(Objects.equals(buttonText, "Update")) {
            alerts = new UserAlerts("UPDATE OVERTIME", "DO YOU WISH TO UPDATED SELECTED OVERTIME?");
            if (alerts.confirmationAlert()) {

            }
        } else {
            alerts = new UserAlerts("SAVE OVERTIME", "DO YOU WANT TO SAVE OVER TIME VALUE?", "confirm to save else CANCEL to abort.");
            if (alerts.confirmationAlert()) {
                if (saveOvertimeValue(overtimeTitle, overtimeCost) > 0) {
                    notificationOBJ.successNotification("SUCCESSFULLY SAVED", "Overtime has saved successfully.");
                    overtimeAmount.clear();
                    addOverTimeButton.setDisable(true);
                } else {
                    notificationOBJ.errorNotification("FAILED TO SAVE OVERTIME VALUE", "Unable to save OVERTIME VALUE");
                }//end of inner if-statement
            }//end of outer if-statement
        }

    }

    @FXML void overtimeTitleSelected() {
        String title = overtimeListViewer.getSelectionModel().getSelectedItem();
        for (OvertimeData item : getOvertimeValues()) {
            if (Objects.equals(item.getTitle(), title)) {
                overtimeAmount.setText(String.valueOf(item.getCost()));
                addOverTimeButton.setText("Update"); addOverTimeButton.setDisable(false);
                overtimeValueSelector.setValue(item.getTitle());
                break;
            }
        }
    }







}//END OF CLASS
