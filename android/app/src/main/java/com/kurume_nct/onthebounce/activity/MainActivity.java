package com.kurume_nct.onthebounce.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.api.UDPPost;
import com.kurume_nct.onthebounce.model.ArduinoCommunicator;
import com.kurume_nct.onthebounce.model.ServerConnection;
import com.kurume_nct.onthebounce.model.ServerPost;
import com.kurume_nct.onthebounce.utility.MessageCallback;

import org.json.JSONArray;

public class MainActivity extends ActionBarActivity implements MessageCallback{
    public void comeMessage(String message){
        TextView t = (TextView)findViewById(R.id.debug);
        t.setText(message);
        Log.d("DEBUG", message);
    }

    public void comeMessage(JSONArray json){

    }

    ArduinoCommunicator ard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ard = new ArduinoCommunicator((UsbManager) getSystemService(Context.USB_SERVICE), this, this);
        //Button button = (Button)findViewById(R.id.create_room_button);
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ard.start();
            }
        });*/
        Button game_button = (Button)findViewById(R.id.create_room_button);
        game_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                startActivity(intent);
            }
        });

        Button join_button = (Button)findViewById(R.id.join_room_button);
        join_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                /*ServerPost post = ServerPost.getInstance();
                post.setUDPPost("191.233.35.210", 8080);
                post.addMessage("hogehogehogehoge");
                post.send();*/
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });

        Button setting_button = (Button)findViewById(R.id.edit_setting_button);
        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
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
