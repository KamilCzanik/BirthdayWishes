package com.example.birthdaywishes;

import com.example.birthdaywishes.data.Birthday;
import com.example.birthdaywishes.data.Months;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BirthdayTest {

    @Test
    public void givenDayBelowZeroWhenIsDateValidThenReturnFalse() {
        assertFalse(Birthday.Companion.isDateValid(-1,1));
    }

    @Test
    public void givenValidDayWhenIsDateValidThenReturnTrue() {
        for(int i = 1; i<=12; i++)
            assertTrue(Birthday.Companion.isDateValid(Months.Companion.get(i).getDaysCount(),i));
    }

    @Test
    public void givenDayGreaterThatDayCountOfTheMonthWhenIsDateValidThenReturnFalse() {
        for(int i = 1; i<=12; i++)
            assertFalse(Birthday.Companion.isDateValid(Months.Companion.get(i).getDaysCount()+1 ,i));
    }
}
