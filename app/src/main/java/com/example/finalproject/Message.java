package com.example.finalproject;

import java.util.Date;

public class Message {
    private String userName, userMessage;
    private long messageTime;

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Message() {
    }

    public Message(String userName, String userMessage) {
        this.userName = userName;
        this.userMessage = userMessage;
        this.messageTime = new Date().getTime();
    }
}
