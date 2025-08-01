// Package
package com.aaron.bibleversebootcamp;
// Classese that model a Bible, Bible Verses etc
import com.aaron.bibleversebootcamp.model.*;
// API
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
// Error
import java.io.IOException;
// Inputs/Reading
import java.io.InputStream;
import java.io.InputStreamReader;
import com.google.gson.*;

public class BibleService {
    // This class runs everything that has to do with getting verses from the API
    public Bible currentBible;

    // API Stuff
    private static final String API_KEY = System.getenv("BIBLE_API_KEY"); 
    private static final String BASE_URL = "https://api.scripture.api.bible";
    

    public Gson gson = new Gson();

    public static void main(String[] args) {
        if (API_KEY == null) {
            // Make sure's the API is set
            System.out.println("Error: Please set BIBLE_API_KEY environment variable");
            System.out.println("Run: echo 'export BIBLE_API_KEY=your_key' >> ~/.zshrc && source ~/.zshrc");
            return;
        }
        
    }

    // API CALLING
    public static HttpResponse<String> APIRequest(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("api-key", API_KEY)
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response;
    }


    
    public Bible getBibleTranslation(String userTranslation, String userLanguage) {
        // This method is in charge of getting the bible the user wants"=
        
        try {
            HttpResponse<String> response = APIRequest(BASE_URL + "/v1/bibles");
            
            BibleResponse bibleResponse = gson.fromJson(response.body(), BibleResponse.class);
            
            // Iterates through available Translation to check for User's desired Translation.
            for(Bible bible : bibleResponse.data) {
                
                if((bible.abbreviation.toUpperCase().endsWith(userTranslation.toUpperCase()) || // Checks if the User's translation is at the end like KJV in engKJV
                bible.abbreviation.toUpperCase().startsWith(userTranslation.toUpperCase()) || // Checks if the User's translations is at the front like KJV in KJVeng
                bible.abbreviation.equalsIgnoreCase(userTranslation)) &&  // Checks if the user's translation exactly matches
                bible.language.name.equalsIgnoreCase(userLanguage))
                {
                    return bible;
                }
            }
            
            // Only runs if nothing is found.
            System.out.println("Your bible wasn't found.");
            System.out.println("Unfortunately because this is a small user project many known bible translations are not accessible.");
            System.out.println("Try instead using the KJV");
            return null;
            
        } catch (Exception e) {
            System.err.println("There's been an error: " + e.getMessage());
            return null;
        }
        
    } 

    // Getting Bible Verse
    public String verseFormater(String verseInput) throws Exception{
        // This method shortens something like Genesis 1:1 to GEN.1.1
        
        String book = getVerseBook(verseInput);
        int chapter = getVerseChapter(verseInput);
        int verse = getVerseNumber(verseInput);

        String bookAbbreviation = ""; // This gets returned

        
        InputStream is = getClass().getClassLoader().getResourceAsStream("book-abbreviations.json");
        JsonArray books = JsonParser.parseReader(new InputStreamReader(is)).getAsJsonArray();
        for(JsonElement element : books) {
            JsonObject object = element.getAsJsonObject();
            if(object.get("name").getAsString().equalsIgnoreCase(book)) {
                bookAbbreviation = object.get("abbr").getAsString();
            } 
        }

        if(bookAbbreviation.equals("")) {
            return "";
        }

        return bookAbbreviation.toUpperCase() + "." + chapter + "." + verse;
    }
    
    // Gets different values given a verse string like "Romans 5:22"
    public String getVerseText(String verseInput) throws Exception {
        
        // Gets API Responese for Verse Request
        String bibleID = currentBible.id;
        String verseID = verseFormater(verseInput);
        String verseResponse = BibleService.APIRequest(BASE_URL + "/v1/bibles/" + bibleID + "/verses/" + verseID).body();

        // Turns API Response to readable verse
        JsonObject obj = JsonParser.parseString(verseResponse).getAsJsonObject();
        JsonObject data = obj.getAsJsonObject("data");

        if (data == null || !data.has("content")) {
            return "";
        }

        String verseText = data.get("content").getAsString();
        verseText = verseText.replaceAll("<[^>]*>","").trim(); // Removes HTML
        verseText = verseText.replaceFirst("^\\d+", "").trim(); // Removes Digits in the front;

        return verseText + "\n -" + verseInput;

    }
    public String getVerseBook(String verseInput) throws Exception {
        // Returns the book name of a verse string  ex: Galatians 5:20 --> Galatians
        String book;
        
        if(Character.isDigit(verseInput.charAt(0))) {
            book = verseInput.split(" ")[0] + " " + verseInput.split(" ")[1];
        } else {
            book = verseInput.split(" ")[0];
        }

        return book;
    }
    public int getVerseChapter(String verseInput) throws Exception {
        int chapter;

        if(Character.isDigit(verseInput.charAt(0))) {
            chapter = Integer.parseInt(verseInput.split(" ")[2].split(":")[0]);
        } else {
            chapter = Integer.parseInt(verseInput.split(" ")[1].split(":")[0]);
        }
        return chapter;
    }
    public int getVerseNumber(String verseInput) throws Exception {
        int verse;
        if(Character.isDigit(verseInput.charAt(0))) {
            verse = Integer.parseInt(verseInput.split(" ")[2].split(":")[1]);
        } else {
            verse = Integer.parseInt(verseInput.split(" ")[1].split(":")[1]);
        }
        return verse;
    }
}


