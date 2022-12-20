package inn.Controllers.settings;

import inn.database.DbConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ResetPassword extends DbConnection {

    /*************************************** FXML NODES ************************************************/
    @FXML private TextField usernameField;
    @FXML private PasswordField newPasswordField, retypePasswordField;
    @FXML private Label userLabel, passLabel, retypeLabel;


    /********************************* SETTER AND GETTER METHODS *********************************/



    /********************************* ACTION EVENT METHOD IMPLEMENTATION *********************************/
    public void searchOnKeyTyped() {}





}//END OF CLASS
