package com.example;

public class CopilotExample {

    //Method to check if login credentials match expected output
    public String login(String username, String password) {
        if (username.equals("test") && password.equals("test")) {
            return "Welcome test";
        } else {
            return "Invalid credentials";
        }
    }
}
