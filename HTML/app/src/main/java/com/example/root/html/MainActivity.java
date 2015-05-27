package com.example.root.html;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.networkconnection.R;

public class MainActivity extends Activity {

    private EditText urlField;
    private TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlField = (EditText)findViewById(R.id.editText1);
        data = (TextView)findViewById(R.id.textView2);
    }



    public void download(View view){

        String url = urlField.getText().toString();
        new DownloadWebPage(this,data).execute(url);
    }

}
