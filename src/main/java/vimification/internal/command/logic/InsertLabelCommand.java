package vimification.internal.command.logic;

import static java.util.Objects.requireNonNull;
import static vimification.commons.util.CollectionUtil.requireAllNonNull;

import vimification.commons.core.Index;
import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;

public class InsertLabelCommand extends UndoableLogicCommand {

    public static final String COMMAND_WORD = "i -l";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": inserts label to the task.\n"
            + "Parameters: INDEX (index number of the target task in the displayed task list)\n"
            + "          : NAME of label\n"
            + "Conditions: Index must be positive integer and cannot exceed total number of tasks.\n"
            + "          : name of label must not be empty.\n"
            + "Example: " + COMMAND_WORD + " 1" + "cs2103T";

    public static final String SUCCESS_MESSAGE_FORMAT =
            "label of task %1$s inserted.";
    public static final String UNDO_MESSAGE =
            "The command has been undone. The label of the task has been changed back.";
    private final String newLabel;
    private final Index targetIndex;

    /**
     * Creates an AddTagCommand to add the specified {@code Task}
     */
    public InsertLabelCommand(String newLabel, Index targetIndex) {
        requireAllNonNull(newLabel, targetIndex);
        this.newLabel = newLabel;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        int zero_based_index = targetIndex.getZeroBased();
        taskList.addLabel(zero_based_index, newLabel);
        return new CommandResult(String.format(SUCCESS_MESSAGE_FORMAT, newLabel, targetIndex));
    }
    @Override
    public CommandResult undo(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        int zero_based_index = targetIndex.getZeroBased();
        taskList.deleteLabel(zero_based_index, newLabel);
        return new CommandResult(UNDO_MESSAGE);
    }
}
