package com.aaron.bibleversebootcamp;

import com.aaron.bibleversebootcamp.model.*;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static BibleService bibleService = new BibleService();
    public static FileService fileService = new FileService();

    public static String userTranslation;
    public static String userTranslationLanguage;

    public static void main(String[] args) {

        fileService.initializeOnStartup();

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

        while(bibleService.currentBible == null) {
            // API Call
            bibleService.currentBible = bibleService.getBibleTranslation(userTranslation, userTranslationLanguage);
    
            if(bibleService.currentBible == null) {
                continue;
            }

            // Checks if this is the desired translation.
            System.out.println("Result found for: " + bibleService.currentBible.name + " (" + bibleService.currentBible.abbreviation + ")");
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

    // These methods are the four the user can do in the home screen
    public static void addVerses() {
        // Adds a verse to storage

        boolean verseSaved = false;

        while(!verseSaved) {
            boolean validInput = false;

            System.out.println("What verse would you like to add to your saved verses? Format: John 3:16");
            String verseReference = scanner.nextLine();
    
    
            try {
                String bookName = bibleService.getVerseBook(verseReference);
                int chapterNumber = bibleService.getVerseChapter(verseReference);
                int verseNumber = bibleService.getVerseNumber(verseReference);
                String verseContent = bibleService.getVerseText(verseReference).strip();
    
                BibleVerse verse = new BibleVerse(bookName, chapterNumber, verseNumber, verseContent, userTranslation);

                // Asks User if this is correct verse        
                while(!validInput) {

                System.out.println("Is this the correct verse: ");
                System.out.println(verse.getVerseText());
                System.out.print("Reply (y,n): ");
                String userValidation = scanner.nextLine().toLowerCase().strip();

                    if(userValidation.equals("y")) {
                        fileService.saveVerse(verse);
                        verseSaved = true;
                        validInput = true;
                        System.out.println("Verse has been Saved!");
                    } else if (userValidation.equals("n")) {
                        validInput = true;
                    } else {
                        System.out.println("Please enter either y or n.");
                    } 
                }
            } catch (Exception e) {
                e.printStackTrace();
            }    

        }
    }   
    public static void viewVerses() {
        System.out.println("This will be coded up later");
    }  
    public static void referenceVerses() {
        
        String verseResponse = "";

        while (verseResponse.equals("")){

            System.out.println("What verse would you look to reference? Format: John 3:16");
            String userVerse = scanner.nextLine(); 

            try {
                verseResponse = bibleService.getVerseText(userVerse);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.out.println("Please try again.");
            }

            System.out.println(" ");
            System.out.println(verseResponse);
        }
    }  
    public static void practiceVerses() {
        System.out.println("This will be coded up later");
    }

}