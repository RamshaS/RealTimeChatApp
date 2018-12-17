package com.example.ramsl_lasaeed.realtimechatapp;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by RamSl-la Saeed on 13-Dec-16.
 */
public class ChatMessage implements Serializable{

    private Date date;
    private String sender;
    private String receiver;
    private String msg;
    private int status = 1;

    public ChatMessage() {
    }

    public ChatMessage(String msg, Date date, String sender, String receiver) {
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
    }
    public boolean isSent(String currentuser)
    {
        return currentuser.contentEquals(sender);
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
