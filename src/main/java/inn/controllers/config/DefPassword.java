package inn.Controllers.config;

public class DefPassword {
    public DefPassword() {}

    //DEFAULT PASSWORD FOR ALL NEW USERS. CAN LATER BE CHANGED TO USER'S DEFINED CHOICE OF CHARACTERS
    public String defaultPassword() {
        return "inn@2023";
    }
}
