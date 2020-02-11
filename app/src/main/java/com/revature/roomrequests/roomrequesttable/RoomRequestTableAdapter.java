package com.revature.roomrequests.roomrequesttable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.revature.roomrequests.R;

public class RoomRequestTableAdapter extends RecyclerView.Adapter<RoomRequestTableAdapter.RequestRoomViewHolder> {

    @NonNull
    @Override
    public RequestRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_room_requests,parent,false);
        //set the view's size, margins, paddings, and layout paramters
        RequestRoomViewHolder vh = new RequestRoomViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestRoomViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RequestRoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvBatch,tvRoom,tvTrainer,tvDates;

        public RequestRoomViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBatch = itemView.findViewById(R.id.tv_row_batch);
            tvRoom = itemView.findViewById(R.id.tv_row_room);
            tvTrainer = itemView.findViewById(R.id.tv_row_trainer);
            tvDates = itemView.findViewById(R.id.tv_row_dates);
        }
    }
}
