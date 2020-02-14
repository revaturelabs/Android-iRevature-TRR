package com.revature.roomrequests.roomrequesttable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Room;
import com.revature.roomrequests.roomrequest.RoomRequestFragment;
import com.revature.roomrequests.roomswap.RoomSwapFragment;

import java.util.ArrayList;

public class RoomRequestTableAdapter extends RecyclerView.Adapter<RoomRequestTableAdapter.RequestRoomViewHolder> {

    ArrayList<String> batches;
    ArrayList<String> rooms;
    ArrayList<String> trainers;
    ArrayList<String> dates;

    Context context;
    FragmentManager fm;
    Room room1=null;
    Room room2=null;
    int room1Pos=RecyclerView.NO_POSITION;
    int room2Pos = RecyclerView.NO_POSITION;

    public RoomRequestTableAdapter(){
        super();
    }

    public RoomRequestTableAdapter(Context context, FragmentManager fm, ArrayList<String> batches, ArrayList<String> rooms, ArrayList<String> trainers, ArrayList<String> dates) {
        this.context = context;
        this.fm = fm;
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
        holder.itemView.setSelected(room1Pos==position);
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
                Log.d("Room check", "Position: "+position + " room1Pos: "+room1Pos);
                if(batches.get(position)==null){
                    Room room = new Room();
                    room.setRoom(rooms.get(position));
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.host_main_fragment_container,new RoomRequestFragment(room));
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();
                } else if(room1==null) {
                    Log.d("Room","We got here");
                    room1 = new Room(batches.get(position),rooms.get(position),trainers.get(position),dates.get(position));
                    notifyItemChanged(room1Pos);
                    room1Pos = position;
                    notifyItemChanged(room1Pos);
                } else if(position==room1Pos) {
                    room1Pos = RecyclerView.NO_POSITION;
                    room1=null;
                    v.setSelected(false);
                    Log.d("Reset Room1","Room 1 is now null and room1Pos is: "+room1Pos);
                } else {
                    room2 = new Room(batches.get(position),rooms.get(position),trainers.get(position),dates.get(position));
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.host_main_fragment_container,new RoomSwapFragment(room1,room2));
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();
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
