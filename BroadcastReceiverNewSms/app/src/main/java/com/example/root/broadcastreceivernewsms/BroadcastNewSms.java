package com.example.root.broadcastreceivernewsms;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;


public class BroadcastNewSms extends Activity implements View.OnClickListener {
    public static String message;
    public static String url;
    EditText et;
    Button b;
    Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_new_sms);
        et = (EditText) findViewById(R.id.editText1);
        b = (Button) findViewById(R.id.button1);
        b.setOnClickListener(this);
    }

    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (et.getText().toString().length() < 1) {

            // out of range
            Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
        } else {
            url = et.getText().toString();
            Sent d = new Sent();
            d.execute(url);
        }


    }

    public String fun1() {
        return url;


    }

    public class Sent extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stu

            try {
                return postData(params[0]);
            } catch (IOException e) {
                return "Unable to connect";
            }


        }

        protected void onPostExecute(String result) {

            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        }


        public String postData(String myurl)throws IOException{

            try {
                URL url = new URL(myurl);
                HttpURLConnection httppost = (HttpURLConnection) url.openConnection();
                httppost.setDoInput(true);
                httppost.setDoOutput(true);
                httppost.setRequestMethod("POST"); //set your method name ..it can be "Get" also
//httppost.setRequestProperty("User-Agent", "UserAgent");//set user agent if there is any..
//httppost.setInstanceFollowRedirects(true);//uncomment if required.
//httppost.setRequestProperty("Connection", "Keep-Alive");//uncomment if required.
                httppost.connect();
                String line = null;
                String result = " ";
                InputStream in = httppost.getInputStream();

                BufferedReader rd = new BufferedReader(new InputStreamReader(in));
                while ((line = rd.readLine()) != null) {
                    result += line;
                }
                //sms.sendTextMessage("9724289087",null,result,null,null);
                in.close();

                return result;


            } catch (IOException r) {
                return "Fail";
            }

        }


    }
}

