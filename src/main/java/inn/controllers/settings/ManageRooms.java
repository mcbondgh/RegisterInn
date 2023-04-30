package inn.controllers.settings;

import inn.StartInn;
import inn.models.ManageRoomsModel;
import inn.prompts.UserNotification;
import inn.tableViews.ManageRoomsData;
import inn.tableViews.RoomPricesData;
import inn.tableViews.RoomsData;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageRooms extends ManageRoomsModel implements Initializable {

    //CLASS OBJECTS / CLASS INSTANTIATION FIELD;
    UserNotification notificationOBJ = new UserNotification();
    ManageRoomsData manageRoomsTableViewOBJ;
    RoomPricesData roomsCategoryTableViewOBJ;

    /*******************************************************************************************************************
     *                                              FXLM NODE EJECTION.
     *******************************************************************************************************************/
    @FXML private AnchorPane manageRoomsPane, addRoomsPane;
    @FXML private BorderPane roomsBorderPane, priceListPane;
    @FXML private Pane controlPanel;
    @FXML private Button priceListBtn, categoryBtn, addRoomBtn, updateRoomButton;
    @FXML private Button saveRoomButton, deleteRoomButton;
    @FXML private TextField roomNumberField, roomCategoryField;
    @FXML private ComboBox<String> categoryComboBox;

    /*******************************************************************************************************************
     ROOMS TABLEVIEW NODES EJECTION */
    @FXML
    private TableView<ManageRoomsData> roomsTableView;
    @FXML private TableColumn<ManageRoomsData, Integer> itemId;
    @FXML private TableColumn<ManageRoomsData, String> roomNumber;
    @FXML private TableColumn<ManageRoomsData, String> roomCategory;
    @FXML private TableColumn<ManageRoomsData, String> priceColumn;
    @FXML private TableColumn<ManageRoomsData, CheckBox> action;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateCategoryComboBox();
        fillRoomsTableView();

    }


    /*******************************************************************************************************************
     SPECIAL METHODS IMPLEMENTATION*/
    public void FlipView(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource(fxmlFileName));
        roomsBorderPane.setCenter(fxmlLoader.load());
    }


    /*******************************************************************************************************************
     *                               IMPLEMENTATION OF CLICK BUTTON EVENTS.
     *******************************************************************************************************************/
    @FXML void setRoomsButtonClicked() throws IOException {
        roomsBorderPane.setCenter(addRoomsPane);
        //FlipView("Modules/rooms_view/addRoomDisplay.fxml");
    }
    @FXML void priceListBtnClicked() throws IOException {
        FlipView("Modules/rooms_view/priceListDisplay.fxml");
    }
    @FXML void HoverEffectForSetRoomBtn() {
        addRoomBtn.setStyle("-fx-background-color:#1f9ec0; -fx-text-fill:#fff;");
    }
    @FXML void mouseExitedSetRoomBtn() {
        addRoomBtn.setStyle("-fx-background-color:#ffff; -fx-text-fill:#1f9ec0; -fx-border-color:#1f9ec0");
    }
    @FXML void hoverEffectForPriceListBtn() {
        priceListBtn.setStyle("-fx-background-color:#1f9ec0; -fx-text-fill:#fff");
    }
    @FXML void mouseExitedForPriceListBtn() {
        priceListBtn.setStyle("-fx-background-color:#ffff; -fx-text-fill:#1f9ec0; -fx-border-color:#1f9ec0");
    }

    /*******************************************************************************************************************
         *                      IMPLEMENTATION OF ACTION EVENT METHODS.
     *******************************************************************************************************************/

    //THIS METHOD RETURNS A FULL ROW SELECTION WHEN AN ITEM IS SELECTED IN THE Rooms Table View.
    @FXML void tableValueSelected() {
        deleteRoomButton.setDisable(roomsTableView.getItems().isEmpty());
        updateRoomButton.setDisable(roomsTableView.getItems().isEmpty());
    }
    @FXML void deleteRoomClicked() throws SQLException {
        try {
                byte roomNo = (byte)roomsTableView.getSelectionModel().getSelectedItem().getRoomId();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"please confirm to execute else cancel to abort.");
                alert.setTitle("DELETE ROOM");
                alert.setHeaderText("ARE YOU SURE YOU WANT TO DELETE ROOM WITH ID: " + roomNo +"?");
                alert.getButtonTypes().remove(ButtonType.OK);
                alert.getButtonTypes().add(ButtonType.YES);
                if(alert.showAndWait().get().equals(ButtonType.YES)) {
                    boolean queryResult = deleteRoom(roomNo);
                    if(queryResult) {
                        notificationOBJ.successNotification("DELETE SUCCESSFUL", "Room No." + roomNo + " Successfully Deleted.");
                        fillRoomsTableView();
                    } else {
                        notificationOBJ.errorNotification("DELETE FAILED", "Delete Operation Failed For Room With No. " + roomNo);
                    }
                }
        } catch (NullPointerException exception) {
            notificationOBJ.errorNotification("EMPTY ROLE", "You have not made any selection yet, please select a room to delete.");
        }
    }
    @FXML void saveRoomButtonClicked() throws SQLException {
        String roomNo = roomNumberField.getText();
        //String roomCategory = categoryComboBox.getValue();
        if (checkIfRoomExist()) {
            notificationOBJ.errorNotification("ROOM No. EXIST", "Room With No. " + roomNo + " Already Exist");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "confirm to add new room or cancel to abort.");
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.YES);
            alert.setTitle("ADD ROOM");
            alert.setHeaderText("ARE YOU SURE YOU WANT TO ADD NEW ROOM WITH NO." + roomNo + "?");
            if (alert.showAndWait().get() == ButtonType.YES) {
                int result = AddNewRoom(roomNo, categoryComboBox.getValue());
                if (result > 0) {
                    notificationOBJ.successNotification("SUCCESSFUL", "Room No." + roomNo + " Successfully Added To Room List.");
                    fillRoomsTableView();
                    clearFields();
                    saveRoomButton.setDisable(true);
                }
            } else notificationOBJ.informationNotification("FAILED TO ADD ROOM", "Add New Room Operation Terminated.");
        }
    }

    //THIS METHOD IS INVOKED WHEN THE UPDATE BUTTON IS CLICKED
    @FXML void updateButtonRoomClicked() {
        if(roomsTableView.getSelectionModel().getSelectedItems().isEmpty()) {
            notificationOBJ.errorNotification("EMPTY ROLE", "You have not made any selection yet, please select a room.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "please confirm action to execute else CANCEL to abort");
            alert.setTitle("UPDATE ROOM ");
            alert.setHeaderText("YOU HAVE REQUESTED TO UPDATE ROOM WITH ID: " + roomsTableView.getSelectionModel().getSelectedItem().getRoomId());
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().remove(ButtonType.OK);
            if(alert.showAndWait().get().equals(ButtonType.YES)) {
                int flag = 0;

                for (ManageRoomsData columnValues : roomsTableView.getItems()) {
                    byte statusFlag = 0;
                    int roomId = columnValues.getRoomId();
                    String roomNo = columnValues.getRoomNo();
                    String roomCategory = columnValues.getRoomCategory();
                    //double standardPrice = columnValues.getRoomPrice();
                    CheckBox action = columnValues.getStatusAction();

                    if(action.isSelected()) {
                        statusFlag = 1;
                    }
                    flag = UpdateRoom(roomId, roomNo, roomCategory, statusFlag);
                }
                if (flag > 0) {
                    fillRoomsTableView();
                    notificationOBJ.successNotification("UPDATE SUCCESSFUL", "You Have Successfully Updated Room Details");
                } else notificationOBJ.errorNotification("UPDATE FAILED", "Update Operation Failed");
            }
        }
    }

    @FXML void refreshRoomsTable()
    {
        //roomsTableView.getItems().clear();
        //fillRoomsTableView();
    }

    //THIS METHOD IS INVOKED WHEN THEN CATEGORY COMBO BOX IS CLICKED AND CHECKS IF ANY SELECTIONS WHERE MADE AND TEXT FIELD IS NOT EMPTY.
    //ENABLES THE SAVE BUTTON IF FALSE ELSE DISABLES THE SAVE BUTTON...
    @FXML void CategoryTypeSelected() {
        saveRoomButton.setDisable(checkRoomNoField() || checkRoomCategoryField());
    }

    //CHECKS IF TEXT FIELD AND COMBO BOX IS EMPTY.
    @FXML void validateFields() {
        try {
            saveRoomButton.setDisable(checkRoomNoField() || checkRoomCategoryField());
//        deleteRoomButton.setDisable(checkRoomNoField() || checkRoomCategoryField());
        } catch (NullPointerException ignored) {
        }
    }


    /*******************************************************************************************************************
     *                              IMPLEMENTATION OF OTHER SPECIAL METHODS.
     *******************************************************************************************************************/
    void clearFields() {
        roomNumberField.clear();
        categoryComboBox.setValue(null);
        saveRoomButton.setDisable(true);
        deleteRoomButton.setDisable(true);
    }
    @FXML void selectedCategoryValue() {
        System.out.println(categoryComboBox.getValue());
    }

    void populateCategoryComboBox() {
       for (RoomPricesData item : fetchRoomPrices()) {
           categoryComboBox.getItems().add(item.getRoomsCateName());
       }
    }

    //THIS METHOD WHEN CALLED SHALL RETURN THE ASSOCIATED ROOM NUMBER FOR THE SELECTED ROOM CATEGORY.
    int returnRoomId() {
        int result = 0;
        for(RoomPricesData item : fetchRoomPrices()) {
            if (categoryComboBox.getValue().equals(item.getRoomsCateName())) {
                result = item.getRoomsCatId();
            }
        }
        return result;
    }


    void fillRoomsTableView() {
        roomsTableView.setItems(fetchAllRooms());
        itemId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        //itemId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        roomNumber.setCellFactory(TextFieldTableCell.forTableColumn());

        //THIS LINE OF CODE GETS THE NEW VALUE ENTERED AND THEN SETS THE SPECIFIED ROLE VALUE TO THE NEW TEXT VALUE.
        roomNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ManageRoomsData, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ManageRoomsData, String> cellEditEvent) {
                manageRoomsTableViewOBJ = cellEditEvent.getRowValue();
                manageRoomsTableViewOBJ.setRoomNo(cellEditEvent.getNewValue());
            }
        });

        roomCategory.setCellValueFactory(new PropertyValueFactory<>("roomCategory"));
        //roomCategory.setCellFactory(TextFieldTableCell.forTableColumn());
        roomCategory.setCellFactory(ComboBoxTableCell.forTableColumn(categoryComboBox.getItems()));

        //THIS LINE OF CODE GETS THE NEW VALUE ENTERED AND THEN SETS THE SPECIFIED ROLE VALUE TO THE NEW TEXT VALUE.
        roomCategory.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ManageRoomsData, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ManageRoomsData, String> cellEditEvent) {
                manageRoomsTableViewOBJ = cellEditEvent.getRowValue();
                manageRoomsTableViewOBJ.setRoomCategory(cellEditEvent.getNewValue());
             }
        });

        priceColumn.setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
        //addedBy.setCellFactory(TextFieldTableCell.forTableColumn());

//        action.setStyle("-fx-alignment-center; fx-font-size:16px");
        action.setCellValueFactory(new PropertyValueFactory<>("statusAction"));


    }



    /*******************************************************************************************************************
     *                     TRUE OR FALSE METHODS IMPLEMENTATION
     *******************************************************************************************************************/
    boolean checkRoomNoField() {
        return roomNumberField.getText().isBlank();
    }
    boolean checkRoomCategoryField() {
        return categoryComboBox.getValue().isEmpty();
    }

    boolean checkIfRoomExist() throws SQLException {
        boolean flag = false;
        String placeholder = roomNumberField.getText();
        for(RoomsData item : fetchRooms()) {
            if (Objects.equals(item.getRoomNo().toLowerCase().trim(), placeholder.toLowerCase().trim())) {
                flag = true;
                break;
            }
        }
    return flag;
    }



}//END OF CLASS
