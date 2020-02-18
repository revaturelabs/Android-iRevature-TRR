package com.revature.roomrequests.roomrequesttable;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;
import com.revature.roomrequests.MainActivity;
import com.revature.roomrequests.R;
import com.revature.roomrequests.api.ApiService;
import com.revature.roomrequests.pojo.Location;
import com.revature.roomrequests.pojo.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomRequestTableFragment extends Fragment implements RoomRequestTableAdapter.ItemsChangedListener {

    ArrayList<Integer> ids;
    ArrayList<String> batches;
    ArrayList<String> rooms;
    ArrayList<String> trainers;
    ArrayList<String> dates;
    ArrayList<String> capacities;
    ArrayList<Boolean> availabilities;
    Location location;

    TextView tvPick;
    RecyclerView recyclerView;

    private ApiService apiService;
    final private String LOG_TAG = "ROOM REQUEST TABLE";

    public RoomRequestTableFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_request_table, container, false);

        tvPick = view.findViewById(R.id.tv_room_request_choice);
        tvPick.setText(R.string.select_first_room);

        recyclerView = view.findViewById(R.id.recycle_room_requests_table);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        getRooms();

        RoomRequestTableAdapter adapter = new RoomRequestTableAdapter(getActivity().getApplicationContext(), getFragmentManager(), new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList());

        adapter.setItemsChangedListener(this);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void getRooms() {

        Response.Listener<JSONArray> getRoomsListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Room> rooms = new ArrayList<>();
                Room room;

                try {

                    for(int i = 0; i<response.length(); i++){

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        room = new Room();

                        String batch = jsonObject.getString("batch_name").equals("null") ? null : jsonObject.getString("batch_name");
                        String trainerName = jsonObject.getString("trainer_name").equals("null") ? null : jsonObject.getString("trainer_name");
                        String startDate = jsonObject.getString("start_date").equals("null") ? null : jsonObject.getString("start_date");
                        String endDate = jsonObject.getString("end_date").equals("null") ? null : jsonObject.getString("end_date");

                        room.setId(jsonObject.getInt("room_id"));
                        room.setBatch(batch);
                        room.setRoomNumber(jsonObject.getString("room_number"));
                        room.setTrainer(trainerName);
                        room.setDates(startDate == null ? null : startDate + "-" + endDate);
                        room.setCapacity(jsonObject.getString("capacity"));
                        room.setAvailable(jsonObject.getBoolean("available"));

                        rooms.add(room);
                    }

                } catch (JSONException e) {
                    Log.d(LOG_TAG,e.toString());
                }

                setRooms(rooms);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, error.toString());
                Snackbar.make(getActivity().findViewById(R.id.container_login) ,"Error logging in", Snackbar.LENGTH_SHORT);
            }
        };

        apiService.getRoomsForLocation(location, getRoomsListener, errorListener);
    }

    public void setRooms(ArrayList<Room> rooms) {

        ids = new ArrayList<>();
        batches = new ArrayList<>();
        this.rooms = new ArrayList<>();
        trainers = new ArrayList<>();
        dates = new ArrayList<>();
        capacities = new ArrayList<>();
        availabilities = new ArrayList<>();

        for(int i = 0; i<rooms.size() ; i++) {
            ids.add(rooms.get(i).getId());
            batches.add(rooms.get(i).getBatch());
            this.rooms.add(rooms.get(i).getRoomNumber());
            trainers.add(rooms.get(i).getTrainer());
            dates.add(rooms.get(i).getDates());
            capacities.add(rooms.get(i).getCapacity());
            availabilities.add(rooms.get(i).isAvailable());
        }

        RoomRequestTableAdapter adapter = (RoomRequestTableAdapter) recyclerView.getAdapter();
        adapter.updateData(ids,batches,this.rooms,trainers,dates,capacities,availabilities);
    }

    @Override
    public void onItemsChanged(int choice) {
        if(choice == 1) {
            tvPick.setText(R.string.select_first_room);
        } else {
            tvPick.setText(R.string.select_second_room);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String state = preferences.getString("location_state", null);
        String campus = preferences.getString("location_campus", null);
        String building = preferences.getString("location_building", null);

        location = new Location(state,campus,building);

        apiService = new ApiService(context);
    }
}
