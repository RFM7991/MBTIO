package com.rk.mbtio;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatRecyclerViewAdapter extends Adapter<ChatRecyclerViewAdapter.CustomViewHolder> {

    // provide a reference to the view for each data item
    // complex data item may need more than one view per item
    // you provide access to all the iews for a data item in a view holder
    private ArrayList<UserMessage> messages;
    public ViewPager viewPager;

    final int IN = 0;
    final int OUT = 1;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        // provide a reference to the view for each data item
        // complex data item may need more than one view per item
        // you provide access to all the iews for a data item in a view holder

        // specify data items
        public View view;
        public TextView message;

        public CustomViewHolder(View v) {
            super(v);
            view = v;
            message = view.findViewById(R.id.message_view);
        }
    }

    // Specify appropriate constructor for dataset
    public ChatRecyclerViewAdapter(ArrayList<UserMessage> data) {
        messages = data;
    }


    public int getItemViewType(int position) {
        UserMessage m = (UserMessage) messages.get(position);

        if (m.getDirection() == 0) {
            // recieving message
            return IN;
        } else {
            // sedning message
            return OUT;
        }
    }

    // Create new views (invoked by the layout manager)
    public ChatRecyclerViewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;

        if (viewType == IN) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.incoming_message, parent, false);
        }
        else if (viewType == OUT) {
            // create a new view
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.outgoing_message, parent, false);

        }
            CustomViewHolder vh = new CustomViewHolder(v);
            return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        // - get element from your dataset at this position
        // -replace the contents of the view with that element
        UserMessage m = messages.get(position);

        // set text
        holder.message.setText(m.getMessageText());


    }

    // get size of dataset in recycler view
    @Override
    public int getItemCount() {
        return messages.size();
    }

    // set view pager for conversations buttons to change to chat fragment
    public void setViewPager(ViewPager vp) {
        viewPager = vp;
    };


    // add message at runtime
    public void addMessage(UserMessage m) {
        messages.add(m);
        notifyDataSetChanged();
    }


}
