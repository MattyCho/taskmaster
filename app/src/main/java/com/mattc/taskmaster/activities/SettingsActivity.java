package com.mattc.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mattc.taskmaster.R;

public class SettingsActivity extends AppCompatActivity {

    public final static String TAG = "mattycho_taskamster_settingsactivity";
    public final static String USERNAME_KEY = "username";
    protected static SharedPreferences sharedPreferences;
    protected static SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferencesEditor = sharedPreferences.edit();

        EditText usernamePlainText = findViewById(R.id.usernameEditText);
        String username2 = sharedPreferences.getString(USERNAME_KEY, "");
        usernamePlainText.setText(username2);

        Button saveSettingsButton = findViewById(R.id.saveSettingsButton);
        saveSettingsButton.setOnClickListener(view -> {
            String username = usernamePlainText.getText().toString();
            sharedPreferencesEditor.putString(USERNAME_KEY, username);
            sharedPreferencesEditor.apply();
            Snackbar.make(findViewById(R.id.SettingsActivity), R.string.settingsSaved, Snackbar.LENGTH_SHORT).show();
//            Toast.makeText(this, R.string.settingsSaved, Toast.LENGTH_SHORT).show();
        });
    }
}