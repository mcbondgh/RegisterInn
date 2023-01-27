package inn.Controllers.settings;

import inn.database.DbConnection;
import inn.prompts.UserNotification;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SetPermissions extends DbConnection implements Initializable {

    public AnchorPane setPermissionsPane;
    DbConnection dbConnectionOBJ = new DbConnection();
    UserNotification notify = new UserNotification();
    @FXML
    private ComboBox<String> roleTypeBox;
    @FXML
    private Button savePermissionButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fillRoleBox();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    void fillRoleBox() throws SQLException {
        roleTypeBox.setItems(dbConnectionOBJ.fetchUserRoles());
    }





}//END OF CLASS
