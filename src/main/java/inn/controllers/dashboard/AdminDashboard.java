package inn.controllers.dashboard;

import inn.models.MainModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboard extends MainModel implements Initializable  {

    public static String showAvailableRooms, showOccupiedRooms;



    @FXML private Label occupiedRoomsLabel, availableRoomsLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String occupiedValue = String.valueOf(countBookedRooms());
        String availableValue = String.valueOf(countFreeRooms());
        occupiedRoomsLabel.setText(occupiedValue);
        availableRoomsLabel.setText(availableValue);
    }
}
