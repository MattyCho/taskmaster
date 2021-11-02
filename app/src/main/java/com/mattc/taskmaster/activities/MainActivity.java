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
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "mattyc_taskmaster_mainactivity";
    public final static String TASK_ID_EXTRA_STRING = "taskID";
    public final static String DATABASE_INSTANCE_NAME = "mattyc_taskmaster_db";
    protected static SharedPreferences sharedPreferences;
    protected static Resources res;
    TaskListRecyclerViewAdapter taskListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Build Database
//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, DATABASE_INSTANCE_NAME)
//                .allowMainThreadQueries()
//                .build();

        // Set up RecyclerView and Layout Manager
        RecyclerView taskListRecyclerView = findViewById(R.id.taskListRecyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        taskListRecyclerView.setLayoutManager(lm);

        // Create list to hold tasks
        List<Task> taskList = new ArrayList<>();


        // Setup RecyclerViewAdapter
        taskListRecyclerViewAdapter = new TaskListRecyclerViewAdapter(this, taskList);
        taskListRecyclerView.setAdapter(taskListRecyclerViewAdapter);

        // Grab sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResources();

        // Add Task Button
        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(view -> {
            Intent addTaskIntent = new Intent(MainActivity.this, AddATaskActivity.class);
            startActivity(addTaskIntent);
        });

        // All Task Button
        Button allTasksButton = findViewById(R.id.allTasksButton);
        allTasksButton.setOnClickListener(view -> {
            Intent allTasksIntent = new Intent(MainActivity.this, AllTasksActivity.class);
            startActivity(allTasksIntent);
        });

        // Settings Button
        ImageView settingsImageView = findViewById(R.id.settingsImageView);
        settingsImageView.setOnClickListener(view -> {
            Intent settingsActivityIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsActivityIntent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        List<Task> taskList2 = taskDatabase.taskDao().findAll();
        List<Task> taskList2 = new ArrayList<>();
        taskListRecyclerViewAdapter.setTaskList(taskList2);
        taskListRecyclerViewAdapter.notifyDataSetChanged();

        // Grabs username from sharedPreferences
        String username = sharedPreferences.getString(USERNAME_KEY, "");
        if (!username.equals("")) {
            ((TextView) findViewById(R.id.myTaskTextView)).setText(res.getString(R.string.WelcomeUsername, username));
        }
    }

}