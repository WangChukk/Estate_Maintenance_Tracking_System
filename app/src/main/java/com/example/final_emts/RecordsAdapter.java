package com.example.final_emts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.EventViewHolder> {
    private List<recordsData> eventList;
    private RecordsActivity recordsActivity;

    public RecordsAdapter(List<recordsData> eventList, RecordsActivity recordsActivity) {
        this.eventList = eventList;
        this.recordsActivity = recordsActivity;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.records_item_layout, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        recordsData eventData = eventList.get(position);

        holder.dateTextView.setText(eventData.getDate());
        holder.descriptionTextView.setText(eventData.getDescription());
        holder.titleTextView.setText(eventData.getTitle());
        holder.contactTextView.setText("17850487");
        holder.timeTextView.setText(eventData.getTime());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView descriptionTextView;
        public TextView titleTextView;
        public TextView contactTextView;
        public TextView timeTextView;

        public EventViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.date);
            descriptionTextView = itemView.findViewById(R.id.discription);
            titleTextView = itemView.findViewById(R.id.title);
            contactTextView = itemView.findViewById(R.id.contact);
            timeTextView = itemView.findViewById(R.id.time);
        }
    }
}
