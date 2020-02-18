package com.revature.roomrequests.pendingrequest;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Request;

import java.util.ArrayList;

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestAdapter.RequestViewHolder> {

    ArrayList<Request> requests;

    Context context;
    FragmentManager fm;

    private String noString="N/A";

    public PendingRequestAdapter(){}

    public PendingRequestAdapter(Context context, FragmentManager fm,ArrayList<Request> requests) {
        this.context=context;
        this.fm = fm;
        this.requests=requests;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_request,parent,false);
        //set the view's size, margins, paddings, and layout paramters
        RequestViewHolder vh = new RequestViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, final int position) {
        if(requests.get(position).getRoom1()!=null) {
            if (requests.get(position).getRoom1().getBatch() == null) {
                holder.tvRoom1Batch.append(" "+noString);
            } else {
                holder.tvRoom1Batch.append(" "+requests.get(position).getRoom1().getBatch());
            }
            if (requests.get(position).getRoom1().getRoomNumber() == null) {
                holder.tvRoom1Room.append(" "+noString);
            } else {
                holder.tvRoom1Room.append(" "+requests.get(position).getRoom1().getRoomNumber());
            }
            if (requests.get(position).getRoom1().getTrainer() == null) {
                holder.tvRoom1Trainer.append(" "+noString);
            } else {
                holder.tvRoom1Trainer.append(" "+requests.get(position).getRoom1().getTrainer());
            }
            if (requests.get(position).getRoom1().getDates() == null) {
                holder.tvRoom1Dates.append(" "+noString);
            } else {
                holder.tvRoom1Dates.append(" "+requests.get(position).getRoom1().getDates());
            }
        } else if(requests.get(position).getRoom2()!=null){
            holder.cvRoom1.setVisibility(CardView.GONE);
//            holder.tvRoom1Batch.append(" "+noString);
//            holder.tvRoom1Room.append(" "+requests.get(position).getRoom2().getRoom());
//            holder.tvRoom1Trainer.append(" "+noString);
//            holder.tvRoom1Dates.append(" "+noString);
        }

        if(requests.get(position).getRoom2()!=null) {
            if (requests.get(position).getRoom2().getBatch() == null) {
                holder.tvRoom2Batch.append(" "+noString);
            } else {
                holder.tvRoom2Batch.append(" "+requests.get(position).getRoom2().getBatch());
            }
            if (requests.get(position).getRoom2().getRoomNumber() == null) {
                holder.tvRoom2Room.append(" "+noString);
            } else {
                holder.tvRoom2Room.append(" "+requests.get(position).getRoom2().getRoomNumber());
            }
            if (requests.get(position).getRoom2().getTrainer() == null) {
                holder.tvRoom2Trainer.append(" "+noString);
            } else {
                holder.tvRoom2Trainer.append(" "+requests.get(position).getRoom2().getTrainer());
            }
            if (requests.get(position).getRoom2().getDates() == null) {
                holder.tvRoom2Dates.append(" "+noString);
            } else {
                holder.tvRoom2Dates.append(" "+requests.get(position).getRoom2().getDates());
            }
        } else if(requests.get(position).getRoom1()!=null) {
            holder.cvRoom2.setVisibility(CardView.GONE);
//            holder.tvRoom2Batch.append(" "+noString);
//            holder.tvRoom2Room.append(" "+requests.get(position).getRoom1().getRoom());
//            holder.tvRoom2Trainer.append(" "+noString);
//            holder.tvRoom2Dates.append(" "+noString);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Request: "+position+" was clicked",Toast.LENGTH_SHORT).show();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.host_main_fragment_container,new AcceptRejectRequestFragment(requests.get(position)));
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoom1Batch,tvRoom1Room,tvRoom1Trainer,tvRoom1Dates;
        TextView tvRoom2Batch,tvRoom2Room,tvRoom2Trainer,tvRoom2Dates;
        CardView cvRoom1,cvRoom2;

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

            cvRoom2 = itemView.findViewById(R.id.card_room2);
            cvRoom1 = itemView.findViewById(R.id.card_room1);

        }
    }
}
