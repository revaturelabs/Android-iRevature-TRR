package com.revature.roomrequests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import com.revature.roomrequests.locationselector.LocationSelectorActivity;
import com.revature.roomrequests.pojo.Location;
import com.revature.roomrequests.pojo.Room;
import com.revature.roomrequests.roomrequest.RoomRequestFragment;
import com.revature.roomrequests.roomrequesttable.RoomRequestTableFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    TextView tvLocation;
    TextView tvUsername;
    TextView tvUserRole;
    NavigationView navigationView;
    Location location;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        tvUsername = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.tv_nav_username);
        tvUserRole = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.tv_nav_userrole);
        tvLocation = findViewById(R.id.tv_main_location);

        tvUsername.setText("This will display the user's username");
        tvUserRole.setText("This will display the user's role");

        tvLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(getApplicationContext(), LocationSelectorActivity.class);

                startActivity(intent);

                return false;
            }
        });

//        mainFragment = findViewById(R.id.host_main_fragment_container);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        ft.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.roomRequestTableFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.host_main_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.host_main_fragment_container);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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
