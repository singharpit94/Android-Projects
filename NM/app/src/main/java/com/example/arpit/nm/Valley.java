package com.example.arpit.nm;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Valley extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private GoogleMap googleMap;
    DatabaseHandler db=new DatabaseHandler(this);
    List<Places> places;
    LatLng Position;
    LatLng Spos=null;
    Bundle bundle=null;
    ArrayList<LatLng> arraypoints=null;
    







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      /*  db.addPlaces(new Places(1, 22.997284, 72.599593, 20.0, "Rambaug", 10));
        db.addPlaces(new Places(2,22.996170,72.599584,30.0,"Maninagar",10));
        db.addPlaces(new Places(3,22.978258,72.600226,30.0,"Isanpur",10));*/
        //bundle = getIntent().getParcelableExtra("bundle");

      a.
       // if(bundle!=null)
         //   Position = bundle.getParcelable("position");
        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

       // Button b1=(Button)findViewById(R.id.button1);


        //Button b2=(Button)findViewById( R.id.button2);
        //b1.setOnClickListener(new OnClickListener() {
           // public void onClick(View arg0) {

                // Start NewActivity.class
               // if(Spos!=null) {
               //     Bundle args = new Bundle();
//
                 //   args.putParcelable("position", Spos);

                //    Intent myIntent = new Intent(Valley.this,
                          //  Booking.class);
                 //   myIntent.putExtra("bundle", args);
                  //  startActivity(myIntent);
                //}
               //else {

                //    Toast.makeText(getApplicationContext(),"SELECT",Toast.LENGTH_LONG);

              //  }



          //  }


       // });



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.action_settings:
                return true;
            case R.id.action_favourite:
            {Intent i= new Intent(this, GooglePlacesAutocompleteActivity.class);
                startActivity(i);
                finish();}

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                // Use default InfoWindow frame
                @Override
                public View getInfoWindow(Marker arg0) {
                    return null;
                }


                // Defines the contents of the InfoWindow
                @Override
                public View getInfoContents(Marker arg0)
                {

                    View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
                    TextView t= (TextView)v.findViewById(R.id.text);
                    TextView t1= (TextView)v.findViewById(R.id.text1);
                    TextView t2= (TextView)v.findViewById(R.id.text2);

                    t.setText("Place :Maningar\n");
                    t1.setText("Rate :20 Rs\n");
                    t2.setText("Available :40\n");


                    return v;

                }
            });


           /* else
            {
                places=db.getAllPlaces();
                for(Places p:places)
                {
                    String log = "Id: "+p.getId()+" ,Name: " + p.getName() + " ,Phone: " + p.getLat();
                    // Writing Contacts to
                    Log.d("Name: ", log);
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(p.getLat(), p.getLongl())).title(p.getName());
                    googleMap.addMarker(marker);
                }*/
            MarkerOptions marker = new MarkerOptions().position(new LatLng(22.996170,72.599584)).title("MANINAGAR");
            googleMap.addMarker(marker);
            if(Position!=null)
            { googleMap.moveCamera(CameraUpdateFactory.newLatLng(Position));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
            }
            // Adding and showing marker while touching the GoogleMap
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker m) {
                    Spos=m.getPosition();

                    m.showInfoWindow();
                    return true;


                }
            });
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }



}
