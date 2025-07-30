package com.aaron.bibleversebootcamp;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

import com.aaron.bibleversebootcamp.model.*;


import com.google.gson.Gson;

public class BibleService {
    // This class runs everything that has to do with getting verses from the API

    private static final String API_KEY = System.getenv("BIBLE_API_KEY"); 
    private static final String baseURL = "https://api.scripture.api.bible";
    // Sets the API

    public static void main(String[] args) {
        if (API_KEY == null) {
            // Make sure's the API is set
            System.out.println("Error: Please set BIBLE_API_KEY environment variable");
            System.out.println("Run: echo 'export BIBLE_API_KEY=your_key' >> ~/.zshrc && source ~/.zshrc");
            return;
        }
        
    }

    public Bible getBibleTranslations(String userTranslation, String userLanguage) {
        // This method is in charge of getting a list of available bibles to reference from.

        try {
            // Calling API
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseURL + "/v1/bibles"))
                .header("api-key", API_KEY)
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Making a Bible Response object to model this
            Gson gson = new Gson();
            BibleResponse bibleResponse = gson.fromJson(response.body(), BibleResponse.class);

            for(Bible bible : bibleResponse.data) {

                if(bible.abbreviation.equalsIgnoreCase(userTranslation) && bible.language.name.equalsIgnoreCase(userLanguage)) {
                    return bible;
                }
            }

            System.out.println("Your bible wasn't found.");
            System.out.println("Unfortunately because this is a small user project many known bible translations are not accessible.");
            System.out.println("Try instead using the KJV");

            return null;
        } catch (Exception e) {
            System.err.println("There's been an error: " + e.getMessage());
            return null;
        }
        
    } 
}

