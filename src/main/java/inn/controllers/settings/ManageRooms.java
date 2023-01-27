package inn.Controllers.settings;

import inn.StartInn;
import inn.models.ManageRoomsModel;
import inn.prompts.UserNotification;
import inn.tableViews.ManageRoomsTableView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class ManageRooms extends ManageRoomsModel implements Initializable {

    //CLASS OBJECTS / CLASS INSTANTIATION FIELD;

    UserNotification notificationOBJ = new UserNotification();
    ManageRoomsTableView manageRoomsTableViewOBJ;

    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    /*******************************************************************************************************************
     *                                              FXLM NODE EJECTION.
     *******************************************************************************************************************/
    @FXML private AnchorPane manageRoomsPane, addRoomsPane;
    @FXML private BorderPane roomsBorderPane;
    @FXML private Button priceListBtn, categoryBtn, addRoomBtn, updateRoomButton;
    @FXML private Button saveRoomButton, deleteRoomButton;
    @FXML private TextField roomNumberField, roomCategoryField;

    /*******************************************************************************************************************
     ROOMS TABLEVIEW NODES EJECTION */
    @FXML
    private TableView<ManageRoomsTableView> roomsTableView;
    @FXML private TableColumn<ManageRoomsTableView, Integer> itemId;
    @FXML private TableColumn<ManageRoomsTableView, String> roomNumber;

    @FXML private TableColumn<ManageRoomsTableView, String> roomCategory;

    @FXML private TableColumn<ManageRoomsTableView, String> addedBy;
    @FXML private TableColumn<ManageRoomsTableView, CheckBox> action;


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
        FlipView("Modules/rooms_view/addRoomDisplay.fxml");
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
                        notificationOBJ.successNotification("DELETE SUCCESSFUL", "Room No." + roomNo + " Successfully Deleted.", roomsBorderPane);
                        fillRoomsTableView();
                    } else {
                        notificationOBJ.errorNotification("DELETE FAILED", "Delete Operation Failed For Room With No. " + roomNo);

                    }
                }
        } catch (NullPointerException exception) {
            notificationOBJ.errorNotification("EMPTY ROLE", "You have not made any selection yet, please select a room to delete.");
        }
    }
     @FXML void refreshRoomsTable()
     {
         fillRoomsTableView();
     }

    @FXML void validateFields() {
        saveRoomButton.setDisable(checkRoomNoField() || checkRoomCategoryField());
//        deleteRoomButton.setDisable(checkRoomNoField() || checkRoomCategoryField());
    }

    @FXML void saveRoomButtonClicked() throws SQLException {
        String roomNo = roomNumberField.getText();
        String roomCategory = roomCategoryField.getText();
        String addedBy = "Super Admin";
        if (checkIfRoomExist()) {
            notificationOBJ.errorNotification("ROOM No. EXIST", "Room With No. " + roomNo + " Already Exist");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "confirm to add new room or cancel to abort.");
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.YES);
            alert.setTitle("ADD ROOM");
            alert.setHeaderText("ARE YOU SURE YOU WANT TO ADD NEW ROOM WITH NO." + roomNo + "?");
            if (alert.showAndWait().get() == ButtonType.YES) {
                int result = AddNewRoom(roomNo, roomCategory, addedBy);
                if (result > 0) {
                    notificationOBJ.successNotification("SUCCESSFUL", "Room No." + roomNo + " Successfully Added To Room List.", roomsBorderPane);
                    fillRoomsTableView();
                    clearFields();
                }
            } else notificationOBJ.informationNotification("FAILED TO ADD ROOM", "Add New Room Operation Terminated.");
        }
    }

    @FXML void updateButtonRoomClicked() {
        if(roomsTableView.getSelectionModel().getSelectedItems().isEmpty()) {
            notificationOBJ.errorNotification("EMPTY ROLE", "You have not made any selection yet, please select a room.");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "please confirm action to execute else CANCEL to abort");
            alert.setTitle("UPDATE ROOM ");
            alert.setHeaderText("YOU HAVE REQUESTED TO UPDATE ROOM DETAILS, DO YOU WISH TO EXECUTE COMMAND?");
            alert.getButtonTypes().add(ButtonType.YES);
            alert.getButtonTypes().remove(ButtonType.OK);
            if(alert.showAndWait().get().equals(ButtonType.YES)) {
                int flag = 0;
                String placeHolder = "SYSTEM ADMIN";
                for (ManageRoomsTableView columnValues : roomsTableView.getItems()) {
                    byte statusFlag = 0;
                    int roomId = columnValues.getRoomId();
                    String roomNo = columnValues.getRoomNo();
                    String roomCategory = columnValues.getRoomCategory();
                    CheckBox action = columnValues.getStatusAction();

                    if(action.isSelected()) {
                        statusFlag = 1;
                    }
                    flag = UpdateRoom(roomId, roomNo, roomCategory,statusFlag, placeHolder);
                }
                if (flag > 0) {
                    fillRoomsTableView();
                    notificationOBJ.successNotification("UPDATE SUCCESSFUL", "You Have Successfully Updated Room Details", roomsBorderPane);
                } else notificationOBJ.errorNotification("UPDATE FAILED", "Update Operation Failed");
            }
        }
    }


    /*******************************************************************************************************************
     *                              IMPLEMENTATION OF OTHER SPECIAL METHODS.
     *******************************************************************************************************************/

    void clearFields() {
        roomNumberField.clear();
        roomCategoryField.clear();
        saveRoomButton.setDisable(true);
        deleteRoomButton.setDisable(true);
    }

    void fillRoomsTableView() {
        roomsTableView.setItems(fetchAllRooms());
        itemId.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        //itemId.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        roomNumber.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        roomNumber.setCellFactory(TextFieldTableCell.forTableColumn());

        //THIS LINE OF CODE GETS THE NEW VALUE ENTERED AND THEN SETS THE SPECIFIED ROLE VALUE TO THE NEW TEXT VALUE.
        roomNumber.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ManageRoomsTableView, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ManageRoomsTableView, String> cellEditEvent) {
                manageRoomsTableViewOBJ = cellEditEvent.getRowValue();
                manageRoomsTableViewOBJ.setRoomNo(cellEditEvent.getNewValue());
            }
        });



        roomCategory.setCellValueFactory(new PropertyValueFactory<>("roomCategory"));
        roomCategory.setCellFactory(TextFieldTableCell.forTableColumn());

        //THIS LINE OF CODE GETS THE NEW VALUE ENTERED AND THEN SETS THE SPECIFIED ROLE VALUE TO THE NEW TEXT VALUE.
        roomCategory.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ManageRoomsTableView, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<ManageRoomsTableView, String> cellEditEvent) {
                manageRoomsTableViewOBJ = cellEditEvent.getRowValue();
                manageRoomsTableViewOBJ.setRoomCategory(cellEditEvent.getNewValue());
             }
        });


        addedBy.setCellValueFactory(new PropertyValueFactory<>("addedBy"));
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
        return roomCategoryField.getText().isBlank();
    }

    boolean checkIfRoomExist() throws SQLException {
        boolean flag = false;
        String placeholder = roomNumberField.getText();
        for(String item : fetchRoomNoOnly()) {
            if (Objects.equals(item.toLowerCase().trim(), placeholder.toLowerCase().trim())) {
                flag = true;
                break;
            }
        }
    return flag;
    }



}//END OF CLASS
