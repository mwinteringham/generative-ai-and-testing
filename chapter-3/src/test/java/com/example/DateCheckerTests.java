package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateCheckerTests {

    // Test that the check in date is before the check out date
    @Test
    public void testCheckInBeforeCheckOut() {
        DateChecker dateChecker = new DateChecker();
        boolean result = dateChecker.checkInBeforeCheckOut("2030-01-01", "2030-01-02");
        assertTrue(result);
    }

    //Test that a check in date after a check out date returns false
    @Test
    public void testCheckInAfterCheckOut() {
        DateChecker dateChecker = new DateChecker();
        boolean result = dateChecker.checkInBeforeCheckOut("2030-01-02", "2030-01-01");
        assertFalse(result);
    }

    //Test that the check in date is the same as the check out date returns false
    @Test
    public void testCheckInSameAsCheckOut() {
        DateChecker dateChecker = new DateChecker();
        boolean result = dateChecker.checkInBeforeCheckOut("2030-01-01", "2030-01-01");
        assertFalse(result);
    }

    //Test that a date in the past returns false
    @Test
    public void testDateInPast() {
        DateChecker dateChecker = new DateChecker();
        boolean result = dateChecker.checkInBeforeCheckOut("2018-01-01", "2019-01-01");
        assertFalse(result);
    }

    //Test that a date in the future returns true
    @Test
    public void testDateInFuture() {
        DateChecker dateChecker = new DateChecker();
        boolean result = dateChecker.checkInBeforeCheckOut("2030-01-01", "2031-01-01");
        assertTrue(result);
    }

}
