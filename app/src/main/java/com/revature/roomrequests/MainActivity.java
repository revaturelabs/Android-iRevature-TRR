package com.revature.roomrequests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.revature.roomrequests.locationselector.LocationSelectorActivity;
import com.revature.roomrequests.pojo.Room;
import com.revature.roomrequests.roomrequesttable.RoomRequestTableFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements RoomRequestFragment.SendRoom {

    final private int LOCATION_SELECTOR_RESULT_CODE = 1;
    TextView tvLocation,tvPickRoom;
    FrameLayout mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPickRoom = findViewById(R.id.tv_main_pickRoom);

        tvLocation = findViewById(R.id.tv_main_location);

        tvLocation.setText("Location Selector");

        tvLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(getApplicationContext(), LocationSelectorActivity.class);

                startActivityForResult(intent, LOCATION_SELECTOR_RESULT_CODE);

                return false;
            }
        });

        tvPickRoom.setText(R.string.select_first_room);

        mainFragment = findViewById(R.id.frame_main_fragment_container);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_main_fragment_container,new RoomRequestTableFragment(new ArrayList<>(Arrays.asList("2001-Mobile-iOS","2001-Mobile-And",null)),
                new ArrayList<>(Arrays.asList("200","300","400")),
                new ArrayList<>(Arrays.asList("Uday","Mayur",null)),
                new ArrayList<>(Arrays.asList("2/21-3/21","2/21-3/21",null))));
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

    }

    @Override
    public void sendRoomForRequest(Room room) {
    }

    @Override
    public void sendRoomsForSwap(Room room1, Room room2) {

    }
}
