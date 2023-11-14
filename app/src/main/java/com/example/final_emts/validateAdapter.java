package com.example.final_emts;

import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class validateAdapter extends RecyclerView.Adapter<validateAdapter.ViewHolder> {
    private final List<dataListValidate> dataList;
    private final Context context;

    public validateAdapter(Context context, List<dataListValidate> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.validate_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        dataListValidate data = dataList.get(position);

        holder.locationTextView.setText(data.getTitle());
        holder.descriptionTextView.setText(data.getDescription());
        holder.rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = dataList.indexOf(data);
                if (position >= 0) {
                    DatabaseReference databaseReferencePre = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToPre");

                    // Check if item has a valid key
                    if (data.getKey() != null) {
                        DatabaseReference itemToRemoveRef = databaseReferencePre.child(data.getKey());
                        itemToRemoveRef.removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Request removed, now you can refresh the page
                                            dataList.remove(position); // Use 'position' to remove the item
                                            notifyDataSetChanged(); // Notify the adapter that data has changed
                                        } else {
                                            // Handle the case where the request couldn't be removed
                                            Toast.makeText(context, "Failed to remove the request.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
        holder.acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle accept button click here
                int position = dataList.indexOf(data);
                if (position >= 0) {
                    // Move the item to the MaintenanceRequestToMaintenanceTeam node
                    DatabaseReference databaseReferenceMaintenanceTeam = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToMaintenanceTeam").push();
                    databaseReferenceMaintenanceTeam.setValue(data);

                    // Remove the item from the MaintenanceRequestToPre node
                    DatabaseReference databaseReferencePre = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToPre");

                    // Check if the item is not null and has a valid key
                    if (data.getKey() != null) {
                        databaseReferencePre.child(data.getKey()).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Request removed, now you can refresh the page
                                            dataList.remove(data);
                                            notifyDataSetChanged();
                                        } else {
                                            // Handle the case where the request couldn't be removed
                                            Toast.makeText(context, "Failed to remove the request.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView locationTextView;
        public TextView descriptionTextView;
        public Button rejectButton;
        public Button acceptButton;

        public ViewHolder(View itemView) {
            super(itemView);
            locationTextView = itemView.findViewById(R.id.valiTitle);
            descriptionTextView = itemView.findViewById(R.id.valiDescription);
            rejectButton = itemView.findViewById(R.id.rejectbtn);
            acceptButton = itemView.findViewById(R.id.acceptdbtn);
        }
    }
}
