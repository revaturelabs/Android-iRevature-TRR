package com.revature.roomrequests.acceptedrequest;


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
public class AcceptedRequestFragment extends Fragment {


    ArrayList<Request> requests;


    public AcceptedRequestFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_accepted_request, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_accepted_requests);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        getAcceptedRequests();

        AcceptedRequestAdapter adapter = new AcceptedRequestAdapter(getActivity().getApplicationContext(), getFragmentManager(),requests);

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void getAcceptedRequests(){


    }

}
