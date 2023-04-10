package vimification.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.util.List;

import org.junit.jupiter.api.Test;
import vimification.common.exceptions.DataConversionException;
import vimification.model.task.Priority;
import vimification.model.task.Status;
import vimification.model.task.Task;

import java.time.LocalDateTime;

class JsonAdaptedTaskTest {

    private static final String INVALID_TITLE = "";
    private static final String INVALID_STATUS = null;
    private static final String INVALID_DEADLINE = null;
    private static final String INVALID_PRIORITY = null;

    private static final String VALID_TITLE = "quiz";
    private static final Status VALID_STATUS = Status.NOT_DONE;
    private static final Priority VALID_PRIORITY = Priority.UNKNOWN;
    private static final LocalDateTime VALID_DEADLINE = LocalDateTime.now();
    private static final List<String> VALID_LABEL = List.of("cs2101");
    private static final String NULL_TITLE = null;
    private static final Status NULL_STATUS = null;
    private static final Priority NULL_PRIORITY = null;
    private static final LocalDateTime NULL_DEADLINE = null;
    private static final List<String> NULL_LABEL = null;

    @Test
    void toModelType_validTaskDetails_returnsTask() throws Exception{
        JsonAdaptedTask Task =
                new JsonAdaptedTask(VALID_TITLE, VALID_STATUS, VALID_PRIORITY, VALID_DEADLINE, VALID_LABEL);
        vimification.model.task.Task test = new Task(VALID_TITLE, VALID_DEADLINE, VALID_STATUS, VALID_PRIORITY);
        VALID_LABEL.forEach(test::addLabel);
        assertEquals(test, Task.toModelType());
    }

    @Test
    void toModelType_invalidTitle_throwsDataConversionException() {
        JsonAdaptedTask Task =
                new JsonAdaptedTask(INVALID_TITLE, VALID_STATUS, VALID_PRIORITY, VALID_DEADLINE, VALID_LABEL);
        assertThrows(DataConversionException.class, Task::toModelType);
    }

    @Test
    void toModelType_nullTitle_throwsDataConversionException() {
        JsonAdaptedTask Task =
                new JsonAdaptedTask(NULL_TITLE, VALID_STATUS, VALID_PRIORITY, VALID_DEADLINE, VALID_LABEL);
        assertThrows(DataConversionException.class, Task::toModelType);
    }

    @Test
    void toModelType_invalidSTATUS_throwsDataConversionException() {
    }

    @Test
    void toModelType_nullSTATUS() {
        JsonAdaptedTask Task =
                new JsonAdaptedTask(VALID_TITLE, NULL_STATUS, VALID_PRIORITY, VALID_DEADLINE, VALID_LABEL);
        assertThrows(DataConversionException.class, Task::toModelType);
    }

    @Test
    void toModelType_invalidPRIORITY_throwsDataConversionException() {
    }

    @Test
    void toModelType_nullPRIORITY() {
        JsonAdaptedTask Task =
                new JsonAdaptedTask(VALID_TITLE, VALID_STATUS, NULL_PRIORITY, VALID_DEADLINE, VALID_LABEL);
        assertThrows(DataConversionException.class, Task::toModelType);
    }

    @Test
    void toModelType_invalidDEADLINE_throwsDataConversionException() {
    }

    @Test
    void toModelType_nullDEADLINE() throws Exception {
        JsonAdaptedTask Task =
                new JsonAdaptedTask(VALID_TITLE, VALID_STATUS, VALID_PRIORITY, NULL_DEADLINE, VALID_LABEL);
        vimification.model.task.Task test = new Task(VALID_TITLE, NULL_DEADLINE, VALID_STATUS, VALID_PRIORITY);
        VALID_LABEL.forEach(test::addLabel);
        assertEquals(test, Task.toModelType());
    }

    @Test
    void toModelType_nullLabel() {
        JsonAdaptedTask Task =
                new JsonAdaptedTask(VALID_TITLE, VALID_STATUS, VALID_PRIORITY, VALID_DEADLINE, NULL_LABEL);
        assertThrows(DataConversionException.class, Task::toModelType);
    }
}