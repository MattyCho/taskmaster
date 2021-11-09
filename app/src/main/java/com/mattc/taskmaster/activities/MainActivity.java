package com.mattc.taskmaster.activities;

import static com.mattc.taskmaster.activities.SettingsActivity.TEAMNAME_KEY;
import static com.mattc.taskmaster.activities.SettingsActivity.USERNAME_KEY;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUser;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.mattc.taskmaster.R;
import com.mattc.taskmaster.adapters.TaskListRecyclerViewAdapter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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

        AuthUser currentUser = Amplify.Auth.getCurrentUser();
        if (currentUser == null) {
            Intent goToLoginActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(goToLoginActivityIntent);
        }

//        Manually saving a test file to S3
//        File testFile = new File(getApplicationContext().getFilesDir(), "testFileName");
//
//        try (BufferedWriter testFileBufferedWriter = new BufferedWriter(new FileWriter(testFile))){
//            testFileBufferedWriter.append("this is a test");
//        } catch (IOException ioe) {
//            Log.i(TAG, "Error when writing test file: " + ioe.getMessage(), ioe);
//        }
//
//        Amplify.Storage.uploadFile(
//          "testFileKey",
//                testFile,
//                success -> {Log.i(TAG, "S3 test file upload was succeeded! Key is: " + success.getKey());},
//                failure -> {Log.i(TAG, "S3 test file upload failed! " + failure.getMessage(), failure);}
//        );

        // Grab sharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        res = getResources();

        if (currentUser != null) {
            String username = currentUser.getUsername();
            Amplify.Auth.fetchUserAttributes(
                    success -> {
                        Log.i(TAG, "Fetch user attributes succeeded: " + success.toString());
                    },
                    failure -> {
                        Log.i(TAG, "Fetch user attributes failed: " + failure.toString());
                    });
        }

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

        getTaskList();

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

        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(view -> {
            Amplify.Auth.signOut(
                    () -> {
                        Log.i(TAG, "Logout Succeeded");
                    },
                    failure -> {
                        Log.i(TAG, "Logout Failed: " + failure.toString());
                    }
            );
            Intent goToLoginActivity = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(goToLoginActivity);
        });
    }

    protected void getTaskList() {
        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    List<Task> taskList = new ArrayList<>();
                    String teamName = sharedPreferences.getString(TEAMNAME_KEY, "");
                    for (Task task : success.getData()) {
                        if (teamName.equals("")) {
                            taskList.add(task);
                            Log.i(TAG, "Succeeded read of Task: " + task.getTaskTitle());
                        }
                        if (teamName.equals(task.getTeam().getTeamName())) {
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        AuthUser currentUser = Amplify.Auth.getCurrentUser();
        if (currentUser == null) {
            Intent goToLoginActivityIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(goToLoginActivityIntent);
        }

        getTaskList();

        // Grabs username from sharedPreferences
        String username = sharedPreferences.getString(USERNAME_KEY, "");
        if (!username.equals("")) {
            ((TextView) findViewById(R.id.myTaskTextView)).setText(res.getString(R.string.WelcomeUsername, username));
        }
    }

}