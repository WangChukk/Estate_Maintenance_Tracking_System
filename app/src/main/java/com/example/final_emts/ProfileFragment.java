package com.example.final_emts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {
    private ImageView image; // Change to ImageView
    private TextView userName, department, stdID, stdEmail, userContact, userGender;
    private Button editProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize your views
        image = view.findViewById(R.id.imgProfile);
        userName = view.findViewById(R.id.userName);
        department = view.findViewById(R.id.department);
        stdID = view.findViewById(R.id.stdID);
        stdEmail = view.findViewById(R.id.stdEmail);
        userContact = view.findViewById(R.id.userContact);
        userGender = view.findViewById(R.id.UserGender);
        editProfile = view.findViewById(R.id.editProfile);

        // You will need to fetch the user's profile data from Firebase Realtime Database.
        // Assuming you have a reference to your Firebase database and user's email:
        String userEmail = getCurrentUserEmail(); // Get the user's email
        String encodedEmail = userEmail.replace(".", ",");

        // Get a reference to the Firebase database node for the user's profile
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("profileData").child(encodedEmail);

        // Add a ValueEventListener to retrieve and update the user's profile data
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);

                    if (userProfile != null) {
                        // Set the ImageView using Picasso for the user's profile image
                        if (userProfile.getProfileImageURL() != null && !userProfile.getProfileImageURL().isEmpty()) {
                            Picasso.get().load(userProfile.getProfileImageURL()).into(image);
                        }
                        userName.setText(userProfile.getName());
                        department.setText(userProfile.getDepartment());
                        stdID.setText(userProfile.getStdID());
                        stdEmail.setText(userProfile.getStdEmail());
                        userContact.setText(userProfile.getUserContact());
                        userGender.setText(userProfile.getUserGender());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error, e.g., display an error message
            }
        });

        // Set an OnClickListener for the Edit Profile button
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event, for example, navigate to the edit profile screen
                // using an Intent
                Intent intent = new Intent(getActivity(), Edit_Profile_Activity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // Implement a function to get the current user's email
    private String getCurrentUserEmail() {
        // Fetch the user's email using Firebase Authentication
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getEmail();
        }
        return null;
    }
}
