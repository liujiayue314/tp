package vimification.internal.command.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;
import vimification.model.task.Task;

import static java.util.Objects.requireNonNull;

public class FilterByKeywordCommand extends FilterCommand {
    public static final String COMMAND_WORD = "f -t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": search for tasks that has title matching with input keyword.\n"
            + "Parameters: KEYWORD\n"
            + "Conditions: Keyword cannot be empty.\n"
            + "Example: " + COMMAND_WORD + " quiz";
    public FilterByKeywordCommand(String keyword) {
        super(task -> task.containsKeyword(keyword));
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
