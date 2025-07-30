package com.aaron.bibleversebootcamp;

import java.util.Scanner;

import com.aaron.bibleversebootcamp.model.*; // Importing the classes that model different parts of the bible

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static BibleService bibleService = new BibleService();

    public static void main(String[] args) {

        clearScreen(); // Resets the Terminal
        intro();
        homeScreen();

        
    }
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
    
    public static String getBibleID() {
        // This method gets the API.bible ID for the User's desired Bible Translation.

        // Values to make sure the method repeats until desired translation is found.
        Bible user_bible = null;
        boolean correctBible = false;

        while(user_bible==null) {
            // While no Bible is found 

            // Gets User Translation
            System.out.println("What Bible Translation would you like to use?");
            String userTranslation = scanner.nextLine();

            // Gets Translation Language - Reason: This is because there are translations that have the same abbreviation but different language. Like Thai KJV and the English KJV.
            System.out.println("What language is it in? English, Spanish, Chinese etc.");
            String userLanguage = scanner.nextLine();

            // Calls the Method to get the bible translation
            user_bible = bibleService.getBibleTranslations(userTranslation, userLanguage);

            // User_bible == null if there's an error or no bible is found
            if(user_bible == null) { 
                continue;
            }

            // Checks if this is the desired translation.
            System.out.println("Result found for: " + user_bible.name + " (" + user_bible.abbreviation + ")");
            System.out.println("Is this the bible you're look for? (y/n)");

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

        return user_bible.id;

    }

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