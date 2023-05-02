package com.example;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ChatGPTDateChecker {

    public boolean isValidBooking(LocalDate checkIn, LocalDate checkOut, List<Booking> existingBookings) {
        LocalDate today = LocalDate.now();
        ZoneId timeZone = ZoneId.of("America/New_York");

        // Check for null inputs
        if (checkIn == null || checkOut == null) {
            return false;
        }

        // Check that the check-in date is not in the past
        if (checkIn.isBefore(today)) {
            return false;
        }

        // Check that the check-out date is after the check-in date
        if (!checkOut.isAfter(checkIn)) {
            return false;
        }

        // Check for overlapping bookings
        for (Booking booking : existingBookings) {
            if (booking.getCheckOutDate().isAfter(checkIn) && checkOut.isAfter(booking.getCheckInDate())) {
                return false;
            }
        }

        // Check that the booking duration doesn't exceed 30 days
        if (Duration.between(checkIn.atStartOfDay(timeZone), checkOut.atStartOfDay(timeZone)).toDays() > 30) {
            return false;
        }

        // Check that the dates are valid
        try {
            checkIn.atStartOfDay(timeZone);
            checkOut.atStartOfDay(timeZone);
        } catch (DateTimeException e) {
            return false;
        }

        return true;
    }

}
