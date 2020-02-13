package com.revature.roomrequests.roomrequesttable;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.revature.roomrequests.MainActivity;
import com.revature.roomrequests.R;
import com.revature.roomrequests.RoomRequestFragment;
import com.revature.roomrequests.pojo.Room;

import java.util.ArrayList;
import java.util.Arrays;

public class RoomRequestTableAdapter extends RecyclerView.Adapter<RoomRequestTableAdapter.RequestRoomViewHolder> {

    ArrayList<String> batches;
    ArrayList<String> rooms;
    ArrayList<String> trainers;
    ArrayList<String> dates;

    Context context;
    AppCompatActivity activity;

    public RoomRequestTableAdapter(){
        super();
    }

    public RoomRequestTableAdapter(Context context,AppCompatActivity activity, ArrayList<String> batches, ArrayList<String> rooms, ArrayList<String> trainers, ArrayList<String> dates) {
        this.context = context;
        this.activity = activity;
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
        if(batches.get(position)!=null) {
            holder.tvBatch.setText(batches.get(position));
        } else {
            holder.tvBatch.setText(R.string.no_string);
        }
        holder.tvRoom.setText(rooms.get(position));
        if(batches.get(position)!=null) {
            holder.tvTrainer.setText(trainers.get(position));
        } else {
            holder.tvTrainer.setText(R.string.no_string);
        }
        if(batches.get(position)!=null) {
            holder.tvDates.setText(dates.get(position));
        } else {
            holder.tvDates.setText(R.string.no_string);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"You chose room: " + rooms.get(position),Toast.LENGTH_SHORT).show();
                if(batches.get(position)==null){
                    Room room = new Room();
                    room.setRoom(rooms.get(position));
                    ((MainActivity)activity).sendRoomForRequest(room);
                }
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

            tvBatch = itemView.findViewById(R.id.tv_room_row_batch);
            tvRoom = itemView.findViewById(R.id.tv_room_row_room);
            tvTrainer = itemView.findViewById(R.id.tv_room_row_trainer);
            tvDates = itemView.findViewById(R.id.tv_room_row_dates);
        }
    }
}
