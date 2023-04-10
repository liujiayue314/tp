package vimification.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import vimification.common.core.GuiSettings;
import vimification.common.exceptions.DataConversionException;
import vimification.model.UserPrefs;

public class JsonUserPrefsStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonUserPrefsStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readUserPrefs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readUserPrefs(null));
    }

    private UserPrefs readUserPrefs(String userPrefsFileInTestDataFolder) throws IOException {
        Path prefsFilePath = addToTestDataPathIfNotNull(userPrefsFileInTestDataFolder);
        return new JsonUserPrefsStorage(prefsFilePath).readUserPrefs(prefsFilePath);
    }

    @Test
    public void readUserPrefs_missingFile_emptyResult() throws IOException {
        assertThrows(NoSuchFileException.class, () -> readUserPrefs("NonExistentFile.json"));
    }

    @Test
    public void readUserPrefs_notJsonFormat_exceptionThrown() {
        assertThrows(IOException.class, () -> readUserPrefs("NotJsonFormatUserPrefs.json"));
    }

    private Path addToTestDataPathIfNotNull(String userPrefsFileInTestDataFolder) {
        return userPrefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(userPrefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readUserPrefs_fileInOrder_successfullyRead() throws IOException {
        UserPrefs expected = getTypicalUserPrefs();
        UserPrefs actual = readUserPrefs("TypicalUserPref.json");
        assertEquals(expected, actual);
    }

    @Test
    public void readUserPrefs_valuesMissingFromFile_defaultValuesUsed() throws IOException {
        UserPrefs actual = readUserPrefs("EmptyUserPrefs.json");
        assertEquals(new UserPrefs(), actual);
    }

    @Test
    public void readUserPrefs_extraValuesInFile_extraValuesIgnored() throws IOException {
        UserPrefs expected = getTypicalUserPrefs();
        UserPrefs actual = readUserPrefs("ExtraValuesUserPref.json");

        assertEquals(expected, actual);
    }


    private UserPrefs getTypicalUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setGuiSettings(new GuiSettings(1000, 600, 270, 50));
        userPrefs.setTaskListFilePath(Paths.get(".vimification\\tasklist.json"));
        userPrefs.setMacroMapFilePath(Paths.get(".vimification\\macromap.json"));
        return userPrefs;
    }


    @Test
    public void savePrefs_nullPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserPrefs(null, "SomeFile.json"));
    }

    @Test
    public void saveUserPrefs_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveUserPrefs(new UserPrefs(), null));
    }

    /**
     * Saves {@code userPrefs} at the specified {@code prefsFileInTestDataFolder} filepath.
     */
    private void saveUserPrefs(UserPrefs userPrefs, String prefsFileInTestDataFolder) {
        try {
            new JsonUserPrefsStorage(addToTestDataPathIfNotNull(prefsFileInTestDataFolder))
                    .saveUserPrefs(userPrefs);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file", ioe);
        }
    }

    @Test
    public void saveUserPrefs_allInOrder_success() throws DataConversionException, IOException {

        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(1200, 200, 0, 2));

        Path pefsFilePath = testFolder.resolve("TempPrefs.json");
        JsonUserPrefsStorage jsonUserPrefsStorage = new JsonUserPrefsStorage(pefsFilePath);

        //Try writing when the file doesn't exist
        jsonUserPrefsStorage.saveUserPrefs(original);
        UserPrefs readBack = jsonUserPrefsStorage.readUserPrefs();
        assertEquals(original, readBack);

        //Try saving when the file exists
        original.setGuiSettings(new GuiSettings(5, 5, 5, 5));
        jsonUserPrefsStorage.saveUserPrefs(original);
        readBack = jsonUserPrefsStorage.readUserPrefs();
        assertEquals(original, readBack);
    }

}