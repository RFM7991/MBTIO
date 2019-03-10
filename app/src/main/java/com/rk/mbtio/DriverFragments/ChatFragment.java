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
import com.rk.mbtio.JsonRequestTool;
import com.rk.mbtio.R;
import com.rk.mbtio.UserMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class ChatFragment extends Fragment {

    private Conversation mConversation;
    private ChatRecyclerViewAdapter mAdapter;
    private JsonRequestTool requestTool;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EditText edit_text;
    private Button sendButton;

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
        // api caller
        requestTool = new JsonRequestTool(getContext());
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
        ArrayList<UserMessage> messages = new ArrayList<UserMessage>();
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
                sendMessage(text);

                // add message to recyclerView
                mAdapter.addMessage(new UserMessage(text ,1));
            }
        });

        return view;
    }

    //  POST for send message
    public void sendMessage(String message) {
        JSONObject data = new JSONObject();

        try {
            data.put("sid", 0);
            data.put("rid", 42);
            data.put("num", 0);
            data.put("pin", 1);
            data.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestTool.JSONRequestObj("/messages/send", "POST", data, new JsonRequestTool.VolleyObjCallback() {
            @Override
            public void onSuccess(JSONObject results) {

                Log.d("JSON", results.toString());

            }
        });
    }

    // load current conversation with messages
    public ArrayList<UserMessage> loadMessages() {
        ArrayList<UserMessage> m = new ArrayList<UserMessage>();

        m.add(new UserMessage("Hey", 0));
        m.add(new UserMessage("Hi", 1));

        Random rand = new Random();
        for (int i=0; i < 15; i++) {
            m.add(new UserMessage("0" + rand.nextInt(), 0));
            m.add(new UserMessage("1" + rand.nextInt(),   1));
        }

        m.add(new UserMessage("fin", 0));
        m.add(new UserMessage("fin", 1));

        return m;
    }
}
