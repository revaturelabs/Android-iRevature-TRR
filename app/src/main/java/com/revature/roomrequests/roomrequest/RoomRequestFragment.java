package com.revature.roomrequests.roomrequest;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomRequestFragment extends Fragment implements View.OnClickListener {

    TextView tvBatch,tvRoom,tvTrainer,tvDates;
    EditText etComments, etStartDate, etEndDate;
    Button btnSubmit;
    ImageButton btnPickStart, btnPickEnd;
    Room room;
    DatePickerDialog.OnDateSetListener startDateListener,endDateListener;


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
        tvBatch.setText(R.string.no_string);
        tvTrainer.setText(R.string.no_string);
        tvDates.setText(R.string.no_string);
        if(room!=null) {
            tvRoom.setText(room.getRoomNumber());
        } else {
            tvRoom.setText(R.string.no_string);
        }

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
            dialog.show();
        } else if (v.getId()==R.id.btn_room_request_submit) {
            Toast.makeText(getContext(),"Room: "+room.getRoomNumber()+" request was submitted",Toast.LENGTH_SHORT).show();
        }
    }

}
