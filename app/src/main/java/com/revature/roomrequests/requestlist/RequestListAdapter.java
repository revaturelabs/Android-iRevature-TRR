package com.revature.roomrequests.requestlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.material.snackbar.Snackbar;
import com.revature.roomrequests.R;
import com.revature.roomrequests.api.ApiService;
import com.revature.roomrequests.pojo.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestViewHolder> {

    ArrayList<Request> requests;
    Context context;
    FragmentManager fm;
    ApiService apiService;
    View adapterView;
    ViewGroup parent;

    final private String noString = "N/A";
    final private String LOG_TAG = "Request List Adapter";

    public RequestListAdapter(){}

    public RequestListAdapter(Context context, FragmentManager fm, ArrayList<Request> requests) {
        this.context=context;
        this.fm = fm;
        this.requests=requests;
        apiService = new ApiService(context);
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        adapterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_request,parent,false);
        //set the view's size, margins, paddings, and layout paramters
        RequestViewHolder vh = new RequestViewHolder(adapterView);
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
            public void onClick(final View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Accept Request");
                builder.setMessage("Are you sure you want to accept this request?");

                final View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_reject_request_alert,parent,false);

                ((EditText)view.findViewById(R.id.et_reject_request_alert_reason)).setHint("Why are you accepting this request");

                builder.setView(view);
                builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Response.Listener<JSONObject> acceptedListener = new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(LOG_TAG, response.toString());
                                try {
                                    Snackbar.make(v,response.getString("message"), Snackbar.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    Log.d(LOG_TAG,e.toString());
                                }
                            }
                        };

                        Response.ErrorListener errorListener = new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(LOG_TAG, error.toString());
                                Snackbar.make(v,error.toString(), Snackbar.LENGTH_SHORT).show();
                            }
                        };

                        Request request = requests.get(position);

                        EditText editText = view.findViewById(R.id.et_reject_request_alert_reason);
                        String comment = editText.getText().toString();

                        apiService.postAcceptRequest(request, comment, acceptedListener, errorListener);
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);

                builder.show();
            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Reject Request");
                builder.setMessage("Are you sure you want to reject this request?");

                final View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_reject_request_alert,parent,false);

                builder.setView(view);
                builder.setPositiveButton(R.string.reject, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Response.Listener<JSONObject> rejectionListener = new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d(LOG_TAG, response.toString());
                                        try {
                                            Snackbar.make(v,response.getString("message"), Snackbar.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            Log.d(LOG_TAG,e.toString());
                                        }
                                    }
                                };

                                Response.ErrorListener errorListener = new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(LOG_TAG, error.toString());
                                        Snackbar.make(v,error.toString(), Snackbar.LENGTH_SHORT).show();
                                    }
                                };

                                Request request = requests.get(position);

                                EditText editText = view.findViewById(R.id.et_reject_request_alert_reason);
                                String comment = editText.getText().toString();

                                apiService.postRejectRequest(request, comment, rejectionListener, errorListener);
                            }
                        });
                builder.setNegativeButton(R.string.cancel, null);

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
