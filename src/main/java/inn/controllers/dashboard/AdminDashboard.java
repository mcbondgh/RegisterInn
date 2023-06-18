package inn.controllers.dashboard;

import inn.models.MainModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboard extends MainModel implements Initializable  {

    public static String showAvailableRooms, showOccupiedRooms;



    @FXML private Label occupiedRoomsLabel, availableRoomsLabel, todayBookingLabel, outOfStockLabel;
    @FXML private Label availableItemsLabel, totalEmployeesLabel, itemRequestLabel, lowOnStockLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String occupiedValue = String.valueOf(countBookedRooms());
        String availableValue = String.valueOf(countFreeRooms());
        occupiedRoomsLabel.setText(occupiedValue);
        availableRoomsLabel.setText(availableValue);
        todayBookingLabel.setText(String.valueOf(countTotalBookings()));

        int totalEmployees = (int) fetchBusinessInfo().get(7);
        outOfStockLabel.setText(String.valueOf(countOutOfStock()));
        availableItemsLabel.setText(String.valueOf(countAvailableItems()));
        totalEmployeesLabel.setText(String.valueOf(totalEmployees));
        lowOnStockLabel.setText(String.valueOf(countLowOnStock()));
        itemRequestLabel.setText(String.valueOf(countRequestedItems()));
    }
}
