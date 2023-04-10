package vimification.storage;

import java.io.IOException;
import java.nio.file.Path;

import vimification.common.util.JsonUtil;
import vimification.model.UserPrefs;

/**
 * A class to access {@link UserPrefs} stored in the hard disk as a json file.
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    private Path filePath;

    /**
     * Creates a new instance with the specified path.
     *
     * @param filePath the path to the data file
     */
    public JsonUserPrefsStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getUserPrefsFilePath() {
        return filePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserPrefs readUserPrefs() throws IOException {
        return JsonUtil.readJsonFile(filePath, UserPrefs.class);
    }

    /**
     * Similar to {@link #readUserPrefs()}. Used for testing.
     *
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws IOException if the file format is not as expected.
     */
    public UserPrefs readUserPrefs(Path prefsFilePath) throws IOException {
        return JsonUtil.readJsonFile(prefsFilePath, UserPrefs.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        JsonUtil.saveJsonFile(userPrefs, filePath);
    }
}
