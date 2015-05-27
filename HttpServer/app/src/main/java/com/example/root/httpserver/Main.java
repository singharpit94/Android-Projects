package com.example.root.httpserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.NTUserPrincipal;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {

    //private static final String DEBUG_TAG = "HttpExample";

    private EditText value;
    private Button btn;
    private ProgressBar pb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        value = (EditText) findViewById(R.id.editText1);
        btn = (Button) findViewById(R.id.button1);
        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.GONE);
        btn.setOnClickListener(this);
    }


    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (value.getText().toString().length() < 1) {

            // out of range
            Toast.makeText(this, "please enter something", Toast.LENGTH_LONG).show();
        } else {
            pb.setVisibility(View.VISIBLE);
            new MyAsyncTask().execute(value.getText().toString());
        }


    }

    private class MyAsyncTask extends AsyncTask<String, Integer, String> {

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
            pb.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }

        protected void onProgressUpdate(Integer... progress) {
            pb.setProgress(progress[0]);
        }

        public String postData(String myurl) throws IOException {
            // Create a new HttpClient and Post Header
/*            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.143.77.224/try.php");

            try {
                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("Arpit", myurl));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();


                InputStream is = response.getEntity().getContent();
                BufferedInputStream bis = new BufferedInputStream(is);
                ByteArrayBuffer baf = new ByteArrayBuffer(20);

                int current = 0;

                while((current = bis.read()) != -1){
                    baf.append((byte)current);
                }

            /* Convert the Bytes read to a String.
                String text = new String(baf.toByteArray());
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
    }*/

            // InputStream is = null;
            // OutputStream os = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            //int len = 500;

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
                String postData = "Arpit I Have done it";
                outPutStream.write(postData);
                outPutStream.flush();
                outPutStream.close();
///Handling the response...
//Here I am expecting String data in response....it could be anything...so you have to handle accordingly..
                String line = null;
                String result=" ";
                InputStream in=httppost.getInputStream();

                BufferedReader rd = new BufferedReader(new InputStreamReader(in));
                while ((line = rd.readLine()) != null) {
                    result+=line;
                }
                    in.close();
                    return result;
            } catch (Exception e){
                return "Fail";
            }



       /* public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }*/
        }
    }
}




