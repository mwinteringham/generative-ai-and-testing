package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ChatGPTDateCheckerTests {

    ChatGPTDateChecker chatGPTDateChecker = new ChatGPTDateChecker();

    @Test
    void testIsValidBooking() {
        LocalDate checkIn = LocalDate.of(2023, 5, 1);
        LocalDate checkOut = LocalDate.of(2023, 5, 5);
        List<Booking> existingBookings = new ArrayList<>();
        existingBookings.add(new Booking("Room 1", LocalDate.of(2023, 5, 10), LocalDate.of(2023, 5, 15)));

        assertTrue(chatGPTDateChecker.isValidBooking(checkIn, checkOut, existingBookings));
    }

    @Test
    void testIsValidBookingWithNullInputs() {
        LocalDate checkIn = LocalDate.of(2023, 5, 1);
        LocalDate checkOut = null;
        List<Booking> existingBookings = new ArrayList<>();

        assertFalse(chatGPTDateChecker.isValidBooking(checkIn, checkOut, existingBookings));
    }

    @Test
    void testIsValidBookingWithPastCheckInDate() {
        LocalDate checkIn = LocalDate.of(2023, 4, 1);
        LocalDate checkOut = LocalDate.of(2023, 5, 1);
        List<Booking> existingBookings = new ArrayList<>();

        assertFalse(chatGPTDateChecker.isValidBooking(checkIn, checkOut, existingBookings));
    }

    @Test
    void testIsValidBookingWithInvalidDates() {
        LocalDate checkIn = LocalDate.of(2023, 2, 30);
        LocalDate checkOut = LocalDate.of(2023, 3, 1);
        List<Booking> existingBookings = new ArrayList<>();

        assertFalse(chatGPTDateChecker.isValidBooking(checkIn, checkOut, existingBookings));
    }

    @Test
    void testIsValidBookingWithOverlappingBookings() {
        LocalDate checkIn = LocalDate.of(2023, 5, 1);
        LocalDate checkOut = LocalDate.of(2023, 5, 5);
        List<Booking> existingBookings = new ArrayList<>();
        existingBookings.add(new Booking("Room 1", LocalDate.of(2023, 5, 2), LocalDate.of(2023, 5, 8)));

        assertFalse(chatGPTDateChecker.isValidBooking(checkIn, checkOut, existingBookings));
    }

    @Test
    void testIsValidBookingWithDurationLimit() {
        LocalDate checkIn = LocalDate.of(2023, 5, 1);
        LocalDate checkOut = LocalDate.of(2023, 6, 1);
        List<Booking> existingBookings = new ArrayList<>();

        assertFalse(chatGPTDateChecker.isValidBooking(checkIn, checkOut, existingBookings));
    }
}
