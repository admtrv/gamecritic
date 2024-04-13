package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateUtil {
    public static String convertDate(String date) {
        LocalDate result = LocalDate.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withLocale(Locale.ENGLISH);;
        return result.format(formatter).toUpperCase();
    }
}
