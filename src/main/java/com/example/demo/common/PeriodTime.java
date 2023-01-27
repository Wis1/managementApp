package com.example.demo.common;

import java.time.LocalDateTime;
import java.time.Month;

public enum PeriodTime {

    YEAR,
    MONTH,
    WEEK;

    public LocalDateTime returnDate() {
        return switch (this) {
            case YEAR -> LocalDateTime.of(LocalDateTime.now().getYear(), Month.JANUARY,
                    1, 0, 0, 0);
            case MONTH -> LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonth(),
                    1, 0, 0, 0);
            case WEEK -> LocalDateTime.now().minusDays(LocalDateTime.now().getDayOfWeek().getValue());
        };
    }
}
