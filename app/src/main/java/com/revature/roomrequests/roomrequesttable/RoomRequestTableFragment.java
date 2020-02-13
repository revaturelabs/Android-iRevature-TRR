package com.revature.roomrequests.roomrequesttable;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revature.roomrequests.MainActivity;
import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Room;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomRequestTableFragment extends Fragment {

    ArrayList<String> batches;
    ArrayList<String> rooms;
    ArrayList<String> trainers;
    ArrayList<String> dates;


    public RoomRequestTableFragment() {
        // Required empty public constructor
    }

    public RoomRequestTableFragment(ArrayList<String> batches, ArrayList<String> rooms, ArrayList<String> trainers, ArrayList<String> dates) {
        this.batches = batches;
        this.rooms = rooms;
        this.trainers = trainers;
        this.dates = dates;
    }

    public RoomRequestTableFragment(ArrayList<Room> rooms) {
        for(int i = 0; i<rooms.size() ; i++) {
            batches.add(rooms.get(i).getBatch());
            this.rooms.add(rooms.get(i).getRoom());
            trainers.add(rooms.get(i).getTrainer());
            dates.add(rooms.get(i).getDates());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_request_table, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_room_requests_table);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        RoomRequestTableAdapter adapter = new RoomRequestTableAdapter(getActivity().getApplicationContext(),(MainActivity)getActivity(),batches,rooms,trainers,dates);

        recyclerView.setAdapter(adapter);

        return view;
    }

}
