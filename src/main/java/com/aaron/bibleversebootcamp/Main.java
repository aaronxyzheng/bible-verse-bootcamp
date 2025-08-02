package com.aaron.bibleversebootcamp;

import com.aaron.bibleversebootcamp.model.*;

import java.io.IOException;
import java.util.*; // For List, Collections, Random

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

        loadSettings(); // Checks for saved settings and gets Bible Translation + ID

        while(true){
            homeScreen();
        }    
    }

    // Loads user-settings.json for user translation preferences;
    public static void loadSettings() {
        try {
            UserSettings userSettings = fileService.loadSettings();
            if(userSettings == null) {
                getUserTranslation();
            } else {
                System.out.println("Saved bible translation setting has been found!"); 
                System.out.println("Would you like to use: " +  userSettings.getBibleName());
                while(true) {
                    System.out.print("Input y or n: ");
    
                    String userInput = scanner.nextLine().toLowerCase().strip();
                    if(userInput.equals("y")) {
                        bibleService.currentBible = new Bible();
                        bibleService.currentBible.setID(userSettings.getBibleID());
                        break;
                    } else if(userInput.equals("n")) {
                        getUserTranslation();
                        break;
                    } else {
                        System.out.println("You have inputed something other than y or n!");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("There was an error");
            e.printStackTrace();
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

        boolean validInput = false;

        while(!validInput) {

            System.out.println("What would you like to do?");
            System.out.println();
            System.out.println("1. Add new verses");
            System.out.println("2. View saved verses");
            System.out.println("3. Remove a saved verse");
            System.out.println("4. Look up a verse");
            System.out.println("5. Practice saved verses");

            String userChoice = scanner.nextLine();
            clearScreen();

            switch(userChoice){
                case "1":
                    addVerses();
                    validInput = true;
                    break;
                case "2":
                    viewSavedVerses();
                    validInput = true;
                    break;
                case "3":
                    removeSavedVerses();
                    validInput = true;
                    break;
                case "4":
                    referenceVerses();
                    validInput = true;
                    break;
                case "5":
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
    public static void returnHome() {
        // Method that allows the user to input when they want to go home. 
        System.out.println("Input anything to go back to home.");
        @SuppressWarnings("unused")
        String unused = scanner.nextLine();
    }

    // These methods have to do with API and Logic
    public static void getUserTranslation() {
            
        while(bibleService.currentBible == null) {
            // Gets User Translation + Language
            System.out.println("What Bible Translation would you like to use?");
            userTranslation = scanner.nextLine();
            System.out.println("What language is it in? English, Spanish, Chinese etc.");
            userTranslationLanguage = scanner.nextLine();


            // API Call
            bibleService.currentBible = bibleService.getBibleTranslation(userTranslation, userTranslationLanguage);
    
            if(bibleService.currentBible == null) {
                continue;
            }

            // Checks if this is the desired translation.
            System.out.println("Result found for: " + bibleService.currentBible.getName() + " (" + bibleService.currentBible.getAbbreviation() + ")");
            System.out.println("Is this the bible you're look for? (y/n)");
    
            boolean correctBible = false;
    
            while(!correctBible) {
                String userValidation = scanner.nextLine().strip().toLowerCase();
                if(userValidation.equals("y")) {

                    // Asks if User wants to save settings to user-settions
                    while(true) {
                        System.out.print("Would you like to save these settings for easier use next time? (y,n): ");
                        String saveSetting = scanner.nextLine().toLowerCase().strip();
    
                        if(saveSetting.equals("y")) {
                            try {
                                fileService.setSettings(bibleService.currentBible.getName(), bibleService.currentBible.getID());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;
                        } else if(saveSetting.equals("n")) {
                            break;
                        } else {
                            System.out.println("You've inputed something other than y or n.");
                        }
                    }

                    correctBible = true;
                } else if(userValidation.equals("n")) {
                    System.out.println("Please try again then.");
                    bibleService.currentBible = null;
                    break;
                } else {
                    System.out.println("You have inputed something other than 'y' or 'n'");
                }
            }
        }
    }

    // These methods are the five the user can do in the home screen
    public static void addVerses() {
        // addVerses() adds a verse to be saved in saved-verses.json

        boolean doneSaving = false; // Makes sure user has saved as many verses as they want

        while(!doneSaving) {
            boolean validInput = false;

            System.out.println("What verse would you like to add to your saved verses? Format: John 3:16");
            String verseReference = scanner.nextLine();

            try {
                // Gets values of the verse for the API Request
                String bookName = bibleService.getVerseBook(verseReference);
                int chapterNumber = bibleService.getVerseChapter(verseReference);
                int verseNumber = bibleService.getVerseNumber(verseReference);
                String verseContent = bibleService.getVerseText(verseReference).strip().split("\n")[0];
                // Makes sure verse Actually Exists
                if(verseContent.equals("")) { 
                    System.out.println("");
                    System.out.println("That verse does not exist.");
                    return;
                }
                // Creates Bible Verse Object
                BibleVerse verse = new BibleVerse(bookName, chapterNumber, verseNumber, verseContent, userTranslation);

                // Asks User if this is correct verse        
                while(!validInput) {

                System.out.println("Is this the correct verse: ");
                System.out.println(verse.getVerseText());
                System.out.print("Reply (y,n): ");
                String userValidation = scanner.nextLine().toLowerCase().strip();

                    if(userValidation.equals("y")) {
                        // If the verse is correct save the verse
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
                                doneSaving = true;
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
        returnHome();
    }   
    public static void viewSavedVerses() {
        
        try {
            List<BibleVerse> savedVerses = fileService.listVerses(); // Creates Object that is List of the saved Bible Verses
            
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
            }

            // Let's User Go Back to Home
            System.out.println("");
            returnHome();

        } catch (IOException e) {
            e.printStackTrace();
        }


        
        
    }  
    public static void removeSavedVerses() {
        try {
            boolean doneSaving = false;

            while(!doneSaving) {
                // Printing out the verses stored
                List<BibleVerse> savedVerses = fileService.listVerses(); // Creates Object that is List of the saved Bible Verses
                if(savedVerses.isEmpty()) {
                    System.out.println("You have 0 verses saved so you can't delete anything");
                    break;
                }

                for(BibleVerse verse : savedVerses) {
                    String verseCitation = verse.getBookName() + " " + verse.getChapterNumber() + ":" + verse.getVerseNumber();
                    System.out.println(verseCitation + "  -  " + verse.getVerseText() + " (" + verse.getVerseTranslation() + ")");
                }
                // Getting the verse that user wants to remove
                System.out.println();
                System.out.println("These are the verses you have saved. Which one would you like to remove? Format: John 3:16");
                String verseToRemove = scanner.nextLine();
                // Removing the Verse
                fileService.removeVerse(verseToRemove);

                //Making sure the verse got removed
                List<BibleVerse> verseList = fileService.listVerses();
                if(verseList.size() == savedVerses.size()) {
                    System.out.println("There was an error. Are you sure you entered the verse correctly?");
                } else {
                    System.out.println("Verse removed");
                }

                // Asking if User wants to remove another verse.
                System.out.println("Would you like to remove any other verse? (y,n)");
                String userInput = scanner.nextLine().toLowerCase().strip(); 

                while(true) {
                    if(userInput.equals("y")) {
                        clearScreen();
                        break;
                    } else if(userInput.equals("n")) {
                        doneSaving = true;
                        break;
                    } else {
                        System.out.println("You entered something other than y or n.");
                    }
                }
            }

            returnHome();


        } catch (Exception e) {
            System.out.println("Something went wrong.");
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

        returnHome();

    }  
    public static void practiceVerses() {
        
        try {
            boolean validInput = false;
            boolean moreThanThree = true; // Sees if there are more than three verses

            // Checks to see if there are 3 verses or more saved
            List<BibleVerse> verseList = fileService.listVerses();
            if(verseList.size() < 3) {
                System.out.println("Quizzer mode requires 3 or more verses saved! Please add more verses. ");
                moreThanThree = false;
            }

            // Practice Mode Intro.
            if(moreThanThree) {
                System.out.println("Welcome to Practice Mode how would you like to play?");
    
                System.out.println("1. Easy: Fill in the Blanks");
                System.out.println("2. Medium: Given Verse --> Write Citation");
                System.out.println("3. Hard: Given Citation --> Write Verse");
            }
            
            // Makes the user input a valid number and calls the correct method.
            while(!validInput && moreThanThree) {
            System.out.println("Please input 1, 2, or 3: ");
    
            String userInput = scanner.nextLine().strip();
    
            switch(userInput) {
                case "1":
                    fillTheBlank(verseList);
                    validInput = true;
                    break;
                case "2":
                    giveCitation(verseList);
                    validInput = true;
                    break;
                case "3":
                    giveVerseText(verseList);
                    validInput = true;
                    break;
                default:
                    System.out.println("You have inputed something other than 1, 2, or 3.");
                    break;
    
            }

            returnHome();
        }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    // Methods for the Verse Quizzing/Training portion
    public static void fillTheBlank(List<BibleVerse> verseList) {
        Collections.shuffle(verseList);

        Random random = new Random();
        int score = 0;
        int questions = verseList.size();

        for(BibleVerse verse : verseList) {
            String verseText = verse.getVerseText();
            String[] verseTextArray = verseText.split(" ");

            int blankStringNumber = random.nextInt(verseTextArray.length);
            StringBuilder fullVerse = new StringBuilder();
            String answer = verseTextArray[blankStringNumber];

            // Buildes Verse Text with blank
            for(int i = 0; i < verseTextArray.length; i++) {
                if(i > 0) fullVerse.append(" ");

                if(i == blankStringNumber) {
                    fullVerse.append("___");
                } else {
                    fullVerse.append(verseTextArray[i]);
                }
            }
            // Prints Verse Text with blank
            System.out.println(fullVerse.toString());
            System.out.println("What is the word in the blank: ");
            String userGuess = scanner.nextLine();
            // Removes punctuations when comparing
            String cleanAnswer = answer.replaceAll("[^a-zA-Z]", "");
            String cleanGuess = userGuess.replaceAll("[^a-zA-Z]", "");
            // Validates User's Answer.
            if(cleanGuess.equalsIgnoreCase(cleanAnswer)) {
                System.out.println("That is correct!");
                score += 1;
            } else {
                System.out.println("Incorrect. The correct answer is: " + cleanAnswer);
            }
        }

        System.out.println();
        System.out.println("You got: " + score + "/" + questions + ".");


    }
    public static void giveCitation(List<BibleVerse> verseList) {
        Collections.shuffle(verseList);

        int score = 0;
        int questions = verseList.size();

        for (BibleVerse verse : verseList) {
            try {
                System.out.println();
                System.out.println(verse.getVerseText());
                System.out.println("Which verse is this? Format: John 3:16");
                System.out.print("input: ");
    
                String userGuess = scanner.nextLine();
                if(bibleService.getVerseBook(userGuess).equalsIgnoreCase(verse.getBookName()) &&
                   bibleService.getVerseChapter(userGuess) == verse.getChapterNumber() &&
                   bibleService.getVerseNumber(userGuess) == verse.getVerseNumber()) {
                   
                    System.out.println("Correct!");
                    score += 1;
                } else {
                    String correctAnswer = verse.getBookName() + " " + verse.getChapterNumber() + ":" + verse.getVerseNumber();
                    System.out.println("Incorrect. The correct answer is: " + correctAnswer);
                }

            } catch (Exception e) {
                System.out.println("There's been an error!");
                e.printStackTrace();
            }
        }

        System.out.println();
        System.out.println("You got: " + score + "/" + questions + ".");
    }
    public static void giveVerseText(List<BibleVerse> verseList) {
        Collections.shuffle(verseList);

        int score = 0;
        int questions = verseList.size();

        for(BibleVerse verse : verseList) {
            try {

                System.out.println();
                System.out.println("What is the text for: " + verse.getBookName() + " " + verse.getChapterNumber() + ":" + verse.getVerseNumber());
                System.out.print("Your Answer: ");

                String userGuess = scanner.nextLine();
                String answer = verse.getVerseText();

                String cleanedGuess = userGuess.replaceAll("[^a-zA-Z0-9]", "");
                String cleanedAnswer = answer.replaceAll("[^a-zA-Z0-9]", "");

                if(cleanedGuess.equalsIgnoreCase(cleanedAnswer)) {
                    System.out.println("Correct.");
                    score += 1;
                } else {
                    System.out.println("Incorrect.");
                    System.out.println("Real Answer: " + answer);
                }

            } catch (Exception e) {
                System.out.println("There's been an error");
            }
        }

        System.out.println();
        System.out.println("You got: " + score + "/" + questions + ".");
    }
}