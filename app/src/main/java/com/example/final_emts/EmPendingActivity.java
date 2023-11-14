package com.example.final_emts;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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

public class EmPendingActivity extends AppCompatActivity {

    private PendingAdapter adapter;
    private List<PendingData> requestList;
    private RecyclerView  recyclerView;
    private DatabaseReference databaseReferenceEM;
    private ImageView imgbth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_em_pending);
        imgbth = findViewById(R.id.backBtn);
        recyclerView = findViewById(R.id.recyclerView33);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestList = new ArrayList<>();
        adapter = new PendingAdapter(requestList, this);

        recyclerView.setAdapter(adapter);

        // Get the current user's email
        String currentUserEmail = getCurrentUserEmail();

        if (currentUserEmail != null) {
            // Retrieve data from Firebase Realtime Database
            databaseReferenceEM = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToEM");

            // Use addValueEventListener to listen for changes in the data
            databaseReferenceEM.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    requestList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // You can access data inside each child node here
                        String title = snapshot.child("title").getValue(String.class);
                        String description = snapshot.child("description").getValue(String.class);

                        // Assuming you have a constructor for PendingData
                        PendingData pendingData = new PendingData();
                        pendingData.setTitle(title);
                        pendingData.setDescription(description);

                        if (pendingData != null) {
                            // Set the key based on the snapshot key
                            pendingData.setKey(snapshot.getKey());
                            requestList.add(pendingData);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors here
                    Toast.makeText(EmPendingActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        imgbth.setOnClickListener(new View.OnClickListener() {
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
