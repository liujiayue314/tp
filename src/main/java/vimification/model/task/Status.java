package vimification.model.task;

/**
 * Represents different status of a task in the application.
 */
public enum Status {

    NOT_DONE, IN_PROGRESS, COMPLETED, OVERDUE;

    public static final String MESSAGE_CONSTRAINTS = "Status should only be 'NOT_DONE', 'IN_PROGRESS' or 'COMPLETED'";

    /**
     * Returns a status, based on a numeric value.
     * 1 being the in progress, 2 being completed, 3 being overdue.
     * Other integers will be treated as not done.
     */
    public static Status fromInt(int level) {
        switch (level) {
        case 1:
            return IN_PROGRESS;
        case 2:
            return COMPLETED;
        case 3:
            return OVERDUE;
        default:
            return NOT_DONE;
        }
    }

    /**
     * Returns if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.matches("IN_PROGRESS") || test.matches("COMPLETED") || test.matches("NOT_DONE");
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace('_', ' ');
    }
}

