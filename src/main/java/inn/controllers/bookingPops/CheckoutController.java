package inn.Controllers.bookingPops;

import inn.Controllers.booking.Booking;
import inn.Controllers.dashboard.Homepage;
import inn.models.BookingModel;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class CheckoutController extends BookingModel implements Initializable {


    Booking bookingOBJ = new Booking();
    UserAlerts userAlerts;
    UserNotification notify;

/***********************************************************************************************************************
 ********************************** IMPLEMENTATION OF ACTION EVENT METHODS FOR CHECKIN *********************************/
    public static String guestName, roomNo, checkinTime, dueTime, overTime, TimeSpent, checkoutTime;
    public static int bookingId;

    @FXML private MFXButton cancelButton, checkoutButton;
    @FXML private TextField guestNameField, roomNoField, bookingIdField;
    @FXML private Label checkInTimeField, DueTimeField, overtimeField, remainingTimeField, checkoutTimeField;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        guestNameField.setText(guestName);
        roomNoField.setText(roomNo);
        bookingIdField.setText(String.valueOf(bookingId));
        checkInTimeField.setText(checkinTime);
        DueTimeField.setText(dueTime);
        checkoutTimeField.setText(checkoutTime);
        overtimeField.setText(overTime);
    }



    /******************************************************************************************************************
     ********************************** IMPLEMENTATION OF OTHER SPECIAL METHODS ***************************************/


    /*******************************************************************************************************************
     ********************************** IMPLEMENTATION OF ACTION EVENT METHODS FOR CHECKIN ****************************/

    @FXML void closeStage() {
        cancelButton.getScene().getWindow().hide();
    }

    @FXML void checkoutButtonClicked() {

        String currentUser = Homepage.activeUsername;
        int currentUserId = getUserIdByUsername(currentUser);
        String clientName = guestNameField.getText();
        String roomNo = roomNoField.getText();

        int roomId = getRoomIdByRoomNo(roomNo);
        int checkInId = Integer.parseInt(bookingIdField.getText());
        LocalTime checkouTime = LocalTime.parse(checkoutTimeField.getText());
        String overTime = overtimeField.getText();

        userAlerts = new UserAlerts("CHECK OUT CLIENT", "ARE YOU SURE YOU WANT TO CHECK OUT '" + guestName + "' at this time?", "please click YES to confirm your action, else CANCEL to abort");
        if (userAlerts.confirmationAlert()) {
            int flag = 0;
            flag = createNewCheckout(checkInId, clientName, roomNo, checkouTime, overTime, currentUserId);
            flag = flag + updateCheckInStatus(checkInId, 0);
            flag = flag + updateRoomStatus(roomId, 0);
            if (flag == 3) {
                checkoutButton.getScene().getWindow().hide();
            }
        }



    }






}//end of class...
