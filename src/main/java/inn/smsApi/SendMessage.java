package inn.smsApi;


import java.net.*;
import java.io.*;

public class SendMessage {
    public SendMessage() {}

    public static void main(String[] args) throws Exception {
        String API_key = "5c926b098c1087dac3f6";
        String message = "THIS IS FROM THE INN REGISTER.  A TEST TRANSMISSION FROM Mcs Republic...!";
        String phone_number = "0200791855";
        String sender_id = "INNREGISTER"; //11 characters

        /*******************API URL FOR SENDING MESSAGES********/
        URL url = new URL("http://clientlogin.bulksmsgh.com/smsapi?key=" + API_key + "&to=" + phone_number + "&msg=" + message + "&sender_id=" + sender_id);

        /****************API URL TO CHECK BALANCE****************/
        URL balanceUrl = new URL("http://clientlogin.bulksmsgh.com/api/smsapibalance?key=" + API_key);

        URLConnection balanceUrl = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        inputLine = in.readLine().trim();
        System.out.println(inputLine);
        if (inputLine.equals("1000")) {
            System.out.println("Message sent");
        } else if (inputLine.equals("1002")) {
            System.out.println("Message not sent");
        } else if (inputLine.equals("1003")) {
            System.out.println("You don't have enough balance");
        } else if (inputLine.equals("1004")) {
            System.out.println("Invalid API Key");
        } else if (inputLine.equals("1005")) {
            System.out.println("Phone number not valid");
        } else if (inputLine.equals("1006")) {
            System.out.println("Invalid Sender ID");
        } else if (inputLine.equals("1008")) {
            System.out.println("Empty message");
        }
        in.close();
    }


}
