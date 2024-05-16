package com.LeBao.sales.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class DataUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate safeToLocalDate(String strDate) {
        try {
            return LocalDate.parse(strDate, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date: " + e.getMessage());
            return null;
        }
    }

    public static boolean isNullOrEmpty(String str) {
        return str.isEmpty();
    }

    public static boolean isNullOrZero(Long data) {
        return data.equals(0L);
    }

    public static Double safeToDouble(Object o) {
        return Double.parseDouble(o.toString());
    }

    public static Integer safeToInt(Object o) {
        return Integer.parseInt(o.toString());
    }

    public static boolean safeEquals(Object o1, Object o2) {
        return o1.toString().equals(o2.toString());
    }
}
