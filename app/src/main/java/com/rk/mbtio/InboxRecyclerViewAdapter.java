package com.rk.mbtio;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rk.mbtio.DriverFragments.ChatFragment;
import com.rk.mbtio.DriverFragments.ConversationFragment;
import com.rk.mbtio.DriverActivity.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Random;

public class InboxRecyclerViewAdapter extends RecyclerView.Adapter<InboxRecyclerViewAdapter.CustomViewHolder> {

    // provide a reference to the view for each data item
    // complex data item may need more than one view per item
    // you provide access to all the iews for a data item in a view holder
    private ArrayList<ConversationFragment> fragments;
    public ViewPager viewPager;
    public SectionsPagerAdapter pagerAdapter;
    private String fragment_context;

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView  preview;
        private ImageView profile;

        // provide a reference to the view for each data item
        // complex data item may need more than one view per item
        // you provide access to all the iews for a data item in a view holder

        // specify data items
        public View view;

        public CustomViewHolder(View v) {
            super(v);
            view = v;

            profile = (ImageView) view.findViewById(R.id.profile);
            preview = (TextView) view.findViewById(R.id.message_preview);
        }
    }

    // Specify appropriate constructor for dataset
    public InboxRecyclerViewAdapter(ArrayList<ConversationFragment> data) {

        fragments = data;
    }

    // Create new views (invoked by the layout manager)
    public InboxRecyclerViewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_conversation, parent, false);

        CustomViewHolder vh = new CustomViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        // - get element from your dataset at this position
        // -replace the contents of the view with that element
        ConversationFragment f = fragments.get(position);

        // set profile initial, font, and color
        holder.profile.getBackground().setColorFilter(randomColor(), PorterDuff.Mode.MULTIPLY);

        // set button text
        holder.preview.setText(f.getPreview());

    }

    // get size of dataset in recycler view
    @Override
    public int getItemCount() {
        return fragments.size();
    }

    // get random color
    public int randomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    // set view pager for conversations buttons to change to chat fragment
    public void setViewPager(ViewPager vp) {
        this.viewPager = vp;
    };

    public void setFragmentContext(String type) {
        fragment_context = type;
    }

    public void setPagerAdapter(SectionsPagerAdapter pagerAdapter) {
        this.pagerAdapter = pagerAdapter;
    }
}
