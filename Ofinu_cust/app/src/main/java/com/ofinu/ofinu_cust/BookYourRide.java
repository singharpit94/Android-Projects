package com.ofinu.ofinu_cust;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class BookYourRide extends AppCompatActivity implements View.OnClickListener{

    Button bRwP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_your_ride);
        bRwP= (Button) findViewById(R.id.bRwP);


        bRwP.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRwP:

                startActivity(new Intent(this, Login.class));
                break;
            }
        }

    }
