package com.aaron.bibleversebootcamp.model;

public class BibleVerse {
    private String bookName;
    private int chapterNumber;
    private int verseNumber;
    private String verseContent;
    private String translation;

    public BibleVerse(String bookName, int chapterNumber, int verseNumber, String verseContent, String translation) {
        this.bookName = bookName;
        this.chapterNumber = chapterNumber;
        this.verseNumber = verseNumber;
        this.verseContent = verseContent;
        this.translation = translation;
    }

    // Getters 
    public String getBookName() {
        return this.bookName;
    }
    public int getChapterNumber() {
        return this.chapterNumber;
    }
    public int getVerseNumber() {
        return this.verseNumber;
    }
    public String getVerseText() {
        return this.verseContent;
    }
    public String getVerseTranslation() {
        return this.translation;
    }

    @Override
    public String toString() {
        return verseContent + "\n - " + bookName + " " + chapterNumber + ":" + verseNumber + " (" + translation + ")";
    }
}



