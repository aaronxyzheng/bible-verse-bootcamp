package com.aaron.bibleversebootcamp;

import com.aaron.bibleversebootcamp.model.*;

import java.io.File;
import java.io.IOException;

public class FileService {
    private static final String DATA_DIRECTORY = "data";
    private static final String SAVED_VERSES = "saved-verses.json";

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
                error.getStackTrace();
            }
            
        }
    }
}