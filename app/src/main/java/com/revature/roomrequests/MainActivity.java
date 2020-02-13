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
import android.widget.Toast;

import com.revature.roomrequests.locationselector.LocationSelectorActivity;
import com.revature.roomrequests.pojo.Location;
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


        Location location = (Location) getIntent().getSerializableExtra("Location");

        tvPickRoom = findViewById(R.id.tv_main_pickRoom);

        tvLocation = findViewById(R.id.tv_main_location);

        tvLocation.setText(location.toString());

        tvLocation.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Intent intent = new Intent(getApplicationContext(), LocationSelectorActivity.class);

                startActivity(intent);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(getApplicationContext(), "got result", Toast.LENGTH_SHORT).show();

        if(requestCode == LOCATION_SELECTOR_RESULT_CODE) {
            Location location = (Location) data.getSerializableExtra("Location");
            tvLocation.setText(location.toString());
        }
    }
}
