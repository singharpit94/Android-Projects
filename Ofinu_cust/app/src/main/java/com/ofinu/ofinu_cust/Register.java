package com.ofinu.ofinu_cust;

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
    EditText etName, etEmail, etContact, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etContact = (EditText) findViewById(R.id.etContact);
        etPassword = (EditText) findViewById(R.id.etPassword);

        bRegister2 = (Button) findViewById(R.id.bRegister2);


        bRegister2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister2:

                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String contact = etContact.getText().toString();
                String password = etPassword.getText().toString();
                User user = new User(name, email, contact, password);

                registerUser(user);

                startActivity(new Intent(this, MainActivity.class));

                break;


        }
    }
    private void registerUser(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallback(){
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

    }
}

