package inn.Controllers.settings;

import inn.StartInn;
import inn.models.MainModel;
import inn.multiStage.MultiStages;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class General extends MainModel implements Initializable {

/************************************ CLASS INSTANTIATION FIELD ***********************************************/
    MultiStages multiStagesOBJ = new MultiStages();




/************************************ FXML OBJECTS EJECTION ***********************************************/

    @FXML private TextField businessNameField, businessNumberField, otherNumberField, digitalAddressField, emailAddressField;
    @FXML private TextField managerNameField, managerEmailField, managerNumberField, totalWorkersField, aliasField;
    @FXML private TextArea descriptionField;
    @FXML private DatePicker datePickerField;
    @FXML private Button updateButton, uploadImageBtn, activationKeyButton, authenBtn, systemInfoBtn, userLogsButton, backUpButton;
    @FXML private Label dateLabel;
    @FXML private ImageView heroImage;
    @FXML private Pane infoPane;
    @FXML private BorderPane settingsPane;
    @FXML private Button resetApplicationBtn;
    @FXML private

    String filePath = null;
    File selectedFile = null;
    InputStream stream = null;



/************************************ INITIALIZER METHOD IMPLEMENTATION ***********************************************/
    public void initialize(URL location, ResourceBundle resourceBundle) {
        try {
            settingsPane.setCenter(infoPane);
            fillAllFields();
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

/************************************ GETTER AND SETTER FIELDS ***********************************************/
    public void setBusinessNameField(String businessNameField) {
        this.businessNameField.setText(businessNameField);
    }
    public String getBusinessName() {return  businessNameField.getText();}
    public void setBusinessNumberField(int businessNumberField) {
        this.businessNumberField.setText(String.valueOf(businessNumberField));
    }
    public int getBusinessNumber() {return Integer.parseInt(businessNumberField.getText());}
    public void setOtherNumberField(int otherNumber) {otherNumberField.setText(String.valueOf(otherNumber));}
    public int getOtherNumberField() {return Integer.parseInt(otherNumberField.getText());}
    public void setDigitalAddressField(String address) {digitalAddressField.setText(address);}
    public String getAddress() {return digitalAddressField.getText();}
    public void setEmail(String email) { emailAddressField.setText(email);}
    public String getEmail() {return emailAddressField.getText();}
    public void setManagerEmailField(String email) {managerEmailField.setText(email);}
    public String getManagerEmailField() {return managerEmailField.getText();}
    public void setManagerNameField(String name) {managerNameField.setText(name);}
    public String getManagerName() {return managerNameField.getText();}
    public void setManagerNumberField(int number) {managerNumberField.setText(String.valueOf(number));}
    public int getManagerNumber() {return Integer.parseInt(managerNumberField.getText());}
    public void setTotalWorkersField(int total) {totalWorkersField.setText(String.valueOf(total));}
    public int getTotalWorkers() {return Integer.parseInt(totalWorkersField.getText());}
    public void setDescriptionField(String descrip) {descriptionField.setText(descrip);}
    public String getDescription () {return descriptionField.getText();}
    public void getDatePicker() {
        datePickerField.getValue();
    }


    /*******************************************************************************************************************
     SPECIAL METHODS IMPLEMENTATION*/
    public void FlipView(String fxmlFileName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartInn.class.getResource(fxmlFileName));
        settingsPane.setCenter(fxmlLoader.load());
    }


    /************************************ ACTION METHOD IMPLEMENTATION FIELD ***********************************************/

    //THIS METHOD CHECKS IF THE ADMIN ENTERED NUMERIC VALUES ONLY INTO business_number_field, IF TRUE ALLOW, ELSE CLEAR FIELD.
    public void validateBusinessNumberField(KeyEvent event){
        if (!(event.getCode().isDigitKey() || event.getCode() == (KeyCode.BACK_SPACE)) ){
            businessNumberField.clear();
        }
    }
    //THIS METHOD CHECKS IF THE ADMIN ENTERED NUMERIC VALUES ONLY INTO other_number_field, IF TRUE ALLOW, ELSE CLEAR FIELD.
    public void validateOtherNumberField(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode() == (KeyCode.BACK_SPACE)) ){
            otherNumberField.clear();
        }
    }

    //THIS METHOD CHECKS IF THE ADMIN ENTERED NUMERIC VALUES ONLY INTO manager_number_field, IF TRUE ALLOW, ELSE CLEAR FIELD.
    public void validateManagerNumberField(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode() == (KeyCode.BACK_SPACE)) ){
            managerNumberField.setText(null);
        }
    }

    //THIS METHOD CHECKS IF THE ADMIN ENTERED NUMERIC VALUES ONLY INTO total_workers_field, IF TRUE ALLOW, ELSE CLEAR FIELD.
    public void validateWorkersField(KeyEvent event) {
        if (!(event.getCode().isDigitKey() || event.getCode() == (KeyCode.BACK_SPACE)) ){
            totalWorkersField.setText(null);
        }
    }

    //THIS METHOD DISABLES THE updateBtn UNTIL THE USER FILLS ALL THE TEXTFIELDS INCLUDING THE DATE PICKER.
    public void OnkeyPressed() {
        try {
            if (getBusinessName().isEmpty() || aliasField.getText().isBlank() || getAddress().isEmpty() || getEmail().isEmpty() || getDescription().isEmpty() ||
                    getManagerName().isEmpty() || getManagerEmailField().isEmpty()  || totalWorkersField.getText().isEmpty() ||
                    businessNumberField.getText().isEmpty() || otherNumberField.getText().isEmpty() || totalWorkersField.getText().isEmpty()) {
                updateButton.setDisable(true);
            }
            else updateButton.setDisable(false);
        } catch (Exception e) {
            dateLabel.setVisible(true);
            datePickerField.setStyle("-fx-border-width:4px; -fx-border-color:#ff0000");
            e.printStackTrace();
        }

    }

    //THIS METHOD IS AN ACTION EVENT WHEN INVOKED WILL UPLOAD A HERO IMAGE UNTO THE IMAGE VIEWER AND GET THE ABSOLUTE FILE PATH.
    @FXML
    void browseImageOnAction() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
            fileChooser.setTitle("SELECT HERO IMAGE");
            selectedFile = fileChooser.showOpenDialog(uploadImageBtn.getScene().getWindow());
            filePath = selectedFile.getAbsolutePath();
            Image image = new Image(filePath);
            heroImage.setImage(image);
            System.out.println(inputStream().toString());
        } catch (NullPointerException | FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "it is optional to set a hero image for your business.");
            alert.setTitle("No Image Selected");
            alert.setHeaderText("YOU SELECTED NO IMAGE AS YOUR HERO IMAGE.🙂");

            alert.show();
        }
    }

    //THIS METHOD WILL CONVERT THE SELECTED FILE INTO AN INPUT STREAM FOR UPLOAD INTO THE DATABASE.
    InputStream inputStream() throws FileNotFoundException {
        try {
            stream = new FileInputStream(selectedFile);
        } catch (NullPointerException | FileNotFoundException ignored) {
             selectedFile = new File(heroImage.getImage().getUrl());
             stream = new FileInputStream(selectedFile);
        }
       return stream;
    }

    public void updateButtonClicked() {
        try {
            getDatePicker();
            if(datePickerField.getValue() == null) {
                dateLabel.setVisible(true);
                datePickerField.setStyle("-fx-border-width:4px; -fx-border-color:#ff0000;");
            } else {
                dateLabel.setVisible(false);
                datePickerField.setStyle(null);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.getButtonTypes().remove(ButtonType.OK);
                alert.getButtonTypes().add(ButtonType.YES);
                alert.setTitle("Confirm Update");
                alert.setHeaderText("ARE YOU SURE YOU WANT TO UPDATE CURRENT RECORDS?");
                if(!(alert.showAndWait().get() == ButtonType.CANCEL)) {
                    updateBusinessInfo(getBusinessName(), aliasField.getText(), getEmail(), getAddress(), getBusinessNumber(), getOtherNumberField(), datePickerField.getValue(), inputStream(), getTotalWorkers(), getDescription(), getManagerName(), getManagerNumber(), getManagerEmailField());
//                    alert.setHeaderText("PERFECT, RECORDS UPDATED SUCCESSFULLY");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void resetButtonClicked() throws IOException {
        multiStagesOBJ.showResetStage();
    }
    public void checkNumberLength() {
        if(businessNumberField.getLength() == 10) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Mobile Number");
            alert.setHeaderText("MOBILE NUMBER MUST BE EXACTLY 10 DIGITS");
            alert.showAndWait();
        }
    }

    @FXML void checkAliasLength() {
        if(aliasField.getLength() == 4) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Mobile Number");
            alert.setHeaderText("ALIAS MUST BE AT MOST 4 CHARACTERS");
            alert.showAndWait();
        }
    }

    public void checkOtherNumberLength() {
        if(otherNumberField.getLength() == 10) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Mobile Number");
            alert.setHeaderText("MOBILE NUMBER MUST BE EXACTLY 10 DIGITS");
            alert.showAndWait();
        }
    }

    public void checkMangNumberLength() {
        if(managerNumberField.getLength() == 10) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid Mobile Number");
            alert.setHeaderText("MOBILE NUMBER MUST BE EXACTLY 10 DIGITS");
            alert.showAndWait();
        }
    }

    @FXML
    void activationBtnClicked() throws IOException {
        FlipView("Modules/Settings/systemActivationStage.fxml");
    }
    @FXML void userLogsBtnClicked() throws IOException {
        FlipView("Modules/Settings/userLogsView.fxml");
    }
    @FXML void systemInfoBtnClicked() throws  IOException {
        settingsPane.setCenter(infoPane);
    }
    @FXML public void AuthenKeyBtnClicked() throws IOException {
        FlipView("Modules/Settings/resetAuthenPassword.fxml");
    }
    @FXML public void backUpButtonOnClick() throws IOException{
        FlipView("Modules/Settings/backUpView.fxml");
    }

    //<------------------------MOUSE HOVER EFFECT FIELD FOR ALL BUTTONS. -------------------------------->
    @FXML
    void HoverEffectForSysInfoBtn() {
        systemInfoBtn.setStyle("-fx-background-color:#ab0303; -fx-text-fill:#fff");
    }
    @FXML
    void mouseExitedForSysInfo() {
        systemInfoBtn.setStyle("-fx-background-color:none; -fx-text-fill: #ab0303; -fx-border-color: #ab0303");
    }
    @FXML void hoverEffectForActivationBtn() {
        activationKeyButton.setStyle("-fx-background-color:#ab0303; -fx-text-fill:#fff");
    }
    @FXML void mouseExitedForActivationBtn() {
        activationKeyButton.setStyle("-fx-background-color:none; -fx-text-fill: #ab0303; -fx-border-color: #ab0303");
    }
    @FXML void mouseHoverForAuthenBtn() {
        authenBtn.setStyle("-fx-background-color:#ab0303; -fx-text-fill:#fff");
    }
    @FXML void mouseExitedForAuthenBtn() {
        authenBtn.setStyle("-fx-background-color:none; -fx-text-fill: #ab0303; -fx-border-color: #ab0303");
    }
    @FXML void HoverEffectForUserLogsBtn() {
        userLogsButton.setStyle("-fx-background-color:#ab0303; -fx-text-fill:#fff");
    }
    @FXML void mouseExitedForUserLogsBtn() {
        userLogsButton.setStyle("-fx-background-color:none; -fx-text-fill: #ab0303; -fx-border-color: #ab0303");
    }
    @FXML void mouseExitedForResetButton() {
        resetApplicationBtn.setStyle("-fx-background-color:none; -fx-text-fill: #ab0303; -fx-border-color: #ab0303");
    }
    @FXML void HoverEffectForResetButton() {
        resetApplicationBtn.setStyle("-fx-background-color:#ab0303; -fx-text-fill:#fff");
    }
    @FXML void HoverEffectForBackupBtn() {
        backUpButton.setStyle("-fx-background-color:#ab0303; -fx-text-fill:#fff");
        backUpButton.setScaleX(1.07);
        backUpButton.setScaleY(1.07);
    }
    @FXML void mouseExitedForBackupBtn() {
        backUpButton.setStyle("-fx-background-color:none; -fx-text-fill: #ab0303; -fx-border-color: #ab0303");
        backUpButton.setScaleX(1);
        backUpButton.setScaleY(1);
    }

    /************************************ OTHER METHODS FIELD ***********************************************/
    //THIS METHOD FILLS THE TEXT FIELDS OF THE FORM WITH DATE RETURNED FROM THE DATABASE.
    public void fillAllFields(){
        try {
            setBusinessNameField((String) fetchBusinessInfo().get(0));
            aliasField.setText((String) fetchBusinessInfo().get(1));
            setEmail((String) fetchBusinessInfo().get(2));
            setDigitalAddressField((String) fetchBusinessInfo().get(3));
            setBusinessNumberField((Integer) fetchBusinessInfo().get(4));
            setOtherNumberField((Integer) fetchBusinessInfo().get(5));
            Date date = (Date) fetchBusinessInfo().get(6);
            datePickerField.setValue((date.toLocalDate()));
            setTotalWorkersField((Integer) fetchBusinessInfo().get(7));
            setDescriptionField((String)fetchBusinessInfo().get(9));
            setManagerNameField((String) fetchBusinessInfo().get(10));
            setManagerNumberField((Integer) fetchBusinessInfo().get(11));
            setManagerEmailField((String) fetchBusinessInfo().get(12));
            Blob imageBlob = (Blob) fetchBusinessInfo().get(8);
            byte[] imageByte = imageBlob.getBytes(1, (int) imageBlob.length());
            OutputStream stream = new FileOutputStream("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\imagexyz.jpg");
            stream.write(imageByte);
            Image profileImage = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\imagexyz.jpg");
            heroImage.setImage(profileImage);
            stream.close();
        } catch (NullPointerException e) {
            Image defaultImage = new Image("E:\\JAVA APPLICATIONS\\InnRegister V2\\InnRegister\\src\\main\\resources\\inn\\images\\imagexyz.jpg");
            heroImage.setImage(defaultImage);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        } catch (IndexOutOfBoundsException index) {
            throw new IndexOutOfBoundsException();
        }
    }




}//END OF CLASS
