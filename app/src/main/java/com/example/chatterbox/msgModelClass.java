package com.example.chatterbox;

public class msgModelClass {
    String message;
    String sendrid;
    long timeStamp;

    public msgModelClass() {
    }

    public msgModelClass(String message, String sendrid, long timeStamp) {
        this.message = message;
        this.sendrid = sendrid;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSendrid() {
        return sendrid;
    }

    public void setSendrid(String sendrid) {
        this.sendrid = sendrid;
    }
}
