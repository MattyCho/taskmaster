package com.mattc.taskmaster.models;

import com.amplifyframework.datastore.generated.model.Task;

public enum TaskStatusEnum {

    NEW("New"),
    ASSIGNED("Assigned"),
    IN_PROGRESS("In Progress"),
    COMPLETE("Complete");

    private final String taskStatusString;

    @Override
    public String toString() {
        return taskStatusString;
    }

    TaskStatusEnum(String taskStatusString) {
        this.taskStatusString = taskStatusString;
    }

    public static TaskStatusEnum fromString(String inputTaskStatusText) {
        for (TaskStatusEnum taskStatus : TaskStatusEnum.values()) {
            if (taskStatus.taskStatusString.equalsIgnoreCase(inputTaskStatusText)) {
                return taskStatus;
            }
        }
        return null;
    }
}
