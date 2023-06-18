package inn.fetchedData;

import javafx.scene.control.Label;

import java.sql.Timestamp;

public class SentMessagesData {
    int messageId;
    String mobileNumber;
    String messageTitle;
    String messageBody;
    Label messageStatus;
    int balance;
    String username;
    Timestamp sentDate;

    public SentMessagesData(int messageId, String mobileNumber, String messageTitle, String messageBody, Label messageStatus, int balance, String username, Timestamp sentDate) {
        this.messageId = messageId;
        this.mobileNumber = mobileNumber;
        this.messageTitle = messageTitle;
        this.messageBody = messageBody;
        this.messageStatus = messageStatus;
        this.balance = balance;
        this.username = username;
        this.sentDate = sentDate;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Label getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(Label messageStatus) {
        this.messageStatus = messageStatus;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getSentDate() {
        return sentDate;
    }

    public void setSentDate(Timestamp sentDate) {
        this.sentDate = sentDate;
    }

}//END OF CLASS;
