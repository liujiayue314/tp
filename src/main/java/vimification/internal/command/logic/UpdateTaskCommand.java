package vimification.internal.command.logic;

import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;
import vimification.model.task.Task;

import static java.util.Objects.requireNonNull;

public class UpdateTaskCommand extends LogicCommand {
    public static final String COMMAND_WORD = "update";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": update all task with recurrence.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SUCCESS_MESSAGE_FORMAT = "Tasks updated";

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */

    @Override
    public CommandResult execute(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        taskList.updateRecurrence();
        return new CommandResult(String.format(SUCCESS_MESSAGE_FORMAT));
    }
}
