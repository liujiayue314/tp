package vimification.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static vimification.testutil.TypicalTaskList.getTypicalTaskList;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import vimification.model.TaskList;
import vimification.model.task.Task;


public class JsonTaskListStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTaskListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readTaskList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readTaskList(null));
    }

    private TaskList readTaskList(String filePath) throws Exception {
        return new JsonTaskListStorage(Paths.get(filePath)).readTaskList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertThrows(NoSuchFileException.class, () -> readTaskList("NonExistentFile.json"));
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(IOException.class, () -> readTaskList("notJsonFormatTaskList.json"));
    }

    @Test
    public void readTaskList_invalidTaskList_throwDataConversionException() {
        assertThrows(IOException.class, () -> readTaskList("invalidTaskList.json"));
    }

    @Test
    public void readTaskList_invalidAndValidPersonTaskList_throwDataConversionException() {
        assertThrows(IOException.class, () -> readTaskList("invalidAndValidTaskList.json"));
    }


    @Test
    public void readAndSaveTaskList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempTaskList.json");
        Task test = new Task("test");
        TaskList original = getTypicalTaskList();
        JsonTaskListStorage jsonTaskListStorage = new JsonTaskListStorage(filePath);

        // Save in new file and read back
        jsonTaskListStorage.saveTaskList(original, filePath);
        TaskList readBack = jsonTaskListStorage.readTaskList(filePath);
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back
        original.add(test);
        original.remove(1);
        jsonTaskListStorage.saveTaskList(original, filePath);
        readBack = jsonTaskListStorage.readTaskList(filePath);
        assertEquals(original, readBack);

        // Save and read without specifying file path
        original.add(test);
        jsonTaskListStorage.saveTaskList(original); // file path not specified
        readBack = jsonTaskListStorage.readTaskList(); // file path not specified
        assertEquals(original, readBack);

    }

    @Test
    public void saveTaskList_nullTaskList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaskList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code TaskList} at the specified {@code filePath}.
     */

    private void saveTaskList(TaskList TaskList, String filePath) {
        try {
            new JsonTaskListStorage(Paths.get(filePath))
                    .saveTaskList(TaskList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTaskList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveTaskList(new TaskList(), null));
    }
}
