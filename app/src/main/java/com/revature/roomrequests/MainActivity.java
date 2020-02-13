package com.revature.roomrequests;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.revature.roomrequests.locationselector.LocationSelectorActivity;
import com.revature.roomrequests.pojo.Location;

public class MainActivity extends AppCompatActivity {

    final private int LOCATION_SELECTOR_RESULT_CODE = 1;
    TextView tvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Location location = (Location) getIntent().getSerializableExtra("Location");

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
