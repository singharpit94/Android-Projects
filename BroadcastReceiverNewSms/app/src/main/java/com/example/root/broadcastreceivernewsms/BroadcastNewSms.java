package com.example.root.broadcastreceivernewsms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.gsm.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class BroadcastNewSms extends Activity implements View.OnClickListener {
            public static final String EXTRA_MESSAGE ="Conncect";
            public static String message;
        public static String url;
        private static Context context;
            Map<String,String> contactlist=new HashMap<String,String>();
       String pa;
    String na;

        EditText welcomeMsg;
        TextView infoIp;
        TextView infoMsg;
        String msgLog = "";
        String phone="";
        String content="";
    
            
                ServerSocket httpServerSocket;
        EditText et;
        Button b;
        SmsManager sms = SmsManager.getDefault();
        PendingIntent sentPI;
        String SENT = "SMS_SENT";
    
            
            
                @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_broadcast_new_sms);
                et = (EditText) findViewById(R.id.editText1);
                b = (Button) findViewById(R.id.button1);
                b.setOnClickListener(this);
                BroadcastNewSms.context = getApplicationContext();
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
                    while (phones.moveToNext())
                    {
                         na=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        pa = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                       //Toast.makeText(getApplicationContext(), pa, Toast.LENGTH_LONG).show();
                        contactlist.put(na,pa);

                    }
                    phones.close();

                    HttpServerThread httpServerThread = new HttpServerThread();
                httpServerThread.start();
        
                    }
    
                public void onClick(View v) {
                // TODO Autogenerated method stub
                        if (et.getText().toString().length() < 1) {
            
                                // out of range
                                        Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
                    } else {
                        url = et.getText().toString();
                        Sent d = new Sent();
                        d.execute(url, "http://"+getIpAddress() + ":" + HttpServerThread.HttpServerPORT);
            
                            }
        
                
                            }
    
                public String fun1() {
                return url;
        
                
                            }
    
                public static Context getAppContext() {
                return BroadcastNewSms.context;
            }
    
                @Override
        protected void onDestroy() {
                super.onDestroy();
        
                        if (httpServerSocket != null) {
                        try {
                                httpServerSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
            }
    
                private String getIpAddress() {
                String ip = "";
                try {
                        Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                                        .getNetworkInterfaces();
                        while (enumNetworkInterfaces.hasMoreElements()) {
                                NetworkInterface networkInterface = enumNetworkInterfaces
                                                .nextElement();
                                Enumeration<InetAddress> enumInetAddress = networkInterface
                                                .getInetAddresses();
                                while (enumInetAddress.hasMoreElements()) {
                                        InetAddress inetAddress = enumInetAddress.nextElement();
                    
                                                if (inetAddress.isSiteLocalAddress()) {
                                                ip +=inetAddress.getHostAddress();
                                            }
                    
                                            }
                
                                    }
            
                            } catch (SocketException e) {
                        // TODO Autogenerated catch block
                                e.printStackTrace();
                        ip += "Something Wrong! " + e.toString() + "\n";
                    }
        
                        return ip;
            }
    
                private class HttpServerThread extends Thread {
        
                        static final int HttpServerPORT = 8888;
        
                        @Override
                public void run() {
                        Socket socket = null;
            
                                try {
                                httpServerSocket = new ServerSocket(HttpServerPORT);
                
                                        while (true) {
                                        socket = httpServerSocket.accept();
                    
                                                HttpResponseThread httpResponseThread =
                                                        new HttpResponseThread(
                                                                        socket,
                                                                        "");
                                        httpResponseThread.start();
                                    }
                            } catch (IOException e) {
                                // TODO Autogenerated catch block
                                        e.printStackTrace();
                            }
            
                            }
        
                
                    }
    
                private class HttpResponseThread extends Thread {
        
                        Socket socket;
                String h1;
        
                        HttpResponseThread(Socket socket, String msg) {
                        this.socket = socket;
                        h1 = msg;
                    }
        
                        @Override
                public void run() {
                        BufferedReader is;
                        PrintWriter os;
                        String request;
            
                    
                                        try {
                                /*StringBuilder answer = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    answer.append(line);
                }


                 request = answer.toString();
                reader.close();*/
                                        is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                request = is.readLine();
                                request += is.readLine();
                                request += is.readLine();
                                request += is.readLine();
                                request += is.readLine();
                                request += is.readLine();
                                request += is.readLine();
                                request += is.readLine();
                
                                        phone+= is.readLine();
                                String line="";
                                while (!(line = is.readLine()).equals("\u0004")) {


                                    content += line;
                                    content += "\n";
                                }
                                int len=phone.length();
                                phone=phone.substring(0, Math.min(phone.length(), len-1));
                                           // Toast.makeText(getAppContext(), phone, Toast.LENGTH_LONG).show();
                               // sms.sendTextMessage("9832821697", null,"Working", null, null);
                        
                                
                                                        //is.close();
                                                                //request+=is.readLine();
                                                                        // request+=is.readLine();
                                                                                os = new PrintWriter(socket.getOutputStream(), true);
                
                                        String response =
                                                "<html><head></head>" +
                                                                "<body>" +
                                                                "<h1>" + "Arpit" + "</h1>" +
                                                                "</body></html>";
                
                                        os.print("HTTP/1.0 200" + "\r\n");
                                os.print("Content type: text/html" + "\r\n");
                                os.print("Content length: " + response.length() + "\r\n");
                                os.print("\r\n");
                                os.print(response + "\r\n");
                                os.flush();
                                //os.close();
                                        socket.close();
                
                        
                                              //  msgLog += "Request of " + request
                                                             //   + " from " + socket.getInetAddress().toString() + "\n";
                                                               /* Toast.makeText(getAppContext(), phone+content, Toast.LENGTH_LONG).show(); */
                                                                        BroadcastNewSms.this.runOnUiThread(new Runnable() {
                                                            
                                                                                        @Override
                                                                                public void run() {
                                                                
                                                                                               // infoMsg.setText(msgLog + "\n" + phone + "\n" + content);
                                                                                                        sentPI = PendingIntent.getBroadcast(getAppContext(), 0, new Intent(SENT), 0);
                                                                                         try
                                                                                         { sms.sendTextMessage(phone, null, content, sentPI, null);}
                                                                                         catch(Exception g){
                                                                                                 Toast.makeText(getAppContext(), "Fail", Toast.LENGTH_LONG).show();
                                                                                             }
                                                                                        phone = "";
                                                                                        content = "";
                                                                
                                                                        
                                                                                                        // phone="";
                                                                                                                // content="";
                                                                                                                    }
                                                                            });
                
                                    } catch (IOException e) {
                                // TODO Autogenerated catch block
                                        e.printStackTrace();
                            }
            
                                return;
                    }
            }
    
                public class Sent extends AsyncTask<String, Integer, String> {
        
                        @Override
                protected String doInBackground(String... params) {
                        // TODO Autogenerated method stu
                                String i = params[0];
                        String j = params[1];
                        try {
                                return postData(i, j);
                            } catch (IOException e) {
                                return "Unable to connect";
                            }
            
                    
                                    }
                protected void onPostExecute(String result) {
                        Toast.makeText(getAppContext(), result, Toast.LENGTH_LONG).show();
            
                    
                            
                                            }
        
                    }
        public String postData(String myurl, String h) throws IOException {
        
                
                                try {
                        URL url = new URL(myurl);
                        HttpURLConnection httppost = (HttpURLConnection) url.openConnection();
                        httppost.setDoInput(true);
                        httppost.setDoOutput(true);
                        httppost.setRequestMethod("POST"); //set your method name ..it can be "Get" also
            //httppost.setRequestProperty("UserAgent", "UserAgent");//set user agent if there is any..
                    //httppost.setInstanceFollowRedirects(true);//uncomment if required.
                            //httppost.setRequestProperty("Connection", "KeepAlive");//uncomment if required.
                                                httppost.connect();
                                    ObjectOutputStream mapOutputStream = new ObjectOutputStream(httppost.getOutputStream());
                                    mapOutputStream.writeObject(contactlist);
                                    mapOutputStream.flush();

                    
                                        OutputStreamWriter outPutStream = new OutputStreamWriter(httppost.getOutputStream());
            
                                String postData = h;
                        outPutStream.write(postData);
                        outPutStream.flush();
                        outPutStream.close();
                                    mapOutputStream.close();
            
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
                        b.setClickable(false);
                        //b.setVisibility(View.INVISIBLE);
                                return "You are connected";
                    } catch (IOException r) {
                        return "Connection failed Try again";
                    }
        
                    }
    }

        
        
