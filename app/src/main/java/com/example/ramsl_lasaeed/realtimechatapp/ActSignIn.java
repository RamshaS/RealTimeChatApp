package com.example.ramsl_lasaeed.realtimechatapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ActSignIn extends AppCompatActivity implements View.OnClickListener{
    Context ctx = ActSignIn.this;
    private EditText mUserEmail;
    private EditText mUserPassWord;
    private TextView mLoginToMChat;
    private ImageView btInBack;
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_sign_in);

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        // Initialize
        mUserEmail =(EditText)findViewById(R.id.userEmailChat);
        mUserPassWord =(EditText)findViewById(R.id.passWordChat);
        mLoginToMChat =(TextView)findViewById(R.id.btn_LogInChat);
        btInBack = (ImageView) findViewById(R.id.btInBack);

        mLoginToMChat.setOnClickListener(this);
        btInBack.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btInBack:
                startActivity(new Intent(ctx,MainActivity.class));
                break;
            case R.id.btn_LogInChat:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = mUserEmail.getText().toString().trim();
        String password  = mUserPassWord.getText().toString().trim();

        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }


        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Signing in Please Wait...");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            ArrayList<String> defaultRoom = new ArrayList<String>();
                            defaultRoom.add("home");
                            FirebaseUser fuser = task.getResult().getUser();
                            FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid()).child("online").setValue(true);
                            ContactsActivity.user = new User(task.getResult().getUser().getUid(),task.getResult().getUser().getDisplayName(), task.getResult().getUser().getEmail(),true,defaultRoom);
                            //start the profile activity
                            startActivity(new Intent(getApplicationContext(), ContactsActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(ctx, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
