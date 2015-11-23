package com.ofinu.ofinudriver;;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class AcceptRide  implements View.OnClickListener{

    Button bAccept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_ride);

        bAccept = (Button) findViewById(R.id.bAccept);

        bAccept.setOnClickListener(this);
    }



    @Override
    public void onClick (View v) {
        switch (v.getId()) {
            case R.id.bAccept:

                startActivity(new Intent(this, StartRide.class));

                break;
        }
    }



}
