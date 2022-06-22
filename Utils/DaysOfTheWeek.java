package com.example.ifbproject.Utils;

/**
 * Kalob Reinholz
 *
 * Last edited: 6/16/2022
 *
 * Enum class for the days of the week
 */

public enum DaysOfTheWeek {
    MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5);
    
    int day;
    DaysOfTheWeek(int x) {
        day = x;
    }
    public int getDay() {
        return day;
    }
}