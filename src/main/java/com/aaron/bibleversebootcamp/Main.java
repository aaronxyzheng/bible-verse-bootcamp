package com.aaron.bibleversebootcamp;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        clearScreen();
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
    }
    
    public static void addVerses() {
        System.out.println("This will be coded up later");
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