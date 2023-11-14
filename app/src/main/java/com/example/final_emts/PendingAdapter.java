package com.example.final_emts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class PendingAdapter extends RecyclerView.Adapter<PendingViewHolder> {
    private List<PendingData> items;
    private Context context;

    public PendingAdapter(List<PendingData> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public PendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_item_layout, parent, false);
        return new PendingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingViewHolder holder, int position) {
        PendingData item = items.get(position);

        holder.titleTextView.setText(item.getTitle());
        holder.descriptionTextView.setText(item.getDescription());

        holder.forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = items.indexOf(item);
                if (position >= 0) {
                    // Move the item to the MaintenanceRequestToPre node
                    DatabaseReference databaseReferencePre = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToPre");
                    DatabaseReference newItemRef = databaseReferencePre.push(); // Create a new unique key
                    newItemRef.setValue(item);

                    // Remove the item from the MaintenanceRequestToEM node
                    DatabaseReference databaseReferenceEM = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToEM");

                    // Check if item has a valid key
                    if (item.getKey() != null) {
                        DatabaseReference itemToRemoveRef = databaseReferenceEM.child(item.getKey());
                        itemToRemoveRef.removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Request removed, now you can refresh the page
                                            items.remove(position); // Use 'position' to remove the item
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

        holder.assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = items.indexOf(item);
                if (position >= 0) {
                    // Move the item to the MaintenanceRequestToPre node
                    DatabaseReference databaseReferencePre = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToMaintenanceTeam");
                    DatabaseReference newItemRef = databaseReferencePre.push(); // Create a new unique key
                    newItemRef.setValue(item);

                    // Remove the item from the MaintenanceRequestToEM node
                    DatabaseReference databaseReferenceEM = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToEM");

                    // Check if item has a valid key
                    if (item.getKey() != null) {
                        DatabaseReference itemToRemoveRef = databaseReferenceEM.child(item.getKey());
                        itemToRemoveRef.removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Request removed, now you can refresh the page
                                            items.remove(position); // Use 'position' to remove the item
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
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
