package com.mattc.taskmaster.models;

import androidx.annotation.NonNull;

import java.util.Date;

public class Task {
    public String taskTitle;
    public String taskDescription;
    public String taskState;
    public Date taskDate;

    public Task(String taskTitle, String taskDescription, String taskState, Date taskDate) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskState = taskState;
        this.taskDate = taskDate;
    }

    @Override
    @NonNull
    public String toString() {
        return taskTitle +
                "\n" + taskDescription +
                "\nState: " + taskState +
                "\nDate Added = " + taskDate + "\n";
    }
}
