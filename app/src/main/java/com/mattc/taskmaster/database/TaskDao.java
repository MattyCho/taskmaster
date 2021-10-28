package com.mattc.taskmaster.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mattc.taskmaster.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long insert(Task task);

    @Query("SELECT * FROM Task")
    List<Task> findAll();

    @Query("SELECT * FROM Task WHERE id= :id")
    Task findById(long id);

    @Update
    void update(Task task);
}
