package com.example.arpit.servicejson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class College extends AppCompatActivity {

    ArrayList<String> limits = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        new PollTask().execute();


    }
    private class PollTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
            // do stuff!
            String title = null;


            try {
                // Create a URL for the desired page
                URL updateURL = new URL("http://172.16.41.132:8000/notif");

                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(updateURL.openStream()));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    total.append(line).append("\n");
                }
                title = total.toString();


                in.close();

            } catch (IOException e) {
                e.printStackTrace();
            }


            return title;

        }


        @Override
        protected void onPostExecute(String title) {
            JSONArray jarray=null;
            JSONObject g1=null;
            JSONObject f=null;
            String update=null;
            if (title == null) {

                limits.add("Unable to Fetch Updates");
                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();


            } else {


                try {
                    jarray = new JSONArray(title);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                for(int i=0;i<jarray.length();i++)
                {
                    try {
                        g1=jarray.getJSONObject(i);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        f=g1.getJSONObject("fields");
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        update=f.getString("text");
                    } catch (JSONException h) {
                        h.printStackTrace();
                    }

                    limits.add(update);
                }

               /* for(int j=0;j<;j++)
                {
                    Toast.makeText(getApplicationContext(),post[j],Toast.LENGTH_LONG).show();
                }*/
                ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, limits);

                ListView listView = (ListView) findViewById(R.id.mobile_list);
                listView.setAdapter(adapter);


            }
        }
    }




}
