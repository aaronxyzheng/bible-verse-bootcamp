package com.aaron.bibleversebootcamp;

import com.aaron.bibleversebootcamp.model.*;

import java.io.*;
import java.util.*;
import java.lang.reflect.Type;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class FileService {
    private static final String DATA_DIRECTORY = "data";
    private static final String USER_SETTINGS = "user-settings.json";
    private static final String SAVED_VERSES = "saved-verses.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public UserSettings loadSettings() throws Exception{
        try(FileReader reader = new FileReader(DATA_DIRECTORY + "/" + USER_SETTINGS)) {
            UserSettings userSettings = gson.fromJson(reader, UserSettings.class);
            return userSettings;
        } catch (Exception e) {
            throw e;
        }
    }
    public void setSettings(String bibleName, String bibleID) throws Exception {
        UserSettings userSettings = new UserSettings(bibleName, bibleID);
        try(FileWriter writer = new FileWriter(DATA_DIRECTORY + "/" + USER_SETTINGS)) {
            gson.toJson(userSettings, writer);
        } catch (Exception e) {
            throw e;
        }
    }

    public void initializeOnStartup() {
        // Creates the needed directories and files in case they don't exist
        File dataFolder = new File(DATA_DIRECTORY);
        File savedVersesFile = new File(dataFolder, SAVED_VERSES);
        if(!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        if(!savedVersesFile.exists()) {
            try {
                savedVersesFile.createNewFile();
            } catch (IOException error) {
                error.printStackTrace();
            }
            
        }
    }
    
    // Methods that have to do with saved-veres.json and bible verses
    public void saveVerse(BibleVerse bibleVerse) throws IOException {
        List<BibleVerse> verses = listVerses();

        if(verses == null) {
            verses = new ArrayList<BibleVerse>();
        }

        verses.add(bibleVerse);
        try (Writer writer = new FileWriter(DATA_DIRECTORY + "/" + SAVED_VERSES)){
            gson.toJson(verses, writer);
        }
    }
    public void removeVerse(String verseToRemove) throws Exception {
        String bookName = Main.bibleService.getVerseBook(verseToRemove);
        int chapterNumber = Main.bibleService.getVerseChapter(verseToRemove);
        int verseNumber = Main.bibleService.getVerseNumber(verseToRemove);

        try (Reader reader = new FileReader(DATA_DIRECTORY + "/" + SAVED_VERSES);){
            Type listType = new TypeToken<List<BibleVerse>>() {}.getType();
            List<BibleVerse> savedVerses = gson.fromJson(reader, listType);

            savedVerses.removeIf(verse -> 
                verse.getBookName().equals(bookName) && 
                verse.getChapterNumber() == chapterNumber && 
                verse.getVerseNumber() == verseNumber
            );
            
            try (Writer writer = new FileWriter(DATA_DIRECTORY + "/" + SAVED_VERSES)) {
                gson.toJson(savedVerses, writer);
            }
        } 
    }
    public List<BibleVerse> listVerses() throws IOException {
        // Returns a List of BibleVerses

        File file = new File(DATA_DIRECTORY + "/" + SAVED_VERSES);
        if(!file.exists()) return new ArrayList<>(); // Sends back empty Array if there's no file. But there should be a file cause of initialize On Startup().
        if(file.length() == 0) return new ArrayList<>(); // If nothing in it
        try(Reader reader = new FileReader(DATA_DIRECTORY + "/" + SAVED_VERSES)) {
            Type listType = new TypeToken<List<BibleVerse>>() {}.getType();
            return gson.fromJson(reader, listType);
        }  
    }
}