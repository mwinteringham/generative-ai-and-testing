package com.example.datamanager;

import java.util.List;

public class Prompt {

    private String model;

    private List<Message> messages;

    public Prompt(String model, List<Message> messages) {
        this.model = model;
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
