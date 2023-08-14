package com.example.datamanager;

import java.util.List;

public class GPTResponse {

    private List<GPTChoice> choices;

    public GPTResponse(List<GPTChoice> choices) {
        this.choices = choices;
    }

    public List<GPTChoice> getChoices() {
        return choices;
    }
}
