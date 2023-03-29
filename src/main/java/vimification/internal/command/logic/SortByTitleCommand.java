package vimification.internal.command.logic;

import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;

import static java.util.Objects.requireNonNull;

public class SortByTitleCommand extends LogicCommand {
    public static final String COMMAND_WORD = "s -t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sort tasks by title.\n"
            + "Example: " + COMMAND_WORD;
    public static final String SUCCESS_MESSAGE_FORMAT =
            "Tasks sorted according to title.";

    public CommandResult execute(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        taskList.sortByTitle((task1, task2) -> task1.getTitle().compareTo(task2.getTitle()));
        return new CommandResult(SUCCESS_MESSAGE_FORMAT);
    }
}



