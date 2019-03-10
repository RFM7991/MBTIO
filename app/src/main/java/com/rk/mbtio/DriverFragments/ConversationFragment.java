package com.rk.mbtio.DriverFragments;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rk.mbtio.Conversation;
import com.rk.mbtio.DriverActivity;
import com.rk.mbtio.GlobalSingleton;
import com.rk.mbtio.R;
import com.rk.mbtio.User;
import com.rk.mbtio.UserMessage;

import java.util.ArrayList;
import com.rk.mbtio.DriverActivity.SectionsPagerAdapter;

public class ConversationFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String preview;
    private String sender;
    private ConstraintLayout mLayout;

    private SectionsPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    private Conversation mConversation;


    public ConversationFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ConversationFragment newInstance() {
        ConversationFragment fragment = new ConversationFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        mConversation = new Conversation(getMesages());
        pagerAdapter = ((GlobalSingleton) this.getActivity().getApplication()).getPagerAdapter();
        viewPager = ((GlobalSingleton) this.getActivity().getApplication()).getViewPager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_conversation, container, false);

        mLayout = view.findViewById(R.id.innerLayout);

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadChat();
            }
        });
        return view;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String s) {
        preview = s;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    // to do associate with
    public ArrayList<UserMessage> getMesages() {
        return null;
    }



    public void loadChat() {
        Log.d("RFM", "pressConversation");
        ChatFragment chat = new ChatFragment();
   //     chat.setMessages();
        pagerAdapter.addFragment(new ChatFragment());
        pagerAdapter.notifyDataSetChanged();
        viewPager.setCurrentItem(pagerAdapter.getCount() -1);
    }

    public Conversation getmConversation() {
        return mConversation;
    }

    public void setmConversation(Conversation mConversation) {
        this.mConversation = mConversation;
    }
}
