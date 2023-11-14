package com.example.final_emts;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ValidateViewHolder extends RecyclerView.ViewHolder {
    public ImageView icon;
    public TextView title;
    public TextView description;
    public Button acceptButton;
    public Button rejectButton;

    public ValidateViewHolder(@NonNull View itemView) {
        super(itemView);
        icon = itemView.findViewById(R.id.icon22);
        title = itemView.findViewById(R.id.valiTitle);
        description = itemView.findViewById(R.id.valiDescription);
        acceptButton = itemView.findViewById(R.id.acceptdbtn);
        rejectButton = itemView.findViewById(R.id.rejectbtn);
    }
}
