package inn.prompts;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class UserPrompts {
    @FXML private Button failedBtn, closeBtn, closeActivationStageBtn, adjustDateTimeCloseBtn;



    public void closeButtonClicked() {
        closeBtn.getScene().getWindow().hide();
    }

    public void failedButtonClicked() {
        failedBtn.getScene().getWindow().hide();
    }

    @FXML void closeActivationPrompt() {
        closeActivationStageBtn.setOnMouseClicked(MouseEvent -> closeActivationStageBtn.getScene().getWindow().hide());
    }

    @FXML void adjustDateTimeCloseBtnAction() {
        adjustDateTimeCloseBtn.setOnMouseClicked(MouseEvent -> Platform.exit());
    }





}//END OF CLASS
