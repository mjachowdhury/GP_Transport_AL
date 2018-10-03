package com.example.mohammed.transportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //calling button
    private Button mVehicle, mCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating find view by id
        mVehicle = (Button) findViewById(R.id.vehicle);
        mCustomer = (Button) findViewById(R.id.customer);

        //now creating onClick listner

        mVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating Intent this will take to the login and registration page
                Intent intent = new Intent(MainActivity.this, VehicleLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });

    }
}
