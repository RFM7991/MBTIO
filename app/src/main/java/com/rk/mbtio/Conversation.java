package com.rk.mbtio;

import java.util.ArrayList;

public class Conversation {

   private ArrayList<Message> messages;
   public String preview;

   public int sid;
   public int rid;

    public Conversation(int sid, int rid, ArrayList<Message> m) {
        this.sid = sid;
        this.rid = rid;

        messages = m;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message m) {
         messages.add(m);
         setPreview(m.message);
    }

    public void setPreview(String text) {
        preview = text;
    }
}
