package com.example.final_emts;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MaintenanceTeamHomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private MaintenanceteamAdapter adapter;
    private List<MaintenanceTeamData> maintenanceTeamList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintenance_team_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView44);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        maintenanceTeamList = new ArrayList<>();
        adapter = new MaintenanceteamAdapter(maintenanceTeamList, getContext());

        recyclerView.setAdapter(adapter);

        // Retrieve data from Firebase and populate the maintenanceTeamList
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MaintenanceRequestToMaintenanceTeam");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                maintenanceTeamList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Assuming your Firebase structure matches the MaintenanceTeamData class
                    MaintenanceTeamData maintenanceTeamData = snapshot.getValue(MaintenanceTeamData.class);
                    if (maintenanceTeamData != null) {
                        maintenanceTeamList.add(maintenanceTeamData);
                    }
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
