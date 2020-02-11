package com.revature.roomrequests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.revature.roomrequests.locationselector.LocationSelectorActivity;

public class MainActivity extends AppCompatActivity {

    final private int LOCATION_SELECTOR_RESULT_CODE = 1;
    TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
