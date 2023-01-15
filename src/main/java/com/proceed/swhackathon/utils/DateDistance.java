package com.proceed.swhackathon.utils;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;

public class DateDistance {
    private static final long SECONDS = 1L;
    private static final long MINUTES = 60*SECONDS;
    private static final long HOURS = 60*MINUTES;
    private static final long DAYS = 24*HOURS;
    private static final long WEEKS = 7*DAYS;
    private static final long MONTHS = 4*WEEKS;
    private static final long YEAR = 365*DAYS;

    public static String of(@NotNull Temporal target) {
        LocalDateTime targetDate;

        if (target instanceof LocalDateTime) {
            targetDate = (LocalDateTime) target;
        } else if (target instanceof Timestamp) {
            targetDate = ((Timestamp) target).toLocalDateTime();
        } else if (target instanceof LocalDate) {
            targetDate = ((LocalDate) target).atStartOfDay();
        } else {
            throw new IllegalArgumentException("지원하지 않는 타입입니다.");
        }

        LocalDateTime now = LocalDateTime.now();
        long dateDistant = Duration.between(targetDate, now).getSeconds();

        if (dateDistant >= YEAR) {
            return String.valueOf(dateDistant / YEAR) + "년 전";
        } else if (dateDistant >= MONTHS) {
            return String.valueOf(dateDistant / MONTHS) + "달 전";
        } else if (dateDistant >= WEEKS) {
            return String.valueOf(dateDistant / WEEKS) + "주 전";
        } else if (dateDistant >= DAYS) {
            return String.valueOf(dateDistant / DAYS) + "일 전";
        } else if (dateDistant >= HOURS) {
            return String.valueOf(dateDistant / HOURS) + "시간 전";
        } else if (dateDistant >= MINUTES) {
            return String.valueOf(dateDistant / MINUTES) + "분 전";
        } else if (dateDistant >= SECONDS) {
            return String.valueOf(dateDistant / SECONDS) + "초 전";
        } else if (dateDistant == 0L) {
            return "방금";
        } else {
            throw new IllegalArgumentException("날짜가 잘못되었습니다.");
        }
    }
}
