package com.example.final_emts;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MaintenanceteamAdapter extends RecyclerView.Adapter<MaintenanceteamAdapter.MaintenanceTeamViewHolder> {
    private final List<MaintenanceTeamData> maintenanceTeamList;
    private Context context;

    public MaintenanceteamAdapter(List<MaintenanceTeamData> maintenanceTeamList, Context context) {
        this.maintenanceTeamList = maintenanceTeamList;
        this.context = context;
    }

    @NonNull
    @Override
    public MaintenanceTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.maintenanceteam_item_layout, parent, false);
        return new MaintenanceTeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaintenanceTeamViewHolder holder, int position) {
        MaintenanceTeamData maintenanceTeamData = maintenanceTeamList.get(position);

        holder.titleTextView.setText(maintenanceTeamData.getTitle());
        holder.descriptionTextView.setText(maintenanceTeamData.getDescription());

        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the current date and time when the button is clicked
                Pair<String, String> dateTimePair = getCurrentDateTime();
                String currentTime = dateTimePair.first;
                String currentDate = dateTimePair.second;

                maintenanceTeamData.setTime(currentTime);
                maintenanceTeamData.setDate(currentDate);

                int position = maintenanceTeamList.indexOf(maintenanceTeamData);
                if (position >= 0) {
                    // Move the item to the "Records" node
                    DatabaseReference databaseReferenceRecords = FirebaseDatabase.getInstance().getReference().child("Records");
                    String newRecordKey = databaseReferenceRecords.push().getKey();
                    maintenanceTeamData.setKey(newRecordKey);
                    databaseReferenceRecords.child(newRecordKey).setValue(maintenanceTeamData);

                    // Remove the item from the "MaintenanceRequestToMaintenanceTeam" node
                    DatabaseReference databaseReferenceEM = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToMaintenanceTeam");
                    databaseReferenceEM.child(maintenanceTeamData.getKey()).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        maintenanceTeamList.remove(position);
                                        notifyDataSetChanged();
                                    } else {
                                        // Handle the case where the request couldn't be removed
                                        Toast.makeText(context, "Failed to remove the request.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return maintenanceTeamList.size();
    }

    public static class MaintenanceTeamViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public Button doneButton;

        public MaintenanceTeamViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.mtTitle);
            descriptionTextView = itemView.findViewById(R.id.mtDescription);
            doneButton = itemView.findViewById(R.id.mtdoneBtn);
        }
    }

    private Pair<String, String> getCurrentDateTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:m a", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/d/yyyy", Locale.getDefault());

        String time = timeFormat.format(new Date());
        String date = dateFormat.format(new Date());

        return new Pair<>(time, date);
    }
}
