package vimification.storage;

import java.io.IOException;
import java.nio.file.Path;

import vimification.model.MacroMap;

/**
 * Represents a storage for {@link MacroMap}.
 */
public interface MacroMapStorage {

    /**
     * Returns the file path of the data file.
     *
     * @return the file path of the data file.
     */
    Path getMacroMapFilePath();


    /**
     * Reads from the data file and constructs a {@link MacroMap} instance from the data read.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return a {@code MacroMap} instance constructed from the file data
     * @throws IOException if there was any problem when reading from the storage.
     */
    MacroMap readMacroMap() throws IOException;

    /**
     * Save the given {@link MacroMap} to the storage.
     *
     * @param macroMap the {@code MacroMap} instance to be saved
     * @throws IOException if there is any problem when writing to the file
     */
    void saveMacroMap(MacroMap macroMap) throws IOException;
}
