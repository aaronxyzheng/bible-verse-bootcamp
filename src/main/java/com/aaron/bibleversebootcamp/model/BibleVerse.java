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

    public String getVerseText() {
        return this.verseContent;
    }

    @Override
    public String toString() {
        return verseContent + "\n - " + bookName + " " + chapterNumber + ":" + verseNumber + " (" + translation + ")";
    }
}



