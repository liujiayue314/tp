package vimification.internal.command.logic;

import static java.util.Objects.requireNonNull;
import static vimification.commons.util.CollectionUtil.requireAllNonNull;

import vimification.commons.core.Index;
import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;

public class DeleteLabelCommand extends UndoableLogicCommand {

    public static final String COMMAND_WORD = "d -l";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": delete label to the task.\n"
            + "Parameters: INDEX (index number of the target task in the displayed task list)\n"
            + "          : NAME of label to be deleted\n"
            + "Conditions: Index must be positive integer and cannot exceed total number of tasks.\n"
            + "          : name of label must not be empty and must match exactly to an existing label.\n"
            + "Example: " + COMMAND_WORD + " 1" + "cs2103T";

    public static final String SUCCESS_MESSAGE_FORMAT =
            "label of task %1$s deleted.";
    public static final String UNDO_MESSAGE =
            "The command has been undone. The label of the task has been changed back.";

    private final Index targetIndex;

    private final String deletedLabel;

    /**
     * Creates a RemoveTagCommand to add the specified {@code Task}
     */
    public DeleteLabelCommand(String deletedLabel, Index targetIndex) {
        requireAllNonNull(deletedLabel, targetIndex);
        this.deletedLabel = deletedLabel;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        int zero_based_index = targetIndex.getZeroBased();
        taskList.deleteLabel(zero_based_index, deletedLabel);
        return new CommandResult(String.format(SUCCESS_MESSAGE_FORMAT, deletedLabel, targetIndex));
    }
    @Override
    public CommandResult undo(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        int zero_based_index = targetIndex.getZeroBased();
        taskList.addLabel(zero_based_index, deletedLabel);
        return new CommandResult(UNDO_MESSAGE);
    }
}
