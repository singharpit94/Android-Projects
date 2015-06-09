package com.example.root.androidhttpserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class WebServerService extends Service {

    private WebServer server = null;

    @Override
    public void onCreate() {
        Log.i("HTTPSERVICE", "Creating and starting httpService");
        super.onCreate();
        server = new WebServer(this);
        server.startServer();
    }

    @Override
    public void onDestroy() {
        Log.i("HTTPSERVICE", "Destroying httpService");
        server.stopServer();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
