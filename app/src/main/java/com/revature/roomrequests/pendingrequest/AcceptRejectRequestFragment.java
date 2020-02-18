package com.revature.roomrequests.pendingrequest;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptRejectRequestFragment extends Fragment {
    
    TextView tvRoom1Batch,tvRoom1Room, tvRoom1Trainer,tvRoom1Dates;
    TextView tvRoom2Batch,tvRoom2Room, tvRoom2Trainer,tvRoom2Dates;

    Button btnAccept,btnReject;
    
    Request request;


    public AcceptRejectRequestFragment() {
        // Required empty public constructor
    }
    
    public AcceptRejectRequestFragment(Request request) {
        this.request = request;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accept_reject_request, container, false);
        
        tvRoom1Batch = view.findViewById(R.id.tv_accept_reject_request_room1_batch);
        tvRoom1Room = view.findViewById(R.id.tv_accept_reject_request_room1_room);
        tvRoom1Trainer = view.findViewById(R.id.tv_accept_reject_request_room1_trainer);
        tvRoom1Dates = view.findViewById(R.id.tv_accept_reject_request_room1_dates);

        tvRoom2Batch = view.findViewById(R.id.tv_accept_reject_request_room2_batch);
        tvRoom2Room = view.findViewById(R.id.tv_accept_reject_request_room2_room);
        tvRoom2Trainer = view.findViewById(R.id.tv_accept_reject_request_room2_trainer);
        tvRoom2Dates = view.findViewById(R.id.tv_accept_reject_request_room2_dates);
        

        if(request.getRoom1()!=null) {
            if (request.getRoom1().getBatch() == null) {
                tvRoom1Batch.setText(R.string.no_string);
            } else {
                tvRoom1Batch.setText(request.getRoom1().getBatch());
            }
            if (request.getRoom1().getRoomNumber() == null) {
                tvRoom1Room.setText(R.string.no_string);
            } else {
                tvRoom1Room.setText(request.getRoom1().getRoomNumber());
            }
            if (request.getRoom1().getTrainer() == null) {
                tvRoom1Trainer.setText(R.string.no_string);
            } else {
                tvRoom1Trainer.setText(request.getRoom1().getTrainer());
            }
            if (request.getRoom1().getDates() == null) {
                tvRoom1Dates.setText(R.string.no_string);
            } else {
                tvRoom1Dates.setText(request.getRoom1().getDates());
            }
        } else if(request.getRoom2()!=null){
            tvRoom1Batch.setText(R.string.no_string);
            tvRoom1Room.setText(request.getRoom2().getRoomNumber());
            tvRoom1Trainer.setText(R.string.no_string);
            tvRoom1Dates.setText(R.string.no_string);
        }

        if(request.getRoom2()!=null) {
            if (request.getRoom2().getBatch() == null) {
                tvRoom2Batch.setText(R.string.no_string);
            } else {
                tvRoom2Batch.setText(request.getRoom2().getBatch());
            }
            if (request.getRoom2().getRoomNumber() == null) {
                tvRoom2Room.setText(R.string.no_string);
            } else {
                tvRoom2Room.setText(request.getRoom2().getRoomNumber());
            }
            if (request.getRoom2().getTrainer() == null) {
                tvRoom2Trainer.setText(R.string.no_string);
            } else {
                tvRoom2Trainer.setText(request.getRoom2().getTrainer());
            }
            if (request.getRoom2().getDates() == null) {
                tvRoom2Dates.setText(R.string.no_string);
            } else {
                tvRoom2Dates.setText(request.getRoom2().getDates());
            }
        } else if(request.getRoom1()!=null) {
            tvRoom2Batch.setText(R.string.no_string);
            tvRoom2Room.setText(request.getRoom1().getRoomNumber());
            tvRoom2Trainer.setText(R.string.no_string);
            tvRoom2Dates.setText(R.string.no_string);
        }
        
        return view;
    }

}
