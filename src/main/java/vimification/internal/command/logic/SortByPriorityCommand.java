package vimification.internal.command.logic;

import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;

import static java.util.Objects.requireNonNull;

public class SortByPriorityCommand extends LogicCommand {
    public static final String COMMAND_WORD = "s -p";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sort tasks by priority.\n"
            + "Example: " + COMMAND_WORD;
    public static final String SUCCESS_MESSAGE_FORMAT =
            "Tasks sorted according to priority.";

    public CommandResult execute(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        taskList.sortByPriority((task1, task2) -> task1.getPriority().compareTo(task2.getPriority()));
        return new CommandResult(SUCCESS_MESSAGE_FORMAT);
    }
}



