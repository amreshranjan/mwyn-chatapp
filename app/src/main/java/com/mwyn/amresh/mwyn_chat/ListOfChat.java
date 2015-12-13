package com.mwyn.amresh.mwyn_chat;

/**
 * Created by Amresh on 12/13/2015.
 */
public class ListOfChat {

    String message;
    String timeStamp;
    String senderNumber;
    String recieverNumber;

   /* public ListOfChat(String message, String timeStamp, String senderNumber, String recieverNumber) {
        this.message = message;
        this.timeStamp = timeStamp;
        this.senderNumber = senderNumber;
        this.recieverNumber = recieverNumber;
    }*/

    public String getMessage() {
        return message;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getRecieverNumber() {
        return recieverNumber;
    }

    public void setRecieverNumber(String recieverNumber) {
        this.recieverNumber = recieverNumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
