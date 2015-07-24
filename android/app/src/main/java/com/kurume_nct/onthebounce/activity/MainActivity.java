package com.kurume_nct.onthebounce.activity;

import android.os.Debug;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.api.UDPPost;
import com.kurume_nct.onthebounce.api.UDPReceiver;

import java.net.SocketException;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        UDPReceiver receiver = new UDPReceiver(4000);
                        Log.d("DEBUG", "start recieve()");
                        Log.d("DEBUG", receiver.recieve());
                    } catch (SocketException e) {
                        Log.d("MYERROR", e.toString());
                    }
                }
            }
        }).start();

        Button b = (Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("DEBUG", "send message");
                        UDPPost udp = new UDPPost("10.0.2.15", 4000);
                        udp.sendMessage("hogehoge");
                    }
                }).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
