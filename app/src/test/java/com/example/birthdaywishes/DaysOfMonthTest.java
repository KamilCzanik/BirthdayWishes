package com.example.birthdaywishes;


import com.example.birthdaywishes.data.Months;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DaysOfMonthTest {

    @Test
    public void givenNegativeIndexWhenGetDayCountThenReturnZero() {
        assertEquals(0, Months.Companion.get(-1).getDaysCount());
    }

    @Test
    public void givenZeroIndexWhenGetDayCountThenReturnZero() {
        assertEquals(0, Months.Companion.get(0).getDaysCount());
    }

    @Test
    public void givenValidIndexWhenGetDayCountThenReturnProperCount() {
        int februaryDaysCount = 31;
        int decemberDaysCount = 31;

        assertEquals(februaryDaysCount, Months.Companion.get(1).getDaysCount());
        assertEquals(decemberDaysCount, Months.Companion.get(12).getDaysCount());
    }

    @Test
    public void givenIndexGreaterThatTwelveWhenGetDayCountThenReturnZero() {
        assertEquals(0, Months.Companion.get(13).getDaysCount());
    }
}
