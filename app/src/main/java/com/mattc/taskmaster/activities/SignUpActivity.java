package com.mattc.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.mattc.taskmaster.R;

public class SignUpActivity extends AppCompatActivity {

    public final static String TAG = "mattyc_taskmaster_signupactivity";
    public final static String NEW_USER_EMAIL = "newUserEmail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener( view -> {
            EditText signUpUsernameEditText = findViewById(R.id.signUpEmailEditText);
            String username = signUpUsernameEditText.getText().toString();
            EditText signUpPasswordEditText = findViewById(R.id.signUpPasswordEditText);
            String password = signUpPasswordEditText.getText().toString();

            Amplify.Auth.signUp(username,
                    password,
                    AuthSignUpOptions.builder()
                            .userAttribute(AuthUserAttributeKey.email(), username)
                            .build(),
                    success -> {
                        Log.i(TAG, "Signup Succeeded: " + success.toString());
                        Intent goToVerifyActivity = new Intent(SignUpActivity.this, SignUpVerifyActivity.class);
                        goToVerifyActivity.putExtra(NEW_USER_EMAIL, username);
                        startActivity(goToVerifyActivity);
                    },
                    failure -> {
                        Log.i(TAG, "Signup Failed: " + failure.toString());
                        runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Could not sign up that user!", Toast.LENGTH_SHORT).show());
                    });
        });
    }
}