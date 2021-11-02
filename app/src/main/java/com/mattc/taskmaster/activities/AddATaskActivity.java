package com.mattc.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mattc.taskmaster.R;
import com.mattc.taskmaster.models.Task;
import com.mattc.taskmaster.models.TaskStatusEnum;

import java.util.Date;

public class AddATaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atask);
//
//        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, DATABASE_INSTANCE_NAME)
//                .allowMainThreadQueries()
//                .build();

        TextView totalTaskTextView = findViewById(R.id.totalTaskTextView);
//        totalTaskTextView.setText("Total Tasks: " + taskDatabase.taskDao().findAll().size());

        // Spinner Stuff
        Spinner taskStatusSpinner = findViewById(R.id.taskStatusSpinner);
        taskStatusSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TaskStatusEnum.values()));

        Button addTaskPageButton = (Button) findViewById(R.id.addTaskPageButton);
        addTaskPageButton.setOnClickListener(view -> {

            EditText taskTitleEditText = findViewById(R.id.taskTitleEditText);
            EditText taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
            String taskTitleEditTextString = taskTitleEditText.getText().toString();
            String taskDescriptionEditTextString = taskDescriptionEditText.getText().toString();
            TaskStatusEnum taskStatus = TaskStatusEnum.valueOf(taskStatusSpinner.getSelectedItem().toString());

            Task newTask = new Task(taskTitleEditTextString, taskDescriptionEditTextString, new Date());
            newTask.taskStatus = taskStatus;
//            long newTaskId = taskDatabase.taskDao().insert(newTask);
            long newTaskId = 0;

//            Toast.makeText(AddATaskActivity.this, "Shopping Item saved! Id: " + newTaskId, Toast.LENGTH_SHORT).show();
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