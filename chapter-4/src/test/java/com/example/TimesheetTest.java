package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimesheetTest {

    // Test that when a timesheet is submitted with a project name and hours it returns true
    @Test
    public void testSubmitTimesheetWithProjectNameAndHours() {
        Timesheet timesheet = new Timesheet();
        boolean result = timesheet.submitTimesheet("Project 1", 8);
        assertEquals(true, result);
    }

    // Test that when timesheets are added they can be retrieved as a list
    @Test
    public void testAddTimesheetsToList() {
        Timesheet timesheet = new Timesheet();
        timesheet.submitTimesheet("Project 1", 8);
        timesheet.submitTimesheet("Project 2", 8);
        timesheet.submitTimesheet("Project 3", 8);
        assertEquals(3, timesheet.getTimesheets().size());
    }

    // Test that the total hours worked can be calculated from a list of timesheets from one project
    @Test
    public void testCalculateTotalHoursWorked() {
        Timesheet timesheet = new Timesheet();
        timesheet.submitTimesheet("Project 1", 8);
        timesheet.submitTimesheet("Project 1", 8);
        timesheet.submitTimesheet("Project 1", 8);

        Long total = timesheet.getTotalTimesheetHours("Project 1");

        assertEquals(24, total);
    }
}
