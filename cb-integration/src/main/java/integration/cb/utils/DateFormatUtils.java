package integration.cb.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatUtils {

    public static String toFormat(LocalDate date, String datePattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        return formatter.format(date);
    }

}