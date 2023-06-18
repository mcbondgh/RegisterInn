package inn.controllers.configurations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatLocalDateTime {

    public static String formatTimeWithTimeOfDate(LocalTime localTime) {
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("HH:mm:ss");
        return formatDate.format(localTime);
    }

    public static String formatDate(LocalDate localDate){
        DateTimeFormatter checkInFormatter = DateTimeFormatter.ofPattern("E dd-MM-yyyy");
        return localDate.format(checkInFormatter);
    }

    public static String formatDateTime(LocalDateTime enterYourDateTime){
        DateTimeFormatter checkInFormatter = DateTimeFormatter.ofPattern("E dd-MM-yyyy HH:mm:ss");
        return enterYourDateTime.format(checkInFormatter);
    }

    public static LocalDateTime formatToLocalDateTime(String dateTimeInString) {
        String dateTimeString = dateTimeInString.substring(4, 23); // Replace with your formatted LocalDateTime string
        String pattern = "dd-MM-yyyy HH:mm:ss"; // Replace with the pattern of your formatted string

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeString, formatter);
    }



}
