package com.revature.roomrequests.locationselector;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class LocationViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<String> states;
    final private String STATE = "state";
    final private String CAMPUS = "campus";
    final private String BUILDING = "building";

    public LocationViewPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<String> states) {
        super(fm, behavior);
        this.states = states;
    }

    public LocationViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<String> states) {
        super(fm);
        this.states = states;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new LocationFragment(states, STATE);
            case 1:
                return  new LocationFragment(new ArrayList<String>(), CAMPUS);
            case 2:
                return new LocationFragment(new ArrayList<String>(), BUILDING);
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
