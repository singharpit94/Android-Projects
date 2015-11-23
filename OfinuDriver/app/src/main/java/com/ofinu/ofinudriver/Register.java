package com.ofinu.ofinudriver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity implements View.OnClickListener{

    Button bRegister2;
    EditText etDName, etDContact, etAutoNo, etPin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etDName = (EditText) findViewById(R.id.etDName);
        etDContact = (EditText) findViewById(R.id.etDContact);
        etAutoNo = (EditText) findViewById(R.id.etAutoNo);
        etPin = (EditText) findViewById(R.id.etPin);

        bRegister2 = (Button) findViewById(R.id.bRegister2);


        bRegister2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister2:

                String name = etDName.getText().toString();
                String contact = etDContact.getText().toString();
                String autono = etAutoNo.getText().toString();
                String pin = etPin.getText().toString();
                User user = new User(name, contact, autono, pin);

                registerUser(user);

                startActivity(new Intent(this, AcceptRide.class));

                break;


        }
    }
    private void registerUser(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallback(){
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(Register.this, MainActivity.class));
            }
        });

    }
}
