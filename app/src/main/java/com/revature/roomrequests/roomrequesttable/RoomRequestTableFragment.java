package com.revature.roomrequests.roomrequesttable;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.revature.roomrequests.MainActivity;
import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Room;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomRequestTableFragment extends Fragment implements RoomRequestTableAdapter.ItemsChangedListener {

    ArrayList<String> batches;
    ArrayList<String> rooms;
    ArrayList<String> trainers;
    ArrayList<String> dates;

    TextView tvPick;


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

        tvPick = view.findViewById(R.id.tv_room_request_choice);
        tvPick.setText(R.string.select_first_room);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_room_requests_table);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        getRooms();

        RoomRequestTableAdapter adapter = new RoomRequestTableAdapter(getActivity().getApplicationContext(), getFragmentManager(),batches,rooms,trainers,dates);

        adapter.setItemsChangedListener(this);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void getRooms() {

        ArrayList<Room> rooms = new ArrayList<>(Arrays.asList(
                new Room("2001-Mobile-iOS","200","Uday","2/21-3/21"),
                new Room("2001-Mobile-And","300","Mayur","2/21-3/21"),
                new Room(null,"400",null,null)));

        batches = new ArrayList<>();
        this.rooms = new ArrayList<>();
        trainers = new ArrayList<>();
        dates = new ArrayList<>();

        for(int i = 0; i<rooms.size() ; i++) {
            batches.add(rooms.get(i).getBatch());
            this.rooms.add(rooms.get(i).getRoom());
            trainers.add(rooms.get(i).getTrainer());
            dates.add(rooms.get(i).getDates());
        }
    }

    @Override
    public void onItemsChanged(int choice) {
        if(choice == 1) {
            tvPick.setText(R.string.select_first_room);
        } else {
            tvPick.setText(R.string.select_second_room);
        }
    }
}
