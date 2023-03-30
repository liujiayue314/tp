package vimification.internal.command.logic;

import vimification.commons.core.Index;
import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;
import vimification.model.task.Priority;

import static java.util.Objects.requireNonNull;

public class EditRecurrenceCommand extends UndoableLogicCommand {
    public static final String COMMAND_WORD = "e -r";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edit recurrence level of a task.\n"
            + "Parameters: INDEX (index number of the target task in the displayed task list)\n"
            + "          : RECURRENCE (true or false)\n"
            + "Conditions: Index must be positive integer and cannot exceed total number of tasks.\n"
            + "Example: " + COMMAND_WORD + " true" + " 1";

    public static final String SUCCESS_MESSAGE_FORMAT =
            "Priority of task %1$s updated.";
    public static final String UNDO_MESSAGE =
            "The command has been undone. The priority of the task has been changed back.";

    // targetIndex is ZERO-BASED
    private final Index targetIndex;
    private final boolean newRecurrence;
    private boolean oldRecurrence;


    public EditRecurrenceCommand(Index targetIndex, boolean newRecurrence) {
        this.targetIndex = targetIndex;
        this.newRecurrence = newRecurrence;
    }

    @Override
    public CommandResult execute(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        int zero_based_index = targetIndex.getZeroBased();
        oldRecurrence = taskList.getRecurrence(zero_based_index);
        taskList.setRecurrence(zero_based_index, newRecurrence);
        return new CommandResult(String.format(SUCCESS_MESSAGE_FORMAT, targetIndex.getOneBased()));
    }

    @Override
    public CommandResult undo(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        int zero_based_index = targetIndex.getZeroBased();
        taskList.setRecurrence(zero_based_index, oldRecurrence);
        return new CommandResult(UNDO_MESSAGE);
    }
}
