package com.aaron.bibleversebootcamp;

import com.aaron.bibleversebootcamp.model.*;

import java.util.Scanner;
import java.io.IOException;
import java.util.List;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static BibleService bibleService = new BibleService();
    public static FileService fileService = new FileService();

    public static String userTranslation;
    public static String userTranslationLanguage;

    public static void main(String[] args) {

        fileService.initializeOnStartup();

        clearScreen();
        intro();
        getUserTranslation();
        clearScreen();
        while(true){
            homeScreen();
        }
        

        
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
        // addVerses() adds a verse to be saved in saved-verses.json

        boolean verseSaved = false; // Makes sure user has saved as many verses as they want

        while(!verseSaved) {
            boolean validInput = false;

            System.out.println("What verse would you like to add to your saved verses? Format: John 3:16");
            String verseReference = scanner.nextLine();
    
    
            try {
                String bookName = bibleService.getVerseBook(verseReference);
                int chapterNumber = bibleService.getVerseChapter(verseReference);
                int verseNumber = bibleService.getVerseNumber(verseReference);
                String verseContent = bibleService.getVerseText(verseReference).strip().split("\n")[0];
    
                if(verseContent.equals("")) {
                    System.out.println("");
                    System.out.println("That verse does not exist.");
                    return;
                }

                BibleVerse verse = new BibleVerse(bookName, chapterNumber, verseNumber, verseContent, userTranslation);

                // Asks User if this is correct verse        
                while(!validInput) {

                System.out.println("Is this the correct verse: ");
                System.out.println(verse.getVerseText());
                System.out.print("Reply (y,n): ");
                String userValidation = scanner.nextLine().toLowerCase().strip();

                    if(userValidation.equals("y")) {
                        fileService.saveVerse(verse);
                        validInput = true;
                        System.out.println("Verse has been Saved!");

                        // Checks if User Wants to input another verse
                        boolean validInput2 = false;
                        while(!validInput2) {


                            System.out.println("Would you like to save another verse? (y,n)");
                            String saveAgain = scanner.nextLine().toLowerCase().strip();

                            if(saveAgain.equals("y")) {
                                validInput2 = true;
                                continue;
                            } else if(saveAgain.equals("n")) {
                                validInput2 = true;
                                verseSaved = true;
                            } else {
                                System.out.println("You have inputed something other than y or n");
                            }
                        }
                        

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
        System.out.println("Input anything to go back to home.");
        @SuppressWarnings("unused")
        String unused = scanner.nextLine();
    }   
    public static void viewVerses() {
        

        try {
            List<BibleVerse> savedVerses = fileService.listVerses();
            System.out.println("You have " + savedVerses.size() + " verses saved");
            while(true) {
                System.out.println("Would you like to see all your verses? (y,n)");
                String userInput = scanner.nextLine();

                if(userInput.equals("y")) {
                    for(BibleVerse verse: savedVerses){
                        String verseCitation = verse.getBookName() + " " + verse.getChapterNumber() + ":" + verse.getVerseNumber();
                        System.out.println(verseCitation + "  -  " + verse.getVerseText() + " (" + verse.getVerseTranslation() + ")");
                    }
                    break;
                } else if(userInput.equals("n")) {
                    System.out.println("Ok.");
                    break;
                } else {
                    System.out.println("You entered something other than y or n.");
                }
            
            // Let's User Go Back to Home
            System.out.println("Input anything to go back to home.");
            @SuppressWarnings("unused")
            String unused = scanner.nextLine();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        
        
    }  
    public static void referenceVerses() {
        
        String verseResponse = "";

        while (verseResponse.equals("")){

            System.out.println("What verse would you look to reference? Format: John 3:16");
            String userVerse = scanner.nextLine(); 

            try {
                verseResponse = bibleService.getVerseText(userVerse);
                if(verseResponse.equals("")) {
                    verseResponse = "That verse does not exist.";
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.out.println("Please try again.");
            }

            System.out.println(" ");
            System.out.println(verseResponse);
            System.out.println(" ");

            while(true) {
                System.out.println("Would you like to reference another verse? (y,n)");
                String userInput = scanner.nextLine().toLowerCase().strip();
                if(userInput.equals("y")) {
                    verseResponse = "";
                    break;
                } else if(userInput.equals("n")) {
                    break;
                } else {
                    System.out.println("You've inputed something other than y or n.");
                }
            }
        }

        System.out.println("Input anything to go back to home.");
        @SuppressWarnings("unused")
        String unused = scanner.nextLine();

    }  
    public static void practiceVerses() {
        System.out.println("This will be coded up later");
    }

}