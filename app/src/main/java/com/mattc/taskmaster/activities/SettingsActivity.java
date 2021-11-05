package com.mattc.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.material.snackbar.Snackbar;
import com.mattc.taskmaster.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SettingsActivity extends AppCompatActivity {

    public final static String TAG = "mattycho_taskamster_settingsactivity";
    public final static String USERNAME_KEY = "username";
    public final static String TEAMNAME_KEY = "teamName";

    protected static SharedPreferences sharedPreferences;
    protected static SharedPreferences.Editor sharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

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
        List<String> teamNamesString = new ArrayList<>();
        for (Team team : teamList2) {
            teamNamesString.add(team.getTeamName());
        }
        Spinner teamSettingsSpinner = findViewById(R.id.teamSettingsSpinner);
        teamSettingsSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teamNamesString));

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferencesEditor = sharedPreferences.edit();

        EditText usernamePlainText = findViewById(R.id.usernameEditText);
        String username2 = sharedPreferences.getString(USERNAME_KEY, "");
        usernamePlainText.setText(username2);

        Button saveSettingsButton = findViewById(R.id.saveSettingsButton);
        saveSettingsButton.setOnClickListener(view -> {
            String username = usernamePlainText.getText().toString();
            String teamName = teamSettingsSpinner.getSelectedItem().toString();
            sharedPreferencesEditor.putString(USERNAME_KEY, username);
            sharedPreferencesEditor.putString(TEAMNAME_KEY, teamName);
            sharedPreferencesEditor.apply();
            Snackbar.make(findViewById(R.id.SettingsActivity), R.string.settingsSaved, Snackbar.LENGTH_SHORT).show();
        });
    }
}