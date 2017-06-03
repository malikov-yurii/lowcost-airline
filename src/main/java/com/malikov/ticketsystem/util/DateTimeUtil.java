package com.malikov.ticketsystem.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author Yurii Malikov
 */
public class DateTimeUtil {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static final LocalDateTime MIN = LocalDateTime.of(2000, 1,1,1,1);

    public static final LocalDateTime MAX = LocalDateTime.of(2030, 1,1,1,1);

    public static final long ONE_MINUTE_IN_MILLIS = 60000;

    public static final long BOOKING_DURATION_MILLIS = DateTimeUtil.ONE_MINUTE_IN_MILLIS * 2;

    private DateTimeUtil() {}

    public static <T extends Comparable<? super T>> boolean isBetween(T value, T start, T end) {
        return value.compareTo(start) >= 0 && value.compareTo(end) <= 0;
    }

    public static String toString(LocalDateTime localDateTime) {
        return localDateTime == null ? "" : localDateTime.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String string) {
        return StringUtils.isEmpty(string) ? null : LocalDate.parse(string);
    }

    public static LocalTime parseLocalTime(String string) {
        return StringUtils.isEmpty(string) ? null : LocalTime.parse(string);
    }

    public static LocalDateTime parseToLocalDateTime(String str) {
        return parseToLocalDateTime(str, DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parseToLocalDateTime(String str, DateTimeFormatter formatter) {
        return StringUtils.isEmpty(str) ? LocalDateTime.now() : LocalDateTime.parse(str, formatter);
    }

    public static LocalDateTime utcToZoneId(LocalDateTime localDateTime, ZoneId zoneId){
        return localDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(zoneId).toLocalDateTime();
    }

    public static LocalDateTime zoneIdToUtc(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime.atZone(zoneId).withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }
}
