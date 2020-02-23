package com.revature.roomrequests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import com.google.android.material.snackbar.Snackbar;
import com.revature.roomrequests.locationselector.LocationSelectorActivity;
import com.revature.roomrequests.login.LoginActivity;
import com.revature.roomrequests.pojo.Location;
import com.revature.roomrequests.pojo.User;


public class MainActivity extends AppCompatActivity {

    TextView tvLocation;
    TextView tvUsername;
    TextView tvUserRole;
    Location location;
    AppBarConfiguration appBarConfiguration;
    User user;
    MainActivity self;

    // for development purposes
    Button btnSiteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        self = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = (User) getIntent().getSerializableExtra("user");

        tvUsername = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.tv_nav_username);
        tvUserRole = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.tv_nav_userrole);
        tvLocation = findViewById(R.id.tv_main_location);
        tvLocation.setPaintFlags(tvLocation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        tvUsername.setText(user.getUsername());
        tvUserRole.setText(user.getRole().toUpperCase());

        tvLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(getApplicationContext(), LocationSelectorActivity.class);
                startActivity(intent);
                return false;
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final NavController navController = Navigation.findNavController(this, R.id.host_main_fragment_container);
        final NavigationView navigationView = findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();

        if (user.getRole().toLowerCase().equals("trainer")) {
            menu.add(R.string.room_request_title);
            menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    navController.navigate(R.id.nav_room_requests);
                    return false;
                }
            });
            menu.getItem(0).setIcon(R.drawable.rooms);
        } else if (user.getRole().toLowerCase().equals("site manager")) {
            menu.add(R.string.requests_title);
            menu.getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    navController.navigate(R.id.nav_request_list);
                    return false;
                }
            });
            menu.getItem(0).setIcon(R.drawable.requests);
            navController.getGraph().setStartDestination(R.id.nav_request_list);
            navController.popBackStack();
            navController.navigate(R.id.nav_request_list);
        }

        menu.add("Logout");
        menu.getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(self);
                builder.setTitle("Logout");
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                });
                builder.setNegativeButton(R.string.no, null);

                builder.show();
                return false;
            }
        });
        menu.getItem(1).setIcon(R.drawable.logout);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_room_requests, R.id.nav_request_list)
                .setDrawerLayout(drawer)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    public void logout() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();

        editor.remove(LoginActivity.AUTH_TOKEN_KEY);
        editor.remove(LoginActivity.USER_ID_KEY);
        editor.remove(LoginActivity.USER_NAME_KEY);
        editor.commit();

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
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

        String state = preferences.getString(LocationSelectorActivity.LOCATION_STATE_KEY, null);
        String campus = preferences.getString(LocationSelectorActivity.LOCATION_CAMPUS_KEY, null);
        String building = preferences.getString(LocationSelectorActivity.LOCATION_BUILDING_KEY, null);

        if (state != null && campus != null && building != null) {
            location = new Location(state, campus, building);
            tvLocation.setText(location.toString());
        } else {
            Intent intent = new Intent(this, LocationSelectorActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public Location getLocation() {
        return location;
    }
}
