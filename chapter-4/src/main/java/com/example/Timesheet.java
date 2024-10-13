package com.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Timesheet {

    // A thread-safe map to store project names and corresponding timesheet hours.
    // The key is the project name (String), and the value is the duration in hours (Long).
    private ConcurrentHashMap<String, Long> timesheets = new ConcurrentHashMap<>();

    /**
     * Submits a timesheet entry for a project.
     * 
     * @param projectName The name of the project to submit time for.
     * @param duration    The duration (in hours) spent on the project.
     * @return boolean    Returns true if the timesheet entry is valid and submitted, false otherwise.
     */
    public boolean submitTimesheet(String projectName, long duration) {
        // Validate that project name is not null and duration is a positive number.
        if (projectName != null && duration > 0) {
            // Convert project name to lowercase to ensure case-insensitivity when storing.
            projectName = projectName.toLowerCase();

            // If the project already exists in the map, add the duration to the existing value.
            // Otherwise, initialize the project with the new duration.
            timesheets.put(projectName, timesheets.getOrDefault(projectName, 0L) + duration);
            return true; // Successfully submitted the timesheet.
        } else {
            return false; // Invalid input, either null projectName or non-positive duration.
        }
    }

    /**
     * Retrieves all the timesheet durations.
     * 
     * @return Collection<Long> A collection of all durations stored in the timesheets map.
     */
    public Collection<Long> getTimesheets() {
        return timesheets.values();
    }

    /**
     * Gets the total timesheet hours for a specific project.
     * 
     * @param projectName The name of the project to retrieve time for.
     * @return long       The total duration (in hours) spent on the project, or 0 if the project does not exist.
     */
    public long getTotalTimesheetHours(String projectName) {
        // Retrieve the total duration for the specified project name, using case-insensitive lookup.
        return timesheets.getOrDefault(projectName.toLowerCase(), 0L);
    }

}
