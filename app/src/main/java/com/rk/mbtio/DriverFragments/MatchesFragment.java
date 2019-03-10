package com.rk.mbtio.DriverFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.rk.mbtio.Conversation;
import com.rk.mbtio.DriverActivity;
import com.rk.mbtio.GlobalSingleton;
import com.rk.mbtio.InboxRecyclerViewAdapter;
import com.rk.mbtio.Match;
import com.rk.mbtio.MatchRecyclerViewAdapter;
import com.rk.mbtio.R;
import com.rk.mbtio.User;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchesFragment extends Fragment {

    public ViewPager viewPager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView usersButton;
    private DriverActivity.SectionsPagerAdapter pagerAdapter;

    public MatchesFragment() {
        // required empty
    }

    public static MatchesFragment newInstance() {
        MatchesFragment fragment = new MatchesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Button button;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matches, container, false);

        button = view.findViewById(R.id.get_matches_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMatches();
            }
        });
        // recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.matches_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<Match> matches = ((GlobalSingleton) getActivity().getApplication()).getMatches(10);

        for(Match m : matches) { //m
            //ConversationFragment cf = new ConversationFragment();
            //cf.otherUser =  ((GlobalSingleton) this.getActivity().getApplication()).getUser(key);
            //cf.setPreview(conversations.get(key).preview);
           // cf.rid = conversations.get(key).rid;
         //   fragments.add(cf);
        }

        // specify an adapter
      //  mAdapter = new MatchRecyclerViewAdapter(fragments);
        //((MatchRecyclerViewAdapter) mAdapter).setViewPager(viewPager);
        //((MatchRecyclerViewAdapter) mAdapter).setPagerAdapter(pagerAdapter);
        //recyclerView.setAdapter(mAdapter);

        return view;
    }


    public void getMatches() {
        User u = ((GlobalSingleton) this.getActivity().getApplication()).user;
        int max = 10;
        ((GlobalSingleton) this.getActivity().getApplication()).requestTool.getMatches(u.uid, u.pin, max);
    }
}