package com.kurume_nct.onthebounce.activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.SerialInputOutputManager;
import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.fragment.TitleAndValueFragment;
import com.kurume_nct.onthebounce.model.ArduinoCommunicator;
import com.kurume_nct.onthebounce.model.ServerConnection;
import com.kurume_nct.onthebounce.utility.Common;
import com.kurume_nct.onthebounce.utility.GameDataTag;
import com.kurume_nct.onthebounce.utility.GameEvent;
import com.kurume_nct.onthebounce.utility.MessageCallback;
import com.kurume_nct.onthebounce.utility.ServerRequestMaker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class GameActivity extends ActionBarActivity implements MessageCallback{
    TitleAndValueFragment round_fragment;
    TitleAndValueFragment hp_fragment;

    Common common;
    Button ready_button;

    final String GAME_ACTIVITY="GAME_ACTIVITY";
    ServerConnection server_connection = ServerConnection.getInstance();;

    ArduinoCommunicator ard;

    View.OnClickListener ready_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO:ready
            Log.d("DEBUG", "READY");
            String request = ServerRequestMaker.user_ready(common.session_id);
            server_connection.send(request);
        }
    };

    View.OnClickListener suicide_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dead();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        common = (Common)getApplication();

        //Fragment init
        FragmentManager fragment_manager = getSupportFragmentManager();
        round_fragment = (TitleAndValueFragment)fragment_manager.findFragmentById(R.id.round_fragment);
        hp_fragment = (TitleAndValueFragment)fragment_manager.findFragmentById(R.id.hp_fragment);
        round_fragment.set("ROUND", common.round+"");
        hp_fragment.set("HP", common.hit_point + "");

        ready_button = (Button)findViewById(R.id.ready_button);
        //View init
        ready_button.setOnClickListener(ready_listener);
        ready_button.setEnabled(false);

        findViewById(R.id.dead_button).setOnClickListener(suicide_listener);

        server_connection.addCallback(GAME_ACTIVITY, this);

        /*
        UsbManager usb_manager = (UsbManager)getSystemService(Context.USB_SERVICE);
        this.ard = new ArduinoCommunicator(usb_manager, this, this);
        ard.start();
        */
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

    @Override
    protected void onStop(){
        super.onStop();
        server_connection.removeCallback(GAME_ACTIVITY);
    }

    //call back method
    public void comeMessage(String message){
        Log.d("DEBUGG", "GameActivity message:"+message);
    }

    public void comeMessage(JSONObject json) {
        try{
            String event = json.getString("event");
            JSONObject data = json.getJSONObject("data");
            /*
            next_roundが自然数で、ゲーム続行
             */
            if(event.equals(GameEvent.GAME_STOP)){
                int next_round = data.getInt(GameDataTag.NEXT_ROUND);
                if(next_round>0){
                    setEnableReadyButton(true);
                }else{
                    Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                    startActivity(intent);
                }
            }
            if(event.equals(GameEvent.GAME_START)){
                gameStart();
            }
        }catch(JSONException e){

        }
    }

    Handler handler = new Handler();
    private void setEnableReadyButton(final boolean flg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                ready_button.setEnabled(flg);
            }
        });
    }

    private void gameStart(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                hp_fragment.setValue(common.hit_point+"");
                ready_button.setEnabled(false);
            }
        });
    }

    private void dead(){
        String request = ServerRequestMaker.user_dead(common.session_id);
        server_connection.send(request);
    }
}
