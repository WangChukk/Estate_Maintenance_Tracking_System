package com.example.final_emts;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecordsViewHolder extends RecyclerView.ViewHolder {
    public TextView dateTextView;
    public TextView descriptionTextView;
    public TextView locationTextView;
    public TextView contactTextView;
    public TextView timeTextView;

    public RecordsViewHolder(@NonNull View itemView) {
        super(itemView);
        dateTextView = itemView.findViewById(R.id.date);
        descriptionTextView = itemView.findViewById(R.id.discription);
        locationTextView = itemView.findViewById(R.id.title);
        contactTextView = itemView.findViewById(R.id.contact);
        timeTextView = itemView.findViewById(R.id.time);
    }
}
