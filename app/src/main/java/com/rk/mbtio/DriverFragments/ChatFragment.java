package com.rk.mbtio.DriverFragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.rk.mbtio.ChatRecyclerViewAdapter;
import com.rk.mbtio.Conversation;
import com.rk.mbtio.GlobalSingleton;
import com.rk.mbtio.JsonRequestTool;
import com.rk.mbtio.R;
import com.rk.mbtio.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class ChatFragment extends Fragment {

    public Conversation mConversation;
    private ChatRecyclerViewAdapter mAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText edit_text;
    private Button sendButton;

    public ArrayList<Message>messages;
    public ChatFragment() {
        // required empty
    }

    public static ChatFragment newInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // work with args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        // get Recycler view in layout
        recyclerView = view.findViewById(R.id.chat_recycler_view);

        layoutManager = new LinearLayoutManager(this.getContext());
        //  ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        // load messages
        messages = loadMessages();

        // specify an adapter for recycler view
        mAdapter = new ChatRecyclerViewAdapter(messages);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);

        sendButton = view.findViewById(R.id.chatbox_send);
        edit_text = view.findViewById(R.id.edittext_chatbox);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = edit_text.getText().toString();
                edit_text.setText("");

                // Post Message
               Message m =  sendChat(text, messages.size());

                // add message to recyclerView
                mAdapter.addMessage(m);
            }
        });

        return view;
    }

    public Message sendChat(String text, int num) {
        int uid = (  (GlobalSingleton) getActivity().getApplication()).user.getUid();
        Message m = new Message(mConversation.sid, mConversation.rid, num, text, true);

        ((GlobalSingleton) getActivity().getApplication()).requestTool.sendMessage(m.sid, m.rid, m.num,
                ( (GlobalSingleton) getActivity().getApplication()).user.pin, m.message);
        return m;
    }


    // load current conversation with messages
    public ArrayList<Message> loadMessages() {
        ArrayList<Message> m = new ArrayList<Message>();

       /* m.add(new Message("Hey", 0));
        m.add(new Message("Hi", 1));

        Random rand = new Random();
        for (int i=0; i < 5; i++) {
            m.add(new Message("0" + rand.nextInt(), 0));
            m.add(new Message("1" + rand.nextInt(),   1));
        }

        m.add(new Message("fin", 0));
        m.add(new Message("fin", 1));
*/
        return m;
    }

}
