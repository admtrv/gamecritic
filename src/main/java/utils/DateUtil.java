package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Provides utility methods for handling date and time transformations across the application.
 */
public class DateUtil {

    /**
     * Converts date string from its standard format ("2004-10-28") to more
     * human-readable string format ("OCT 28, 2004"). This method is typically used for
     * display purposes where user-friendly date representation is required.
     * @param date formatted date string to be converted
     * @return  string representing the date in "MMM dd, yyyy" format
     */
    public static String convertDate(String date) {
        LocalDate result = LocalDate.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withLocale(Locale.ENGLISH);;
        return result.format(formatter).toUpperCase();
    }

    /**
     * Generates current date-time string in "yyyy-MM-dd HH:mm:ss" format.
     * This is primarily used for timestamping in logging processes, ensuring that logs have
     * precise time information for better traceability and debugging.
     * @return string representing the current date-time formatted as "yyyy-MM-dd HH:mm:ss"
     */
    public static String loggerDate() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }
}
