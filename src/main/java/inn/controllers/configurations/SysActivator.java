package inn.Controllers.configurations;

import inn.models.InnActivationModel;
import javafx.beans.NamedArg;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;
import java.util.Random;


public class SysActivator{
    InnActivationModel activationOBJ = new InnActivationModel();
    private String userPassword;

    public SysActivator(@NamedArg("User's Password") String userPassword) {
        this.userPassword = userPassword;
    }

    public SysActivator() {}

    //THIS METHOD WHEN INVOKED SHALL RETURN A 16 CHARACTER RANDOM ACTIVATION KEY IN ADDITION TO SYSTEM alias fetched from the database.
    protected  String activationKeyGenerator() {
        String bisAlias = activationOBJ.businessAlias();
        String key = "";
        Random random = new Random();
        String alphaNumeric = "abcdefghijkl1234567890MNOPQRSTUVWXYZ";
        int charSize = alphaNumeric.length();
        for(int x = 0; x <= 16; x++) {
            key = key.concat(String.valueOf(alphaNumeric.charAt(random.nextInt(charSize))));
        }
        String badge1 = key.substring(0, 3);
        String badge2 = key.substring(4, 7);
        String badge3 = key.substring(8, 11);
        String badge4 = key.substring(12, 16);
        return bisAlias + "-" + badge1 + "-" + badge2 + "-" + badge3 + "-" + badge4;
    }


    /** THIS METHOD WHEN CALLED SHALL TAKE A USER'S INPUT AS AN ARGUMENT AND RETURN A HASHED
     * REPRESENTATIVE OF THE USER'S INPUT.
     */
    public String passwordEncryptor(@NotNull @NamedArg("User's Password")String userInput) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(userInput.getBytes());
    }

    /** THIS METHOD WHEN INVOKED SHALL TAKE TWO ARGUMENTS AND RETURN TRUE IF THEY MATCH ELSE FALSE.
     */
    public boolean passwordVerify(@NamedArg("EncryptedPassword") String hashCode, @NamedArg("UserText")String plainText) {
        boolean flag = false;
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] hashedByte = decoder.decode(hashCode);
        String dehashedString = new String(hashedByte);
        if(dehashedString.equals(plainText)) {
            flag = true;
        }
        return flag;
    }



}//END OF CLASS
