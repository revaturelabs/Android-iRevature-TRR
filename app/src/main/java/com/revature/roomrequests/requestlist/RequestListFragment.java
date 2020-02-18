package com.revature.roomrequests.requestlist;


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
public class RequestListFragment extends Fragment {

    ArrayList<Request> requests;

    public RequestListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycle_request_list);

        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(view.getContext());

        recyclerView.setLayoutManager(linearLayoutManager);

        getPendingRequests();

        RequestListAdapter adapter = new RequestListAdapter(getActivity().getApplicationContext(), getFragmentManager(),requests);

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void getPendingRequests(){
        ArrayList<Request> requests = new ArrayList<>();

        this.requests=requests;
    }

}
