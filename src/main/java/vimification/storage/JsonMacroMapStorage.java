package vimification.storage;

import java.io.IOException;
import java.nio.file.Path;

import vimification.common.util.JsonUtil;
import vimification.model.MacroMap;

/**
 * A class to access {@link MacroMap} stored in the hard disk as a json file.
 */
public class JsonMacroMapStorage implements MacroMapStorage {

    private Path filePath;

    /**
     * Creates a new instance with the specified path.
     *
     * @param filePath the path to the data file
     */
    public JsonMacroMapStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getMacroMapFilePath() {
        return filePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MacroMap readMacroMap() throws IOException {
        return JsonUtil.readJsonFile(filePath, MacroMap.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveMacroMap(MacroMap macroMap) throws IOException {
        JsonUtil.saveJsonFile(macroMap, filePath);
    }
}
