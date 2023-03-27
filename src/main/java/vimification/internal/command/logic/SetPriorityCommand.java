package vimification.internal.command.logic;

import static java.util.Objects.requireNonNull;

import vimification.commons.core.Index;
import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;
import vimification.model.task.Priority;

public class SetPriorityCommand extends UndoableLogicCommand {
    public static final String COMMAND_WORD = "-p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds priority level to the task identified by the index number used in the displayed task list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + ": PRIORITY LEVEL (must be either 1, 2 or 3) "
            + "Example: " + COMMAND_WORD + " 1" + " 1";

    public static final String SUCCESS_MESSAGE_FORMAT =
            "Priority of task %1$s updated.";
    public static final String UNDO_MESSAGE =
            "The command has been undone. The priority of the task has been changed back.";

    // targetIndex is ZERO-BASED
    private final Index targetIndex;
    private final Priority newPriority;
    private Priority oldPriority;


    public SetPriorityCommand(Index targetIndex, Priority newPriority) {
        this.targetIndex = targetIndex;
        this.newPriority = newPriority;
        this.oldPriority = null;
    }

    public SetPriorityCommand(Index targetIndex, int newLevel) {
        this(targetIndex, Priority.fromInt(newLevel));
    }

    @Override
    public CommandResult execute(LogicTaskList taskList)
            throws IndexOutOfBoundsException, CommandException {
        requireNonNull(taskList);
        int zero_based_index = targetIndex.getZeroBased();
        oldPriority = taskList.getPriority(zero_based_index);
        taskList.setPriority(zero_based_index, newPriority);
        return new CommandResult(String.format(SUCCESS_MESSAGE_FORMAT, targetIndex.getOneBased()));
    }

    @Override
    public CommandResult undo(LogicTaskList taskList)
            throws IndexOutOfBoundsException, CommandException {
        requireNonNull(taskList);
        int zero_based_index = targetIndex.getZeroBased();
        taskList.setPriority(zero_based_index, oldPriority);
        return new CommandResult(UNDO_MESSAGE);
    }
}
