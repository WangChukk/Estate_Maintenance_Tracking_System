package com.example.final_emts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Edit_Profile_Activity extends AppCompatActivity {
    private EditText username, useremail, userdepartment, userID, userContact, userGender;
    private Button SaveBtn, chooseimg;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private Uri imgUri;
    private StorageReference storageProfilePicker;
    private ShapeableImageView profileimageView;
    private String currentUserEmail;
    private ImageView imagebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        imagebtn = findViewById(R.id.backBtn);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUserEmail = currentUser != null ? currentUser.getEmail() : null;

        storageProfilePicker = FirebaseStorage.getInstance().getReference().child("profilePics");
        profileimageView = findViewById(R.id.imgProfile);
        username = findViewById(R.id.username);
        useremail = findViewById(R.id.useremail);
        userdepartment = findViewById(R.id.userdepartment);
        userID = findViewById(R.id.userID);
        userContact = findViewById(R.id.userContact);
        userGender = findViewById(R.id.userGender);
        SaveBtn = findViewById(R.id.SaveBtn);
        chooseimg = findViewById(R.id.chooseImage);

        imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("profileData");
        fetchUserProfileData();

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserProfile();
            }
        });
    }

    private void fetchUserProfileData() {
        if (currentUserEmail != null) {
            String encodedEmail = currentUserEmail.replace(".", ",");
            DatabaseReference userRef = databaseReference.child(encodedEmail);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                        if (userProfile != null) {
                            username.setText(userProfile.getName());
                            useremail.setText(userProfile.getStdEmail());
                            userdepartment.setText(userProfile.getDepartment());
                            userID.setText(userProfile.getStdID());
                            userContact.setText(userProfile.getUserContact());
                            userGender.setText(userProfile.getUserGender());

                            // Load and display the existing profile picture (if available)
                            String profileImageURL = userProfile.getProfileImageURL();
                            if (profileImageURL != null && !profileImageURL.isEmpty()) {
                                Picasso.get().load(profileImageURL).into(profileimageView);
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error, e.g., display an error message
                }
            });
        }
    }

    private void saveUserProfile() {
        String updatedName = username.getText().toString();
        String updatedEmail = useremail.getText().toString();
        String updatedDepartment = userdepartment.getText().toString();
        String updatedID = userID.getText().toString();
        String updatedContact = userContact.getText().toString();
        String updatedGender = userGender.getText().toString();

        DatabaseReference userRef = databaseReference.child(currentUserEmail.replace(".", ","));

        userRef.child("name").setValue(updatedName);
        userRef.child("stdEmail").setValue(updatedEmail);
        userRef.child("department").setValue(updatedDepartment);
        userRef.child("stdID").setValue(updatedID);
        userRef.child("userContact").setValue(updatedContact);
        userRef.child("userGender").setValue(updatedGender);

        if (imgUri != null) {
            // Upload the new profile picture
            uploadProfileImage(userRef);
        } else {
            Toast.makeText(Edit_Profile_Activity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileImage(final DatabaseReference userRef) {
        if (currentUserEmail != null) {
            final String fileName = "profilePic_" + System.currentTimeMillis();
            StorageReference profileImageRef = storageProfilePicker.child(fileName);

            profileImageRef.putFile(imgUri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()) {
                                profileImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imgUrl = uri.toString();
                                        userRef.child("profileImageURL").setValue(imgUrl);
                                        Toast.makeText(Edit_Profile_Activity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(Edit_Profile_Activity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private String getCurrentUserEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getEmail();
        }
        return null;
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        startActivityForResult(gallery, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            imgUri = data.getData();
            profileimageView.setImageURI(imgUri);
        }
    }
}
