package com.revature.roomrequests.roomrequesttable;


import android.content.Context;
import android.content.SharedPreferences;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomRequestTableFragment extends Fragment implements View.OnClickListener {
    
    EditText etStartDate,etEndDate;
    ImageButton btnStartDate, btnEndDate;

    DatePickerDialog.OnDateSetListener startDateListener,endDateListener;
    private SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");

    ArrayList<Integer> ids;
    ArrayList<String> batches;
    ArrayList<String> rooms;
    ArrayList<String> trainers;
    ArrayList<String> dates;
    ArrayList<String> capacities;
    ArrayList<Boolean> availabilities;
    Location location;

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
        
        etStartDate = view.findViewById(R.id.et_room_table_start_date);
        etStartDate.setOnClickListener(this);
        etEndDate = view.findViewById(R.id.et_room_table_end_date);
        etEndDate.setOnClickListener(this);
        btnStartDate = view.findViewById(R.id.btn_room_table_start_date);
        btnStartDate.setOnClickListener(this);
        btnEndDate = view.findViewById(R.id.btn_room_table_end_date);
        btnEndDate.setOnClickListener(this);

        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date = month+"/"+dayOfMonth+"/"+year;
                etStartDate.setText(date);
            }
        };

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = month+"/"+dayOfMonth+"/"+year;
                etEndDate.setText(date);
            }
        };

        recyclerView = view.findViewById(R.id.recycle_room_requests_table);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        getRooms();

        RoomRequestTableAdapter adapter = new RoomRequestTableAdapter(getActivity().getApplicationContext(), getFragmentManager(), new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList(),new ArrayList());

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
                ArrayList<Room> temp = new ArrayList<>();
                Room room;

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                int userId = preferences.getInt("user_id",-1);


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

                        Integer trainerId = jsonObject.getString("trainer_id").equals("null") ? -1 : Integer.parseInt(jsonObject.getString("trainer_id"));

                        if(trainerId==userId) {
                            rooms.add(room);
                        } else {
                            temp.add(room);
                        }
                    }

                } catch (JSONException e) {
                    Log.d(LOG_TAG,e.toString());
                }

                rooms.addAll(temp);
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
    public void onClick(View v) {
        if(v.getId()== R.id.et_room_table_start_date || v.getId()==R.id.btn_room_table_start_date){
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                    android.R.style.Theme_Material_Dialog_MinWidth,
                    startDateListener,
                    year,month,day);
            if(!etEndDate.getText().toString().equals("")) {
                Date endDate = null;
                try {
                    endDate = f.parse(etEndDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                    endDate = new Date(Long.MAX_VALUE);
                }
                dialog.getDatePicker().setMaxDate(endDate.getTime());
            }
            dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dialog.show();
        } else if ( v.getId()==R.id.et_room_table_end_date || v.getId()==R.id.btn_room_table_end_date) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                    android.R.style.Theme_Material_Dialog_MinWidth,
                    endDateListener,
                    year,month,day);
            if(!etStartDate.getText().toString().equals("")){
                String startDateString = etStartDate.getText().toString();
                Date startDate = null;
                try {
                    startDate = f.parse(startDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    startDate = new Date(System.currentTimeMillis());
                }
                dialog.getDatePicker().setMinDate(startDate.getTime());
            } else {
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            }
            dialog.show();
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
