package com.kurume_nct.onthebounce.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.hardware.usb.*;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.model.ServerConnection;

import org.shokai.firmata.ArduinoFirmata;

import java.io.IOException;

public class TestActivity extends AppCompatActivity implements Runnable{

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("DEBUG", "Onclick");
            ServerConnection connection = ServerConnection.getInstance();
            connection.connect("192.168.1.2", 8000);
        }
    };

    View.OnClickListener sendListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("DEBUG", "send");
            ServerConnection connection = ServerConnection.getInstance();
            connection.send("hogehogehoge");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.connect).setOnClickListener(clickListener);
        findViewById(R.id.send).setOnClickListener(sendListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
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

    public void run(){
    }
}
