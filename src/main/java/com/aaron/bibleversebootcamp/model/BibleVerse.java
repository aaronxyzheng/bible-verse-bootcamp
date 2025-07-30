package com.aaron.bibleversebootcamp.model;

public class BibleVerse {
    String bookName;
    int chapterNumber;
    int verseNumber;
    String verseContent;
    String translation;

    public BibleVerse(String bookName, int chapterNumber, int verseNumber, String verseContent, String translation) {
        this.bookName = bookName;
        this.chapterNumber = chapterNumber;
        this.verseNumber = verseNumber;
        this.verseContent = verseContent;
        this.translation = translation;
    }

    @Override
    public String toString() {
        return verseContent + "\n - " + bookName + " " + chapterNumber + ":" + verseNumber + " (" + translation + ")";
    }
}



