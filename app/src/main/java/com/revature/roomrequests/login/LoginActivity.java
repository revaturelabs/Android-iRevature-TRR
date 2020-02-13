package com.revature.roomrequests.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.revature.roomrequests.MainActivity;
import com.revature.roomrequests.R;
import com.revature.roomrequests.locationselector.LocationSelectorActivity;

public class LoginActivity extends AppCompatActivity {

    TextView tvForgot;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvForgot = findViewById(R.id.tv_login_forgot);

        btnLogin = findViewById(R.id.btn_login_submit);

        tvForgot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ForgotPasswordFragment dialog = new ForgotPasswordFragment();
                dialog.show(getSupportFragmentManager(),"Forgot Password");
                return false;
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LocationSelectorActivity.class);
                startActivity(intent);
            }
        });
    }
}
