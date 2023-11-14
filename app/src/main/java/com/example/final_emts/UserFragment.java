package com.example.final_emts;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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

public class UserFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserRequestAdapter adapter;
    private List<RequestData> requestList;
    private String currentUserEmail;

    private List<RequestData> ssoRequestList = new ArrayList<>();
    private List<RequestData> emRequestList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        recyclerView = view.findViewById(R.id.recyclerView123);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        requestList = new ArrayList<>();
        adapter = new UserRequestAdapter(requestList);
        recyclerView.setAdapter(adapter);

        // Get the current user's email
        currentUserEmail = getCurrentUserEmail();

        if (currentUserEmail != null) {
            // Retrieve data from Firebase Realtime Database
            DatabaseReference databaseReferenceSSO = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToSSO");
            DatabaseReference databaseReferenceEM = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToEM");

            databaseReferenceSSO.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ssoRequestList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String userEmail = snapshot.child("userEmail").getValue(String.class);
                        Log.d("FirebaseData", "UserEmail from Firebase: " + userEmail);

                        if (userEmail != null && TextUtils.equals(userEmail, currentUserEmail)) {
                            String requestId = snapshot.getKey(); // Getting the unique key of the node
                            String requestTitle = snapshot.child("title").getValue(String.class);
                            String requestDescription = snapshot.child("description").getValue(String.class);

                            Log.d("FirebaseData", "RequestId: " + requestId);
                            Log.d("FirebaseData", "RequestTitle: " + requestTitle);
                            Log.d("FirebaseData", "RequestDescription: " + requestDescription);

                            if (requestId != null && requestTitle != null && requestDescription != null) {
                                RequestData requestData = new RequestData(requestId, requestTitle, userEmail, requestDescription);
                                ssoRequestList.add(requestData);
                            }
                        }
                    }
                    updateCombinedList();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors here
                }
            });
            databaseReferenceEM.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ssoRequestList.clear();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String userEmail = snapshot.child("userEmail").getValue(String.class);
                        Log.d("FirebaseData", "UserEmail from Firebase: " + userEmail);

                        if (userEmail != null && TextUtils.equals(userEmail, currentUserEmail)) {
                            String requestId = snapshot.getKey(); // Getting the unique key of the node
                            String requestTitle = snapshot.child("title").getValue(String.class);
                            String requestDescription = snapshot.child("description").getValue(String.class);

                            Log.d("FirebaseData", "RequestId: " + requestId);
                            Log.d("FirebaseData", "RequestTitle: " + requestTitle);
                            Log.d("FirebaseData", "RequestDescription: " + requestDescription);

                            if (requestId != null && requestTitle != null && requestDescription != null) {
                                RequestData requestData = new RequestData(requestId, requestTitle, userEmail, requestDescription);
                                ssoRequestList.add(requestData);
                            }
                        }
                    }
                    updateCombinedList();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors here
                }
            });
        }

        return view;
    }

    private void updateCombinedList() {
        requestList.clear();
        requestList.addAll(ssoRequestList);
        requestList.addAll(emRequestList);
        adapter.notifyDataSetChanged();
    }

    private String getCurrentUserEmail() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return currentUser.getEmail();
        }
        return null;
    }
}
