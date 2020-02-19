package com.revature.roomrequests.requestlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.revature.roomrequests.R;
import com.revature.roomrequests.pojo.Request;

import java.util.ArrayList;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestViewHolder> {
    ArrayList<Request> requests;

    Context context;
    FragmentManager fm;

    private String noString="N/A";

    public RequestListAdapter(){}

    public RequestListAdapter(Context context, FragmentManager fm, ArrayList<Request> requests) {
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
        holder.tvRequestDates.append(" " + requests.get(position).getDates());

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

            holder.tvRoom1Size.append(" " + requests.get(position).getRoom1().getCapacity());

            if (requests.get(position).getRoom1().getTrainer() == null) {
                holder.tvRoom1Trainer.append(" "+noString);
            } else {
                holder.tvRoom1Trainer.append(" "+requests.get(position).getRoom1().getTrainer());
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

            holder.tvRoom2Size.append(" " + requests.get(position).getRoom2().getCapacity());

            if (requests.get(position).getRoom2().getTrainer() == null) {
                holder.tvRoom2Trainer.append(" "+noString);
            } else {
                holder.tvRoom2Trainer.append(" "+requests.get(position).getRoom2().getTrainer());
            }

        } else if(requests.get(position).getRoom1()!=null) {
            holder.cvRoom2.setVisibility(CardView.GONE);
//            holder.tvRoom2Batch.append(" "+noString);
//            holder.tvRoom2Room.append(" "+requests.get(position).getRoom1().getRoom());
//            holder.tvRoom2Trainer.append(" "+noString);
//            holder.tvRoom2Dates.append(" "+noString);
        }

        if(requests.get(position).getStatus().equals("accepted")){
            holder.btnReject.setVisibility(View.GONE);
            holder.btnAccept.setVisibility(View.GONE);
            holder.cvRoom1.setBackgroundColor(context.getResources().getColor(R.color.room_available));
            holder.cvRoom2.setBackgroundColor(context.getResources().getColor(R.color.room_available));
        } else if (requests.get(position).getStatus().equals("rejected")){
            holder.btnReject.setVisibility(View.GONE);
            holder.cvRoom1.setBackgroundColor(context.getResources().getColor(R.color.room_unavailable));
            holder.cvRoom2.setBackgroundColor(context.getResources().getColor(R.color.room_unavailable));
        }

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"Request: "+position+" was accepted",Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Reject Request");
                builder.setMessage("Are you sure you want to reject this request?");
                builder.setPositiveButton(R.string.reject, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                requests.get(position).setStatusRejected();
                            }
                        });
                builder.setNegativeButton(R.string.cancel, null);
                builder.setView(R.layout.layout_reject_request_alert);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvRequestDates;
        TextView tvRoom1Batch,tvRoom1Room,tvRoom1Trainer,tvRoom1Size;
        TextView tvRoom2Batch,tvRoom2Room,tvRoom2Trainer,tvRoom2Size;
        CardView cvRoom1,cvRoom2;
        Button btnAccept, btnReject;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRequestDates = itemView.findViewById(R.id.tv_request_dates);

            tvRoom1Batch = itemView.findViewById(R.id.tv_request_room1_batch);
            tvRoom1Room = itemView.findViewById(R.id.tv_request_room1_room);
            tvRoom1Trainer = itemView.findViewById(R.id.tv_request_room1_trainer);
            tvRoom1Size = itemView.findViewById(R.id.tv_request_room1_size);

            tvRoom2Batch = itemView.findViewById(R.id.tv_request_room2_batch);
            tvRoom2Room = itemView.findViewById(R.id.tv_request_room2_room);
            tvRoom2Trainer = itemView.findViewById(R.id.tv_request_room2_trainer);
            tvRoom2Size = itemView.findViewById(R.id.tv_request_room2_size);

            cvRoom2 = itemView.findViewById(R.id.card_room2);
            cvRoom1 = itemView.findViewById(R.id.card_room1);

            btnAccept = itemView.findViewById(R.id.btn_request_accept);
            btnReject = itemView.findViewById(R.id.btn_request_reject);

        }
    }

    public void updateData(ArrayList<Request> requests) {
        this.requests = requests;
        notifyDataSetChanged();
    }
}
