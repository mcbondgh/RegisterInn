package inn.multiStage;

import inn.StartInn;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MultiStages{


    public void Homepage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("Modules/dashboard/homepage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Homepage");
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.sizeToScene();
        stage.setTitle("HOMEPAGE");
        stage.isFullScreen();
        stage.setResizable(true);
        stage.setMinHeight(1000);
        stage.setMinHeight(500);
        stage.getScene().getWindow().setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "you can only log out with the signout button");
            alert.setTitle("Close Button Clicked");
            alert.setHeaderText("LOGOUT BY CLICKING THE SIGNOUT BUTTON.");
            alert.showAndWait();
        });
        stage.show();
    }

    public DialogPane pane() {
        DialogPane pane = new DialogPane();
        pane.setMaxWidth(200);
        pane.isVisible();
        return pane;
    }

    public void DisplayPrompt() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("prompt.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.setTitle("Confirm Action");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void showSuccessPrompt() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("prompts/success.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.setTitle("Successful");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        stage.onCloseRequestProperty().get();
    }

    public void showFailedPrompt() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("prompts/failed.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.setTitle("Failed Action");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void LoginForm() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("index.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.show();
    }

    public void UpdateLoginDetails() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("Modules/settings/updateUserLogin.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Update Login Info");
        stage.setResizable(false);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void resetPassword()throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("Modules/Settings/resetPassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Reset Password");
        stage.setResizable(false);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


    public void systemStatusStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("Modules/Settings/systemActivationStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("System Status");
        stage.setResizable(false);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void innRegisterActivationStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("Modules/Settings/InnRegisterActivationStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("System Activation");
        stage.setResizable(false);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }


    public void systemUpdateAlertStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("prompts/systemActivationPrompt.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.setTitle("Successfully Activated.");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }
    public void wrongDateTimeStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("prompts/wrongDateTime.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.setTitle("Successfully Activated.");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setAlwaysOnTop(true);
        stage.show();
    }
    public void addBrandStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("prompts/addBrandView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.setTitle("Add Brand");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);
        stage.show();
    }
    public void showResetStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource("prompts/resetDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        Image logo = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\logo.png");
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.setTitle("Add Brand");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setAlwaysOnTop(true);
        stage.show();
    }



}//END OF CLASS
