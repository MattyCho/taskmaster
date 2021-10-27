package com.mattc.taskmaster.activities;

import static com.mattc.taskmaster.activities.SettingsActivity.USERNAME_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattc.taskmaster.R;
import com.mattc.taskmaster.adapters.TaskListRecyclerViewAdapter;
import com.mattc.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "mattyc_taskmaster_mainactivity";
    public final static String TASK_TITLE_EXTRA_STRING = "taskTitle";
    protected static SharedPreferences sharedPreferences;
    protected static Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView taskListRecyclerView = findViewById(R.id.taskListRecyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        taskListRecyclerView.setLayoutManager(lm);

        List<Task> taskList = new ArrayList<>();
        taskList.add(new Task("Task Title 1", "Task Description 1", "new", new Date()));
        taskList.add(new Task("Task Title 2", "Task Description 2", "assigned", new Date()));
        taskList.add(new Task("Task Title 3", "Task Description 3", "in progress", new Date()));
        taskList.add(new Task("Task Title 4", "Task Description 4", "complete", new Date()));
        taskList.add(new Task("Task Title 5", "Task Description 5", "new", new Date()));
        taskList.add(new Task("Task Title 6", "Task Description 6", "assigned", new Date()));
        taskList.add(new Task("Task Title 7", "Task Description 7", "in progress", new Date()));
        taskList.add(new Task("Task Title 8", "Task Description 8", "complete", new Date()));
        taskList.add(new Task("Task Title 9", "Task Description 9", "new", new Date()));
        taskList.add(new Task("Task Title 10", "Task Description 10", "assigned", new Date()));

        TaskListRecyclerViewAdapter taskListRecyclerViewAdapter = new TaskListRecyclerViewAdapter(this, taskList);
        taskListRecyclerView.setAdapter(taskListRecyclerViewAdapter);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResources();

        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(view -> {
            Intent addTaskIntent = new Intent(MainActivity.this, AddATaskActivity.class);
            startActivity(addTaskIntent);
        });

        Button allTasksButton = findViewById(R.id.allTasksButton);
        allTasksButton.setOnClickListener(view -> {
            Intent allTasksIntent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(allTasksIntent);
        });

//        Button task1Button = findViewById(R.id.task1Button);
//        task1Button.setOnClickListener(view -> {
//            TextView task1TextView = findViewById(R.id.task1TextView);
//            Intent taskDetailIntent = new Intent(MainActivity.this, TaskDetailActivity.class);
//            taskDetailIntent.putExtra(TASK_TITLE_EXTRA_STRING, task1TextView.getText());
//            startActivity(taskDetailIntent);
//        });
//
//        Button task2Button = findViewById(R.id.task2Button);
//        task2Button.setOnClickListener(view -> {
//            TextView task2TextView = findViewById(R.id.task2TextView);
//            Intent taskDetailIntent = new Intent(MainActivity.this, TaskDetailActivity.class);
//            taskDetailIntent.putExtra(TASK_TITLE_EXTRA_STRING, task2TextView.getText());
//            startActivity(taskDetailIntent);
//        });
//
//        Button task3Button = findViewById(R.id.task3Button);
//        task3Button.setOnClickListener(view -> {
//            TextView task3TextView = findViewById(R.id.task3TextView);
//            Intent taskDetailIntent = new Intent(MainActivity.this, TaskDetailActivity.class);
//            taskDetailIntent.putExtra(TASK_TITLE_EXTRA_STRING, task3TextView.getText());
//            startActivity(taskDetailIntent);
//        });

        ImageView settingsImageView = findViewById(R.id.settingsImageView);
        settingsImageView.setOnClickListener(view -> {
            Intent settingsActivityIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsActivityIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        String username = sharedPreferences.getString(USERNAME_KEY, "");

        if (!username.equals("")) {
            ((TextView) findViewById(R.id.myTaskTextView)).setText(res.getString(R.string.WelcomeUsername, username));
        }
    }

}