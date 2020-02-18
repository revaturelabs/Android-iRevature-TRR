package com.revature.roomrequests.roomrequest;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Room;

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
    
    private String noString = "N/A";


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
        tvBatch = view.findViewById(R.id.tv_room_request_batch);
        tvRoom = view.findViewById(R.id.tv_room_request_room);
        tvTrainer = view.findViewById(R.id.tv_room_request_trainer);
        tvDates = view.findViewById(R.id.tv_room_request_dates);
        tvSeats = view.findViewById(R.id.tv_room_request_size);
        tvBatch.append(" "+noString);
        tvTrainer.append(" "+noString);
        tvDates.append(" "+noString);
        if(room!=null) {
            tvRoom.append(" "+room.getRoom());
            tvSeats.append(" "+room.getSeats());
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
        return view;
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
            Toast.makeText(getContext(),"Room: "+room.getRoom()+" request was submitted",Toast.LENGTH_SHORT).show();
        }
    }

}
