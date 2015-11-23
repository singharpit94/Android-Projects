package com.ofinu.ofinu_cust;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button bLogin, bRegister;
    EditText etEmail, etPassword;

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bRegister = (Button) findViewById(R.id.bRegister);

        bLogin.setOnClickListener(this);
        bRegister.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);

    }

    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogin:
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                User user = new User(email, password);
                authenticate(user);


                break;

            case R.id.bRegister:

                startActivity(new Intent(this, Register.class));

                break;
        }
    }

    private void authenticate(User user){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchUserDataInBackground(user, new GetUserCallback(){
            @Override
            public void done(User returnedUser) {
                if (returnedUser == null){
                    showErrorMessage();
                }else{
                    logUserIn(returnedUser);
                }

            }
        });

    }

    private void showErrorMessage() {

        AlertDialog.Builder dialogueBuilder = new AlertDialog.Builder(Login.this);
        dialogueBuilder.setMessage("Incorrect Details");
        dialogueBuilder.setPositiveButton("ok", null);
        dialogueBuilder.show();

    }

    private void logUserIn(User returnedUser) {

        userLocalStore.storeUserData(returnedUser);
        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));

    }

}
