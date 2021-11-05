package com.mattc.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.mattc.taskmaster.R;
import com.mattc.taskmaster.models.TaskStatusEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddATaskActivity extends AppCompatActivity {

    public final static String TAG = "mattyc_taskmaster_addataskactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atask);

        // Task Status Spinner
        Spinner taskStatusSpinner = findViewById(R.id.taskStatusSpinner);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TaskStatusEnum.values()));

        // Team Spinner
        CompletableFuture<List<Team>> teamListCompletableFuture = new CompletableFuture<>();
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    List<Team> teamList = new ArrayList<>();
                    for (Team team : success.getData()) {
                        teamList.add(team);
                        Log.i(TAG, "Successfully grabbed teams");
                    }
                    teamListCompletableFuture.complete(teamList);
                },
                failure -> {
                    Log.i(TAG, "Failed");
                    teamListCompletableFuture.complete(null);
                }
        );

        List<Team> teamList2 = null;
        try {
            teamList2 = teamListCompletableFuture.get();
        } catch (InterruptedException ie) {
            Log.i(TAG, "InterruptedException while getting business unit: " + ie.getMessage());
            Thread.currentThread().interrupt();
        }
        catch (ExecutionException ee)
        {
            Log.i(TAG, "ExecutionException while getting business unit:  " + ee.getMessage());
        }

        // Team Spinner
        List<String> teamNamesString = new ArrayList<>();
        for (Team team : teamList2) {
            teamNamesString.add(team.getTeamName());
        }
        Spinner teamSpinner = findViewById(R.id.teamSpinner);
        teamSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teamNamesString));

        Button addTaskPageButton = findViewById(R.id.addTaskPageButton);
        List<Team> teamList3 = teamList2;
        addTaskPageButton.setOnClickListener(view -> {

            EditText taskTitleEditText = findViewById(R.id.taskTitleEditText);
            EditText taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
            String taskTitleEditTextString = taskTitleEditText.getText().toString();
            String taskDescriptionEditTextString = taskDescriptionEditText.getText().toString();
            String taskStatus = TaskStatusEnum.fromString(taskStatusSpinner.getSelectedItem().toString()).toString();
            String taskTeamName = teamSpinner.getSelectedItem().toString();
            Team taskTeam = null;
            for (Team team : teamList3) {
                if (taskTeamName.equals(team.getTeamName())) {
                    taskTeam = team;
                }
            };
            String taskDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

            // use builder to create new Task
            Task newTask = Task.builder()
                    .team(taskTeam)
                    .taskTitle(taskTitleEditTextString)
                    .taskDescription(taskDescriptionEditTextString)
                    .taskStatus(taskStatus)
                    .taskDate(new Temporal.DateTime(taskDateString))
                    .build();
            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    success -> Log.i(TAG, "Succeeded"),
                    failure -> Log.i(TAG, "Failed")
            );

            Toast.makeText(AddATaskActivity.this, "Task Saved!", Toast.LENGTH_SHORT).show();
        });
    }

    private int getSpinnerIndex(Spinner spinner, String stringValueToCheck){
        for (int i = 0;i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(stringValueToCheck)){
                return i;
            }
        }
        return 0;
    }
}