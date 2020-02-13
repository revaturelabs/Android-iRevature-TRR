package com.revature.roomrequests.locationselector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.revature.roomrequests.R;

import java.util.ArrayList;

public class LocationSelectorAdapter extends RecyclerView.Adapter<LocationSelectorAdapter.LocationViewHolder> {

    ArrayList<String> locations;
    Context context;
    String tag;
    LocationFragment.LocationCollector locationCollector;

    public LocationSelectorAdapter(ArrayList<String> locations, Context context, String tag, LocationFragment.LocationCollector locationCollector) {
        this.locations = locations;
        this.context = context;
        this.tag = tag;
        this.locationCollector = locationCollector;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_location_selector, parent, false);
        LocationViewHolder locationViewHolder = new LocationViewHolder(v);
        return locationViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final LocationViewHolder holder, int position) {
        holder.location.setText(locations.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            locationCollector.locationUpdate(tag, holder.location.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {

        TextView location;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.tv_locationselector_locationrow);
        }
    }

    public void updateLocations(ArrayList<String> locations) {
        this.locations = locations;
    }
}
