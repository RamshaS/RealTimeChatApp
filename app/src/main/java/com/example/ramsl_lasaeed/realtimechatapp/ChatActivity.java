package com.example.ramsl_lasaeed.realtimechatapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ramsl_lasaeed.realtimechatapp.utils.Const;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatActivity extends AppCompatActivity  implements View.OnClickListener{
    Context ctx = ChatActivity.this;
    NotificationManager manager;
    Notification notification;
    private ArrayList<ChatMessage> messagelist;
    private adt_chat adt;
    private EditText txt;
    private User buddy;
    private Date lastmsgdate;
    ListView lvchat;
    TextView tvbuddy, btbcktocontacts;
    Button btnsend;
    DatabaseReference ref;
    ChildEventListener listener;
    @TargetApi(Build.VERSION_CODES.KITKAT)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        btbcktocontacts = (TextView) findViewById(R.id.btbcktocontacts);
        lvchat = (ListView) findViewById(R.id.lvchat);
        btnsend = (Button) findViewById(R.id.btnSend);
        txt = (EditText) findViewById(R.id.txt);
        tvbuddy = (TextView) findViewById(R.id.tvbuddy);
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        messagelist = new ArrayList<ChatMessage>();
        adt = new adt_chat(messagelist,ctx, user.getUid());
        lvchat.setAdapter(adt);
        lvchat.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        lvchat.setStackFromBottom(true);

        txt.setInputType(InputType.TYPE_CLASS_TEXT
                | InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        buddy = (User) getIntent().getSerializableExtra(Const.EXTRA_DATA);
        tvbuddy.setText(buddy.getUsername());
        loadmessaeglistnew();
        btnsend.setOnClickListener(this);
        btbcktocontacts.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadmessagelist();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ref.removeEventListener(listener);
    }

    private void loadmessaeglistnew() {
        ref = FirebaseDatabase.getInstance().getReference("messages");
       listener = ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
               // Log.d("zxc","snapshot"+dataSnapshot.getValue(ChatMessage.class).getMsg());
                /*
                if(user!=null){
                        ChatMessage cmsg = dataSnapshot.getValue(ChatMessage.class);
                        if (cmsg.getReceiver().contentEquals(user.getUid())||cmsg.getSender().contentEquals(user.getUid())) {
                            {    messagelist.add(cmsg);
                                shownotification(cmsg.getMsg(), buddy.getUsername());
                            }
                            if (lastmsgdate == null||   lastmsgdate.before(cmsg.getDate())){
                                lastmsgdate = cmsg.getDate();
                            }
                           // adt.notifyDataSetChanged();
                            Toast.makeText(ctx,"Message: "+cmsg.getMsg(),Toast.LENGTH_SHORT).show();
                            Log.d("zxc","Message :"+cmsg.getMsg());
                        }

                }*/

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("zxc","snapshot"+dataSnapshot.getValue(ChatMessage.class).getMsg());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void loadmessagelist() {
        FirebaseDatabase.getInstance().getReference("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
             //   Log.d("zxc","Children Message??:"+dataSnapshot.child("-KZ1KFvxLn369JQvqd7G").child("msg"));
                if(user!=null){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        ChatMessage cmsg = ds.getValue(ChatMessage.class);
                        if ((cmsg.getReceiver().contentEquals(buddy.getId())&&cmsg.getSender().contentEquals(user.getUid()))||(cmsg.getSender().contentEquals(buddy.getId())&&cmsg.getReceiver().contentEquals(user.getUid()))){
                            messagelist.add(cmsg);
                            //shownotification(cmsg.getMsg(), buddy.getUsername());
                        }
                            if (lastmsgdate == null||   lastmsgdate.before(cmsg.getDate())){
                                lastmsgdate = cmsg.getDate();
                            }
                            adt.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void shownotification(String msg, String username) {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx,0,new Intent(ctx,ChatActivity.class),0);
        Notification.Builder builder = new Notification.Builder(ctx);
        builder.setAutoCancel(false);
        builder.setTicker("This is ticker text...");
        builder.setContentTitle(username);
        builder.setContentText(msg);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setNumber(100);
        builder.setVibrate(new long[]{1000,1000,1000});
        builder.build();

        notification = builder.getNotification();
        manager.notify(11, notification);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChatActivity.this,ContactsActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btbcktocontacts:
                startActivity(new Intent(ChatActivity.this,ContactsActivity.class));
                break;

            case R.id.btnSend:
                btnSendAction();
                break;
        }
    }

    private void btnSendAction() {
        if (txt.length() == 0){
            return;
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txt.getWindowToken(), 0);

        String msg = txt.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            final ChatMessage cmsg = new ChatMessage(msg, Calendar.getInstance().getTime(),user.getUid(),buddy.getId());
            cmsg.setStatus(0);
            messagelist.add(cmsg);
            final String key = FirebaseDatabase.getInstance().getReference("messages").push().getKey();
          //  Toast.makeText(ChatActivity.this, "KEY: "+key , Toast.LENGTH_SHORT).show();
            Log.d("zxc","Key: "+key);
            FirebaseDatabase.getInstance().getReference("messages").child(key).setValue(cmsg).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        messagelist.get(messagelist.indexOf(cmsg)).setStatus(1);
                    }else {
                        messagelist.get(messagelist.indexOf(cmsg)).setStatus(2);
                    }
                    FirebaseDatabase.getInstance().getReference("messages").child(key).setValue(messagelist.get(messagelist.indexOf(cmsg))).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            adt.notifyDataSetChanged();
                            //loadmessagelist();
                         //   Toast.makeText(ctx,"load message list called",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
        adt.notifyDataSetChanged();
       // Toast.makeText(ctx,"chat List updated",Toast.LENGTH_SHORT).show();
        txt.setText(null);
           }
}
