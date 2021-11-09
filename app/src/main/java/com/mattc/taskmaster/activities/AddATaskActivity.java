package com.mattc.taskmaster.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.mattc.taskmaster.R;
import com.mattc.taskmaster.models.TaskStatusEnum;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddATaskActivity extends AppCompatActivity {

    public final static String TAG = "mattyc_taskmaster_addataskactivity";
    ActivityResultLauncher<Intent> activityResultLauncher;

    Team taskTeam;
    String taskTitleEditTextString;
    String taskDescriptionEditTextString;
    String taskStatus;
    String taskDateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atask);

        activityResultLauncher = getImagePickingActivityResultLauncher();

        // Task Status Spinner
        Spinner taskStatusSpinner = findViewById(R.id.taskStatusSpinner);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TaskStatusEnum.values()));

        // Get List of Teams with CompletableFuture
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
                    Log.e(TAG, "Failed to get Teams with error: " + failure.getMessage(), failure);
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
            Log.e(TAG, "ExecutionException while getting business unit:  " + ee.getMessage(), ee);
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
            taskTitleEditTextString = taskTitleEditText.getText().toString();
            taskDescriptionEditTextString = taskDescriptionEditText.getText().toString();
            taskStatus = TaskStatusEnum.fromString(taskStatusSpinner.getSelectedItem().toString()).toString();
            String taskTeamName = teamSpinner.getSelectedItem().toString();
            for (Team team : teamList3) {
                if (taskTeamName.equals(team.getTeamName())) {
                    taskTeam = team;
                }
            };
            taskDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

            saveToS3AndDb();
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

    // Intent to grab an image file using a file picker
    protected void saveToS3AndDb() {
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("*/*");
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png", "image/gif"});
        activityResultLauncher.launch(imageFilePickingIntent);
    }

    protected ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
        ActivityResultLauncher<Intent> imagePickingActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    // converts image file to an input stream
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            if (result.getData() != null) {
                                Uri pickedImageFileUri = result.getData().getData();

                                try {
                                    InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
                                    String pickedImageFilename = getFilenameFromUri(pickedImageFileUri);
                                    Log.i(TAG, "Succeeded in getting input stream from file on phone! Filename is: " + pickedImageFilename);
                                    // upload InputStream to S3
                                    uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename);
                                } catch (FileNotFoundException fnfe) {
                                    Log.e(TAG, "Could not get file from file picker!" + fnfe.getMessage(), fnfe);
                                }
                            }
                        }
                    }
                }
        );
        return imagePickingActivityResultLauncher;
    }

    // Taken from https://stackoverflow.com/a/25005243/16889809
    @SuppressLint("Range")
    protected String getFilenameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null))
            {
                if (cursor != null && cursor.moveToFirst())
                {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    protected void uploadInputStreamToS3(InputStream pickedImageFileInputStream, String pickedImageFilename) {
        Amplify.Storage.uploadInputStream(
                pickedImageFilename,
                pickedImageFileInputStream,
                success -> {
                    Log.i(TAG, "Succeeded in uploading file to S3! Key is: " + success.getKey());
                    saveTaskToDB(success.getKey());
                },
                failure -> {
                    Log.e(TAG, "Failed to upload file to S3 with filename: " + pickedImageFilename + " with error: " + failure.getMessage(), failure);
                }
        );
    }

    protected void saveTaskToDB(String awsImageKey) {
        // use builder to create new Task
        Task newTask = Task.builder()
                .team(taskTeam)
                .taskTitle(taskTitleEditTextString)
                .taskDescription(taskDescriptionEditTextString)
                .taskStatus(taskStatus)
                .taskDate(new Temporal.DateTime(taskDateString))
                .taskImageKey(awsImageKey)
                .build();
        Amplify.API.mutate(
                ModelMutation.create(newTask),
                success -> Log.i(TAG, "Succeeded"),
                failure -> Log.i(TAG, "Failed")
        );

        Toast.makeText(AddATaskActivity.this, "Task Saved!", Toast.LENGTH_SHORT).show();
    }
}