package com.example.ramsl_lasaeed.realtimechatapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ramsl_lasaeed.realtimechatapp.utils.Const;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactsActivity extends AppCompatActivity {
    Context ctx = ContactsActivity.this;
    ListView lvcontacts;
    Button btlogout;
    public static User user;
    DatabaseReference dbreference;
    FirebaseAuth firebaseAuth;
    adt_contacts adapter;
    ArrayList<User> userlist;

    @Override
    protected void onResume() {
        super.onResume();
        loadUserList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        updateUserStatus(false);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(ContactsActivity.this, "Please Use the SignOut Button on Top-Right to go back", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        dbreference = FirebaseDatabase.getInstance().getReference();
        btlogout = (Button) findViewById(R.id.btlogout);
        firebaseAuth = FirebaseAuth.getInstance();
        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("users").child(firebaseAuth.getCurrentUser().getUid()).child("online").setValue(false);
                firebaseAuth.signOut();
                startActivity(new Intent(ContactsActivity.this,ActSignIn.class));
            }
        });
        lvcontacts = (ListView) findViewById(R.id.lvcontacts);
        updateUserStatus(true);

    }

    private void updateUserStatus(boolean b) {

    }

    /**
     * Load list of users.
     */
    private void loadUserList() {
        final ProgressDialog dia = ProgressDialog.show(this,null,"Loading...");

        // Pull the users list once no sync required.
        dbreference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dia.dismiss();
                long size = dataSnapshot.getChildrenCount();
                if (size == 0) {
                    Toast.makeText(ContactsActivity.this, "No users found for chat!",Toast.LENGTH_SHORT).show();
                    return;
                }
                userlist = new ArrayList<User>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    Logger.getLogger(ContactsActivity.class.getName()).log(Level.ALL, user.getUsername());
                 try {
                     if (!user.getId().contentEquals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                         userlist.add(user);
                 }catch (Exception e){
                     Log.d("zxc","Exception: "+e);
                 }
                }
                adapter = new adt_contacts(userlist,ctx);
                lvcontacts.setAdapter(adapter);
                lvcontacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
                        startActivity(new Intent(ctx, ChatActivity.class).putExtra(Const.EXTRA_DATA, userlist.get(pos)));
                    }
                });
                adapter.updatelist(userlist);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}