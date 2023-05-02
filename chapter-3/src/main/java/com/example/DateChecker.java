package com.example;

import java.time.LocalDate;

public class DateChecker {

    // Compare a check in date to a check out date and return true if the check in date is before the check out date
    public boolean checkInBeforeCheckOut(String checkInDate, String checkOutDate) {
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);

        // Return false if the check in date is in the past
        if (checkIn.isBefore(LocalDate.now())) {
            return false;
        }

        return checkIn.isBefore(checkOut);
    }
}
