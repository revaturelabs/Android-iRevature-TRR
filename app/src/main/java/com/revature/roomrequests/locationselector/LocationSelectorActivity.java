package com.revature.roomrequests.locationselector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Location;

import java.util.ArrayList;

public class LocationSelectorActivity extends AppCompatActivity {

    ArrayList<Location> locations;
    ViewPager viewPager;
    LocationViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selector);

        locations = getLocations();

        viewPager = findViewById(R.id.vp_locationselector_list);

        viewPagerAdapter = new LocationViewPagerAdapter(getSupportFragmentManager(),locations);

        viewPager.setAdapter(viewPagerAdapter);

    }

    public ArrayList<Location> getLocations() {
        return null;
    }
}
