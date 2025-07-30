package com.aaron.bibleversebootcamp;

import java.util.Scanner;
import java.io.FileReader;

import com.google.gson.*;
import com.aaron.bibleversebootcamp.model.*; // Importing the classes that model different parts of the bible

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static BibleService bibleService = new BibleService();
    public static Bible userBible = null;

    public static String userTranslation;
    public static String userTranslationLanguage;

    public static void main(String[] args) {

        clearScreen(); // Resets the Terminal
        intro();
        getUserTranslation();
        homeScreen();

        
    }
    
    // These methods have to do with Terminal Interface
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void intro() {
        // Intro of the program - Pretty self explanatory so idk why this would need a comment. Oh also if a future recruiter is looking at this comment please go to a newer project. This is one of my oldest projects. Also gimme a job.

        System.out.println("                   Welcome to Bible Verse Bootcamp");
        System.out.println("                   -------------------------------");
        System.out.println("Your word is a lamp for my feet, a light on my path. Psalm 119:105 (NIV)");
        System.out.println();
        System.out.println();
    }  
    public static void homeScreen() {
        // Where the user chooses what to do

        System.out.println("What would you like to do?");
        System.out.println();
        System.out.println("1. Add new verses");
        System.out.println("2. View saved verses");
        System.out.println("3. Look up a verse");
        System.out.println("4. Practice saved verses");

        String userChoice = scanner.nextLine();
        boolean validInput = false;

        while(!validInput) {
            switch(userChoice){
                case "1":
                    addVerses();
                    validInput = true;
                    break;
                case "2":
                    viewVerses();
                    validInput = true;
                    break;
                case "3":
                    referenceVerses();
                    validInput = true;
                    break;
                case "4":
                    practiceVerses();
                    validInput = true;
                    break;
                default:    
                    System.out.println("Invalid choice. Please pick a number between 1-4");
                    break;

            }
        }
        System.out.println();
    }
    
    // These methods have to do with API and Logic
    public static void getUserTranslation() {
            
        // Gets User Translation + Language
        System.out.println("What Bible Translation would you like to use?");
        userTranslation = scanner.nextLine();
        System.out.println("What language is it in? English, Spanish, Chinese etc.");
        userTranslationLanguage = scanner.nextLine();

        while(userBible == null) {
            // API Call
            userBible = bibleService.getBibleTranslation(userTranslation, userTranslationLanguage);
    
            // Checks if this is the desired translation.
            System.out.println("Result found for: " + userBible.name + " (" + userBible.abbreviation + ")");
            System.out.println("Is this the bible you're look for? (y/n)");
    
            boolean correctBible = false;
    
            while(!correctBible) {
                String userValidation = scanner.nextLine().strip().toLowerCase();
                if(userValidation.equals("y")) {
                    correctBible = true;
                } else if(userValidation.equals("n")) {
                    System.out.println("Please try again then.");
                    break;
                } else {
                    System.out.println("You have inputed something other than 'y' or 'n'");
                }
            }
        }
    }
    public static String getBibleID() {
        return userBible.id;
    }
    public static String verseShortener(String bookName, int chapterNumber, int verseNumber) throws Exception{
        // This method shortens something like Genesis 1:1 to GEN.1.1

        bookName = bookName.strip();
        String bookAbbreviation = "";

        JsonArray books = JsonParser.parseReader(new FileReader("BookAbbreviation.json")).getAsJsonArray();
        for(JsonElement element : books) {
            JsonObject object = element.getAsJsonObject();
            if(object.get("name").getAsString().equalsIgnoreCase(bookName)) {
                bookAbbreviation = object.get("abbr").getAsString();
            } 
        }

        if(bookAbbreviation.equals("")) {
            return "";
        }

        return bookAbbreviation.toUpperCase() + "." + chapterNumber + "." + verseNumber;
    }
    
    // These methods are the four the user can do in the home screen
    public static void addVerses() {

        System.out.println(getBibleID());
        
    }   
    public static void viewVerses() {
        System.out.println("This will be coded up later");
    }  
    public static void referenceVerses() {
        System.out.println("This will be coded up later");
    }  
    public static void practiceVerses() {
        System.out.println("This will be coded up later");
    }

}