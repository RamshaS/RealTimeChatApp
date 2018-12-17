package com.example.ramsl_lasaeed.realtimechatapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by RamSl-la Saeed on 14-Dec-16.
 */
public class adt_contacts extends BaseAdapter {
    LayoutInflater inflater = null;
    ArrayList<User> userlist;
    Context ctx;

    public void updatelist(ArrayList<User> userlist){
        this.userlist = userlist;
    }
    public adt_contacts(ArrayList<User> userlist, Context ctx) {
        this.userlist = userlist;
        this.ctx = ctx;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return userlist.size();
    }

    @Override
    public Object getItem(int i) {
        return userlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
         holder = new ViewHolder();
            view = inflater.inflate(R.layout.contacts_single_row,null);
            holder.tvcontact = (TextView) view.findViewById(R.id.tvcontact);
            holder.tvroundstatus = (TextView) view.findViewById(R.id.tvroundstatus);
            holder.tvroundstatusoff = (TextView) view.findViewById(R.id.tvroundstatusoff);
            view.setTag(holder);
    Log.d("zxc","is this code running....");
        }
        else{
            holder = (ViewHolder) view.getTag();
        }
try{
    Log.d("zxc","Position:"+i+"   name:"+userlist.get(i).getUsername());
    holder.tvcontact.setText(userlist.get(i).getUsername());
    if (userlist.get(i).getOnline()){
        holder.tvroundstatus.setVisibility(View.VISIBLE);
        holder.tvroundstatusoff.setVisibility(View.INVISIBLE);
    }
    else{
        holder.tvroundstatusoff.setVisibility(View.VISIBLE);
        holder.tvroundstatus.setVisibility(View.INVISIBLE);
    }
}catch (NullPointerException e){
    Toast.makeText(ctx,"Exception: "+e,Toast.LENGTH_SHORT).show();
}
        return view;
    }
    static class ViewHolder{
       protected TextView tvcontact;
        protected TextView tvroundstatus;
        protected TextView tvroundstatusoff;
    }
}
