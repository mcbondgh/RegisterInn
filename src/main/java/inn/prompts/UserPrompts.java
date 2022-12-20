package inn.prompts;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserPrompts {
    @FXML private Button failedBtn, closeBtn;



    public void closeButtonClicked() {
        closeBtn.getScene().getWindow().hide();
    }

    public void failedButtonClicked() {
        failedBtn.getScene().getWindow().hide();
    }
}
