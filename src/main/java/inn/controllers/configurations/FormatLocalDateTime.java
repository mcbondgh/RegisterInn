package inn.controllers.configurations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatLocalDateTime {



    public static String formatTime(LocalTime localTime) {
        DateTimeFormatter checkInFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return localTime.format(checkInFormatter);
    }

    public static String formatDate(LocalDate localDate){
        DateTimeFormatter checkInFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDate.format(checkInFormatter);
    }

    public static String formatDateTime(LocalDateTime enterYourDateTime){
        DateTimeFormatter checkInFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
        return enterYourDateTime.format(checkInFormatter);
    }


}
