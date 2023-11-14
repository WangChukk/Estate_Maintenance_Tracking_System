package com.example.final_emts;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SSOViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;
    public TextView descriptionTextView;
    public Button ssoFowardbtn;
    public Button ssoAssignbtn;

    public SSOViewHolder(@NonNull View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.valiTitle);
        descriptionTextView = itemView.findViewById(R.id.valiDescription);
        ssoFowardbtn = itemView.findViewById(R.id.ssoFowardbtn);
        ssoAssignbtn = itemView.findViewById(R.id.ssoAssignbtn);
    }

    public String setTitle(String title) {
        return title;
    }

    public String setDescription(String description) {
        return description;
    }
}
