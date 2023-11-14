package com.example.final_emts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegesterActivity extends AppCompatActivity {
    private EditText userEmail, userPassword, userConfirmPassword;
    private Button regbtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);
        mAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userpassword);
        userConfirmPassword = findViewById(R.id.userConfirmpassword);
        regbtn = findViewById(R.id.regbtn);

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = userEmail.getText().toString().trim();
                String pass = userPassword.getText().toString().trim();
                String confpass = userConfirmPassword.getText().toString().trim();

                // Input validation and error handling
                if (TextUtils.isEmpty(user)) {
                    Toast.makeText(RegesterActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(user)) {
                    Toast.makeText(RegesterActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(RegesterActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confpass)) {
                    Toast.makeText(RegesterActivity.this, "Please Enter Confirmation Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(confpass)) {
                    Toast.makeText(RegesterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Firebase user registration
                mAuth.createUserWithEmailAndPassword(user, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registration success
                                    Toast.makeText(RegesterActivity.this, "Successfully Registered. Please verify your email.", Toast.LENGTH_SHORT).show();
                                    sendEmailVerification(); // Send email verification

                                } else {
                                    // Registration failed
                                    Toast.makeText(RegesterActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    // Validate email using a regular expression
    private boolean isValidEmail(String email) {
        String pattern = "^[0-9]+\\.cst@rub\\.edu\\.bt$";
        return email.matches(pattern);
    }

    // Send email verification link
    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegesterActivity.this, "Verification email sent.", Toast.LENGTH_SHORT).show();
                                redirectToMainActivity();
                            } else {
                                Toast.makeText(RegesterActivity.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // Redirect to the main activity
    private void redirectToMainActivity() {
        Intent intent = new Intent(RegesterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
