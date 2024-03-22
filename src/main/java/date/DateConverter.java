package date;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateConverter {
    public static String convert(String date) {
        LocalDate result = LocalDate.parse(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy").withLocale(Locale.ENGLISH);;
        return result.format(formatter).toUpperCase();
    }

}
