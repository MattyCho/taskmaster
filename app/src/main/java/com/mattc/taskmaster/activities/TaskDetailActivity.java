package com.mattc.taskmaster.activities;

import static com.mattc.taskmaster.activities.MainActivity.DATABASE_INSTANCE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mattc.taskmaster.R;
import com.mattc.taskmaster.database.TaskDatabase;
import com.mattc.taskmaster.models.Task;

public class TaskDetailActivity extends AppCompatActivity {

    TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, DATABASE_INSTANCE_NAME)
                .allowMainThreadQueries()
                .build();

        Intent intent = getIntent();
        long taskId = intent.getLongExtra(MainActivity.TASK_ID_EXTRA_STRING, -1);
        Task task = taskDatabase.taskDao().findById(taskId);

        TextView taskTitleTextView = findViewById(R.id.taskTitleTextView);
        TextView taskDescriptionTextView = findViewById(R.id.taskDescriptionTextView);
        TextView taskStatusTextView = findViewById(R.id.taskStatusTextView);
        TextView taskDateTextView = findViewById(R.id.taskDateTextView);
        taskTitleTextView.setText(task.taskTitle);
        taskDescriptionTextView.setText(task.taskDescription);
        taskStatusTextView.setText(task.taskStatus.toString());
        taskDateTextView.setText(task.taskDate.toString());
    }
}