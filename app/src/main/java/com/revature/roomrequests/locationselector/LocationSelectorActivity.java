package com.revature.roomrequests.locationselector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;
import com.revature.roomrequests.MainActivity;
import com.revature.roomrequests.R;
import com.revature.roomrequests.api.ApiService;
import com.revature.roomrequests.login.LoginActivity;
import com.revature.roomrequests.pojo.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationSelectorActivity extends AppCompatActivity implements LocationFragment.LocationCollector {

    final private String STATE = "state";
    final private String CAMPUS = "campus";
    final private String BUILDING = "building";
    final private String LOG_TAG =  "LOCATION SELECTOR";

    ArrayList<Location> locations;
    ViewPager viewPager;
    LocationViewPagerAdapter viewPagerAdapter;
    String selectedState;
    String selectedCampus;

    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);

        viewPager = findViewById(R.id.vp_locationselector_list);

        apiService = new ApiService(this);

        getLocations();
    }

    public void getLocations() {

        Response.Listener<JSONArray> getLocationsListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                locations = new ArrayList<>();
                Location location;

                try {

                    for(int i = 0; i<response.length(); i++){

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        location = new Location();

                        location.setState(jsonObject.getString("state"));
                        location.setCampus(jsonObject.getString("campus"));
                        location.setBuilding(jsonObject.getString("building"));

                        locations.add(location);
                    }

                } catch (JSONException e) {
                    Log.d(LOG_TAG,e.toString());
                }

                updateViewWithLocations(locations);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, error.toString());
                Snackbar.make(findViewById(R.id.container_login) ,"Error logging in", Snackbar.LENGTH_SHORT);
            }
        };

        apiService.getLocations(getLocationsListener, errorListener);
    }

    public ArrayList<String> getUniqueStates(ArrayList<Location> locations) {

        ArrayList<String> states = new ArrayList<String>();

        for (Location location : locations) {

            String state = location.getState();

            if (!states.contains(state)) {
                states.add(state);
            }
        }

        return states;
    }

    public ArrayList<String> getUniqueCampusesByState(ArrayList<Location> locations, String state) {

        ArrayList<String> campuses = new ArrayList<String>();

        for (Location location : locations) {

            if (state.equals(location.getState())) {

                String campus = location.getCampus();

                if (!campuses.contains(campus))
                campuses.add(campus);
            }
        }

        return campuses;
    }

    public ArrayList<String> getBuildingsByStateAndCampus(ArrayList<Location> locations, String state, String campus) {

        ArrayList<String> buildings = new ArrayList<String>();

        for (Location location : locations) {

            if (state.equals(location.getState())) {
                if (campus.contains(location.getCampus())) {

                    String building = location.getBuilding();

                    if (!buildings.contains(building))
                        buildings.add(building);
                }
            }
        }

        return buildings;
    }

    @Override
    public void locationUpdate(String tag, String update) {

        String tag1 = "android:switcher:" + R.id.vp_locationselector_list + ":" + 1;
        LocationFragment campusFragment = (LocationFragment) getSupportFragmentManager().findFragmentByTag(tag1);

        String tag2 = "android:switcher:" + R.id.vp_locationselector_list + ":" + 2;
        LocationFragment buildingFragment = (LocationFragment) getSupportFragmentManager().findFragmentByTag(tag2);

        switch (tag) {

            case STATE:
                selectedState = update;
                campusFragment.updateLocations(getUniqueCampusesByState(locations, selectedState));
                try {
                    buildingFragment.clearLocations();
                } catch (NullPointerException e) {
                    // this try/catch is only to reset the locations of the building fragment on state change
                    // if the fragment has not been made yet, then we don't need to reset it
                }
                viewPager.setCurrentItem(1);
                break;
            case CAMPUS:
                selectedCampus = update;
                buildingFragment.updateLocations(getBuildingsByStateAndCampus(locations, selectedState, selectedCampus));
                viewPager.setCurrentItem(2);
                break;
            case BUILDING:

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("location_state", selectedState);
                editor.putString("location_campus", selectedCampus);
                editor.putString("location_building", update);
                editor.commit();

                String className = getIntent().getStringExtra("calling_activity");
                String loginActivityName = LoginActivity.class.toString();

                if (loginActivityName.equals(className)) {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    finish();
                }

                break;
        }
    }

    public void updateViewWithLocations(ArrayList<Location> locations) {

        viewPagerAdapter = new LocationViewPagerAdapter(getSupportFragmentManager(), getUniqueStates(locations));

        viewPager.setAdapter(viewPagerAdapter);
    }
}
