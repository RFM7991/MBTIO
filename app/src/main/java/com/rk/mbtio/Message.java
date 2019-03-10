package com.rk.mbtio;

public class Message {

     public String message;
     public int rid;
     public int sid;
     public  int num;
     public boolean sent;

    public Message(int sid, int rid, int num, String text, boolean sent) {
        this.message = text;
        this.sid = sid;
        this.rid = rid;
        this.num = num;
        this.sent = sent;
    }
}
