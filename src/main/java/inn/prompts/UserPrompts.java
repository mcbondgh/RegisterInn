package inn.prompts;

import inn.models.ManageStocksModel;
import inn.tableViews.StoresTableData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Objects;

public class UserPrompts extends ManageStocksModel {
    @FXML private Button failedBtn, closeBtn, closeActivationStageBtn, adjustDateTimeCloseBtn, saveBrand;
    @FXML private TextField brandInputField;
    @FXML private Label brandLabelDisplay;



//    CLASS INSTANTIATION FIELD
    UserNotification notify = new UserNotification();

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
    @FXML void saveBrandOnAction() {
        boolean result  = addNewBrand(brandInputField.getText());
        if(!result) {
            brandInputField.clear();
        }
    }
    @FXML void brandInputAction() {
        saveBrand.setDisable(brandInputField.getText().isBlank());
        String brandName = brandInputField.getText();
        for (StoresTableData.BrandsTableData item : fetchProductBrands()) {
            if(Objects.equals(item.getBrandName().toLowerCase().replaceAll("\\s", ""), brandName.replaceAll("\\s", ""))) {
                brandLabelDisplay.setVisible(true);
                saveBrand.setDisable(true);
            } else brandLabelDisplay.setVisible(false);
        }
    }




}//END OF CLASS
