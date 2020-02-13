package com.revature.roomrequests.locationselector;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
    LocationSelectorAdapter locationSelectorAdapter;
    String tag;
    LocationCollector locationCollector;

    public LocationFragment() {
        // Required empty public constructor
    }

    public LocationFragment(ArrayList<String> locations, String tag) {
        this.locations = locations;
        this.tag = tag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_location, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rv_locationselector_locations);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        locationSelectorAdapter = new LocationSelectorAdapter(locations, view.getContext(), tag, locationCollector);

        recyclerView.setAdapter(locationSelectorAdapter);

        return view;
    }

    public void updateLocations(ArrayList<String> locations) {
        this.locations = locations;
        locationSelectorAdapter.updateLocations(locations);
        locationSelectorAdapter.notifyDataSetChanged();
    }

    public void clearLocations() {
        this.locations = new ArrayList<String>();
    }

    interface LocationCollector{
        void locationUpdate(String tag, String update);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        locationCollector = (LocationCollector) getActivity();
    }


}
