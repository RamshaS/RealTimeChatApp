package com.example.ramsl_lasaeed.realtimechatapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
   Context ctx = RegisterActivity.this;

    private EditText displayname;
    private EditText mUserEmailRegister;
    private EditText mUserPassWordRegister;
    private Button mRegisterButton;
    private Button   mCancelRegister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize
        displayname=(EditText)findViewById(R.id.displayname);
        mUserEmailRegister=(EditText)findViewById(R.id.userEmailRegister);
        mUserPassWordRegister=(EditText)findViewById(R.id.passWordRegister);
        mRegisterButton=(Button)findViewById(R.id.registerButton);
        mCancelRegister=(Button)findViewById(R.id.cancelRegisterButton);
        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        mRegisterButton.setOnClickListener(this);
        mCancelRegister.setOnClickListener(this);

    }
    private void registerUser() {
        final String email =mUserEmailRegister.getText().toString().trim();
        String password = mUserPassWordRegister.getText().toString().trim();
        final String name = displayname.getText().toString().trim();
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(ctx,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(ctx,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(ctx,"Please enter name",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("zxc","Task:"+task.isSuccessful());
                //checking if success
                if(task.isSuccessful()){
                    final ArrayList<String> defaultRoom = new ArrayList<String>();
                    defaultRoom.add("home");
                    // Update the user profile information
                    final FirebaseUser user = task.getResult().getUser();
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();
                    user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Logger.getLogger(RegisterActivity.class.getName()).log(Level.ALL, "User profile updated.");
                                // Construct the ChatUser
                                ContactsActivity.user = new User(user.getUid(),name, email,true,defaultRoom);

                                // Setup link to users database
                                FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).setValue(ContactsActivity.user);
                                Log.d("zxc","all users"+FirebaseDatabase.getInstance().getReference("users"));
                                startActivity(new Intent(RegisterActivity.this, ContactsActivity.class));
                                finish();
                            }
                        }
                    });
                    //display some message here
                    Toast.makeText(RegisterActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();

                }else{
                    //display some message here
                    Toast.makeText(RegisterActivity.this,"Registration Error",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.registerButton:
                //calling register method on click
                registerUser();
                break;
            case R.id.cancelRegisterButton:
                Intent intent = new Intent(ctx,LogInActivity.class);
                startActivity(intent);
                break;
        }

    }
}
