package inn;

import inn.Controllers.dashboard.Homepage;

public class MultiThreads implements Runnable {
    @Override

        public void run() {
        int value = 0;
            while (true) {
                // Perform task here
                int result = value+=1;
                Homepage.counterValue = result;
                System.out.println(result);
                try {
                    Thread.sleep(1000); // sleep for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

}
