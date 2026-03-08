package com.tienda.abarrotes.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateTimeUtils {

    private DateTimeUtils() {
        // Evita instanciación
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat(AppConstants.DATE_FORMAT, Locale.getDefault())
                .format(new Date());
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat(AppConstants.TIME_FORMAT, Locale.getDefault())
                .format(new Date());
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat(AppConstants.DATE_TIME_FORMAT, Locale.getDefault())
                .format(new Date());
    }
}