package vimification.internal.command.logic;

import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;

import static java.util.Objects.requireNonNull;

public class SortByDeadlineCommand extends LogicCommand {
    public static final String COMMAND_WORD = "s -d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": sort tasks by deadline.\n"
            + "Example: " + COMMAND_WORD;
    public static final String SUCCESS_MESSAGE_FORMAT =
            "Tasks sorted according to deadline.";

    public CommandResult execute(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        taskList.sortByDeadline((t1, t2) -> {
            if (t1.getDeadline() == null) {
                return -1;
            } else if (t2.getDeadline() == null) {
                return 1;
            } else {
                return t1.getDeadline().compareTo(t2.getDeadline());
            }
        });
        return new CommandResult(SUCCESS_MESSAGE_FORMAT);
    }
}





