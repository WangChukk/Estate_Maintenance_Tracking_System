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

public class SSOHomeAdapter extends RecyclerView.Adapter<SSOHomeAdapter.SSOViewHolder> {
    private final List<SSOData> requestList;
    private Context context;

    public SSOHomeAdapter(List<SSOData> requestList, Context context) {
        this.requestList = requestList;
        this.context = context;
    }

    @NonNull
    @Override
    public SSOViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sso_home_item_layout, parent, false);
        return new SSOViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SSOViewHolder holder, int position) {
        SSOData requestData = requestList.get(position);

        holder.titleTextView.setText(requestData.getTitle());
        holder.descriptionTextView.setText(requestData.getDescription());

        holder.ssoAssignbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = requestList.indexOf(requestData);
                if (position >= 0) {
                    // Move the item to the MaintenanceRequestToPre node
                    DatabaseReference databaseReferencePre = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToMaintenanceTeam");
                    DatabaseReference newItemRef = databaseReferencePre.push(); // Create a new unique key
                    newItemRef.setValue(requestData);

                    // Remove the item from the MaintenanceRequestToEM node
                    DatabaseReference databaseReferenceEM = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToSSO");

                    // Check if item has a valid key
                    if (requestData.getKey() != null) {
                        DatabaseReference itemToRemoveRef = databaseReferenceEM.child(requestData.getKey());
                        itemToRemoveRef.removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Request removed, now you can refresh the page
                                            requestList.remove(position); // Use 'position' to remove the item
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
        holder.ssoFowardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = requestList.indexOf(requestData);
                if (position >= 0) {
                    // Move the item to the MaintenanceRequestToPre node
                    DatabaseReference databaseReferencePre = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToEM");
                    DatabaseReference newItemRef = databaseReferencePre.push(); // Create a new unique key
                    newItemRef.setValue(requestData);

                    // Remove the item from the MaintenanceRequestToEM node
                    DatabaseReference databaseReferenceEM = FirebaseDatabase.getInstance().getReference().child("MaintenanceRequestToSSO");

                    // Check if item has a valid key
                    if (requestData.getKey() != null) {
                        DatabaseReference itemToRemoveRef = databaseReferenceEM.child(requestData.getKey());
                        itemToRemoveRef.removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Request removed, now you can refresh the page
                                            requestList.remove(position); // Use 'position' to remove the item
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
        return requestList.size();
    }

    public static class SSOViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView descriptionTextView;
        public Button ssoAssignbtn;
        public Button ssoFowardbtn;

        public SSOViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.valiTitle);
            descriptionTextView = itemView.findViewById(R.id.valiDescription);
            ssoFowardbtn = itemView.findViewById(R.id.ssoFowardbtn);
            ssoAssignbtn = itemView.findViewById(R.id.ssoAssignbtn);
        }

    }
}
