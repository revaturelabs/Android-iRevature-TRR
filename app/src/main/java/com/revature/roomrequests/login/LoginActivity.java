package com.revature.roomrequests.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.revature.roomrequests.MainActivity;
import com.revature.roomrequests.R;
import com.revature.roomrequests.locationselector.LocationSelectorActivity;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    CheckBox chkRemember;
    Button btnLogin;
    SharedPreferences preferences;
    final private String USERNAME_KEY = "username";
    final private String PASSWORD_KEY = "password";
    final private String REMEMBER_KEY = "remember";

    private TextWatcher textWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        String username = preferences.getString(USERNAME_KEY,null);
        String password = preferences.getString(PASSWORD_KEY,null);
        Boolean remember = preferences.getBoolean(REMEMBER_KEY,false);

        etUsername = findViewById(R.id.et_login_username);
        etPassword =  findViewById(R.id.et_login_password);
        chkRemember = findViewById(R.id.chk_login_remember);
        btnLogin = findViewById(R.id.btn_login_submit);

        if (username != null && password != null) {
            etUsername.setText(username);
            etPassword.setText(password);
            chkRemember.setChecked(remember);
//            authenticate();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate();
            }
        });

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // check Fields For Empty Values
                checkFieldsForEmptyValues();
            }
        };

        etUsername.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);

        checkFieldsForEmptyValues();
    }

    void checkFieldsForEmptyValues(){

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        if(username.equals("") || password.equals("")){
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundColor(getResources().getColor(R.color.revature_orange_faded));
        } else {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundColor(getResources().getColor(R.color.revature_orange));
        }
    }

    public void authenticate() {

        SharedPreferences.Editor editor = preferences.edit();

        if (chkRemember.isChecked()) {
            editor.putString(USERNAME_KEY, etUsername.getText().toString());
            editor.putString(PASSWORD_KEY, etPassword.getText().toString());
            editor.putBoolean(REMEMBER_KEY, true);
            editor.commit();
        } else {
            editor.remove(USERNAME_KEY);
            editor.remove(PASSWORD_KEY);
            editor.remove(REMEMBER_KEY);
            editor.commit();
        }

        String state = preferences.getString("location_state",null);
        String campus = preferences.getString("location_campus",null);
        String building = preferences.getString("location_building",null);

        if (state != null && campus != null && building != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), LocationSelectorActivity.class);
            intent.putExtra("calling_activity",LoginActivity.class.toString());
            startActivity(intent);
        }

    }
}
