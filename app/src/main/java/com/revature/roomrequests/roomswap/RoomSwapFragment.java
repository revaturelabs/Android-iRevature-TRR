package com.revature.roomrequests.roomswap;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Room;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomSwapFragment extends Fragment {

    TextView tvBatch1,tvRoom1,tvTrainer1,tvDates1,tvBatch2,tvRoom2,tvTrainer2,tvDates2;
    EditText etComments, etStartDate, etEndDate;
    Button btnSubmit;
    ImageButton btnPickStart, btnPickEnd;
    
    Room room1,room2;

    DatePickerDialog.OnDateSetListener startDateListener,endDateListener;


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
        
        tvBatch1 = view.findViewById(R.id.tv_room1_swap_batch);
        tvRoom1 = view.findViewById(R.id.tv_room1_swap_room);
        tvTrainer1 = view.findViewById(R.id.tv_room1_swap_trainer);
        tvDates1 = view.findViewById(R.id.tv_room1_swap_dates);

        tvBatch2 = view.findViewById(R.id.tv_room2_swap_batch);
        tvRoom2 = view.findViewById(R.id.tv_room2_swap_room);
        tvTrainer2 = view.findViewById(R.id.tv_room2_swap_trainer);
        tvDates2 = view.findViewById(R.id.tv_room2_swap_dates);
        
        etStartDate = view.findViewById(R.id.et_room_swap_start_date);
        etEndDate = view.findViewById(R.id.et_room_swap_end_date);
        
        btnSubmit = view.findViewById(R.id.btn_room_swap_submit);
        
        btnPickStart = view.findViewById(R.id.btn_room_swap_start_date);
        btnPickEnd = view.findViewById(R.id.btn_room_swap_end_date);
        
        
        if(room1!=null && room2!=null) {
            tvBatch1.setText(room1.getBatch());
            tvRoom1.setText(room1.getRoom());
            tvTrainer1.setText(room1.getTrainer());
            tvDates1.setText(room1.getDates());
            tvBatch2.setText(room2.getBatch());
            tvRoom2.setText(room2.getRoom());
            tvTrainer2.setText(room2.getTrainer());
            tvDates2.setText(room2.getDates());
        }
        
        
        
        return view;
    }

}
