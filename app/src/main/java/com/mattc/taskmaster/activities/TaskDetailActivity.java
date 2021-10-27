package com.mattc.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mattc.taskmaster.R;

public class TaskDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        Intent intent = getIntent();
        String taskTitle = intent.getExtras().get(MainActivity.TASK_TITLE_EXTRA_STRING).toString();
        TextView taskTitleTextView = findViewById(R.id.taskTitleTextView);
        taskTitleTextView.setText(taskTitle);
    }
}