package inn.threads;

import inn.smsApi.SendMessage;
import javafx.concurrent.Task;

public class CheckSmsBalanceTask extends Task<Integer>{

    public CheckSmsBalanceTask() {}



    @Override
    protected Integer call() throws Exception {
      return SendMessage.checkSmsBalance();
    }
}//end of class;
