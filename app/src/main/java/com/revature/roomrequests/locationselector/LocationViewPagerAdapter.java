package com.revature.roomrequests.locationselector;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.revature.roomrequests.pojo.Location;

import java.util.ArrayList;
import java.util.Arrays;

public class LocationViewPagerAdapter extends FragmentPagerAdapter {

    ArrayList<Location> locations;

    public LocationViewPagerAdapter(@NonNull FragmentManager fm, int behavior, ArrayList<Location> locations) {
        super(fm, behavior);
        this.locations = locations;
    }

    public LocationViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<Location> locations) {
        super(fm);
        this.locations = locations;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new LocationFragment(new ArrayList(Arrays.asList("Florida","Texas")));
            default:
                return  new LocationFragment(new ArrayList<String>());
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
