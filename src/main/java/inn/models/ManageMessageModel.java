package inn.models;

import inn.ErrorLogger;

import java.sql.SQLException;
import java.sql.Timestamp;

public class ManageMessageModel extends MainModel {

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
    protected int saveNewMessageTemplate(String templateTitle, String templateBody, int createdBy, Timestamp dateCreated) {
        int flag = 0;
        try {
            String insertQuery = "INSERT INTO messageTemplates(templateTitle, templateBody, createdBy, dateCreated) VALUES(?, ?, ?, ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, templateTitle);
            prepare.setString(2, templateBody);
            prepare.setInt(3, createdBy);
            prepare.setTimestamp(4, dateCreated);
            flag =  prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }
    protected int deleteTemplate(int templateId) {
        int flag = 0;
        try {
            String deleteQuery = "DELETE FROM messageTemplates WHERE templateId = '" +templateId+ "';";
            prepare = CONNECTOR().prepareStatement(deleteQuery);
            flag = prepare.executeUpdate();

        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return flag;
    }
    protected int updateTemplate(int templateId, String templateTitle, String templateBody, byte createdBy) {
        int flag = 0;
        try {
            String insertQuery = "UPDATE messageTemplates SET templateTitle = ?,  templateBody = ?, createdBy = ?, dateModified = DEFAULT WHERE(templateId = ?)";
            prepare = CONNECTOR().prepareStatement(insertQuery);
            prepare.setString(1, templateTitle);
            prepare.setString(2, templateBody);
            prepare.setByte(3, createdBy);
            prepare.setInt(4, templateId);
            flag =  prepare.executeUpdate();
        }catch (SQLException ex) {
            logger = new ErrorLogger();
            logger.log(ex.getMessage());
        }
        return flag;
    }

}//end of class
