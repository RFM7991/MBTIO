package com.rk.mbtio;

public class UserMessage {

    private String messageText;
    private int direction;

    public UserMessage(String text) {
        messageText = text;
    }

    public UserMessage(String text, int dir) {
        messageText = text;
        direction = dir;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
