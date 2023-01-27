package inn.models;

import inn.database.DbConnection;
import inn.multiStage.MultiStages;
import javafx.beans.NamedArg;

public class UserLoginsModel extends DbConnection {
    MultiStages multiStagesOBJ = new MultiStages();

    public UserLoginsModel() {
        super();
    }

    public int updateUserLogins(@NamedArg("username") String username, @NamedArg("password") String password, @NamedArg("confirmPassword")String confirm, int roleId, int status, int added_by, String selectedUser) {
        int count = 0;
        try {
            String updateQuery = "UPDATE users SET username = ?, password = ?, confirm_password = ?, role_id = ?, status = ?, added_by = ?, modified_date = DEFAULT WHERE(username = ?);";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            prepare.setString(1, username);
            prepare.setString(2, password);
            prepare.setString(3, confirm);
            prepare.setInt(4, roleId);
            prepare.setInt(5, status);
            prepare.setInt(6, added_by);
            prepare.setString(7, selectedUser);
            count = prepare.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }






}//END OF CLASS
