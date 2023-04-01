package inn.threads;

import java.time.LocalTime;

public class BookingTimeGenerator extends Thread{

    private LocalTime checkInTime, checkOutTime;
    private int durationTimeInHours, timeIntervalInMinutes;

    public BookingTimeGenerator(int durationTimeInHours) {
        this.durationTimeInHours = durationTimeInHours;
    }


    public void run() {
        checkInTime = LocalTime.now();
        checkOutTime = checkInTime.plusHours(durationTimeInHours);
        timeIntervalInMinutes = checkOutTime.getMinute() - checkInTime.getMinute();
        System.out.println(checkOutTime);

    }

    //
//
//
//
//
//
//    int hours = Integer.parseInt(allocatedtimeField.getText());
//    LocalTime checkIn = LocalTime.now();
//    LocalTime checkout = checkIn.plusMinutes(hours);
//
//    int returnedValue = checkout.getMinute() - checkIn.getMinute();
//    Thread runThread = new Thread(()-> {
//        int session = 60;
//        if (returnedValue == 5)  {
//            while ( > 0session) {
//                session --;
//                System.out.println(session);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//            if (session == 0){
//                System.out.println("time out");
//            }
//        }
//    });
//       runThread.start();

}
