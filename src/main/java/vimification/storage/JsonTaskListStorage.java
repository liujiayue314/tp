package vimification.storage;

import java.io.IOException;
import java.nio.file.Path;

import vimification.common.exceptions.DataConversionException;
import vimification.common.util.JsonUtil;
import vimification.model.TaskList;

/**
 * One implementation of {@link TaskListStorage}, using JSON as serialization format.
 */
public class JsonTaskListStorage implements TaskListStorage {

    private Path filePath;

    /**
     * Creates a new instance with the specified path.
     *
     * @param filePath the path to the data file
     */
    public JsonTaskListStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Path getTaskListFilePath() {
        return filePath;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskList readTaskList() throws DataConversionException, IOException {
        return JsonUtil
                .readJsonFile(filePath, JsonAdaptedTaskList.class)
                .toModelType();
    }

    /**
     * Similar to {@link #readTaskList()}. Used for testing
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public TaskList readTaskList(Path filePath) throws DataConversionException, IOException {
        JsonAdaptedTaskList j = JsonUtil.readJsonFile(filePath, JsonAdaptedTaskList.class);
        return j.toModelType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveTaskList(TaskList taskList) throws IOException {
        JsonUtil.saveJsonFile(new JsonAdaptedTaskList(taskList), filePath);
    }

    /**
     * Similar to {@link #saveTaskList(TaskList)}. Used for testing
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveTaskList(TaskList taskList, Path filePath) throws IOException {
        JsonUtil.saveJsonFile(new JsonAdaptedTaskList(taskList), filePath);
    }
}
