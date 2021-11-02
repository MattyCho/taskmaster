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

import java.util.ArrayList;
import java.util.List;

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
                        taskTitleTextView.setText(thisTask2.getTaskTitle());
                        taskDescriptionTextView.setText(thisTask2.getTaskDescription());
                        taskStatusTextView.setText(thisTask2.getTaskStatus());
                        taskDateTextView.setText(thisTask2.getTaskDate().toString());
                    });
                },
                failure -> {
                    Log.i(TAG, "Failed");
                }
        );
    }
}