package com.proceed.swhackathon.utils

import java.sql.Timestamp
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.Temporal

class DateDistance() {
    companion object {
        val SECONDS: Long = 1
        val MINUTES: Long = 60*SECONDS
        val HOURS: Long = 60*MINUTES
        val DAYS: Long = 24*HOURS
        val WEEKS: Long = 7*DAYS
        val MONTHS: Long = 4*WEEKS
        val YEAR: Long = 365*DAYS

        /**
         * 현재 날짜로 부터 얼마나 시간이 지났는 지 나타냄.
         */
        fun of(target: Temporal): String {
            val targetDate: LocalDateTime = when (target) {
                is LocalDateTime -> target
                is Timestamp -> target.toLocalDateTime()
                is LocalDate -> target.atStartOfDay()
                else -> throw IllegalArgumentException("지원하지 않는 타입입니다.")
            }

            val now = LocalDateTime.now()
            val dateDistant: Long = Duration.between(targetDate, now).seconds

            return when (dateDistant) {
                in YEAR..Long.MAX_VALUE -> (dateDistant / YEAR).toString() + "년 전"
                in MONTHS..YEAR -> (dateDistant / MONTHS).toString() + "달 전"
                in WEEKS..MONTHS -> (dateDistant / WEEKS).toString() + "주 전"
                in DAYS..WEEKS -> (dateDistant / DAYS).toString() + "일 전"
                in HOURS..DAYS -> (dateDistant / HOURS).toString() + "시간 전"
                in MINUTES..HOURS -> (dateDistant / MINUTES).toString() + "분 전"
                in SECONDS..MINUTES -> (dateDistant / SECONDS).toString() + "초 전"
                0L -> "방금"
                else -> throw IllegalArgumentException("날짜가 잘못되었습니다.")
            }
        }
    }
}