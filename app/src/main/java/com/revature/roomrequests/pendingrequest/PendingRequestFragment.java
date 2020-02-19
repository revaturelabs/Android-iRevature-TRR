package com.revature.roomrequests.pendingrequest;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Request;
import com.revature.roomrequests.pojo.Room;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class PendingRequestFragment extends Fragment {

    ArrayList<Request> requests;


    public PendingRequestFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pending_request, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_pending_requests);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        getPendingRequests();

        PendingRequestAdapter adapter = new PendingRequestAdapter(getActivity().getApplicationContext(), getFragmentManager(),requests);

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void getPendingRequests(){

    }

}
