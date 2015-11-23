package com.ofinu.ofinu_cust;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button bRideAuto, bReserveRide, beCart, bRateCard, bLogout,bgetRide;
    EditText etName;
    UserLocalStore userLocalStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = (EditText) findViewById(R.id.etName);
        bRideAuto = (Button) findViewById(R.id.bRideAuto);
        bReserveRide = (Button) findViewById(R.id.bReserveRide);
        beCart = (Button) findViewById(R.id.beCart);
        bRateCard = (Button) findViewById(R.id.bRateCard);
        bLogout = (Button) findViewById(R.id.bLogout);
       // bgetRide=(Button)findViewById(R.id.bGetRide);
        bRideAuto.setOnClickListener(this);
        bReserveRide.setOnClickListener(this);
        beCart.setOnClickListener(this);
        bRateCard.setOnClickListener(this);
        bLogout.setOnClickListener(this);
       // bgetRide.setOnClickListener(this);
        userLocalStore = new UserLocalStore(this);


    }

    @Override
    protected void onStart(){
        super.onStart();
        if (authenticate() == true){
            displayUserDetails();
        }else {
            startActivity(new Intent(MainActivity.this, Login.class));
        }
    }

    private boolean authenticate(){
        return userLocalStore.getUserLoggedIn();
    }

    private void displayUserDetails(){
        User user = userLocalStore.getLoggedInUser();
        etName.setText(user.name);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bReserveRide:

                startActivity(new Intent(this, ReserveYourRide.class));
                break;

            case R.id.beCart:

                startActivity(new Intent(this, Ecart.class));
                break;

            case R.id.bRateCard:

                startActivity(new Intent(this, RateCard.class));
                break;


            case R.id.bLogout:

                userLocalStore.clearUserData();
                userLocalStore.setUserLoggedIn(false);

                startActivity(new Intent(this, Login.class));
                break;


            case R.id.bRideAuto:

            GPSTracker gpsTracker = new GPSTracker(getApplicationContext());

                //if (gpsTracker.canGetLocation()) {
            {  String latitude = gpsTracker.getLatitude();
                    String longitude = gpsTracker.getLongitude();
                    String contact=null;

                    User user = new User(latitude, longitude, contact);

                    registerLocation(user);
                    //Toast.makeText(getApplicationContext(), "Your loc. is -\nLat" + latitude, "\nLong" + longitude, Toast.LENGTH_LONG).show();
                }
               // else {
                 //   gpsTracker.showSettingsAlert();
               // }

                break;


        }
    }





public void registerLocation(User user){
        LocationRequests locationRequests = new LocationRequests(this);
        locationRequests.storeUserDataInBackground(user,new GetUserCallback(){

    @Override
    public void done(User returnedUser) {
        startActivity(new Intent(MainActivity.this, GetYourRide.class));
    }
        });
    }

}


