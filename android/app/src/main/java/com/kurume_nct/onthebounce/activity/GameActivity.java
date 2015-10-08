package com.kurume_nct.onthebounce.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;
import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.fragment.TitleAndValueFragment;
import com.kurume_nct.onthebounce.model.ArduinoCommunicator;
import com.kurume_nct.onthebounce.utility.MessageCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameActivity extends ActionBarActivity implements MessageCallback{
    TitleAndValueFragment round_fragment;
    TitleAndValueFragment hp_fragment;

    ArduinoCommunicator ard;

    View.OnClickListener ready_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO:ready
            Log.d("DEBUG", "READY");
            //DEBUG:
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                startActivity(intent);
            //END
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Fragment init
        FragmentManager fragment_manager = getSupportFragmentManager();
        round_fragment = (TitleAndValueFragment)fragment_manager.findFragmentById(R.id.round_fragment);
        hp_fragment = (TitleAndValueFragment)fragment_manager.findFragmentById(R.id.hp_fragment);
        round_fragment.set("ROUND", "10");
        hp_fragment.set("HP", "40");

        //View init
        findViewById(R.id.ready_button).setOnClickListener(ready_listener);

        UsbManager usb_manager = (UsbManager)getSystemService(Context.USB_SERVICE);
        this.ard = new ArduinoCommunicator(usb_manager, this, this);
        ard.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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


    //call back method
    public void comeMessage(String message){
        Log.d("DEBUGG", "GameActivity message:"+message);
    }

    public void comeMessage(JSONObject json) {
    }
}
