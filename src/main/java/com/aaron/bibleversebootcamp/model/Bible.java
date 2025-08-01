package com.aaron.bibleversebootcamp.model;

public class Bible {
    private String id;
    private Language language;
    private String name;
    private String abbreviation;

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
