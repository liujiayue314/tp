package vimification.internal.command.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;
import vimification.model.task.Task;

import static java.util.Objects.requireNonNull;

public class FilterByLabel extends FilterCommand {
    public static final String COMMAND_WORD = "f -l";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": search for tasks with labels matching the input label.\n"
            + "Parameters: LABEL \n"
            + "Example: " + COMMAND_WORD + " cs2103T";

    public FilterByLabel(String label) {
        super(task -> task.containsLabel(label));
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
