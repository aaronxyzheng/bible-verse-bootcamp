# Bible Verse Bootcamp
*"Your word is a lamp for my feet, a light on my path." - Psalm 119:105 (NIV)*

A Java terminal application for referencing, memorizing and practicing Bible verses with interactive quiz modes.

## Features
### 📚 Verse Management 
-  **Add Verses**: Save your favorite Bible verses for later study 
-   **View Collection**: Browse all your saved verses 
-  **Remove Verses**: Clean up your collection as needed 
-  **Quick Reference**: Look up any Bible verse instantly 

### 🎯 Practice Modes 
-  **Easy**: Fill-in-the-blank questions 
-  **Medium**: Given a verse, identify the reference 
-  **Hard**: Given a reference, recite the verse 

### 🌍 Multi-Translation Support 
- Works with multiple Bible translations (KJV, WEB, etc.) -
-  Multi-language Bible Translations (English, Spanish, Chinese, and more) 
-  Saves your preferred translation for future sessions

## Prerequisites

- Java 17 or higher 
- Maven 3.6+ 
- Bible API key from [scripture.api.bible](https://scripture.api.bible)

## Installation & Setup

1.  **Clone the repository** 
```bash 
git clone https://github.com/yourusername/bible-verse-bootcamp.git 
cd bible-verse-bootcamp
```

2. **Get your API key**

-   Visit [scripture.api.bible](https://scripture.api.bible)
-   Sign up for a free account
-   Copy your API key
3. **Set up environment variable** **On macOS/Linux:**

```bash
echo 'export BIBLE_API_KEY=your_api_key_here' >> ~/.zshrc
source ~/.zshrc
```

**On Windows:**

```cmd
setx BIBLE_API_KEY "your_api_key_here"
```

4. **Build and run**
```bash
mvn compile exec:java
```


## Optimizations

- **User-Settings.json**  
  Caches the user's selected Bible translation and ID to avoid repeated API calls.

- **Book-Abbreviations.json**  
  Maps book names to abbreviations (e.g., *Matthew → Mat*) to prevent extra API requests for book IDs.


## Project Structure

```pgsql
.
├── data 
│   ├── saved-verses.json   // Saves User's Favorite Verses
│   └── user-settings.json  // Saves User's Favorite Bible Translation + ID
├── pom.xml 
├── src
│   └── main
│       ├── java/com/aaron/bibleversebootcamp
│       │   ├── Main.java // Entryway to Code
│       │   ├── BibleService.java // API + Bible Parsing
│       │   ├── FileService.java // Manages Files
│       │   └── model // All Classes
│       │       ├── Bible.java
│       │       ├── BibleResponse.java
│       │       ├── BibleVerse.java
│       │       ├── Language.java
│       │       └── UserSettings.java
│       └── resources
│           └── book-abbreviations.json
└── target
```



## What I Learned :)

- Working with REST APIs  
- Using Gson Class
- Reading and writing files with `FileReader` and `FileWriter`  
- Error handling  
- Using `TypeToken`  
- Deeper understanding of OOP  
- Basic regex


## Contributing

This is a personal learning project, but suggestions and feedback are welcome! Feel free to:

-   Open issues for bugs or feature requests
-   Submit pull requests for improvements
-   Share ideas for new practice modes

## Connect with Me

**Github:** aaronxyzheng (but if you're reading this you can already access my github account so IDK why'd you need this but oh well 🤷‍♂️)

