package com.rk.mbtio.DriverFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rk.mbtio.Conversation;
import com.rk.mbtio.R;
import com.rk.mbtio.User;
import com.rk.mbtio.UserMessage;

import java.util.ArrayList;

public class ConversationFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String preview;
    private String sender;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_conversation, container, false);

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
}
