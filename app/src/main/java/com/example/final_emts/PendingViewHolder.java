package com.example.final_emts;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PendingViewHolder extends RecyclerView.ViewHolder {
    public ImageView iconImageView;
    public TextView titleTextView;
    public TextView descriptionTextView;
    public Button forwardButton;
    public Button assignButton;

    public PendingViewHolder(@NonNull View itemView) {
        super(itemView);

        iconImageView = itemView.findViewById(R.id.icon22);
        titleTextView = itemView.findViewById(R.id.ReqTitle);
        descriptionTextView = itemView.findViewById(R.id.ReqDescription);
        forwardButton = itemView.findViewById(R.id.forwardbtn);
        assignButton = itemView.findViewById(R.id.assignbtn);
    }
}
