package com.revature.roomrequests.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Log;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Location;
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

import java.util.Arrays;

public class ApiService {

    // API urls
    final private String apiUrl;
    final private String loginUrlExtension =  "/login";
    final private String locationsUrlExtension = "/locations";
    final private String roomsUrlExtension = "/rooms";
    final private String batchesUrlExtension = "/trainer_batches";
    final private String roomRequestsUrlExtension = "/room_requests";
    final private String pendingRoomRequestsUrlExtension = "/pending_requests";
    final private String acceptedRoomRequestsUrlExtension = "/accepted_requests";
    final private String rejectedRoomRequestsUrlExtension = "/rejected_requests";
    final private String acceptRoomRequestUrlExtension = "/accept_requests";
    final private String rejectRoomRequestUrlExtension = "/reject_requests";

    private Context context;
    final private String LOG_TAG = "API SERVICE ERROR";
    private Integer auth_token;

    public ApiService(Context context) {
        this.context = context;
        apiUrl = context.getResources().getString(R.string.api_url);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        auth_token = preferences.getInt("auth_token", -1);

        if (auth_token == -1) {
            Log.d(LOG_TAG, "auth token not made available for api service");
        }
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
            Log.d(LOG_TAG, e.toString());
        }

        jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl + loginUrlExtension,
                jsonObject,
                responseListener,
                errorListener
        );

        requestQueue.add(jsonObjectRequest);

    }

    public void getLocations(Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {

        RequestQueue requestQueue;
        CustomJsonRequest customJsonRequest;

        requestQueue = Volley.newRequestQueue(context);

        customJsonRequest = new CustomJsonRequest(
                Request.Method.GET,
                apiUrl + locationsUrlExtension,
                getJsonObjectWithToken(),
                responseListener,
                errorListener
        );

        requestQueue.add(customJsonRequest);

    }

    public JSONObject getJsonObjectWithToken() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", auth_token);
        } catch (JSONException e) {
            Log.d(LOG_TAG, e.toString());
        }
        return jsonObject;
    }

    public void getRoomsForLocation(Location location, Response.Listener<JSONArray> responseListener, Response.ErrorListener errorListener) {

        RequestQueue requestQueue;
        CustomJsonRequest customJsonRequest;
        JSONObject jsonObject = getJsonObjectWithToken();


        requestQueue = Volley.newRequestQueue(context);

        try {
            jsonObject.put("state", location.getState());
            jsonObject.put("campus", location.getCampus());
            jsonObject.put("building", location.getBuilding());
        } catch (JSONException e) {
            Log.d(LOG_TAG, e.toString());
        }

        customJsonRequest = new CustomJsonRequest(
                Request.Method.GET,
                apiUrl + roomsUrlExtension,
                jsonObject,
                responseListener,
                errorListener
        );

        requestQueue.add(customJsonRequest);

    }

}
