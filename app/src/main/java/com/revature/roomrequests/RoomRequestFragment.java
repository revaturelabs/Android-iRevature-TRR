package com.revature.roomrequests;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.roomrequests.R;
import com.revature.roomrequests.pojo.Room;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomRequestFragment extends Fragment {

    TextView tvBatch,tvRoom,tvTrainer,tvDates;
    EditText etComments, etStartDate, etEndDate;
    Button btnSubmit;


    public RoomRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_request, container, false);

        return view;
    }

    interface SendRoom {
        void sendRoomForRequest(Room room);
        void sendRoomsForSwap(Room room1, Room room2);
    }

}
