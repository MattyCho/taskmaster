package com.mattc.taskmaster.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.Date;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String taskTitle;
    public String taskDescription;
    public TaskStatusEnum taskStatus;
    public Date taskDate;

    public Task(String taskTitle, String taskDescription, Date taskDate) {
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDate = taskDate;
    }

    @Override
    @NonNull
    public String toString() {
        return taskTitle +
                "\n" + taskDescription +
                "\nState: " + taskStatus +
                "\nDate Added = " + taskDate + "\n";
    }
}
