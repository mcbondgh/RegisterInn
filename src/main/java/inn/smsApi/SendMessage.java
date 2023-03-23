package inn.smsApi;

import inn.ErrorLogger;
import inn.models.MainModel;

import java.net.*;
import java.io.*;

/**
 *
 * @author BulkSMS Ghana
 */
public class SendMessage {
    static ErrorLogger  errorLogger = new ErrorLogger();
    MainModel DOA = new MainModel();

    String alias = (String) DOA.fetchBusinessInfo().get(1);

    private static String API_key = "5c926b098c1087dac3f6";
    private String message;
    private String phone_number;
    private String sender_id = alias; //11 characters


//    public SendMessage() {}
    public SendMessage(String phone_number, String message) {
        this.phone_number = phone_number;
        this.message = message;
    }

    public String submitMessage() {
        String status = "";
        try {
            /*******************API URL FOR SENDING MESSAGES********/
            URL url = new URL("http://clientlogin.bulksmsgh.com/smsapi?key=" + API_key + "&to=" + phone_number + "&msg=" + message + "&sender_id=" + sender_id);

            URLConnection connection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            status = bufferedReader.readLine().trim();
            bufferedReader.close();
        }catch (Exception e) {
            errorLogger.log(e.getLocalizedMessage());
        }
        return status;
    }

    public static int checkSmsBalance() {
        int balance = 0;
        try {
            /****************API URL TO CHECK BALANCE****************/
            URL balanceUrl = new URL("http://clientlogin.bulksmsgh.com/api/smsapibalance?key=" + API_key);

            URLConnection connection = balanceUrl.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            inputLine = bufferedReader.readLine().trim();
            balance = Integer.parseInt(inputLine);

            bufferedReader.close();
        }catch (UnknownHostException e) {
            balance = 20000;
            errorLogger.log(e.getLocalizedMessage());
            e.printStackTrace();
        } catch (IOException ignored) {}

        return balance;
    }



    public String getAPI_key() {
        return API_key;
    }

    public void setAPI_key(String API_key) {
        this.API_key = API_key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

//    public static void main(String[] args) throws Exception {

//        int balance = SendMessage.checkSmsBalance();
//        System.out.println(balance);
////        String API_key = "5c926b098c1087dac3f6";
////        String message = "I SEE YOU GHOST...";
////        String phone_number = "0200791855";
////        String sender_id = "GHOST"; //11 characters
////
////        /*******************API URL FOR SENDING MESSAGES********/
////        URL url = new URL("http://clientlogin.bulksmsgh.com/smsapi?key=" + API_key + "&to=" + phone_number + "&msg=" + message + "&sender_id=" + sender_id);
////
////        /****************API URL TO CHECK BALANCE****************/
////        URL balanceUrl = new URL("http://clientlogin.bulksmsgh.com/api/smsapibalance?key=" + API_key);
////
////        URLConnection connection = balanceUrl.openConnection();
////        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
////        String inputLine;
////        inputLine = in.readLine().trim();
////        System.out.println(inputLine);
////        if (inputLine.equals("1000")) {
////            System.out.println("Message sent");
////        } else if (inputLine.equals("1002")) {
////            System.out.println("Message not sent");
////        } else if (inputLine.equals("1003")) {
////            System.out.println("You don't have enough balance");
////        } else if (inputLine.equals("1004")) {
////            System.out.println("Invalid API Key");
////        } else if (inputLine.equals("1005")) {
////            System.out.println("Phone number not valid");
////        } else if (inputLine.equals("1006")) {
////            System.out.println("Invalid Sender ID");
////        } else if (inputLine.equals("1008")) {
////            System.out.println("Empty message");
////        }
////        in.close();
////    }
//    }
}
