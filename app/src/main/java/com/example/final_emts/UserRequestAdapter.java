package com.example.final_emts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserRequestAdapter extends RecyclerView.Adapter<UserRequestAdapter.RequestViewHolder> {
    private final List<RequestData> requestList;

    public UserRequestAdapter(List<RequestData> requestList) {
        this.requestList = requestList;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycle_item_layout, parent, false);
        return new RequestViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        RequestData requestData = requestList.get(position);
        holder.titleTextView.setText(requestData.getRequestTitle());
        holder.descriptionTextView.setText(requestData.getRequestDescription());
    }


    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;

        public RequestViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.userReqTitle);
            descriptionTextView = itemView.findViewById(R.id.userReqDescription);
        }
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
