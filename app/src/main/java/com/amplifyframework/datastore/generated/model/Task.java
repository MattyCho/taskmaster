package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;

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
@Index(name = "teamAndTask", fields = {"teamId"})
public final class Task implements Model {
  public static final QueryField ID = field("Task", "id");
  public static final QueryField TASK_TITLE = field("Task", "taskTitle");
  public static final QueryField TASK_DESCRIPTION = field("Task", "taskDescription");
  public static final QueryField TASK_STATUS = field("Task", "taskStatus");
  public static final QueryField TASK_DATE = field("Task", "taskDate");
  public static final QueryField TEAM = field("Task", "teamId");
  public static final QueryField TASK_IMAGE_KEY = field("Task", "taskImageKey");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String taskTitle;
  private final @ModelField(targetType="String") String taskDescription;
  private final @ModelField(targetType="String") String taskStatus;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime taskDate;
  private final @ModelField(targetType="Team", isRequired = true) @BelongsTo(targetName = "teamId", type = Team.class) Team team;
  private final @ModelField(targetType="String") String taskImageKey;
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
  
  public Team getTeam() {
      return team;
  }
  
  public String getTaskImageKey() {
      return taskImageKey;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Task(String id, String taskTitle, String taskDescription, String taskStatus, Temporal.DateTime taskDate, Team team, String taskImageKey) {
    this.id = id;
    this.taskTitle = taskTitle;
    this.taskDescription = taskDescription;
    this.taskStatus = taskStatus;
    this.taskDate = taskDate;
    this.team = team;
    this.taskImageKey = taskImageKey;
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
              ObjectsCompat.equals(getTeam(), task.getTeam()) &&
              ObjectsCompat.equals(getTaskImageKey(), task.getTaskImageKey()) &&
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
      .append(getTeam())
      .append(getTaskImageKey())
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
      .append("team=" + String.valueOf(getTeam()) + ", ")
      .append("taskImageKey=" + String.valueOf(getTaskImageKey()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TeamStep builder() {
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
      taskDate,
      team,
      taskImageKey);
  }
  public interface TeamStep {
    BuildStep team(Team team);
  }
  

  public interface BuildStep {
    Task build();
    BuildStep id(String id);
    BuildStep taskTitle(String taskTitle);
    BuildStep taskDescription(String taskDescription);
    BuildStep taskStatus(String taskStatus);
    BuildStep taskDate(Temporal.DateTime taskDate);
    BuildStep taskImageKey(String taskImageKey);
  }
  

  public static class Builder implements TeamStep, BuildStep {
    private String id;
    private Team team;
    private String taskTitle;
    private String taskDescription;
    private String taskStatus;
    private Temporal.DateTime taskDate;
    private String taskImageKey;
    @Override
     public Task build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Task(
          id,
          taskTitle,
          taskDescription,
          taskStatus,
          taskDate,
          team,
          taskImageKey);
    }
    
    @Override
     public BuildStep team(Team team) {
        Objects.requireNonNull(team);
        this.team = team;
        return this;
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
    
    @Override
     public BuildStep taskImageKey(String taskImageKey) {
        this.taskImageKey = taskImageKey;
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
    private CopyOfBuilder(String id, String taskTitle, String taskDescription, String taskStatus, Temporal.DateTime taskDate, Team team, String taskImageKey) {
      super.id(id);
      super.team(team)
        .taskTitle(taskTitle)
        .taskDescription(taskDescription)
        .taskStatus(taskStatus)
        .taskDate(taskDate)
        .taskImageKey(taskImageKey);
    }
    
    @Override
     public CopyOfBuilder team(Team team) {
      return (CopyOfBuilder) super.team(team);
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
    
    @Override
     public CopyOfBuilder taskImageKey(String taskImageKey) {
      return (CopyOfBuilder) super.taskImageKey(taskImageKey);
    }
  }
  
}
