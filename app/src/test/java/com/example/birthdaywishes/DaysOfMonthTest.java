package com.example.birthdaywishes;


import com.example.birthdaywishes.POJO.DaysOfMonth;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DaysOfMonthTest {

    @Test
    public void givenNegativeIndexWhenGetDayCountThenReturnZero() {
        assertEquals(0, DaysOfMonth.Companion.get(-1));
    }

    @Test
    public void givenZeroIndexWhenGetDayCountThenReturnZero() {
        assertEquals(0,DaysOfMonth.Companion.get(0));
    }

    @Test
    public void givenValidIndexWhenGetDayCountThenReturnProperCount() {
        int februaryDaysCount = 31;
        int decemberDaysCount = 31;

        assertEquals(februaryDaysCount,DaysOfMonth.Companion.get(1));
        assertEquals(decemberDaysCount,DaysOfMonth.Companion.get(12));
    }

    @Test
    public void givenIndexGreaterThatTwelveWhenGetDayCountThenReturnZero() {
        assertEquals(0,DaysOfMonth.Companion.get(13));
    }
}
