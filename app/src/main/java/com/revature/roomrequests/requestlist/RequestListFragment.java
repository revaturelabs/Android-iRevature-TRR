package com.revature.roomrequests.requestlist;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.revature.roomrequests.R;
import com.revature.roomrequests.api.ApiService;
import com.revature.roomrequests.locationselector.LocationSelectorActivity;
import com.revature.roomrequests.pojo.Location;
import com.revature.roomrequests.pojo.Request;
import com.revature.roomrequests.pojo.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestListFragment extends Fragment {


    RecyclerView recyclerView;
    TextView tvNumberOfRequests, tvLocation;

    ArrayList<Request> requests;
    Context context;
    Location location;

    private ApiService apiService;
    final String LOG_TAG = "REQUEST LIST";

    public RequestListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_list, container, false);

        tvLocation = view.findViewById(R.id.tv_main_location);
        tvLocation.setPaintFlags(tvLocation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getActivity().getApplicationContext(), LocationSelectorActivity.class);
                startActivity(intent);
                return false;
            }
        });

        tvLocation.setText(location.toString());

        context = view.getContext();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String state = preferences.getString("location_state", null);
        String campus = preferences.getString("location_campus", null);
        String building = preferences.getString("location_building", null);

        location = new Location(state,campus,building);

        getAllRequests();

        recyclerView = view.findViewById(R.id.recycle_request_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        RequestListAdapter adapter = new RequestListAdapter(context, getFragmentManager(),new ArrayList<Request>());
        recyclerView.setAdapter(adapter);

        tvNumberOfRequests = view.findViewById(R.id.tv_request_results);

        return view;
    }

    private void getAllRequests(){

        Response.Listener<JSONArray> getRequestsListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                requests= new ArrayList<>();
                Request request;
                Room room1;
                Room room2;

                try {

                    for(int i = 0; i<response.length(); i++){

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        request = new Request();
                        room1 = new Room();
                        room2 = new Room();


                        String startDate = jsonObject.getString("start_date");
                        String endDate = jsonObject.getString("end_date");

                        request.setId(jsonObject.getInt("request_id"));
                        request.setDates(startDate + " - " + endDate);
                        request.setReasonForRequest(jsonObject.getString("reason_request"));
                        request.setStatus(jsonObject.getString("status"));
                        request.setDateMade(jsonObject.getString("date_made"));

                        room1.setId(jsonObject.getInt("room_id"));
                        room1.setBatch(jsonObject.getString("batch_name"));
                        room1.setRoomNumber(jsonObject.getString("room_number"));
                        room1.setTrainer(jsonObject.getString("trainer_name"));
                        room1.setCapacity(jsonObject.getString("room_capacity"));
                        request.setRoom1(room1);

                        Integer roomId = jsonObject.getString("second_room_id").equals("null") ? -1 : Integer.parseInt(jsonObject.getString("second_room_id"));
                        if (roomId == -1) {

                            request.setRoom2(null);

                        } else {

                            String batch = jsonObject.getString("second_batch_name").equals("null") ? null : jsonObject.getString("second_batch_name");
                            String trainerName = jsonObject.getString("second_trainer_name").equals("null") ? null : jsonObject.getString("second_trainer_name");
                            String capacity = jsonObject.getString("second_room_capacity").equals("null") ? null : jsonObject.getString("second_room_capacity");

                            room2.setId(roomId);
                            room2.setBatch(batch);
                            room2.setRoomNumber(jsonObject.getString("second_room_number"));
                            room2.setTrainer(trainerName);
                            room2.setCapacity(capacity);

                            request.setRoom2(room2);
                        }

                        requests.add(request);
                    }

                } catch (JSONException e) {
                    Log.d(LOG_TAG,e.toString());
                }

                setRequests(requests);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, error.toString());
            }
        };

        apiService.getRequestsForLocation(location, getRequestsListener, errorListener);

    }

    public void setRequests(ArrayList<Request> requests) {

        tvNumberOfRequests.setText("Showing " + requests.size() + " results");

        RequestListAdapter adapter = (RequestListAdapter) recyclerView.getAdapter();
        adapter.updateData(requests);
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String state = preferences.getString("location_state", null);
        String campus = preferences.getString("location_campus", null);
        String building = preferences.getString("location_building", null);

        if (state != null && campus != null && building != null) {
            location = new Location(state, campus, building);
            tvLocation.setText(location.toString());
        } else {
            Intent intent = new Intent(getActivity(), LocationSelectorActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.context = context;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        String state = preferences.getString("location_state", null);
        String campus = preferences.getString("location_campus", null);
        String building = preferences.getString("location_building", null);

        if (state != null && campus != null && building != null) {
            location = new Location(state, campus, building);
        } else {
            Intent intent = new Intent(getActivity(), LocationSelectorActivity.class);
            startActivity(intent);
        }

        apiService = new ApiService(context);
    }
}
