package inn.Controllers.configurations;

public class DefPassword extends SysActivator{
    public DefPassword() {}

    //DEFAULT PASSWORD FOR ALL NEW USERS. CAN LATER BE CHANGED TO USER'S DEFINED CHOICE OF CHARACTERS
    public String defaultPassword() {
        String defaultPassword = "inn@2023";
       return passwordEncryptor(defaultPassword);
    }
}
