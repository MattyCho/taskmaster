package com.mattc.taskmaster.activities;

import static com.mattc.taskmaster.activities.SettingsActivity.TEAMNAME_KEY;
import static com.mattc.taskmaster.activities.SettingsActivity.USERNAME_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.mattc.taskmaster.R;
import com.mattc.taskmaster.adapters.TaskListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "mattyc_taskmaster_mainactivity";
    public final static String TASK_ID_EXTRA_STRING = "taskID";
    protected static SharedPreferences sharedPreferences;
    protected static Resources res;
    TaskListRecyclerViewAdapter taskListRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResources();

//        Manual creation of three teams
//        Team teamMystic = Team.builder()
//                .teamName("Team Mystic")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(teamMystic),
//                success -> Log.i(TAG, "Succeeded in creating a team"),
//                failure -> Log.i(TAG, "Failed to create a team")
//        );
//        Team teamValor = Team.builder()
//                .teamName("Team Valor")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(teamValor),
//                success -> Log.i(TAG, "Succeeded in creating a team"),
//                failure -> Log.i(TAG, "Failed to create a team")
//        );
//        Team teamInstinct = Team.builder()
//                .teamName("Team Instinct")
//                .build();
//        Amplify.API.mutate(
//                ModelMutation.create(teamInstinct),
//                success -> Log.i(TAG, "Succeeded in creating a team"),
//                failure -> Log.i(TAG, "Failed to create a team")
//        );

        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    List<Task> taskList = new ArrayList<>();
                    for (Task task : success.getData()) {
                        taskList.add(task);
                        Log.i(TAG, "Succeeded read of Task: " + task.getTaskTitle());
                    }
                    runOnUiThread(() -> {
                        taskListRecyclerViewAdapter.setTaskList(taskList);
                        taskListRecyclerViewAdapter.notifyDataSetChanged();
                    });
                },
                failure -> {
                    Log.i(TAG, "Failed");
                }
        );

        // Set up RecyclerView and Layout Manager
        RecyclerView taskListRecyclerView = findViewById(R.id.taskListRecyclerView);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        taskListRecyclerView.setLayoutManager(lm);

        // Create list to hold tasks
        List<Task> taskList = new ArrayList<>();

        // Setup RecyclerViewAdapter
        taskListRecyclerViewAdapter = new TaskListRecyclerViewAdapter(this, taskList);
        taskListRecyclerView.setAdapter(taskListRecyclerViewAdapter);

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

        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    List<Task> taskList = new ArrayList<>();
                    String teamName = sharedPreferences.getString(TEAMNAME_KEY, "");
                    for (Task task : success.getData()) {
                        if (!teamName.equals("") && teamName.equals(task.getTeam().getTeamName())) {
                            taskList.add(task);
                            Log.i(TAG, "Succeeded read of Task: " + task.getTaskTitle());
                        }
                    }
                    runOnUiThread(() -> {
                        taskListRecyclerViewAdapter.setTaskList(taskList);
                        taskListRecyclerViewAdapter.notifyDataSetChanged();
                    });
                },
                failure -> {
                    Log.i(TAG, "Failed");
                }
        );

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