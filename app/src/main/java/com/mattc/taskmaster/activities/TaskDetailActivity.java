package com.mattc.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.mattc.taskmaster.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TaskDetailActivity extends AppCompatActivity {

    public final static String TAG = "mattyc_taskmaster_taskdetailactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent intent = getIntent();
        String taskId = intent.getStringExtra(MainActivity.TASK_ID_EXTRA_STRING);

        Amplify.API.query(
                ModelQuery.list(Task.class),
                success -> {
                    Task thisTask = null;
                    for (Task task : success.getData()) {
                        if (taskId.equals(task.getId())) {
                            thisTask = task;
                            break;
                        }
                        Log.i(TAG, "Succeeded read of Task: " + task.getTaskTitle());
                    }
                    Task thisTask2 = thisTask;
                    runOnUiThread(() -> {
                        TextView taskTitleTextView = findViewById(R.id.taskTitleTextView);
                        TextView taskDescriptionTextView = findViewById(R.id.taskDescriptionTextView);
                        TextView taskStatusTextView = findViewById(R.id.taskStatusTextView);
                        TextView taskDateTextView = findViewById(R.id.taskDateTextView);

                        DateFormat localIso8601InputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                        localIso8601InputDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                        DateFormat dateOutputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        dateOutputFormat.setTimeZone(TimeZone.getDefault());
                        String timeAddedString = "";
                        try
                        {
                            Date timeAddedDate = localIso8601InputDateFormat.parse(thisTask2.getTaskDate().format());
                            if (timeAddedDate != null)
                            {
                                timeAddedString = dateOutputFormat.format(timeAddedDate);
                            }
                        }
                        catch (ParseException pe)
                        {
                            Log.i(TAG, "Error converting shopping item date to string: " + pe.getMessage(), pe);
                        }

                        taskTitleTextView.setText(thisTask2.getTaskTitle());
                        taskDescriptionTextView.setText(thisTask2.getTaskDescription());
                        taskStatusTextView.setText(thisTask2.getTaskStatus());
                        taskDateTextView.setText(timeAddedString);
                    });
                },
                failure -> {
                    Log.i(TAG, "Failed");
                }
        );
    }
}