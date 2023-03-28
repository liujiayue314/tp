package vimification.internal.command.logic;

import vimification.commons.core.Index;
import vimification.internal.command.CommandException;
import vimification.internal.command.CommandResult;
import vimification.model.LogicTaskList;

import static java.util.Objects.requireNonNull;

public class MarkCommand extends UndoableLogicCommand {
    public static final String COMMAND_WORD = "mark";
    public static final String SUCCESS_MESSAGE_FORMAT = "Task %1$d has been marked as done.";
    public static final String UNDO_MESSAGE =
            "The command has been undoed. The Task %1$d has been unmarked.";

    // targetIndex is ZERO-BASED
    private final Index targetIndex;

    public MarkCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(LogicTaskList taskList)
            throws IndexOutOfBoundsException, CommandException {
        requireNonNull(taskList);
        taskList.mark(targetIndex.getZeroBased());
        return new CommandResult(String.format(SUCCESS_MESSAGE_FORMAT, targetIndex.getOneBased()));
    }

    @Override
    public CommandResult undo(LogicTaskList taskList) throws CommandException {
        requireNonNull(taskList);
        int zero_based_index = targetIndex.getZeroBased();
        //taskList.unmark(zero_based_index);
        return new CommandResult(UNDO_MESSAGE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkCommand // instanceof handles nulls
                && targetIndex.equals(((MarkCommand) other).targetIndex));
    }
}
