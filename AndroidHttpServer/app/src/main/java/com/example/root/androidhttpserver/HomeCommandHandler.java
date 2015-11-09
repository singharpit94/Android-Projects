package com.example.root.androidhttpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentProducer;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestHandler;

import android.content.Context;

public class HomeCommandHandler implements HttpRequestHandler {
    private Context context = null;

    public HomeCommandHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handle(HttpRequest request, HttpResponse response,
                       HttpContext httpContext) throws HttpException, IOException {
        HttpEntity entity = new EntityTemplate(new ContentProducer() {
            public void writeTo(final OutputStream outstream) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(outstream, "UTF-8");
                String resp = "<html><head></head><body><h1>Home<h1><p>This is the homepage.</p></body></html>";

                writer.write(resp);
                writer.flush();
            }
        });
        response.setHeader("Content-Type", "text/html");
        response.setEntity(entity);
    }

    public Context getContext() {
        return context;
    }
}
