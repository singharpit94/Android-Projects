package com.example.arpit.ashish;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpit on 2/4/16.
 */
public class GETR extends Fragment {
    View view;
    Button b;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    ArrayList<User> arrayList;
    String s=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.getr, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();
        SendMessage s1=new SendMessage();
       s1.execute();
        //fun();
        adapter=new UserAdapter(arrayList,getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(null);

        return  view;
    }
    public class SendMessage extends AsyncTask<String, String, String> {

        private Dialog loadingDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... params) {
            InputStream is = null;


            String result =null;
            // Setting up the connection inside the try and catch block.
            try {
                //Setting up the default HttpClient
                HttpClient httpClient = new DefaultHttpClient();

                //Setting up the http post method and passing the url in case
                //of online database and the ip address in case of local database.
                //And the php files which serves as the link between the android app
                //and mysql database.
                HttpGet httpPost = new HttpGet("http://jsonplaceholder.typicode.com/posts");



                //Getting the response
                HttpResponse response = httpClient.execute(httpPost);

                //Setting up the entity
                HttpEntity entity = response.getEntity();


                //Setting up the content inside the input stream reader.
                //Lets define the input stream reader (defined above)
                is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = " ";
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                result = sb.toString();
                s=result;

            }
            catch (ClientProtocolException e) {
                Log.e("ClientProtocol", "Log_tag");
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("Log_tag", "IO Exception");
                e.printStackTrace();
            }



            return result;
        }



        @Override
        protected void onPostExecute(String result) {
            if(result==null)
            {
                Toast.makeText(getContext(), "DOne", Toast.LENGTH_LONG).show();

            }
            else{             s = result.trim();



                Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                parseData();

            }





        }
    }
    //This method will parse json data
    private void parseData() {
        JSONArray array=null;
        try
        {
            array=new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            User stationary= new User();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);

                //Adding data to the superhero object
                stationary.setBody(json.getString("body"));
                stationary.setTitle(json.getString("title"));
                stationary.setID(json.getInt("id"));
                stationary.setUserid(json.getInt("userId"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            arrayList.add(stationary);
        }

        //Notifying the adapter that data has been added or changed
        adapter.notifyDataSetChanged();
    }
    void fun()
    {
        User stationary= new User();
        //Adding data to the superhero object
        stationary.setBody("djfhdjd");
        stationary.setTitle("sdjshjshx");
        stationary.setID(1);
        stationary.setUserid(2);
        arrayList.add(stationary);



    }

}
