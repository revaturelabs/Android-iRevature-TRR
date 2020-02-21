package com.revature.roomrequests.roomrequest;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;
import com.revature.roomrequests.R;
import com.revature.roomrequests.api.ApiService;
import com.revature.roomrequests.pojo.Room;
import com.revature.roomrequests.roomrequesttable.RoomRequestTableFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomRequestFragment extends Fragment implements View.OnClickListener {

    ScrollView scrollView;
    TextView tvBatch,tvRoom,tvTrainer,tvDates,tvSeats,etStartDate, etEndDate;
    EditText etComments;
    Button btnSubmit;
//    ImageButton btnPickStart, btnPickEnd;
    Spinner spinnerBatch;
    ArrayAdapter<String> spinnerBatchAdapter;
    Room room;
    DatePickerDialog.OnDateSetListener startDateListener,endDateListener;
    private SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
    private String startDate,endDate;

    RoomRequestFragment self;

    TextWatcher textWatcher;
    
    private String noString = "N/A";

    private ApiService apiService;
    final String LOG_TAG = "ROOM REQUEST";


    public RoomRequestFragment() {
        // Required empty public constructor
        this.self = this;
    }

    public RoomRequestFragment(Room room) {
        this.room = room;
        this.self = this;
    }

    public RoomRequestFragment(Room room,String startDate, String endDate) {
        this.room = room;
        this.startDate = startDate;
        this.endDate = endDate;
        this.self = this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_request, container, false);

        apiService = new ApiService(getContext());

        scrollView = view.findViewById(R.id.scroll_room_request_layout);

        tvBatch = view.findViewById(R.id.tv_room_request_batch);
        tvRoom = view.findViewById(R.id.tv_room_request_room);
        tvTrainer = view.findViewById(R.id.tv_room_request_trainer);
        tvDates = view.findViewById(R.id.tv_room_request_dates);
        tvSeats = view.findViewById(R.id.tv_room_request_size);
        tvDates.append(" "+noString);
        if(room!=null) {
            tvRoom.append(" "+room.getRoomNumber());
            tvSeats.append(" "+room.getCapacity());
            tvTrainer.append(" "+room.getTrainer());
        } else {
            tvRoom.append(" "+noString);
        }

        ArrayList<String> batches = getBatches();
        spinnerBatch = view.findViewById(R.id.spinner_room_request_batches);
        spinnerBatchAdapter = new ArrayAdapter<>(
                getContext(),R.layout.support_simple_spinner_dropdown_item,batches);
        spinnerBatchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBatch.setAdapter(spinnerBatchAdapter);

        etComments = view.findViewById(R.id.et_room_request_comments);
        etComments.setFilters(new InputFilter[] {new InputFilter.LengthFilter(getResources().getInteger(R.integer.comments_maximum))});
        etComments.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                InputMethodManager imm =  (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        btnSubmit = view.findViewById(R.id.btn_room_request_submit);
        btnSubmit.setOnClickListener(this);

        etStartDate = view.findViewById(R.id.et_room_request_start_date);
        etStartDate.setOnClickListener(this);
        if(startDate != null) {
            etStartDate.setText(" "+startDate);
        }

//        btnPickStart = view.findViewById(R.id.btn_room_request_start_date);
//        btnPickStart.setOnClickListener(this);

        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date = month+"/"+dayOfMonth+"/"+year;
                etStartDate.setText(date);
            }
        };

        etEndDate = view.findViewById(R.id.et_room_request_end_date);
        etEndDate.setOnClickListener(this);
        if(endDate != null) {
            etEndDate.setText(" "+endDate);
        }

