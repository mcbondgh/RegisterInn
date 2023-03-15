package inn.Controllers.messagebox;

import inn.ErrorLogger;
import inn.StartInn;
import inn.database.DbConnection;
import inn.models.ManageMessageModel;
import inn.prompts.UserAlerts;
import inn.prompts.UserNotification;
import inn.smsApi.SendMessage;
import inn.tableViews.MessageTemplatesData;
import inn.threads.CheckSmsBalanceTask;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class ManageMessages extends ManageMessageModel implements Initializable{

    SendMessage sendMessageOBJ;
    UserAlerts userAlerts;
    UserNotification notify = new UserNotification();
    ErrorLogger logger;
    

    /*******************************************************************************************************************
     *                                              FXLM NODE EJECTION.
     *******************************************************************************************************************/
    @FXML private Label smsBalanceValue;
    @FXML private Rectangle smsBalancePane;
    @FXML private TextField messageTitleField, mobileNumberField;
    @FXML private TextArea messageContentField;
    @FXML private Button sendMessageButton, cancelMessageButton, addContactButton, removeContactButton;
    @FXML private Label aliasDisplay, contactSizeIndicator, successIndicator, failedIndicator;

    @FXML private ListView<String> contactListView;


    //CLASS INSTANTIATION FIELD
    CheckSmsBalanceTask messageTask = new CheckSmsBalanceTask();

    DbConnection DOA = new DbConnection();

    String alias = (String) DOA.fetchBusinessInfo().get(1);

    /*******************************************************************************************************************
     SPECIAL METHODS IMPLEMENTATION*/
    public void FlipView(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource(fxmlFileName));
    }


    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadWebpage();
        fillTemplateListView();
        int returnedBalance = SendMessage.checkSmsBalance();
             aliasDisplay.setText(alias);
        if (returnedBalance == 20000) {
            smsBalanceValue.setText("No internet");
            smsBalanceValue.setStyle("-fx-text-fill:#ff0000");
            smsBalancePane.setWidth(120);
        } else {
            smsBalanceValue.setText(String.valueOf(returnedBalance));
        }
    }

    /*******************************************************************************************************************
     TRUE OR FALSE STATEMENTS - BOOLEAN METHODS....*/

    boolean isMessageTitleEmpty(){
        return messageTitleField.getText().isBlank();
    }

    boolean isMessageBodyEmpty() {
        return messageContentField.getText().isBlank();
    }

    boolean isMobileNumberEmpty() {
        return mobileNumberField.getText().isBlank();
    }

    boolean isContactListViewEmpty() {
        return contactListView.getItems().isEmpty();
    }

    boolean checkListViewForNumberExit() {
        boolean flag = false;
        String mobileNumber = mobileNumberField.getText();
        for (String item : contactListView.getItems()) {
            if (Objects.equals(item, mobileNumber)) {
                flag = true;
                break;
            }
        }
        return flag;
    }



    /*******************************************************************************************************************
     FIELDS VALIDATION METHODS.....*/
        @FXML void checkAllFields() {
            sendMessageButton.setDisable(isMessageBodyEmpty() || isMessageTitleEmpty() || isContactListViewEmpty());
        }

        @FXML void  validateMobileNumberField(KeyEvent event) {
            if (!(event.getCode().isDigitKey() || event.getCode().isArrowKey() || event.getCode() == KeyCode.BACK_SPACE )) {
                mobileNumberField.clear();
            } else if (mobileNumberField.getLength() > 10) {
                mobileNumberField.deleteText(10, mobileNumberField.getLength());
            } else if(mobileNumberField.getLength() == 10) {
                addContactButton.setDisable(false);
            } else if (mobileNumberField.getLength() < 10) {
                addContactButton.setDisable(true);
            }
        }


            void executeMessageTimer() {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                int counter = 0;
                @Override
                public void run() {
                    if (counter < contactListSize()) {
                        System.out.println(contactListView.getItems().get(counter));
                        counter++;
                    } else {
                        timer.cancel();
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 0, 1000);

        }


    /*******************************************************************************************************************
     ACTION EVENT METHODS IMPLEMENTATION....*/
    @FXML void sendMessageButtonClicked() {
        try {
            if (!(isContactListViewEmpty() || isMessageTitleEmpty() || isMessageBodyEmpty())) {
                executeMessageTimer();
            }

        } catch (Exception e) {
            logger = new ErrorLogger();
            logger.log(e.getStackTrace().toString());
        }
    }

    @FXML void cancelMessageButtonClicked() {
        messageTitleField.clear();
        messageContentField.clear();
        contactListView.getItems().clear();
    }

    @FXML void addContactButtonClicked() {
        String contact = mobileNumberField.getText();
        if (checkListViewForNumberExit()) {
            notify.errorNotification("CONTACT EXIST", "Mobile Number is already added.");
        } else if(contactListSize() == 10) {
            notify.informationNotification("LIST FULL", "List is full you cannot add more than 10 numbers.");
        }else {
            contactListView.getItems().add(contact);
            contactSizeIndicator.setText(String.valueOf(contactListSize()));
            mobileNumberField.clear();
            addContactButton.setDisable(true);
        }
    }

    @FXML void contactListViewActive() {
        removeContactButton.setDisable(contactListView.getSelectionModel().isEmpty());
    }


    @FXML void removeContactButtonClicked() {
        int selectedItemIndex = contactListView.getSelectionModel().getSelectedIndex();
        contactListView.getItems().remove(selectedItemIndex);
        contactSizeIndicator.setText(String.valueOf(contactListSize()));
    }



    /*******************************************************************************************************************
     OTHER METHODS IMPLEMENTATION....*/

    void clearFields() {
        messageTitleField.clear();
        messageContentField.clear();
        sendMessageButton.setDisable(true);
        contactListView.getItems().clear();
    }

    int contactListSize() {
        return contactListView.getItems().size();
    }


    /*******************************************************************************************************************
     MESSAGE TEMPLATE VIEW CONTROLS AND FXML NODES EJECTION....*/
    @FXML private TextField templateTitle;
    @FXML private TextArea templateBody;
    @FXML private Button saveTemplateButton, deleteTemplateButton, deselectItemButton;
    @FXML private ListView<String> templateTitleView;
    @FXML private Label lastUpdatedDateTime, lastUpdatedName;


    /*******************************************************************************************************************
     TRUE OR FALSE STATEMENTS FOR MESSAGE TEMPLATE......*/

    boolean isTemplateTitleEmpty() {
        return templateTitle.getText().isBlank();
    }

    boolean isTemplateBodyEmpty() {
        return templateBody.getText().isEmpty();
    }
    boolean isTemplateViewEmpty() {
        return templateTitleView.getItems().isEmpty();
    }

    boolean isTemplateItemSelected() {
        return templateTitleView.getSelectionModel().getSelectedItem().isEmpty();
    }


/*******************************************************************************************************************
 ACTION EVENT IMPLEMENTATION FOR MESSAGE TEMPLATE......*/

    @FXML void checkTemplateFields() {
        saveTemplateButton.setDisable(isTemplateBodyEmpty() || isTemplateTitleEmpty());
    }


    @FXML void deleteTemplateButtonClicked() {

    }

    @FXML void saveTemplateButtonClicked() {
        String title = templateTitle.getText();
        String body = templateBody.getText();

        LocalDateTime generatedDate = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String stringDate = dateTimeFormatter.format(generatedDate);
        Timestamp currentDatetime = Timestamp.valueOf(stringDate);

        userAlerts = new UserAlerts("SAVE TEMPLATE", "ARE YOU SURE YOU WANT TO SAVE THIS TEMPLATE?", "please confirm to save template else cancel to abort.");
        if (userAlerts.confirmationAlert()) {
            int savedResult = saveNewMessageTemplate(title, body, (byte) 1, currentDatetime);
            if (savedResult == 1) {
                notify.successNotification("SAVE SUCCESSFUL", "Perfect, message template saved successfully");
                clearTemplateFields();
                fillTemplateListView();
            } else {
                notify.errorNotification("SAVE FAILED", "Oops, your attempt to save message template failed.");
            }
        }
    }

    @FXML void templateListViewClicked() {
        if (!(isTemplateViewEmpty() && isTemplateItemSelected())) {
            deselectItemButton.setDisable(false);
            saveTemplateButton.setText("Update");
            String selectedItem = templateTitleView.getSelectionModel().getSelectedItem();
            for (MessageTemplatesData item : fetchMessageTemplates()) {
                if(item.getTemplateTitle().equals(selectedItem)) {
                    templateTitle.setText(item.getTemplateTitle());
                    templateBody.setText(item.getTemplateBody());
                    lastUpdatedDateTime.setText(item.getDateModified().toString());
                    lastUpdatedName.setText(item.getAddedBy());
                }
            }
        }
    }

    @FXML void deselectListItems() {
            fillTemplateListView();
            templateTitle.clear();
            templateBody.clear();
            lastUpdatedDateTime.setText(null);
            lastUpdatedName.setText(null);
            saveTemplateButton.setText("Save");
            deselectItemButton.setDisable(true);
    }

    void clearTemplateFields() {
        templateBody.clear();
        templateTitle.clear();
    }

    void fillTemplateListView() {
        templateTitleView.getItems().clear();
        for (MessageTemplatesData title : fetchMessageTemplates()) {
            templateTitleView.getItems().add(title.getTemplateTitle());
        }
    }
    /*******************************************************************************************************************
     TOP UP AIRTIME VIEW CONTROLS ...*/
    @FXML private WebView webviewSection;
    @FXML private WebEngine webEngine;


    private  void loadWebpage() {
            webEngine = webviewSection.getEngine();
            webEngine.load("https://www.tester.kwegyiraggrey.com/");
    }





}//end of class
