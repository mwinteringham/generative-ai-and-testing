package com.example.datamanager;

import java.util.List;

public class ChatGPTResponse {

    private List<ChatGPTChoice> choices;

    public ChatGPTResponse(List<ChatGPTChoice> choices) {
        this.choices = choices;
    }

    public List<ChatGPTChoice> getChoices() {
        return choices;
    }
}
