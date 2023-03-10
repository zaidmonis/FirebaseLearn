package com.zaid.firebaselearn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText editEmail, editPassword, confirmPassword;
    Button signupButton;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeUI();
        firebaseAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(view -> {
            if (areCredentialsValidated()) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
                signup(editEmail.getText().toString(), editPassword.getText().toString());
            }
        });

    }

    private void signup(String email, String password) {
        //Signup
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                startActivity(new Intent(SignupActivity.this, MainActivity.class));
            } else {
                Toast.makeText(this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean areCredentialsValidated() {
        if (editEmail.getText().toString().equals("")) {
            editEmail.setError("Enter Email");
            return false;
        }
        if (editPassword.getText().toString().isEmpty()) {
            editPassword.setError("Enter Password");
            return false;
        }
        if (confirmPassword.getText().toString().isEmpty()) {
            confirmPassword.setError("Please Confirm Password");
            return false;
        }
        if (!editPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            editPassword.setError("Password & Confirm Password do not match!!!");
            Toast.makeText(this, "Password & Confirm Password do not match!!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editEmail.getText()!=null
                && editPassword.getText()!=null
                && confirmPassword.getText()!=null
                && !editEmail.getText().toString().isEmpty()
                && !editPassword.getText().toString().isEmpty()
                && !confirmPassword.getText().toString().isEmpty())
            return true;
        return false;
    }

    private void initializeUI() {
        editEmail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_password);
        confirmPassword = findViewById(R.id.edit_password_confirm);
        signupButton = findViewById(R.id.signup_button);
    }
}