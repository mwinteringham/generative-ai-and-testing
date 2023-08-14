package com.example.datamanager;

import com.google.gson.Gson;
import okhttp3.*;

import java.util.ArrayList;

public class GPTClient {

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private String apiKey;
    public GPTClient(String apiKey){
        this.apiKey = apiKey;
    }

    public String prompt(String prompt) throws Exception{
        Prompt promptObject = new Prompt("gpt-3.5-turbo", new ArrayList<>() {{{
            add(new Message("user", prompt));
        }}});

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(promptObject));

        Request request = new Request.Builder()
                .url(API_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        System.out.println(responseBody);

        return new Gson().fromJson(responseBody, GPTResponse.class).getChoices().get(0).getMessage().getContent();
    }

}
