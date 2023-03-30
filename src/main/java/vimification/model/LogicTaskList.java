package vimification.model;

import static java.util.Objects.requireNonNull;
import static vimification.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import vimification.model.task.Priority;
import vimification.model.task.Status;
import vimification.model.task.Task;

/**
 * Responsible for storing, retrieving and updating all the tasks that are currently on the list.
 */
public class LogicTaskList {

    private final List<Task> tasks;

    public LogicTaskList(List<Task> tasks) {
        this.tasks = tasks;
    }

    public LogicTaskList() {
        this(new ArrayList<>());
    }

    public LogicTaskList(Task... tasks) {
        this(new ArrayList<>(Arrays.asList(tasks)));
    }

    public List<Task> getInternalList() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    //// Task-level operations

    /**
     * Replaces the task with the specified index with the given task.
     */
    public void set(int index, Task newTask) { //no use?
        requireNonNull(newTask);
        tasks.set(index, newTask);
    }

    /**
     * Returns the index of the task with the specified index.
     */
    public int indexOf(Task t) {
        return tasks.indexOf(t);
    } //no use?

    /**
     * Returns true if a task that is the same as {@code t} exists in the task list.
     */


    /**
     * Returns the task with the specified index.
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    public void addTask(Task task) {
        requireNonNull(task);
        tasks.add(task);
    }

    public void addTask(int index, Task task) {
        requireAllNonNull(task);
        tasks.add(index, task);
    }
    public void deleteTask(int index) {
        tasks.remove(index);
    }
    public boolean containTask(Task task) {
        requireNonNull(task);
        return tasks.contains(task);
    }
    public Priority getPriority(int index) {
        return tasks.get(index).getPriority();
    }
    public void setPriority(int index, int newLevel) {
        tasks.get(index).setPriority(newLevel);
    }

    public void setPriority(int index, Priority newPriority) {
        requireNonNull(newPriority);
        tasks.get(index).setPriority(newPriority);
    }

    public String getTitle(int index) {
        return tasks.get(index).getTitle();
    }

    public void setTitle(int index, String newDescription) {
        requireNonNull(newDescription);
        tasks.get(index).setTitle(newDescription);
    }

    public Status getStatus(int index) {
        return tasks.get(index).getStatus();
    }

    public void setStatus(int index, Status newStatue) {
        requireNonNull(newStatue);
        tasks.get(index).setStatus(newStatue);
    }

    public boolean getRecurrence(int index) {return tasks.get(index).getRecurrence();}

    public void setRecurrence(int index, boolean newRecurrence) {
        tasks.get(index).setRecurrence(newRecurrence);
    }

    public LocalDateTime getDeadline(int index) {
        return tasks.get(index).getDeadline();
    }

    public void setDeadline(int index, LocalDateTime newDate) {
        requireNonNull(newDate);
        tasks.get(index).setDeadline(newDate);
    }

    public void deleteDeadline(int index) {
        tasks.get(index).deleteDeadline();
    }

    public void addLabel(int index, String newLabel) {
        requireNonNull(newLabel);
        tasks.get(index).addLabel(newLabel);
    }

    public void deleteLabel(int index, String oldLabel) {
        tasks.get(index).deleteLabel(oldLabel);
    }

    public void addAll(List<Task> tasks) {
        requireAllNonNull(tasks);
        this.tasks.addAll(tasks);
    }

    public void sortByTitle(Comparator<Task> titleComparator) {
        requireNonNull(titleComparator);
        tasks.sort(titleComparator);
    }

    public void sortByPriority(Comparator<Task> priorityComparator) {
        requireNonNull(priorityComparator);
        tasks.sort(priorityComparator);
    }

    public void sortByDeadline(Comparator<Task> deadlineComparator) {
        requireNonNull(deadlineComparator);
        tasks.sort(deadlineComparator);
    }

    public void clear() {
        this.tasks.clear();
    }

    //// util methods

    public Stream<Task> stream() {
        return tasks.stream();
    }

    public LogicTaskList slice(int start, int end) {
        return new LogicTaskList(tasks.subList(start, end));
    }

    /**
     * Filter {@code LogicTaskList} based on predicate.
     *
     * @param pred
     * @return
     */
    public List<Task> filter(Predicate<Task> pred) {
        requireNonNull(pred);
        List<Task> filteredTasks = stream()
                .filter(pred)
                .collect(Collectors.toCollection(ArrayList::new));
        return filteredTasks;
    }

    public void updateRecurrence() {
        for (Task t : tasks) {
            tasks.add(t.updateRecurrence());
        }
    }


    /**
    @Override
    public String toString() {
        // TODO: rewrite this, too confusing
        String result = "";
        for (int i = 0; i < size(); i++) {
            String prefix = i + 1 < 10 ? "0" : "";
            result += prefix + (i + 1) + ". " + get(i).toString();
            if (i < size() - 1) {
                result += "\n";
            }
        }
        return result;
    }
     */

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LogicTaskList // instanceof handles nulls
                        && tasks.equals(((LogicTaskList) other).tasks));
    }
}
