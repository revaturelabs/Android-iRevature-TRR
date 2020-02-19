package com.revature.roomrequests.roomrequest;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;
import com.revature.roomrequests.R;
import com.revature.roomrequests.api.ApiService;
import com.revature.roomrequests.pojo.Room;
import com.revature.roomrequests.roomrequesttable.RoomRequestTableFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomRequestFragment extends Fragment implements View.OnClickListener {

    TextView tvBatch,tvRoom,tvTrainer,tvDates,tvSeats;
    EditText etComments, etStartDate, etEndDate;
    Button btnSubmit;
    ImageButton btnPickStart, btnPickEnd;
    Room room;
    DatePickerDialog.OnDateSetListener startDateListener,endDateListener;
    private SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");

    TextWatcher textWatcher;
    
    private String noString = "N/A";

    private ApiService apiService;
    final String LOG_TAG = "ROOM REQUEST";


    public RoomRequestFragment() {
        // Required empty public constructor
    }

    public RoomRequestFragment(Room room) {
        this.room = room;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_request, container, false);

        apiService = new ApiService(getContext());

        tvBatch = view.findViewById(R.id.tv_room_request_batch);
        tvRoom = view.findViewById(R.id.tv_room_request_room);
        tvTrainer = view.findViewById(R.id.tv_room_request_trainer);
        tvDates = view.findViewById(R.id.tv_room_request_dates);
        tvSeats = view.findViewById(R.id.tv_room_request_size);
        tvBatch.append(" "+noString);
        tvDates.append(" "+noString);
        if(room!=null) {
            tvRoom.append(" "+room.getRoomNumber());
            tvSeats.append(" "+room.getCapacity());
            tvTrainer.append(" "+room.getTrainer());
        } else {
            tvRoom.append(" "+noString);
        }

        etComments = view.findViewById(R.id.et_room_request_comments);
        int maxLength = 500;
        etComments.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

        btnSubmit = view.findViewById(R.id.btn_room_request_submit);
        btnSubmit.setOnClickListener(this);

        etStartDate = view.findViewById(R.id.et_room_request_start_date);
        etStartDate.setOnClickListener(this);

        btnPickStart = view.findViewById(R.id.btn_room_request_start_date);
        btnPickStart.setOnClickListener(this);

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

        btnPickEnd = view.findViewById(R.id.btn_room_request_end_date);
        btnPickEnd.setOnClickListener(this);

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
        if(v.getId()== R.id.et_room_request_start_date || v.getId()==R.id.btn_room_request_start_date){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                        android.R.style.Theme_Material_Dialog_MinWidth,
                        startDateListener,
                        year,month,day);
                if(!etEndDate.getText().toString().equals("")) {
                    Date endDate = null;
                    try {
                        endDate = f.parse(etEndDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        endDate = new Date(Long.MAX_VALUE);
                    }
                    dialog.getDatePicker().setMaxDate(endDate.getTime());
                }
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
                dialog.show();
        } else if ( v.getId()==R.id.et_room_request_end_date || v.getId()==R.id.btn_room_request_end_date) {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(v.getContext(),
                    android.R.style.Theme_Material_Dialog_MinWidth,
                    endDateListener,
                    year,month,day);
            if(!etStartDate.getText().toString().equals("")){
                String startDateString = etStartDate.getText().toString();
                Date startDate = null;
                try {
                    startDate = f.parse(startDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                    startDate = new Date(System.currentTimeMillis());
                }
                dialog.getDatePicker().setMinDate(startDate.getTime());
            } else {
                dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            }
            dialog.show();
        } else if (v.getId()==R.id.btn_room_request_submit) {
            submitRequest(v);

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.host_main_fragment_container,new RoomRequestTableFragment());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();
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

        apiService.postSubmitRoomRequest(room,null,etStartDate.getText().toString(),etEndDate.getText().toString(),etComments.getText().toString(),submitListener,errorListener);
    }

}
