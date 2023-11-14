package com.example.final_emts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class preValidateActivity extends AppCompatActivity {

    private validateAdapter adapter;
    private List<dataListValidate> requestList;
    private ImageView imgbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_validate);

        RecyclerView recyclerView = findViewById(R.id.recyclerView99);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        imgbtn = findViewById(R.id.backBtn);
        requestList = new ArrayList<>();
        adapter = new validateAdapter(this, requestList);

        recyclerView.setAdapter(adapter);

        // Get the current user's email
        String currentUserEmail = getCurrentUserEmail();

        if (currentUserEmail != null) {
            // Retrieve data from Firebase Realtime Database
            DatabaseReference databaseReferenceEM = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToPre");
            databaseReferenceEM.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    requestList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PendingData pendingData = snapshot.getValue(PendingData.class);
                        dataListValidate validateData = new dataListValidate(pendingData.getTitle(), false, false);
                        requestList.add(validateData);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors here
                }
            });
        }
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Method to get the current user's email
    private String getCurrentUserEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getEmail();
        }
        return null;
    }
}
