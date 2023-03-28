package vimification.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import vimification.commons.exceptions.IllegalValueException;
import vimification.model.task.Deadline;
import vimification.model.task.Priority;
import vimification.model.task.Status;
import vimification.model.task.Task;

/**
 * Jackson-friendly version of {@link Task}.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = JsonAdaptedTodo.class, name = "todo"),
        @JsonSubTypes.Type(value = JsonAdaptedDeadline.class, name = "deadline"),
        @JsonSubTypes.Type(value = JsonAdaptedEvent.class, name = "event")
})
public abstract class JsonAdaptedTask {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Task's %s field is missing!";

    final String description;
    final Status status;
    final Priority priority;

    @JsonCreator
    public JsonAdaptedTask(
            @JsonProperty("description") String description,
            @JsonProperty("status") Status status,
            @JsonProperty("priority") Priority priority) {
        this.description = description;
        this.status = status;
        this.priority = priority;
    }


    /**
     * Converts a given {@code Task} into this class for Jackson use.
     */
    public JsonAdaptedTask(Task task) {
        description = task.getDescription();
        status = task.getStatus();
        priority = task.getPriority();
    }


    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *         person.
     */
    public abstract Task toModelType() throws IllegalValueException;
}
