package com.ofinu.ofinu_cust;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class DashBoard extends AppCompatActivity implements View.OnClickListener{

    Button bLogout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        bLogout2 = (Button) findViewById(R.id.bLogout2);

        bLogout2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bLogout2:
                startActivity(new Intent(this, Login.class));

                break;
        }
    }
}
