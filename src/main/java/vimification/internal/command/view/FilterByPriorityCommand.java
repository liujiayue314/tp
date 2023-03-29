package vimification.internal.command.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;
import vimification.model.task.Priority;
import vimification.model.task.Task;

import static java.util.Objects.requireNonNull;

public class FilterByPriorityCommand extends FilterCommand {

    public static final String COMMAND_WORD = "f -p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": search for tasks that has the same priority as the input priority.\n"
            + "Parameters: PRIORITY (high, med or low)\n"
            + "Example: " + COMMAND_WORD + " high";

    public FilterByPriorityCommand(int level) {
        super(task -> task.isSamePriority(level));
    }

    public FilterByPriorityCommand(Priority priority) {
        super(task -> task.isSamePriority(priority));
    }

    @Override
    public CommandResult execute(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        ObservableList<Task> viewTaskList =
                FXCollections.observableList(taskList.filter(getPredicate()));
        setViewTaskList(viewTaskList);
        return new CommandResult(SUCCESS_MESSAGE_FORMAT);
    }
}
