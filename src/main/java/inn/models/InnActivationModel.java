package inn.models;

import java.sql.Date;
import java.sql.SQLException;

public class InnActivationModel extends MainModel {

    public InnActivationModel(String activationKey, Date expiryDate) {
    }

    public InnActivationModel() {}

    //THIS METHOD GETS THE BUSINESS alias FROM THE business_info TABLE.
    public String businessAlias() {
        return fetchBusinessInfo().get(1).toString();
    }

    //THIS METHOD WHEN INVOKED WILL UPDATE THE ACTIVATION KEY, START AND EXPIRY DATES.
    public int updateSoftwareActivation(String keyValue, Date selectedDate) {
        int counter = 0;
        try {
            String InsertQuery = "UPDATE activation_key SET activation_code = ?, start_date = DEFAULT, expiry_date = ?, updated_date = DEFAULT;";
            prepare = CONNECTOR().prepareStatement(InsertQuery);
            prepare.setString(1, keyValue);
            prepare.setDate(2, selectedDate);
            counter = prepare.executeUpdate();
            if(counter > 0) {
               return counter;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counter;
    }

    //THIS METHOD WHEN INVOKED SHALL UPDATE ONLY THE updated_date table IN THE activation_key table.
    //THIS METHOD IS RESPONSIBLE FOR UPDATING AND KEEPING TRACK OF INCREMENTAL DAYS SINCE ACTIVATED OR RENEWED.
    public int updateTrackerDateOnly() {
        int counter = 0;
        try {
            String updateQuery = "UPDATE activation_key SET updated_date = DEFAULT;";
            prepare = CONNECTOR().prepareStatement(updateQuery);
            counter = prepare.executeUpdate();
            if (counter > 0) {
                counter = 1;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();;
        }
        return counter;
    }

    protected int updateAuthenticationPassword(String userInput) throws SQLException {
            String insertQuery = "UPDATE activation_password SET admin_key = '" + userInput + "'";
            prepare =  CONNECTOR().prepareStatement(insertQuery);
            return prepare.executeUpdate();
    }

    protected int updateSMSAPI(String key, String senderId) throws SQLException {
        prepare = CONNECTOR().prepareStatement("UPDATE sms_api_key SET api_key = '"+key+"', sender_name = '"+senderId+"' WHERE id = 1;");
        return prepare.executeUpdate();
    }


}//END OF CLASS
