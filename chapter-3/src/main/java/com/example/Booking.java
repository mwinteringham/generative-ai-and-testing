package com.example;

import java.time.Instant;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class Booking {

    private String room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    public Booking(String room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public String getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }
}
