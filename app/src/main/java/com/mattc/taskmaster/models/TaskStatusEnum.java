package com.mattc.taskmaster.models;

public enum TaskStatusEnum {

    NEW("New"),
    ASSIGNED("Assigned"),
    IN_PROGRESS("In Progress"),
    COMPLETE("Complete");

    private final String taskStatusString;

    TaskStatusEnum(String taskStatusString) {
        this.taskStatusString = taskStatusString;
    }

    public String getTaskStatusString() {
        return taskStatusString;
    }
}
