package com.aaron.bibleversebootcamp.model;

public class UserSettings {
    private String bibleName; // Name of the user's bible
    private String bibleID;

    public UserSettings() {
        
    }

    public UserSettings(String bibleName, String bibleID) {
        this.bibleName = bibleName;
        this.bibleID = bibleID;
    }

    public void setBibleName(String bibleName) {
        this.bibleName = bibleName;
    }

    public void setBibleID(String bibleID) {
        this.bibleID = bibleID;
    }

    public String getBibleName() {
        return this.bibleName;
    }

    public String getBibleID() {
        return this.bibleID;
    }
}
