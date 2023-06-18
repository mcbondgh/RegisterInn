package inn.controllers.settings;

import inn.config.database.DatabaseConfiguration;
import inn.models.BackupModel;
import inn.prompts.UserAlerts;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder;

public class DatabaseBackup extends DatabaseConfiguration {

    UserAlerts userAlerts;

    /*****************************************************************
     *          FXML NODE EJECTIONS...
     *****************************************************************/
    @FXML private Button backUpButton;
    @FXML private TableView<Object>  userLogsTableView;
    @FXML private MFXProgressBar progressBar;
    @FXML private ProgressBar progressBar1;
    @FXML private Label backupProgressLabel, displayValue;
    @FXML private Rectangle valueCounterPane;
    @FXML private TextField counterInputField;


    /*****************************************************************
     * ACTION EVENT METHODS IMPLEMENTATION.
     *****************************************************************/
        @FXML public void backupBtnOnAction() {
            try {
                userAlerts = new UserAlerts("DATABASE BACKUP", "YOU HAVE REQUESTED TO BACKUP YOUR DATABASE, DO YOU WANT TO PROCEED?", "" +
                        "please confirm your action to begin backup else CANCEL to abort");
                if (userAlerts.confirmationAlert()) {
                    DirectoryChooser chooseDirectory = new DirectoryChooser();
                    File filePath = chooseDirectory.showDialog(backUpButton.getScene().getWindow());
                    String dbName = "backup.sql";
                    String backupFile =filePath.getAbsolutePath()+ File.separator + dbName;
                    System.out.println(backupFile);

                    if (filePath != null) {
                        String command = "mysqldump --user="+ USERNAME + " --password="+ PASSWORD +" --host="+SERVER_NAME+" --port="+PORT +
                                " --databases " + DATABASE_NAME + " > " + backupFile;
                        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c" + command);
                        Process status = processBuilder.start();
                        int exitCode = status.waitFor();
                        if(exitCode != 0) {
                            System.out.println("Error: " + exitCode);
                        } else {
                            System.out.println("Success");
                        }
                    }
                }
            }catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }




}//end of class

