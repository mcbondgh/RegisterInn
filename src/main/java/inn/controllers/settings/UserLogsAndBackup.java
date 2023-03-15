package inn.Controllers.settings;

import inn.threads.NumberCounterTask;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;

public class UserLogsAndBackup {

    /*****************************************************************
     *          FXML NODE EJECTIONS...
     *****************************************************************/
    @FXML private Button backUpButton;
    @FXML private TableView<Object>  userLogsTableView;
    @FXML private MFXProgressBar progressBar;
    @FXML private ProgressBar progressBar1;
    @FXML private Label backupProgressLabel, displayValue;
    @FXML  private Rectangle valueCounterPane;
    @FXML private TextField counterInputField;


    NumberCounterTask numberCounterTask;

    /*****************************************************************
     * ACTION EVENT METHODS IMPLEMENTATION.
     *****************************************************************/
        @FXML public void backupBtnOnAction() {

        }

        @FXML void computeButtonOnAction() {
        long inputValue = Integer.parseInt(counterInputField.getText());
            numberCounterTask = new NumberCounterTask(inputValue);
            numberCounterTask.valueProperty().addListener((observableValue, aLong, t1) -> displayValue.setText(String.valueOf(t1)));
            progressBar1.progressProperty().bind(numberCounterTask.progressProperty());
            Thread thread = new Thread(numberCounterTask);
            thread.setDaemon(true);
            thread.start();
            numberCounterTask.cancel();
        }




}//end of class

