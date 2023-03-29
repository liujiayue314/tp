package vimification.internal.command.view;

import java.util.function.Predicate;

import vimification.model.task.Task;

public abstract class FilterCommand extends ViewCommand {
    public static final String COMMAND_WORD = "filter";
    public static final String SUCCESS_MESSAGE_FORMAT = "Here are your filter results:";

    private final Predicate<Task> pred;

    FilterCommand(Predicate<Task> pred) {
        this.pred = pred;
    }

    protected Predicate<Task> getPredicate() {
        return pred;
    }
}
