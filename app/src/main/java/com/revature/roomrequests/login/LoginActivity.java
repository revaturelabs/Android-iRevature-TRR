package com.revature.roomrequests.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;
import com.revature.roomrequests.MainActivity;
import com.revature.roomrequests.R;
import com.revature.roomrequests.api.ApiService;
import com.revature.roomrequests.locationselector.LocationSelectorActivity;
import com.revature.roomrequests.pojo.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    CheckBox chkRemember;
    Button btnLogin;
    SharedPreferences preferences;

    final private String USERNAME_KEY = "username";
    final private String PASSWORD_KEY = "password";
    final private String REMEMBER_KEY = "remember";
    final private String LOG_TAG = "LOGIN ACTIVITY";

    private TextWatcher textWatcher;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService = new ApiService(this);

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
                checkFieldsForValidValues();
            }
        };

        etUsername.addTextChangedListener(textWatcher);
        etPassword.addTextChangedListener(textWatcher);

        checkFieldsForValidValues();
    }

    void checkFieldsForValidValues(){

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
        Matcher emailMatcher = emailPat.matcher(username);
        if(!emailMatcher.find() || password.length()<8){
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundColor(getResources().getColor(R.color.revature_orange_faded));
        } else {
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundColor(getResources().getColor(R.color.revature_orange));
        }
    }

    public void authenticate() {
        Response.Listener<JSONObject> loginListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                User user = new User();

                try {

                    user.setUsername(response.getString("username"));
                    user.setName(response.getString("name"));
                    user.setRole(response.getString("role"));
                    user.setId(response.getInt("user_id"));
                    user.setToken(response.getInt("token"));

                } catch (JSONException e) {
                    Log.d(LOG_TAG,e.toString());
                }

                loggedIn(user);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, error.toString());
                Snackbar.make(findViewById(R.id.container_login) ,"Error logging in", Snackbar.LENGTH_SHORT);
            }
        };

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        apiService.authenticateUser(username, password, loginListener, errorListener);
    }

    public void loggedIn(User user) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putInt("auth_token", user.getToken());
        editor.putInt("user_id",user.getId());
        editor.putString("name",user.getName());

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
            intent.putExtra("user", user);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), LocationSelectorActivity.class);
            intent.putExtra("calling_activity",LoginActivity.class.toString());
            intent.putExtra("user", user);
            startActivity(intent);
        }

    }
}
