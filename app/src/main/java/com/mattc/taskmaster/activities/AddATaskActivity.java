package com.mattc.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.mattc.taskmaster.R;

public class AddATaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_atask);

        Button addTaskPageButton = (Button) findViewById(R.id.addTaskPageButton);
        addTaskPageButton.setOnClickListener(view -> {
            TextView submittedTextView = (TextView) findViewById(R.id.submittedTextView);
            submittedTextView.setText(R.string.submitted);
        });
    }
}