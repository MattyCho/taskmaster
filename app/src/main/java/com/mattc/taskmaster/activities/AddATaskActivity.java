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
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.mattc.taskmaster.R;
import com.mattc.taskmaster.models.TaskStatusEnum;

import java.util.Date;

public class AddATaskActivity extends AppCompatActivity {

    public final static String TAG = "mattyc_taskmaster_addataskactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atask);

        // TODO: fix task counter
        TextView totalTaskTextView = findViewById(R.id.totalTaskTextView);

        // Spinner Stuff
        Spinner taskStatusSpinner = findViewById(R.id.taskStatusSpinner);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TaskStatusEnum.values()));

        Button addTaskPageButton = (Button) findViewById(R.id.addTaskPageButton);
        addTaskPageButton.setOnClickListener(view -> {

            EditText taskTitleEditText = findViewById(R.id.taskTitleEditText);
            EditText taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
            String taskTitleEditTextString = taskTitleEditText.getText().toString();
            String taskDescriptionEditTextString = taskDescriptionEditText.getText().toString();
            String taskStatus = taskStatusSpinner.getSelectedItem().toString();
            String taskDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());

            // use builder to create new Task
            Task newTask = Task.builder()
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