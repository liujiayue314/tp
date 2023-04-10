package vimification.testutil;

import vimification.model.TaskList;
import vimification.model.task.Priority;
import vimification.model.task.Status;
import vimification.model.task.Task;

import java.time.LocalDateTime;

public class TypicalTaskList {

    private static Task t1 = new Task("test1", LocalDateTime.now(), Status.NOT_DONE, Priority.VERY_URGENT);
    private static Task t2 = new Task("test2", LocalDateTime.now(), Status.COMPLETED, Priority.UNKNOWN);

    public static TaskList getTypicalTaskList() {
        TaskList taskList = new TaskList();
        taskList.add(t1);
        taskList.add(t2);
        return taskList;
    }

}
