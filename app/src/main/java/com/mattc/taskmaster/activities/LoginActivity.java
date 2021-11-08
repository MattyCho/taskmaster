package com.mattc.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.mattc.taskmaster.R;

public class LoginActivity extends AppCompatActivity {

    public final static String TAG = "mattyc_taskmaster_loginactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener( view -> {
            EditText loginUsernameEditText = findViewById(R.id.loginEmailEditText);
            String username = loginUsernameEditText.getText().toString();
            EditText loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
            String password = loginPasswordEditText.getText().toString();

            Amplify.Auth.signIn(username,
                    password,
                    success -> {
                        Log.i(TAG, "Login Succeeded: " + success.toString());
                        Intent goToMainActivityIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(goToMainActivityIntent);
                    },
                    failure -> {
                        Log.i(TAG, "Login Failed: " + failure.toString());
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Could not log in that user!", Toast.LENGTH_SHORT).show());
                    });
        });

        Button signUpButton = findViewById(R.id.loginSignupButton);
        signUpButton.setOnClickListener( view -> {
            Intent goToSignUpActivity = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(goToSignUpActivity);
        });
    }
}