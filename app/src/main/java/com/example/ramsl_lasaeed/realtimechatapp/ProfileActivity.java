package com.example.ramsl_lasaeed.realtimechatapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    Context ctx = ProfileActivity.this;

    TextView connectionStatus,userEmail;
    Button btnLogOut,btnChatRoom;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //getting firebase instance location

        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null){
                    startActivity(new Intent(getApplicationContext(),ActSignIn.class));
                    finish();
                }
            }
        };

        //getting JSON top node reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Log.d("zxc","Database top node:"+databaseReference);

        connectionStatus = (TextView) findViewById(R.id.connectionStatus);
        userEmail = (TextView) findViewById(R.id.userEmail);

        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnChatRoom = (Button) findViewById(R.id.btnChatRoom);

        btnLogOut.setOnClickListener(this);
        btnChatRoom.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogOut:
                firebaseAuth.signOut();
                break;
            case R.id.btnChatRoom:
                startActivity(new Intent(ctx,ContactsActivity.class));
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(listener!=null){
            firebaseAuth.removeAuthStateListener(listener);
        }
    }

}
