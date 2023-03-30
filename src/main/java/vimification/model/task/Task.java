package vimification.model.task;

import static java.util.Objects.requireNonNull;
import static vimification.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Task {

    private String title;
    private LocalDateTime deadline;
    private Status status;
    private boolean recurrence;
    private Priority priority;
    private Set<String> labels;


    /**
     * Every field must be present and not null.
     */
    public Task(String title, LocalDateTime deadline, Status status, boolean recurrence, Priority priority) {
        requireAllNonNull(title, status, priority);
        this.title = title;
        this.deadline = deadline;
        this.status = status;
        this.priority = priority;
        this.recurrence = recurrence;
        this.labels = new HashSet<>();
    }

    public Task(String title) {
        this(title, null, Status.NOT_DONE, false, Priority.UNKNOWN);
    }

    public Task(String title, Status status) {        //new todo with status
        this(title, null, status, false,Priority.UNKNOWN);
    }

    public Task(String title, Priority priority) {
        this(title, null, Status.NOT_DONE, false, priority);
    }

    public Task(String title, boolean recurrence) {
        this(title, null, Status.NOT_DONE, recurrence, Priority.UNKNOWN);
    }

    public Task(String title, Status status, Priority priority) {
        this(title, null, status, false, priority);
    }

    public Task(String title, Status status, boolean recurrence) {
        this(title, null, status, recurrence, Priority.UNKNOWN);
    }

    public Task(String title, boolean recurrence, Priority priority) {
        this(title, null, Status.NOT_DONE, recurrence, priority);
    }

    public Task(String title, Status status, boolean recurrence, Priority priority) {
        this(title, null, status, recurrence, priority);
    }
    public Task(String title, LocalDateTime deadline) {
        this(title, deadline, Status.NOT_DONE,false, Priority.UNKNOWN);
    }

    public Task(String title, LocalDateTime deadline, Status status) {
        this(title, deadline, status, false, Priority.UNKNOWN);
    }

    public Task(String title, LocalDateTime deadline, Priority priority) {
        this(title, deadline, Status.NOT_DONE, false, priority);
    }
    public Task(String title, LocalDateTime deadline, boolean recurrence) {
        this(title, deadline, Status.NOT_DONE, recurrence, Priority.UNKNOWN);
    }

    public Task(String title, LocalDateTime deadline, Status status, Priority priority) {
        this(title, deadline, status, false, priority);
    }

    public Task(String title, LocalDateTime deadline, Status status, boolean recurrence) {
        this(title, deadline, status, recurrence, Priority.UNKNOWN);
    }

    public Task(String title, LocalDateTime deadline,  boolean recurrence , Priority priority) {
        this(title, deadline, Status.NOT_DONE, recurrence, priority);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        requireNonNull(title);
        this.title = title;
    }
    public boolean containsKeyword(String keyword) {
        requireNonNull(keyword);
        return title.contains(keyword);
    }
    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        requireNonNull(deadline);
        this.deadline = deadline;
    }

    public void deleteDeadline() {
        this.deadline = null;
    }
    public void updateDeadline() {
        if (this.deadline != null) {
            deadline = deadline.plusWeeks(1);
        }
    }
    public boolean isDateBefore(LocalDateTime date) {
        return deadline != null && (deadline.isBefore(date) || deadline.isEqual(date));
    }

    public boolean isDateAfter(LocalDateTime date) {
        return deadline != null && (deadline.isAfter(date) || deadline.isEqual(date));
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        requireNonNull(status);
        this.status = status;
    }

    public void setStatus(int level) {
        this.status = Status.fromInt(level);
    }

    public boolean getRecurrence() { return recurrence; }

    public void setRecurrence(boolean recurrence) {
        this.recurrence = recurrence;
    }

    public Task updateRecurrence() {
        Task newTask = this.clone();
        newTask.updateDeadline();
        newTask.setStatus(Status.NOT_DONE);
        return newTask;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        requireNonNull(priority);
        this.priority = priority;
    }

    public void setPriority(int level) {
        this.priority = Priority.fromInt(level);
    }

    public boolean isSamePriority(Priority priority) {
        return this.priority.equals(priority);
    }

    public boolean isSamePriority(int level) {
        return isSamePriority(Priority.fromInt(level));
    }

    public void addLabel(String label) {
        requireNonNull(label);
        label = label.toLowerCase();
        if (!labels.add(label)) {
            throw new IllegalArgumentException("Tag already exists");
        }
    }

    public void deleteLabel(String label) {
        requireNonNull(label);
        label = label.toLowerCase();
        if (!labels.remove(label)) {
            throw new IllegalArgumentException("Tag does not exist");
        }
    }

    public boolean containsLabel(String label) {
        return labels.contains(label.toLowerCase());
    }


    public Task clone() {
        Task clonedTask = new Task(title, deadline, status, recurrence, priority);
        clonedTask.labels.addAll(labels);
        return clonedTask;
    }

    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }
        return otherTask.title.equals(title)
                && otherTask.deadline.equals(deadline)
                && otherTask.status.equals(status)
                && otherTask.priority.equals(priority)
                && otherTask.labels.equals(labels);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Task)) {
            return false;
        }
        Task otherTask = (Task) other;
        return otherTask.title.equals(title) && otherTask.status.equals(status);
    }

    @Override
    public String toString() {
        return String.format("Task [title: %s;"
                + " deadline: %s; status: %s;"
                + " priority: %s; labels: %s]",
                title, deadline, status, priority, labels);
    }
}
