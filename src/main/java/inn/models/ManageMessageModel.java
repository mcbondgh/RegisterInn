package inn.models;

import inn.ErrorLogger;
import inn.database.DbConnection;

import java.sql.SQLException;
import java.sql.Timestamp;

public class ManageMessageModel extends DbConnection {

    ErrorLogger logger;

    protected int submitNewMessage(String mobileNumber, String messageTitle, String messageBody, byte messageStatus, int balance, int sendBy) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO sentmessages(mobileNumber, messageTitle, messageBody, messageStatus, balance, sendBy) VALUES(?, ?, ?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, mobileNumber);
            prepare.setString(2, messageTitle);
            prepare.setString(3, messageBody);
            prepare.setByte(4, messageStatus);
            prepare.setInt(5, balance);
            prepare.setInt(6, sendBy);
           flag =  prepare.executeUpdate();
        }catch (SQLException ex) {
            ex.printStackTrace();
            logger = new ErrorLogger();
            logger.log(ex.getMessage());

        }
        return flag;
    }

    protected int saveNewMessageTemplate(String templateTitle, String templateBody, byte createdBy, Timestamp dateCreated) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO messageTemplates(templateTitle, templateBody, createdBy, dateCreated) VALUES(?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, templateTitle);
            prepare.setString(2, templateBody);
            prepare.setByte(3, createdBy);
            prepare.setTimestamp(4, dateCreated);
            flag =  prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }



}//end of class
