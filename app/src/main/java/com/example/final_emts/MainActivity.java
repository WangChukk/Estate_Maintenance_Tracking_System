package com.example.final_emts;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText loginEmail, loginPassword;
    private Button login;
    private TextView registerButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        registerButton = findViewById(R.id.regesterbtn);
        loginEmail = findViewById(R.id.userEmail);
        loginPassword = findViewById(R.id.userPassword);
        login = findViewById(R.id.loginbtn);

        // Check if the user is already signed in
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startHomeActivity();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = loginEmail.getText().toString().trim();
                password = loginPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    showToast("Please Enter Email");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    showToast("Please Enter Password");
                    return;
                }

                signInWithEmailAndPassword(email, password);
            }
        });

        setupPasswordVisibilityToggle();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRegisterActivity();
            }
        });
    }

    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            showToast("Login successful");
                            startHomeActivity();
                        } else {
                            handleLoginError(task.getException());
                        }
                    }
                });
    }

    private void startHomeActivity() {
        Intent intent = new Intent(MainActivity.this, Home_Page_LoaderActivity.class);
        startActivity(intent);
        finish();
    }

    private void handleLoginError(Exception exception) {
        if (exception instanceof FirebaseAuthException) {
            showToast("Login failed: " + ((FirebaseAuthException) exception).getMessage());
        } else {
            showToast("Incorrect Password");
        }
    }

    private void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void setupPasswordVisibilityToggle() {
        TextInputEditText userPassword = findViewById(R.id.userPassword);
        userPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rightBound = userPassword.getRight();
                int iconWidth = userPassword.getCompoundPaddingRight();
                int clickX = (int) v.getX();

                if (clickX >= rightBound - iconWidth && clickX <= rightBound) {
                    togglePasswordVisibility(userPassword);
                }
            }
        });
    }

    private void togglePasswordVisibility(TextInputEditText editText) {
        if (editText.getInputType() == InputType.TYPE_TEXT_VARIATION_PASSWORD) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private void navigateToRegisterActivity() {
        Intent intent = new Intent(MainActivity.this,RegesterActivity.class);
        startActivity(intent);
    }
}
