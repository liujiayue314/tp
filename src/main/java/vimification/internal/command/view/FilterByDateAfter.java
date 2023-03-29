package vimification.internal.command.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;
import vimification.model.task.Task;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;


public class FilterByDateAfter extends FilterCommand {
    public static final String COMMAND_WORD = "f -d -after";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": search for tasks that have deadline after (inclusive) the input date.\n"
            + "Parameters: DATETIME \n"
            + "Conditions: Date time must be valid in the format of YYYY-MM-DD.\n"
            + "Example: " + COMMAND_WORD + " 2023-01-01";

    public FilterByDateAfter(LocalDateTime date) {
        super(task -> task.isDateAfter(date));
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

