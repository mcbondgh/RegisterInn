package inn.prompts;

import inn.enumerators.AlertTypesEnum;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class UserAlerts {
    String alertTitle;
    String alertHeader;
    String alertContent;

    AlertTypesEnum selectAlert = AlertTypesEnum.WARNING;

    public UserAlerts(String alertTitle, String alertHeader, String alertContent) {
        this.alertTitle = alertTitle;
        this.alertHeader = alertHeader;
        this.alertContent = alertContent;
    }

    public UserAlerts(String alertTitle, String alertHeader) {
        this.alertTitle = alertTitle;
        this.alertHeader = alertHeader;
    }

    public boolean confirmationAlert() {
        boolean flag = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertContent);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.getButtonTypes().add(ButtonType.YES);
        alert.getButtonTypes().remove(ButtonType.OK);
        if (alert.showAndWait().get().equals(ButtonType.YES)) {
            flag = true;
        }
        return flag;
    }

    private void warningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }

    private void informationAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }

    private void errorAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertHeader);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }


    public void chooseAlert(AlertTypesEnum typesEnum) {
        switch (typesEnum) {
            case INFORMATION -> informationAlert();
            case WARNING -> warningAlert();
            case ERROR -> errorAlert();
//            case CONFIRMATION -> confirmationAlert();
        }
    }

}//end of class
