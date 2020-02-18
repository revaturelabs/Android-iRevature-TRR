package com.revature.roomrequests.roomrequesttable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Room;
import com.revature.roomrequests.roomrequest.RoomRequestFragment;
import com.revature.roomrequests.roomswap.RoomSwapFragment;

import java.util.ArrayList;

public class RoomRequestTableAdapter extends RecyclerView.Adapter<RoomRequestTableAdapter.RequestRoomViewHolder> {

    final String NO_STRING = "N/A";

    Room myRoom;

    ArrayList<String> batches;
    ArrayList<String> rooms;
    ArrayList<String> trainers;
    ArrayList<String> dates;
    ArrayList<String> seats;
    ArrayList<Boolean> availabilities;

    Context context;
    FragmentManager fm;
    Room room1=null;
    Room room2=null;
    int room1Pos=RecyclerView.NO_POSITION;
    int room2Pos = RecyclerView.NO_POSITION;

    ItemsChangedListener itemsChangedListener;

    public RoomRequestTableAdapter(){
        super();
    }

    public RoomRequestTableAdapter(Context context, FragmentManager fm, ArrayList<String> batches, ArrayList<String> rooms, ArrayList<String> trainers, ArrayList<String> dates, ArrayList<String> seats, ArrayList<Boolean> availabilities) {
        this.context = context;
        this.fm = fm;
        this.batches = batches;
        this.rooms = rooms;
        this.trainers = trainers;
        this.dates = dates;
        this.seats = seats;
        this.availabilities = availabilities;
        myRoom = new Room("My Batch","My room","Me","myDates","myseats",false);
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
            holder.tvBatch.append(" " + batches.get(position));
        } else {
            holder.tvBatch.append(" " + NO_STRING);
        }
        holder.tvRoom.append(" " + rooms.get(position));
        if(batches.get(position)!=null) {
            holder.tvTrainer.append(" " + trainers.get(position));
        } else {
            holder.tvTrainer.append(" " + NO_STRING);
        }
        if(batches.get(position)!=null) {
            holder.tvDates.append(" " + dates.get(position));
        } else {
            holder.tvDates.append(" " + NO_STRING);
        }
        if(seats.get(position)!=null) {
            holder.tvSeats.append(" " + seats.get(position));
        } else {
            holder.tvSeats.append(" " + NO_STRING);
        }
        if(availabilities.get(position)){
            holder.cvRoom.setCardBackgroundColor(context.getResources().getColor(R.color.room_available));
            holder.btnAction.setText("Request");
        } else {
            holder.cvRoom.setCardBackgroundColor(context.getResources().getColor(R.color.room_unavailable));
            holder.btnAction.setText("Swap");
        }
        holder.btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Room check", "Position: "+position + " room1Pos: "+room1Pos);
                if(batches.get(position)==null){
                    Room room = new Room();
                    room.setRoom(rooms.get(position));
                    room.setSeats(seats.get(position));
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.host_main_fragment_container,new RoomRequestFragment(room));
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();
                } else {
                    Room room = new Room(batches.get(position),rooms.get(position),trainers.get(position),dates.get(position),seats.get(position),availabilities.get(position));
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.host_main_fragment_container,new RoomSwapFragment(room,myRoom));
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                    //else if(room1==null) {
//                    Log.d("Room","We got here");
//                    room1 = new Room(batches.get(position),rooms.get(position),trainers.get(position),dates.get(position),seats.get(position),availabilities.get(position));
//                    notifyItemChanged(room1Pos);
//                    room1Pos = position;
//                    notifyItemChanged(room1Pos);
//                    itemsChangedListener.onItemsChanged(2);
//                } else if(position==room1Pos) {
//                    room1Pos = RecyclerView.NO_POSITION;
//                    room1=null;
//                    v.setSelected(false);
//                    itemsChangedListener.onItemsChanged(1);
//                    Log.d("Reset Room1","Room 1 is now null and room1Pos is: "+room1Pos);
//                } else {
//                    room2 = new Room(batches.get(position),rooms.get(position),trainers.get(position),dates.get(position),seats.get(position),availabilities.get(position));
//                    FragmentTransaction ft = fm.beginTransaction();
//                    ft.replace(R.id.host_main_fragment_container,new RoomSwapFragment(room1,room2));
//                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    ft.addToBackStack(null);
//                    ft.commit();
//                }
            }
        });
    }

    public interface ItemsChangedListener {
        void onItemsChanged(int choice);

    }

    public void setItemsChangedListener(ItemsChangedListener listener) {
        this.itemsChangedListener = listener;

    }


    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class RequestRoomViewHolder extends RecyclerView.ViewHolder {
        TextView tvBatch,tvRoom,tvTrainer,tvDates,tvSeats;
        CardView cvRoom;
        Button btnAction;

        public RequestRoomViewHolder(@NonNull View itemView) {
            super(itemView);

            tvBatch = itemView.findViewById(R.id.tv_room_row_batch);
            tvRoom = itemView.findViewById(R.id.tv_room_row_room);
            tvTrainer = itemView.findViewById(R.id.tv_room_row_trainer);
            tvDates = itemView.findViewById(R.id.tv_room_row_dates);
            tvSeats = itemView.findViewById(R.id.tv_room_row_seats);
            cvRoom = itemView.findViewById(R.id.cv_room);
            btnAction = itemView.findViewById(R.id.btn_room_row_action);
        }
    }
}
