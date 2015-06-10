package com.example.root.broadcastreceivernewsms;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
//import com.example.root.broadcastreceivernewsms.BroadcastNewSms;
public  class IncomingSms extends BroadcastReceiver {


    public String message=" ";
    public String name="Unkonwn";

    public String add1=" ";
    public  Context c1;
    Toast mtoast;
    int i=1;
   BroadcastNewSms b=new BroadcastNewSms();






    public void onReceive(Context context, Intent intent) {

        // Retrieves a map of extended data from the intent.
        MyPhoneStateListener phoneListener=new MyPhoneStateListener();
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        final Bundle bundle = intent.getExtras();
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String contactName="Unknown";
                    message = currentMessage.getDisplayMessageBody();
                    ContentResolver cr = context.getContentResolver();
                    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
                    Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
                    if (cursor == null) {
                        contactName="Unknown";
                    }

                    if(cursor.moveToFirst()) {
                        contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                    }

                    if(cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    name=contactName;
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                    // Show Alert
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context,
                            "senderNum: " + senderNum + ", message: " + message+ " "+ name, duration);
                    toast.show();
                    add1=b.fun1();
                    message=message+"\n"+"from"+senderNum;
                    SendMessage sendMessageTask = new SendMessage();
                    sendMessageTask.execute();


                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);

        }
    }
    public class MyPhoneStateListener extends PhoneStateListener {
        public void onCallStateChanged(int state,String incomingNumber){
            switch(state){


                case TelephonyManager.CALL_STATE_RINGING:
                {    String contactName="Unknown";
                    add1=b.fun1();
                   c1=b.getAppContext();
                    ContentResolver cr = c1.getContentResolver();
                    Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(incomingNumber));
                    Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
                    if (cursor == null) {
                        contactName="Unknown";
                    }

                    if(cursor.moveToFirst()) {
                        contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
                    }

                    if(cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    name=contactName;

                    message="Phone from "+incomingNumber+"\n";

                  if( URLUtil.isValidUrl(add1))
                    { SendMessage sendMessageTask1 = new SendMessage();
                    sendMessageTask1.execute();}
                    else {
                      Toast.makeText(b.getAppContext(), "Empty", Toast.LENGTH_LONG).show();
                  }
                }
                    break;
            }
        }
    }
    public class SendMessage extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stu

            try {
                return postData(add1,message,name);
            } catch (IOException e) {
                return "Unable to connect";
            }


        }
      /*  protected void onPostExecute(String result) {

                mtoast.setText(result);
            mtoast.show();

        }*/




        public String postData(String myurl, String h,String name) throws IOException {


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


                OutputStreamWriter outPutStream = new OutputStreamWriter(httppost.getOutputStream());

                String postData = h;
                outPutStream.write(name+"\n");
                outPutStream.write(postData);
                outPutStream.flush();
                outPutStream.close();

///Handling the response...
//Here I am expecting String data in response....it could be anything...so you have to handle accordingly..
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
