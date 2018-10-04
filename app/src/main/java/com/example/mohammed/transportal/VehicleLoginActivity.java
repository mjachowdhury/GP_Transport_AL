package com.example.mohammed.transportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VehicleLoginActivity extends AppCompatActivity {

    //calling edit text and button
    private EditText mEmail, mPassword;
    private Button mLogin, mRegistration;

    private FirebaseAuth mAuth;
    //Creating Auth listner
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_login);

        //getting the instance of the firebase
        mAuth = FirebaseAuth.getInstance();

        //now creating onClick listener for Authentication
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //here going to check user status
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //user variable will store the current user status
                //if user not null then we can go to the next stage
                //in order to move forward we need to user loged in and also its listening user logout
                if( user != null ){
                    //creating Intent this will take to the login and registration page
                    Intent intent = new Intent(VehicleLoginActivity.this, VehicleMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };




        //creating find view by id
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        //creating find view by id
        mLogin = (Button) findViewById(R.id.login);
        mRegistration = (Button) findViewById(R.id.registration);


        /**
         * This part is doing registration part
         */
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(VehicleLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if sign is not successful then it will show a text message in the screen
                        if (!task.isSuccessful()){
                            Toast.makeText(VehicleLoginActivity.this,"Sign Up Error", Toast.LENGTH_SHORT).show();
                        }else{
                            //if its successful then we are getting the current user id
                            String user_id = mAuth.getCurrentUser().getUid();
                            //creating database reference
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Vehicles").child(user_id);
                            current_user_db.setValue(true);
                        }
                    }
                });
            }
        });

        /**
         * This part is about login with email and password
         */
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(VehicleLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(VehicleLoginActivity.this,"Sign In Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    // Starting and Stoping the firebase

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
