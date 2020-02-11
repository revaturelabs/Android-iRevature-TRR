package com.revature.roomrequests.roomrequesttable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.revature.roomrequests.R;

import java.util.ArrayList;

public class RoomRequestTableAdapter extends RecyclerView.Adapter<RoomRequestTableAdapter.RequestRoomViewHolder> {

    ArrayList<String> batches;
    ArrayList<String> rooms;
    ArrayList<String> trainers;
    ArrayList<String> dates;

    Context context;

    public RoomRequestTableAdapter(){
        super();
    }

    public RoomRequestTableAdapter(Context context, ArrayList<String> batches, ArrayList<String> rooms, ArrayList<String> trainers, ArrayList<String> dates) {
        this.context = context;
        this.batches = batches;
        this.rooms = rooms;
        this.trainers = trainers;
        this.dates = dates;
    }

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
    public void onBindViewHolder(@NonNull RequestRoomViewHolder holder, final int position) {

        holder.tvBatch.setText(batches.get(position));
        holder.tvRoom.setText(rooms.get(position));
        holder.tvTrainer.setText(trainers.get(position));
        holder.tvDates.setText(dates.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"You chose room: " + rooms.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
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
