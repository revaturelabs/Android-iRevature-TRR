package com.revature.roomrequests;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.revature.roomrequests.pojo.Room;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomRequestFragment extends Fragment {

    TextView tvBatch,tvRoom,tvTrainer,tvDates;
    EditText etComments, etStartDate, etEndDate;
    Button btnSubmit;
    Room room;


    public RoomRequestFragment() {
        // Required empty public constructor
    }

    public RoomRequestFragment(Room room) {
        this.room = room;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_request, container, false);
        tvBatch = view.findViewById(R.id.tv_room_request_batch);
        tvRoom = view.findViewById(R.id.tv_room_request_room);
        tvTrainer = view.findViewById(R.id.tv_room_request_trainer);
        tvDates = view.findViewById(R.id.tv_room_request_dates);
        tvBatch.setText(R.string.no_string);
        tvBatch.setText(R.string.no_string);
        tvBatch.setText(R.string.no_string);
        if(room!=null) {
            tvRoom.setText(room.getRoom());
        } else {
            tvRoom.setText(R.string.no_string);
        }
        return view;
    }

    interface SendRoom {
        void sendRoomForRequest(Room room);
        void sendRoomsForSwap(Room room1, Room room2);
    }

}
