package com.example.travelbot;

public class Message {
    private boolean isMine;
    private String content;

    public Message(String message, boolean mine) {
        content = message;
        isMine = mine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
    }

}
