package com.example.final_emts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SSOHomePageFragment extends Fragment {
    private RecyclerView recyclerView;
    private SSOHomeAdapter adapter;
    private List<SSOData> requestList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_s_s_o_home_page, container, false);

        recyclerView = view.findViewById(R.id.recyclerView22);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestList = new ArrayList<>();
        adapter = new SSOHomeAdapter(requestList, getContext()); // Pass the context

        recyclerView.setAdapter(adapter);

        // Retrieve data from Firebase Realtime Database
        DatabaseReference databaseReferenceSSO = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToSSO");
        databaseReferenceSSO.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SSOData ssoData = snapshot.getValue(SSOData.class);
                    requestList.add(ssoData);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });

        return view;
    }
}
