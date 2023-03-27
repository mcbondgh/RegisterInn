package inn.Controllers.booking;

import inn.models.MainModel;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.util.ResourceBundle;

public class Booking extends MainModel implements Initializable {


    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        fillPaymentMethodComboBox();

    }

    @FXML private MFXLegacyComboBox<String> paymentMethodComboBox;

    void fillPaymentMethodComboBox() {
        String[] constants = new String[]{"ALL METHODS", "CASH", "MOBILE MONEY"};
        for (String items : constants) {
            paymentMethodComboBox.getItems().add(items);
        }
    }


















}//end of class
