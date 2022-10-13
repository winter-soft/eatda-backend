package com.proceed.swhackathon.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;

import static java.time.temporal.ChronoField.*;

public class LocalDateTimeFormatUtils {

    /**
     * @param ldt
     * LocalDateTime을 입력받아서 'yyyy-dd-mm hh'형식으로 포맷팅한다.
     * ex) 2022-10-12T13:07:26.734171 => 2022-10-12 13
     * @return
     */
    public static String dateHour(LocalDateTime ldt){
        DateTimeFormatter dtf = new DateTimeFormatterBuilder()
                .appendValue(YEAR, 4, 10, SignStyle.EXCEEDS_PAD)
                .appendLiteral('-')
                .appendValue(MONTH_OF_YEAR, 2)
                .appendLiteral('-')
                .appendValue(DAY_OF_MONTH, 2)
                .appendLiteral(' ')
                .appendValue(HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(MINUTE_OF_HOUR, 2)
                .toFormatter();

        return ldt.format(dtf);
    }

    /**
     * @param timeIndex
     * timeIndex를 입력받아 현재 시간 기준 언제를 가르키는지를 계산해 출력한다.
     * @return (LocalDateTime)
     */
    public static LocalDateTime calcTime(Long timeIndex){
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dealTime = null;
        if(timeIndex == 0) dealTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 11,0); // 오늘 점심
        else if(timeIndex == 1) dealTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 16,30); // 11시
        else if(timeIndex == 2) dealTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 11,0).plusDays(1); // 11시
        else if(timeIndex == 3) dealTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 16,30).plusDays(1); // 11시

        return dealTime;
    }
}
