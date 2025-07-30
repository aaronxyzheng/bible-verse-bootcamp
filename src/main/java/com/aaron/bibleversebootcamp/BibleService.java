package com.aaron.bibleversebootcamp;

public class BibleService {
    // This class runs everything that has to do with getting verses from the API

    private static final String API_KEY = System.getenv("BIBLE_API_KEY"); 
    // Sets the API

    public static void main(String[] args) {
        if (API_KEY == null) {
            // Make sure's the API is set
            System.out.println("Error: Please set BIBLE_API_KEY environment variable");
            System.out.println("Run: echo 'export BIBLE_API_KEY=your_key' >> ~/.zshrc && source ~/.zshrc");
            return;
        }
        
    }
}

