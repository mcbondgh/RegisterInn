package inn;

import inn.multiStage.MultiStages;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartInn extends Application implements Initializable {

    public void initialize(URL location, ResourceBundle resourceBundle) {

    }
    @Override
    public void start(Stage stage) throws IOException {
        MultiStages multiStages = new MultiStages();
        try {
                multiStages.LoginForm();
        }catch (IndexOutOfBoundsException e) {
            inn.ErrorLogger errorLogger = new inn.ErrorLogger();
            errorLogger.log(e.getMessage());
            multiStages.wrongDateTimeStage();
        }
    }

    public static void main() {
        launch();
    }


}//END OF CLASS

