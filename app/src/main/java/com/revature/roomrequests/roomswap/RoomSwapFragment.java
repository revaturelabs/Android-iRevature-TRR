package com.revature.roomrequests.roomswap;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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
import com.revature.roomrequests.roomrequesttable.RoomRequestTableFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomSwapFragment extends Fragment implements View.OnClickListener {

    TextView tvBatch1,tvRoom1,tvTrainer1,tvDates1,tvSeats1,tvBatch2,tvRoom2,tvTrainer2,tvDates2,tvSeats2;
    EditText etComments, etStartDate, etEndDate;
    Button btnSubmit;
    ImageButton btnPickStart, btnPickEnd;
    
    Room room1,room2;

    DatePickerDialog.OnDateSetListener startDateListener,endDateListener;
    private SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");

    TextWatcher textWatcher;


    public RoomSwapFragment() {
        // Required empty public constructor
    }
    
    public RoomSwapFragment(Room room1, Room room2) {
        this.room1 = room1;
        this.room2 = room2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room_swap, container, false);
        
        tvBatch1 = view.findViewById(R.id.tv_swap_room1_batch);
        tvRoom1 = view.findViewById(R.id.tv_swap_room1_room);
        tvTrainer1 = view.findViewById(R.id.tv_swap_room1_trainer);
        tvDates1 = view.findViewById(R.id.tv_swap_room1_dates);
        tvSeats1 = view.findViewById(R.id.tv_swap_room1_size);

        tvBatch2 = view.findViewById(R.id.tv_swap_room2_batch);
        tvRoom2 = view.findViewById(R.id.tv_swap_room2_room);
        tvTrainer2 = view.findViewById(R.id.tv_swap_room2_trainer);
        tvDates2 = view.findViewById(R.id.tv_swap_room2_dates);
        tvSeats2 = view.findViewById(R.id.tv_swap_room2_size);
        
        etStartDate = view.findViewById(R.id.et_room_swap_start_date);
        etStartDate.setOnClickListener(this);
        etEndDate = view.findViewById(R.id.et_room_swap_end_date);
        etEndDate.setOnClickListener(this);

        etComments = view.findViewById(R.id.et_room_swap_comments);
        int maxLength = 500;
        etComments.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
        
        btnSubmit = view.findViewById(R.id.btn_room_swap_submit);
        btnSubmit.setOnClickListener(this);
        
        btnPickStart = view.findViewById(R.id.btn_room_swap_start_date);
        btnPickStart.setOnClickListener(this);
        btnPickEnd = view.findViewById(R.id.btn_room_swap_end_date);
        btnPickEnd.setOnClickListener(this);
        
        
        if(room1!=null && room2!=null) {

            tvBatch1.append(" "+room1.getBatch());
            tvRoom1.append(" "+room1.getRoomNumber());
            tvTrainer1.append(" "+room1.getTrainer());
            tvDates1.append(" "+room1.getDates());
            tvSeats1.append(" "+room1.getCapacity());
            tvBatch2.append(" "+room2.getBatch());
            tvRoom2.append(" "+room2.getRoomNumber());
            tvTrainer2.append(" "+room2.getTrainer());
            tvDates2.append(" "+room2.getDates());
            tvSeats2.append(" "+room2.getCapacity());
        }

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

        checkFieldsForValidValues();
        
        return view;
    }

    void checkFieldsForValidValues() {
        if(etComments.getText().toString().equals("")){
            btnSubmit.setEnabled(false);
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.revature_orange_faded));
        } else {
            btnSubmit.setEnabled(true);
            btnSubmit.setBackgroundColor(getResources().getColor(R.color.revature_orange));
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.et_room_swap_start_date || v.getId()==R.id.btn_room_swap_start_date){
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
        } else if ( v.getId()==R.id.et_room_swap_end_date || v.getId()==R.id.btn_room_swap_end_date) {
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
        } else if (v.getId()==R.id.btn_room_swap_submit) {
            Toast.makeText(getContext(),"Room: "+room1.getRoomNumber()+" swap with "+room2.getRoomNumber()+" was submitted",Toast.LENGTH_SHORT).show();

            //TODO: submit swap request

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.host_main_fragment_container,new RoomRequestTableFragment());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.commit();
        }
    }

}
