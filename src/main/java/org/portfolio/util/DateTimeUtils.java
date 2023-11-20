package org.portfolio.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private static final String BASE_DATE_FORMAT_IN_DATE_PICKERS = "yyyy-MM-dd";
    private static final String SIMPLE_DATE_FORMAT_DISPLAY = "dd.MM.yyyy";

    private DateTimeUtils() {
    }

    public static String generateDateInFuture(int daysToAdd) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(BASE_DATE_FORMAT_IN_DATE_PICKERS);

        LocalDate shiftedDate = currentDate.plusDays(daysToAdd);
        return shiftedDate.format(formatter);
    }

    public static boolean isToday(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SIMPLE_DATE_FORMAT_DISPLAY);
        LocalDate dateToCheck = LocalDate.parse(dateString.trim(), formatter);
        LocalDate today = LocalDate.now();

        return dateToCheck.isEqual(today);
    }
}
