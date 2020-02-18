package com.revature.roomrequests.api;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.User;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginService {

    private String apiUrl;
    private Context context;
    private String urlExtension =  "/login";
    private String logTag = "LOGIN SERVICE ERROR";

    public LoginService(Context context) {
        this.context = context;
        apiUrl = context.getResources().getString(R.string.api_url);
    }

    public void authenticateUser(String username, String password, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {

        RequestQueue requestQueue;
        JsonObjectRequest jsonObjectRequest;
        JSONObject jsonObject = new JSONObject();

        requestQueue = Volley.newRequestQueue(context);

        try {
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            Log.d(logTag, e.toString());
        }

        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl + urlExtension,
                jsonObject,
                responseListener,
                errorListener
        );

        requestQueue.add(jsonObjectRequest);

    }

}
