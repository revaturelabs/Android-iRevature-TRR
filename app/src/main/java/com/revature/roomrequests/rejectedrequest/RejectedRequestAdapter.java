package com.revature.roomrequests.rejectedrequest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Request;

import java.util.ArrayList;

public class RejectedRequestAdapter extends RecyclerView.Adapter<RejectedRequestAdapter.RequestViewHolder> {

    ArrayList<Request> requests;

    Context context;
    FragmentManager fm;

    public RejectedRequestAdapter() {
    }

    public RejectedRequestAdapter(Context context, FragmentManager fm, ArrayList<Request> requests) {
        this.context = context;
        this.fm = fm;
        this.requests = requests;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_request, parent, false);
        //set the view's size, margins, paddings, and layout paramters
        RequestViewHolder vh = new RequestViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, final int position) {
        if (requests.get(position).getRoom1() != null) {
            if (requests.get(position).getRoom1().getBatch() == null) {
                holder.tvRoom1Batch.setText(R.string.no_string);
            } else {
                holder.tvRoom1Batch.setText(requests.get(position).getRoom1().getBatch());
            }
            if (requests.get(position).getRoom1().getRoomNumber() == null) {
                holder.tvRoom1Room.setText(R.string.no_string);
            } else {
                holder.tvRoom1Room.setText(requests.get(position).getRoom1().getRoomNumber());
            }
            if (requests.get(position).getRoom1().getTrainer() == null) {
                holder.tvRoom1Trainer.setText(R.string.no_string);
            } else {
                holder.tvRoom1Trainer.setText(requests.get(position).getRoom1().getTrainer());
            }
            if (requests.get(position).getRoom1().getDates() == null) {
                holder.tvRoom1Dates.setText(R.string.no_string);
            } else {
                holder.tvRoom1Dates.setText(requests.get(position).getRoom1().getDates());
            }
        } else if (requests.get(position).getRoom2() != null) {
            holder.tvRoom1Batch.setText(R.string.no_string);
            holder.tvRoom1Room.setText(requests.get(position).getRoom2().getRoomNumber());
            holder.tvRoom1Trainer.setText(R.string.no_string);
            holder.tvRoom1Dates.setText(R.string.no_string);
        }

        if (requests.get(position).getRoom2() != null) {
            if (requests.get(position).getRoom2().getBatch() == null) {
                holder.tvRoom2Batch.setText(R.string.no_string);
            } else {
                holder.tvRoom2Batch.setText(requests.get(position).getRoom2().getBatch());
            }
            if (requests.get(position).getRoom2().getRoomNumber() == null) {
                holder.tvRoom2Room.setText(R.string.no_string);
            } else {
                holder.tvRoom2Room.setText(requests.get(position).getRoom2().getRoomNumber());
            }
            if (requests.get(position).getRoom2().getTrainer() == null) {
                holder.tvRoom2Trainer.setText(R.string.no_string);
            } else {
                holder.tvRoom2Trainer.setText(requests.get(position).getRoom2().getTrainer());
            }
            if (requests.get(position).getRoom2().getDates() == null) {
                holder.tvRoom2Dates.setText(R.string.no_string);
            } else {
                holder.tvRoom2Dates.setText(requests.get(position).getRoom2().getDates());
            }
        } else if (requests.get(position).getRoom1() != null) {
            holder.tvRoom2Batch.setText(R.string.no_string);
            holder.tvRoom2Room.setText(requests.get(position).getRoom1().getRoomNumber());
            holder.tvRoom2Trainer.setText(R.string.no_string);
            holder.tvRoom2Dates.setText(R.string.no_string);
        }
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoom1Batch, tvRoom1Room, tvRoom1Trainer, tvRoom1Dates;
        TextView tvRoom2Batch, tvRoom2Room, tvRoom2Trainer, tvRoom2Dates;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoom1Batch = itemView.findViewById(R.id.tv_request_room1_batch);
            tvRoom1Room = itemView.findViewById(R.id.tv_request_room1_room);
            tvRoom1Trainer = itemView.findViewById(R.id.tv_request_room1_trainer);
            tvRoom1Dates = itemView.findViewById(R.id.tv_request_room1_dates);

            tvRoom2Batch = itemView.findViewById(R.id.tv_request_room2_batch);
            tvRoom2Room = itemView.findViewById(R.id.tv_request_room2_room);
            tvRoom2Trainer = itemView.findViewById(R.id.tv_request_room2_trainer);
            tvRoom2Dates = itemView.findViewById(R.id.tv_request_room2_dates);
        }
    }
}