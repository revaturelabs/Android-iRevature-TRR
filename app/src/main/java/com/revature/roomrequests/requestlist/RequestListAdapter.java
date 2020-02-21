package com.revature.roomrequests.requestlist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    public void onBindViewHolder(@NonNull final RequestViewHolder holder, final int position) {

        final Resources resources = context.getResources();

        holder.tvRequestDates.setText(resources.getText(R.string.swap_dates) + " " + requests.get(position).getDates());
        holder.tvDateMade.setText(" "+requests.get(position).getDateMade());
        holder.tvReason.setText(requests.get(position).getReasonForRequest());

        if(requests.get(position).getRoom1()!=null) {
            holder.tvRequestType.setText(R.string.request_made_on);
            if (requests.get(position).getRoom1().getBatch() == null) {
                holder.tvRoom1Batch.setText(resources.getText(R.string.batch) + " " + noString);
            } else {
                holder.tvRoom1Batch.setText(resources.getText(R.string.batch) + " " + requests.get(position).getRoom1().getBatch());
            }

            if (requests.get(position).getRoom1().getRoomNumber() == null) {
                holder.tvRoom1Room.setText(resources.getText(R.string.room_number) + " " + noString);
            } else {
                holder.tvRoom1Room.setText(resources.getText(R.string.room_number) + " " + requests.get(position).getRoom1().getRoomNumber());
            }

            holder.tvRoom1Size.setText(resources.getText(R.string.seats) + " " + requests.get(position).getRoom1().getCapacity());

            if (requests.get(position).getRoom1().getTrainer() == null) {
                holder.tvRoom1Trainer.setText(resources.getText(R.string.trainer) + " " + noString);
            } else {
                holder.tvRoom1Trainer.setText(resources.getText(R.string.trainer) + " " + requests.get(position).getRoom1().getTrainer());
            }
        } else if(requests.get(position).getRoom2()!=null){
            holder.cvRoom1.setVisibility(CardView.GONE);
        }

        if(requests.get(position).getRoom2()!=null) {
            holder.tvRequestType.setText(R.string.swap_request_made_on);
            if (requests.get(position).getRoom2().getBatch() == null) {
                holder.tvRoom2Batch.setText(resources.getText(R.string.batch) + " " + noString);
            } else {
                holder.tvRoom2Batch.setText(resources.getText(R.string.batch) + " " + requests.get(position).getRoom2().getBatch());
            }

            if (requests.get(position).getRoom2().getRoomNumber() == null) {
                holder.tvRoom2Room.setText(resources.getText(R.string.room_number) + " " + noString);
            } else {
                holder.tvRoom2Room.setText(resources.getText(R.string.room_number) + " " + requests.get(position).getRoom2().getRoomNumber());
            }

            holder.tvRoom2Size.setText(resources.getText(R.string.seats) + " " + requests.get(position).getRoom2().getCapacity());

            if (requests.get(position).getRoom2().getTrainer() == null) {
                holder.tvRoom2Trainer.setText(resources.getText(R.string.trainer) + " " +  noString);
            } else {
                holder.tvRoom2Trainer.setText(resources.getText(R.string.trainer) + " " + requests.get(position).getRoom2().getTrainer());
            }
        } else if(requests.get(position).getRoom1()!=null) {
            holder.cvRoom2.setVisibility(CardView.GONE);
        }

        if(requests.get(position).getStatus().equals("accepted")){
            holder.btnReject.setVisibility(View.GONE);
            holder.btnAccept.setVisibility(View.GONE);
            holder.cvRoom1.setBackgroundColor(resources.getColor(R.color.room_available));
            holder.cvRoom2.setBackgroundColor(resources.getColor(R.color.room_available));
        } else if (requests.get(position).getStatus().equals("rejected")){
            holder.btnReject.setVisibility(View.GONE);
            holder.cvRoom1.setBackgroundColor(resources.getColor(R.color.room_unavailable));
            holder.cvRoom2.setBackgroundColor(resources.getColor(R.color.room_unavailable));
        }

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Accept Request");
                builder.setMessage(resources.getString(R.string.accept_request_dialog_message));

                final View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_request_action_dialog,parent,false);

                final EditText etReason = view.findViewById(R.id.et_reject_request_alert_reason);
                final TextView tvCount = view.findViewById(R.id.tv_request_dialog_char_count);
                tvCount.setText("0/" + resources.getInteger(R.integer.comments_maximum));
                etReason.setFilters(new InputFilter[] {new InputFilter.LengthFilter(resources.getInteger(R.integer.comments_maximum))});

                builder.setView(view);

                builder.setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Response.Listener<JSONObject> acceptedListener = new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Request request = requests.get(position);
                                request.setStatus("accepted");
                                notifyDataSetChanged();
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

                        EditText editText = view.findViewById(R.id.et_reject_request_alert_reason);
                        String comment = editText.getText().toString();
                        Request request = requests.get(position);

                        apiService.postAcceptRequest(request, comment, acceptedListener, errorListener);
                    }
                });

                builder.setNegativeButton(R.string.cancel, null);

                final AlertDialog acceptDialog = builder.create();

                acceptDialog.show();

                acceptDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        // check Fields For Empty Values
                        if (etReason.getText().toString().length() == 0) {
                            acceptDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                        } else {
                            acceptDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        }
                        tvCount.setText(etReason.getText().toString().length() + "/" + resources.getInteger(R.integer.comments_maximum));
                    }
                };

                etReason.addTextChangedListener(textWatcher);
            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Reject Request");
                builder.setMessage(resources.getString(R.string.reject_request_dialog_message));

                final View view = LayoutInflater.from(v.getContext()).inflate(R.layout.layout_request_action_dialog,parent,false);

                final EditText etReason = view.findViewById(R.id.et_reject_request_alert_reason);
                final TextView tvCount = view.findViewById(R.id.tv_request_dialog_char_count);
                tvCount.setText("0/" + resources.getInteger(R.integer.comments_maximum));
                etReason.setFilters(new InputFilter[] {new InputFilter.LengthFilter(resources.getInteger(R.integer.comments_maximum))});

                builder.setView(view);

                builder.setPositiveButton(R.string.reject, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Response.Listener<JSONObject> rejectionListener = new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Request request = requests.get(position);
                                        request.setStatus("rejected");
                                        notifyDataSetChanged();
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

                                EditText editText = view.findViewById(R.id.et_reject_request_alert_reason);
                                String comment = editText.getText().toString();
                                Request request = requests.get(position);

                                apiService.postRejectRequest(request, comment, rejectionListener, errorListener);
                            }
                        });

                builder.setNegativeButton(R.string.cancel, null);

                final AlertDialog rejectDialog = builder.create();

                rejectDialog.show();

                rejectDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

                TextWatcher textWatcher = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        // check Fields For Empty Values
                        if (etReason.getText().toString().length() == 0) {
                            rejectDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                        } else {
                            rejectDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                        }
                        tvCount.setText(etReason.getText().toString().length() + "/" + resources.getInteger(R.integer.comments_maximum));
                    }
                };

                etReason.addTextChangedListener(textWatcher);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class RequestViewHolder extends RecyclerView.ViewHolder {
        TextView tvRequestDates,tvDateMade,tvRequestType,tvReason;
        TextView tvRoom1Batch,tvRoom1Room,tvRoom1Trainer,tvRoom1Size;
        TextView tvRoom2Batch,tvRoom2Room,tvRoom2Trainer,tvRoom2Size;
        CardView cvRoom1,cvRoom2;
        Button btnAccept, btnReject;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRequestDates = itemView.findViewById(R.id.tv_request_dates);
            tvDateMade = itemView.findViewById(R.id.tv_date_request_made);

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

            tvRequestType = itemView.findViewById(R.id.tv_request_type);
            tvReason = itemView.findViewById(R.id.tv_request_row_comments);

        }
    }

    public void updateData(ArrayList<Request> requests) {
        this.requests = requests;
        notifyDataSetChanged();
    }
}
