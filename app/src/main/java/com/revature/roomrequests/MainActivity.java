package com.revature.roomrequests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.revature.roomrequests.locationselector.LocationSelectorActivity;
import com.revature.roomrequests.pojo.Location;
import com.revature.roomrequests.pojo.Room;
import com.revature.roomrequests.roomrequest.RoomRequestFragment;
import com.revature.roomrequests.roomrequesttable.RoomRequestTableFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    final private int LOCATION_SELECTOR_RESULT_CODE = 1;
    TextView tvLocation;
    FrameLayout mainFragment;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLocation = findViewById(R.id.tv_main_location);

        tvLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(getApplicationContext(), LocationSelectorActivity.class);

                startActivity(intent);

                return false;
            }
        });

        mainFragment = findViewById(R.id.frame_main_fragment_container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_main_fragment_container,new RoomRequestTableFragment(new ArrayList<>(Arrays.asList("2001-Mobile-iOS","2001-Mobile-And",null,null)),
                new ArrayList<>(Arrays.asList("200","300","400","500")),
                new ArrayList<>(Arrays.asList("Uday","Mayur",null,null)),
                new ArrayList<>(Arrays.asList("2/21-3/21","2/21-3/21",null,null))));
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String state = preferences.getString("location_state", null);
        String campus = preferences.getString("location_campus", null);
        String building = preferences.getString("location_building", null);

        if (state != null && campus != null && building != null) {
            location = new Location(state, campus, building);

            tvLocation.setText(location.toString());
        } else {
            Intent intent = new Intent(this, LocationSelectorActivity.class);
            startActivity(intent);
        }
        Toast.makeText(this, "on start", Toast.LENGTH_SHORT).show();
    }

}
