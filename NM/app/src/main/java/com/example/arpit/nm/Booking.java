package com.example.arpit.nm;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class Booking extends AppCompatActivity {



    LatLng Position;

    Bundle bundle=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bundle = getIntent().getParcelableExtra("bundle");

        TextView t=(TextView)findViewById(R.id.text3);
        if(bundle!=null)
            Position = bundle.getParcelable("position");
            String POS=Position.toString();

       // Toast.makeText(getApplicationContext(),POS,Toast.LENGTH_LONG);
            t.setText(POS+"\n"+"Maninager\n"+"Available 40\n");

    }

}
