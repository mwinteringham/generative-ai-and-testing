package com.example.datamanager;

public class ChatGPTChoice {

    private int index;

    private Message message;

    private String finish_reason;

    public ChatGPTChoice(int index, Message message, String finish_reason) {
        this.index = index;
        this.message = message;
        this.finish_reason = finish_reason;
    }

    public int getIndex() {
        return index;
    }

    public Message getMessage() {
        return message;
    }
}
