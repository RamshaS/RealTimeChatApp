package com.example.ramsl_lasaeed.realtimechatapp;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by RamSl-la Saeed on 13-Dec-16.
 */
public class User implements Serializable {
    public String id;
    public String username;
    public String email;
    public Boolean online;
    public ArrayList<String> room;
    public User() {
    }

    public User(String id, String username, String email, Boolean online, ArrayList<String> room) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.online = online;
        this.room = room;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getOnline() {
        return online;
    }

    public void setOnline(Boolean online) {
        this.online = online;
    }

    public ArrayList<String> getRoom() {
        return room;
    }

    public void setRoom(ArrayList<String> room) {
        this.room = room;
    }
}