//        btnPickEnd = view.findViewById(R.id.btn_room_request_end_date);
//        btnPickEnd.setOnClickListener(this);

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = month+"/"+dayOfMonth+"/"+year;
                etEndDate.setText(date);
            }
        };

        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                scrollView.scrollTo(0,btnSubmit.getBottom());
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

        etComments.addTextChangedListener(textWatcher);
        etStartDate.addTextChangedListener(textWatcher);
        etEndDate.addTextChangedListener(textWatcher);

        checkFieldsForValidValues();

        return view;
    }

    void checkFieldsForValidValues() {
        if(etComments.getText().toString().equals("") || etStartDate.getText().toString().equals("") || etEndDate.getText().toString().equals("")){
            btnSubmit.setEnabled(false);
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.revature_orange_faded));
        } else {
            btnSubmit.setEnabled(true);
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.revature_orange));
        }
    }

    @Override
    public void onClick(View v) {
//        if(v.getId()== R.id.et_room_request_start_date || v.getId()==R.id.btn_room_request_start_date){
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//
//                DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
//                        R.style.CalendarDialog,
//                        startDateListener,
//                        year,month,day);
//                if(!etEndDate.getText().toString().equals("")) {
//                    Date endDate = null;
//                    try {
//                        endDate = f.parse(etEndDate.getText().toString());
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                        endDate = new Date(Long.MAX_VALUE);
//                    }
//                    dialog.getDatePicker().setMaxDate(endDate.getTime());
//                }
//                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
//                dialog.show();
//        } else if ( v.getId()==R.id.et_room_request_end_date || v.getId()==R.id.btn_room_request_end_date) {
//            Calendar cal = Calendar.getInstance();
//            int year = cal.get(Calendar.YEAR);
//            int month = cal.get(Calendar.MONTH);
//            int day = cal.get(Calendar.DAY_OF_MONTH);
//
//            DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
//                    R.style.CalendarDialog,
//                    endDateListener,
//                    year,month,day);
//            if(!etStartDate.getText().toString().equals("")){
//                String startDateString = etStartDate.getText().toString();
//                Date startDate = null;
//                try {
//                    startDate = f.parse(startDateString);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                    startDate = new Date(System.currentTimeMillis());
//                }
//                dialog.getDatePicker().setMinDate(startDate.getTime());
//            } else {
//                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
//            }
//            dialog.show();
//        } else
        if (v.getId()==R.id.btn_room_request_submit) {
            submitRequest(v);

//            FragmentManager fm = getFragmentManager();
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.replace(R.id.host_main_fragment_container,new RoomRequestTableFragment());
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            ft.addToBackStack(null);
//            ft.commit();
            self.getActivity().getSupportFragmentManager().popBackStack();

            AlertDialog.Builder submitAlert = new AlertDialog.Builder(getActivity(),R.style.CalendarDialog);
            submitAlert.setPositiveButton(R.string.okay,null);
            submitAlert.setTitle(R.string.request_submitted);
            submitAlert.setMessage(R.string.request_submit_message);
            submitAlert.show();

        }
    }

    void submitRequest(final View v) {
        Response.Listener<JSONObject> submitListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Snackbar.make(v,response.getString("message"), Snackbar.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Log.d(LOG_TAG,e.toString());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, error.toString());
                Snackbar.make(v,"Error submitting request", Snackbar.LENGTH_SHORT).show();
            }
        };
        room.setBatch(spinnerBatch.getSelectedItem().toString());
        apiService.postSubmitRoomRequest(room,null,etStartDate.getText().toString(),etEndDate.getText().toString(),etComments.getText().toString(),submitListener,errorListener);
    }

    ArrayList<String> getBatches() {
        final ArrayList<String> batches = new ArrayList<>();
        Response.Listener<JSONArray> batchesListener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i =0; i<response.length(); i++) {
                    try {
                        batches.add(response.getJSONObject(i).getString("batch_name"));
                    } catch (JSONException e) {
                        Log.d(LOG_TAG,e.toString());
                    }
                }
                spinnerBatchAdapter.notifyDataSetChanged();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, error.toString());
            }
        };

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int trainerId = preferences.getInt("user_id",-1);
        apiService.getTrainerBatches(trainerId,batchesListener,errorListener);
        return batches;
    }

}
