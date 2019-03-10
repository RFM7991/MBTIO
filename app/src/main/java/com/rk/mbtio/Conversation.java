package com.rk.mbtio;

import java.util.ArrayList;

public class Conversation {

   private ArrayList<UserMessage> messages;

    public Conversation(ArrayList<UserMessage> m) {
        messages = m;
    }

    public ArrayList<UserMessage> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<UserMessage> messages) {
        this.messages = messages;
    }

    public void addMessage(UserMessage m) {
         messages.add(m);
    }
    
}
