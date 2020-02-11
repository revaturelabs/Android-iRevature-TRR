package com.revature.roomrequests.locationselector;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Location;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    ArrayList<String> locations;

    public LocationFragment() {
        // Required empty public constructor
    }

    public LocationFragment(ArrayList<String> locations) {
        this.locations = locations;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_location, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_locationselector_locations);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        LocationSelectorAdapter locationSelectorAdapter = new LocationSelectorAdapter(locations,view.getContext());

        recyclerView.setAdapter(locationSelectorAdapter);

        return view;
    }

}
