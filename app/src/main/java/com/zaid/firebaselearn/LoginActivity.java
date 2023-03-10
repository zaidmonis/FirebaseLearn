package com.zaid.firebaselearn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText editEmail, editPassword;
    Button loginButton, gotoSignupButton;
    TextView forgotPassword;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeUI();
        firebaseAuth = FirebaseAuth.getInstance();


        gotoSignupButton.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));

        loginButton.setOnClickListener(view -> {
            if (areCredentialsValidated())
                login(editEmail.getText().toString(), editPassword.getText().toString());
        });

        forgotPassword.setOnClickListener(view -> {
            if (editEmail.getText() != null && !editEmail.getText().toString().isEmpty()) {
                sendForgotPasswordEmail(editEmail.getText().toString());
            } else {
                editEmail.setError("Please enter email ID to send password reset email!");
            }
        });
    }

    private void login(String email, String password) {
        //Login
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Toast.makeText(this, "Error: " +task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendForgotPasswordEmail(String emailAddress) {

    }

    private boolean areCredentialsValidated() {
        if (editEmail.getText()!=null
                && editPassword.getText()!=null
                && !editEmail.getText().toString().isEmpty()
                && !editPassword.getText().toString().isEmpty())
            return true;
        if (editEmail.getText().toString().equals("")) {
            editEmail.setError("Enter Email");
            return false;
        }
        if (editPassword.getText().toString().isEmpty()) {
            editPassword.setError("Enter Password");
            return false;
        }
        return false;
    }

    private void initializeUI() {
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        loginButton = findViewById(R.id.login_button);
        gotoSignupButton = findViewById(R.id.goto_signup_button);
        forgotPassword = findViewById(R.id.forgot_password);

    }
}