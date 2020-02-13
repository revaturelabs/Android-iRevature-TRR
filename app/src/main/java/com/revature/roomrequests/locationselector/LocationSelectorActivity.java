package com.revature.roomrequests.locationselector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.revature.roomrequests.MainActivity;
import com.revature.roomrequests.R;
import com.revature.roomrequests.login.LoginActivity;
import com.revature.roomrequests.pojo.Location;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LocationSelectorActivity extends AppCompatActivity implements LocationFragment.LocationCollector {

    final private int LOCATION_SELECTOR_RESULT_CODE = 1;
    final private String STATE = "state";
    final private String CAMPUS = "campus";
    final private String BUILDING = "building";
    ArrayList<Location> locations;
    ViewPager viewPager;
    LocationViewPagerAdapter viewPagerAdapter;
    String selectedState;
    String selectedCampus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);

        locations = getLocations();

        viewPager = findViewById(R.id.vp_locationselector_list);

        viewPagerAdapter = new LocationViewPagerAdapter(getSupportFragmentManager(), getUniqueStates(locations));

        viewPager.setAdapter(viewPagerAdapter);

    }

    public ArrayList<Location> getLocations() {

        ArrayList<Location> locations = new ArrayList<Location>();

        Location location = new Location();
        location.setState("Florida");
        location.setCampus("USF");
        location.setBuilding("NEC");
        locations.add(location);

        Location location2 = new Location();
        location2.setState("Florida");
        location2.setCampus("USF");
        location2.setBuilding("BSM");
        locations.add(location2);

        Location location3 = new Location();
        location3.setState("Texas");
        location3.setCampus("Dallas");
        location3.setBuilding("BigBuiding");
        locations.add(location3);

        Location location4 = new Location();
        location4.setState("Virginia");
        location4.setCampus("Reston");
        location4.setBuilding("Hive");
        locations.add(location4);

        Location location5 = new Location();
        location5.setState("Virginia");
        location5.setCampus("Reston");
        location5.setBuilding("Other building");
        locations.add(location5);

        Location location6 = new Location();
        location6.setState("West Virginia");
        location6.setCampus("Morgantown");
        location6.setBuilding("Main");
        locations.add(location6);

        Location location7 = new Location();
        location7.setState("New York");
        location7.setCampus("New York");
        location7.setBuilding("Big Apple Building");
        locations.add(location7);

        return locations;
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

        ArrayList<Location> locations = getLocations();

        String tag1 = "android:switcher:" + R.id.vp_locationselector_list + ":" + 1;
        LocationFragment campusFragment = (LocationFragment) getSupportFragmentManager().findFragmentByTag(tag1);

        String tag2 = "android:switcher:" + R.id.vp_locationselector_list + ":" + 2;
        LocationFragment buildingFragment = (LocationFragment) getSupportFragmentManager().findFragmentByTag(tag2);

        switch (tag) {
            case STATE:
                Toast.makeText(getApplicationContext(),"clicked on a state",Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(),"clicked on a campus",Toast.LENGTH_SHORT).show();
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

}
