package com.mattc.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.mattc.taskmaster.models.Task;

@Database(entities = {Task.class}, version = 1)
@TypeConverters({TaskDatabaseConverter.class})
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
