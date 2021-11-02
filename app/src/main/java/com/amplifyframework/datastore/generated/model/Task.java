package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Task type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Tasks")
public final class Task implements Model {
  public static final QueryField ID = field("Task", "id");
  public static final QueryField TASK_TITLE = field("Task", "taskTitle");
  public static final QueryField TASK_DESCRIPTION = field("Task", "taskDescription");
  public static final QueryField TASK_STATUS = field("Task", "taskStatus");
  public static final QueryField TASK_DATE = field("Task", "taskDate");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String taskTitle;
  private final @ModelField(targetType="String") String taskDescription;
  private final @ModelField(targetType="String") String taskStatus;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime taskDate;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getTaskTitle() {
      return taskTitle;
  }
  
  public String getTaskDescription() {
      return taskDescription;
  }
  
  public String getTaskStatus() {
      return taskStatus;
  }
  
  public Temporal.DateTime getTaskDate() {
      return taskDate;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Task(String id, String taskTitle, String taskDescription, String taskStatus, Temporal.DateTime taskDate) {
    this.id = id;
    this.taskTitle = taskTitle;
    this.taskDescription = taskDescription;
    this.taskStatus = taskStatus;
    this.taskDate = taskDate;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Task task = (Task) obj;
      return ObjectsCompat.equals(getId(), task.getId()) &&
              ObjectsCompat.equals(getTaskTitle(), task.getTaskTitle()) &&
              ObjectsCompat.equals(getTaskDescription(), task.getTaskDescription()) &&
              ObjectsCompat.equals(getTaskStatus(), task.getTaskStatus()) &&
              ObjectsCompat.equals(getTaskDate(), task.getTaskDate()) &&
              ObjectsCompat.equals(getCreatedAt(), task.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), task.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTaskTitle())
      .append(getTaskDescription())
      .append(getTaskStatus())
      .append(getTaskDate())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Task {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("taskTitle=" + String.valueOf(getTaskTitle()) + ", ")
      .append("taskDescription=" + String.valueOf(getTaskDescription()) + ", ")
      .append("taskStatus=" + String.valueOf(getTaskStatus()) + ", ")
      .append("taskDate=" + String.valueOf(getTaskDate()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Task justId(String id) {
    return new Task(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      taskTitle,
      taskDescription,
      taskStatus,
      taskDate);
  }
  public interface BuildStep {
    Task build();
    BuildStep id(String id);
    BuildStep taskTitle(String taskTitle);
    BuildStep taskDescription(String taskDescription);
    BuildStep taskStatus(String taskStatus);
    BuildStep taskDate(Temporal.DateTime taskDate);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String taskTitle;
    private String taskDescription;
    private String taskStatus;
    private Temporal.DateTime taskDate;
    @Override
     public Task build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Task(
          id,
          taskTitle,
          taskDescription,
          taskStatus,
          taskDate);
    }
    
    @Override
     public BuildStep taskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
        return this;
    }
    
    @Override
     public BuildStep taskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
        return this;
    }
    
    @Override
     public BuildStep taskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }
    
    @Override
     public BuildStep taskDate(Temporal.DateTime taskDate) {
        this.taskDate = taskDate;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String taskTitle, String taskDescription, String taskStatus, Temporal.DateTime taskDate) {
      super.id(id);
      super.taskTitle(taskTitle)
        .taskDescription(taskDescription)
        .taskStatus(taskStatus)
        .taskDate(taskDate);
    }
    
    @Override
     public CopyOfBuilder taskTitle(String taskTitle) {
      return (CopyOfBuilder) super.taskTitle(taskTitle);
    }
    
    @Override
     public CopyOfBuilder taskDescription(String taskDescription) {
      return (CopyOfBuilder) super.taskDescription(taskDescription);
    }
    
    @Override
     public CopyOfBuilder taskStatus(String taskStatus) {
      return (CopyOfBuilder) super.taskStatus(taskStatus);
    }
    
    @Override
     public CopyOfBuilder taskDate(Temporal.DateTime taskDate) {
      return (CopyOfBuilder) super.taskDate(taskDate);
    }
  }
  
}
