package com.rk.mbtio;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rk.mbtio.DriverFragments.ChatFragment;

import java.util.ArrayList;

public class MatchRecyclerViewAdapter extends Adapter<MatchRecyclerViewAdapter.CustomViewHolder> {

    // provide a reference to the view for each data item
    // complex data item may need more than one view per item
    // you provide access to all the iews for a data item in a view holder
    private ArrayList<Match> matches;
    public ViewPager viewPager;
    public Activity activity;
    public int state = 0;
    final int IN = 0;
    final int OUT = 1;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        // provide a reference to the view for each data item
        // complex data item may need more than one view per item
        // you provide access to all the iews for a data item in a view holder

        // specify data items
        public View view;
        public TextView label, name, score;
        public Button button;
        public int uid;

        public CustomViewHolder(View v) {
            super(v);
            view = v;
            label = view.findViewById(R.id.match_label);
            name = view.findViewById(R.id.match_name);
            score = view.findViewById(R.id.match_score);
            button = view.findViewById(R.id.start_chat);
        }
    }

    // Specify appropriate constructor for dataset
    public MatchRecyclerViewAdapter(ArrayList<Match> data) {
        matches = data;
    }

    // Create new views (invoked by the layout manager)
    public MatchRecyclerViewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.match, parent, false);

            CustomViewHolder vh = new CustomViewHolder(v);
            return vh;
    }

    public GlobalSingleton gg;

    public Match ma;

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        // - get element from your dataset at this position
        // -replace the contents of the view with that element
        Match m = matches.get(position);

        // set text

        holder.label.setText(m.mbti);
        holder.name.setText(m.name);
        holder.score.setText(m.score + "");
        holder.uid = m.uid;

        ma = m;
        // set button listeners
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // no chat created
                if (state == 0) {
                    state++;
                    int s = gg.user.uid;
                    gg.addConversation(ma.uid, new Conversation(s, ma.uid, new ArrayList<Message>() ));

                    ChatFragment cf = new ChatFragment();
                    gg.pagerAdapter.notifyDataSetChanged();
                gg.viewPager.setCurrentItem(
                   gg.pagerAdapter.getCount() - 1);
                }
                else {
                    // change current chatFragment to corresponding chat

                }

            }
        });
    }

    // get size of dataset in recycler view
    @Override
    public int getItemCount() {
        return matches.size();
    }

    // set view pager for conversations buttons to change to chat fragment
    public void setViewPager(ViewPager vp) {
        viewPager = vp;
    };


    // add Match at runtime
    public void ADDMatch(Match m, GlobalSingleton x ) {
        gg = x;
        matches.add(m);
        notifyDataSetChanged();
    }


}
