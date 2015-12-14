package com.example.arpit.servicejson;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class Notify extends Service {

     String title1=null;
    static int id=0;
    int cid=0;
    String s1;


    /**
     * Simply return null, since our Service will not be communicating with
     * any other components. It just does its work silently.
     *
     */

   /* SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    SharedPreferences.Editor editor=prefs.edit();*/

    public static final long NOTIFY_INTERVAL = 10 * 1000; // 10 seconds

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {
        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        // schedule task
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    new PollTask().execute();
                }

            });
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
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+5:30"));
                Date currentLocalTime = cal.getTime();
                DateFormat date = new SimpleDateFormat("HHmma");
// you can get seconds by adding  "...:ss" to it
                date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                String localTime = date.format(currentLocalTime);

                try {
                    // Create a URL for the desired page
                    URL updateURL = new URL("http://192.168.1.100:8000/notif");

                    // Read all the text returned by the server
                    BufferedReader in = new BufferedReader(new InputStreamReader(updateURL.openStream()));
                    StringBuilder total = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        total.append(line).append("\n");
                    }
                    title = total.toString();
                    title1 = title;

                    in.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return title;

            }

            /**
             * In here you should interpret whatever you fetched in doInBackground
             * and push any notifications you need to the status bar, using the
             * NotificationManager. I will not cover this here, go check the docs on
             * NotificationManager.
             * <p>
             * What you HAVE to do is call stopSelf() after you've pushed your
             * notification(s). This will:
             * 1) Kill the service so it doesn't waste precious resources
             * 2) Call onDestroy() which will release the wake lock, so the device
             * can go to sleep again and save precious battery.
             */
            @Override
            protected void onPostExecute(String title) {
                int mId = 420;
                JSONArray jarray=null;
                JSONObject g1=null;
                JSONObject f=null;
                String update=null;
                if (title == null) {
                    //stopSelf();
                    Toast.makeText(getApplicationContext(), "Arpit", Toast.LENGTH_SHORT).show();

                } else {

                   // s1=prefs.getString("current",null);
                    //id=Integer.parseInt(s1);

                    try {
                        jarray = new JSONArray(title);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for(int i=0;i<jarray.length();i++) {
                        try {
                            g1 = jarray.getJSONObject(i);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            f = g1.getJSONObject("fields");
                            cid=g1.getInt("pk");
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            update = f.getString("text");
                        } catch (JSONException h) {
                            h.printStackTrace();
                        }
                        break;

                    }
                     if(cid>id)


                     {
                         id=cid;
                         //s1=Integer.toString(id);
                       //  editor.putString("current",s1);
                       //  editor.commit();
                         Intent notificationIntent = new Intent(getApplicationContext(), Hoja.class);
                         PendingIntent contentIntent = PendingIntent.getActivity(Notify.this, 0, notificationIntent, 0);
                         Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


                         Notification noti = new NotificationCompat.Builder(getApplicationContext()).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                                 .setSmallIcon(R.mipmap.ic_launcher)
                                 .setTicker("New Notification ...")
                                 .setWhen(System.currentTimeMillis())
                                 .setContentTitle("Mukti")
                                 .setContentText(update)
                                 .setContentIntent(contentIntent)
                                 .setSound(uri)

                                         //At most three action buttons can be added
                                         //.addAction(android.R.drawable.ic_menu_camera, "Action 1", contentIntent)
                                         //.addAction(android.R.drawable.ic_menu_compass, "Action 2", contentIntent)
                                         //.addAction(android.R.drawable.ic_menu_info_details, "Action 3", contentIntent)
                                 .setAutoCancel(true).build();

                         //Show the notification
                         NotificationManager mNotificationManager =
                                 (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                         // mId allows you to update the notification later on.
                         mNotificationManager.notify(mId, noti);
                         // handle your data
                         // stopSelf();
                         //Toast.makeText(getApplicationContext(),title,Toast.LENGTH_LONG).show();
                     }
                }
            }
        }


    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;

}

    @Override
    public void onTaskRemoved(Intent rootIntent){
        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
        restartServiceIntent.setPackage(getPackageName());

        PendingIntent restartServicePendingIntent =  PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(
                AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + 1000,
                restartServicePendingIntent);

        super.onTaskRemoved(rootIntent);
    }

    }
