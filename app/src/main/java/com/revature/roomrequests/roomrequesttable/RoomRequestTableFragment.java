package com.revature.roomrequests.roomrequesttable;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.revature.roomrequests.MainActivity;
import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class RoomRequestTableFragment extends Fragment implements View.OnClickListener {
    
    EditText etStartDate,etEndDate;
    ImageButton btnStartDate, btnEndDate;

    DatePickerDialog.OnDateSetListener startDateListener,endDateListener;
    private SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");

    ArrayList<String> batches;
    ArrayList<String> rooms;
    ArrayList<String> trainers;
    ArrayList<String> dates;
    ArrayList<String> seats;
    ArrayList<Boolean> availabilities;


    public RoomRequestTableFragment() {
        // Required empty public constructor
    }

    public RoomRequestTableFragment(ArrayList<String> batches, ArrayList<String> rooms, ArrayList<String> trainers, ArrayList<String> dates) {
        this.batches = batches;
        this.rooms = rooms;
        this.trainers = trainers;
        this.dates = dates;
    }

    public RoomRequestTableFragment(ArrayList<Room> rooms) {
        for(int i = 0; i<rooms.size() ; i++) {
            batches.add(rooms.get(i).getBatch());
            this.rooms.add(rooms.get(i).getRoom());
            trainers.add(rooms.get(i).getTrainer());
            dates.add(rooms.get(i).getDates());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_request_table, container, false);
        
        etStartDate = view.findViewById(R.id.et_room_table_start_date);
        etStartDate.setOnClickListener(this);
        etEndDate = view.findViewById(R.id.et_room_table_end_date);
        etEndDate.setOnClickListener(this);
        btnStartDate = view.findViewById(R.id.btn_room_table_start_date);
        btnStartDate.setOnClickListener(this);
        btnEndDate = view.findViewById(R.id.btn_room_table_end_date);
        btnEndDate.setOnClickListener(this);

        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                String date = month+"/"+dayOfMonth+"/"+year;
                etStartDate.setText(date);
            }
        };

        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = month+"/"+dayOfMonth+"/"+year;
                etEndDate.setText(date);
            }
        };

        RecyclerView recyclerView = view.findViewById(R.id.recycle_room_requests_table);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        getRooms();

        RoomRequestTableAdapter adapter = new RoomRequestTableAdapter(getActivity().getApplicationContext(), getFragmentManager(),batches,rooms,trainers,dates,seats,availabilities);

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void getRooms() {

        ArrayList<Room> rooms = new ArrayList<>(Arrays.asList(
                new Room("2001-Mobile-iOS","200","Uday","2/21-3/21","30",false),
                new Room("2001-Mobile-And","300","Mayur","2/21-3/21","25",false),
                new Room(null,"400",null,null,"20",true)));

        batches = new ArrayList<>();
        this.rooms = new ArrayList<>();
        trainers = new ArrayList<>();
        dates = new ArrayList<>();
        seats = new ArrayList<>();
        availabilities = new ArrayList<>();

        for(int i = 0; i<rooms.size() ; i++) {
            batches.add(rooms.get(i).getBatch());
            this.rooms.add(rooms.get(i).getRoom());
            trainers.add(rooms.get(i).getTrainer());
            dates.add(rooms.get(i).getDates());
            seats.add(rooms.get(i).getSeats());
            availabilities.add(rooms.get(i).isAvailable());
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.et_room_table_start_date || v.getId()==R.id.btn_room_table_start_date){
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
        } else if ( v.getId()==R.id.et_room_table_end_date || v.getId()==R.id.btn_room_table_end_date) {
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
        }
    }
}
