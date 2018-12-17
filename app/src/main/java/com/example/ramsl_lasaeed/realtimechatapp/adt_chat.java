package com.example.ramsl_lasaeed.realtimechatapp;

import android.content.Context;
import android.text.format.DateUtils;
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
public class adt_chat extends BaseAdapter {
    LayoutInflater inflater = null;
    ArrayList<ChatMessage> messagelist;
    Context ctx;
    String uid;

    public adt_chat(ArrayList<ChatMessage> messagelist, Context ctx, String uid) {
        this.messagelist = messagelist;
        this.uid =uid;
        this.ctx = ctx;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messagelist.size();
    }
    @Override
    public Object getItem(int i) {
        return messagelist.get(i);
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        ChatMessage  message = (ChatMessage) getItem(i);

        if (message.isSent(uid)){
            view = inflater.inflate(R.layout.chat_single_row_send,null);
        }else {
            view = inflater.inflate(R.layout.chat_single_row_rcv,null);
        }
        if (view!= null){
            holder = new ViewHolder();
            holder.lbl1 = (TextView) view.findViewById(R.id.lbl1);
            holder.lbl2 = (TextView) view.findViewById(R.id.lbl2);
            holder.lbl3 = (TextView) view.findViewById(R.id.lbl3);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        try{
            holder.lbl1.setText(DateUtils.getRelativeDateTimeString(ctx, message.getDate().getTime(), DateUtils.SECOND_IN_MILLIS,
                    DateUtils.DAY_IN_MILLIS, 0));
            holder.lbl2.setText(message.getMsg());
            if (message.isSent(uid)){
                if (message.getStatus() == 1){
                    holder.lbl3.setText("Delivered");
                }else {
                    if (message.getStatus()== 0){
                        holder.lbl3.setText("Sending");
                    }else{
                        holder.lbl3.setText("Failed");
                    }
                }
            }else {
                holder.lbl3.setText("");
            }
        }catch (Exception e){
            Toast.makeText(ctx,"Exception:"+e,Toast.LENGTH_SHORT).show();
            Log.d("zxc","Exception: "+e);
        }

        return view;
    }
    static class ViewHolder{
        protected TextView lbl1, lbl2, lbl3;
    }
}
