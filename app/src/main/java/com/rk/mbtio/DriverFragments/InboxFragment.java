package com.rk.mbtio.DriverFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rk.mbtio.Conversation;
import com.rk.mbtio.DriverActivity;
import com.rk.mbtio.GlobalSingleton;
import com.rk.mbtio.InboxRecyclerViewAdapter;
import com.rk.mbtio.DriverActivity.SectionsPagerAdapter;
import com.rk.mbtio.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class InboxFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ViewPager viewPager;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView usersButton;
    private SectionsPagerAdapter pagerAdapter;


    public void addViewPager(ViewPager v) {
        this.viewPager = v;
    }
    public void addPagerAdapter(SectionsPagerAdapter adp) {
        pagerAdapter = adp;
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public InboxFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static InboxFragment newInstance() {

        InboxFragment fragment = new InboxFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        // recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.inbox_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // users button
        usersButton = view.findViewById(R.id.users);
        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(3);
            }
        });


        HashMap<Integer, Conversation> conversations = ((GlobalSingleton) getActivity().getApplication()).getConversations();

        ArrayList<ConversationFragment> fragments = new ArrayList<ConversationFragment>();

        for(Integer key : conversations.keySet()) {
            ConversationFragment cf = new ConversationFragment();
            cf.otherUser =  ((GlobalSingleton) this.getActivity().getApplication()).getUser(key);
            cf.setPreview(conversations.get(key).preview);
            cf.rid = conversations.get(key).rid;
            fragments.add(cf);
        }

        // specify an adapter
        mAdapter = new InboxRecyclerViewAdapter(fragments);
        ((InboxRecyclerViewAdapter) mAdapter).setViewPager(viewPager);
        ((InboxRecyclerViewAdapter) mAdapter).setPagerAdapter(pagerAdapter);
        recyclerView.setAdapter(mAdapter);

        return view;
    }

}
