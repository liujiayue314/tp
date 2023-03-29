package vimification.internal.command.logic;

import static java.util.Objects.requireNonNull;

import vimification.commons.core.Index;
import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;
import vimification.model.task.Task;

/**
 * Deletes a task identified using it's displayed index from the address book.
 */
public class DeleteTaskCommand extends UndoableLogicCommand {

    public static final String COMMAND_WORD = "d";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a task\n"
            + "Parameters: INDEX (index number of the target task in the displayed task list)\n"
            + "Conditions: Index must be positive integer and cannot exceed total number of tasks.\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String SUCCESS_MESSAGE_FORMAT = "Task %1$s deleted";
    public static final String UNDO_MESSAGE =
            "The command has been undone. The deleted task has been added back.";

    // targetIndex is ZERO-BASED
    private final Index targetIndex;
    private Task deletedTask;

    public DeleteTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.deletedTask = null;
    }

    @Override
    public CommandResult execute(LogicTaskList taskList)
            throws IndexOutOfBoundsException, CommandException {
        requireNonNull(taskList);
        int zero_based_index = targetIndex.getZeroBased();
        deletedTask = taskList.getTask(zero_based_index);
        taskList.deleteTask(zero_based_index);
        return new CommandResult(String.format(SUCCESS_MESSAGE_FORMAT, deletedTask));
    }

    @Override
    public CommandResult undo(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        taskList.addTask(targetIndex.getZeroBased(), deletedTask);
        return new CommandResult(String.format(UNDO_MESSAGE, deletedTask));
    }

}
