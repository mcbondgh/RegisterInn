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
            //WE SHALL MAKE SOME NEW CHANGES HERE LATER IN THE PROJECT...
            multiStages.LoginForm();
        }catch (IndexOutOfBoundsException e) {
            ErrorLogger errorLogger = new ErrorLogger();
            errorLogger.log(e.getMessage());
            multiStages.wrongDateTimeStage();
        }
    }

    public static void main() {
        launch();
    }


}//END OF CLASS

