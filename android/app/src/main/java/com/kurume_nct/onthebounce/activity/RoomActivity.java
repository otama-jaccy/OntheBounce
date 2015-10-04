package com.kurume_nct.onthebounce.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.fragment.CounterFragment;
import com.kurume_nct.onthebounce.model.ServerConnection;
import com.kurume_nct.onthebounce.utility.Common;
import com.kurume_nct.onthebounce.utility.MessageCallback;
import com.kurume_nct.onthebounce.utility.ServerRequestMaker;
import com.kurume_nct.onthebounce.utility.Setting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RoomActivity extends ActionBarActivity implements MessageCallback{
    CounterFragment round_counter_fragment;
    CounterFragment hp_counter_fragment;
    Common common;
    ServerConnection server_connection;
    final String ROOM_ACTIVITY = "ROOM_ACTIVITY";

    //create room botton's listener
    View.OnClickListener create_room_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO:部屋の作成依頼
            Log.d("DEBUG", "Create Room");

            if(common.session_id==1){
                return;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        //Fragment init
        FragmentManager manager = getSupportFragmentManager();
        this.round_counter_fragment = (CounterFragment)manager.findFragmentById(R.id.round_counter);
        this.round_counter_fragment.setValue("ROUND", 5);
        this.hp_counter_fragment = (CounterFragment)manager.findFragmentById(R.id.hp_counter);
        this.hp_counter_fragment.setValue("HP", 10);

        //View init
        findViewById(R.id.create_room_button).setOnClickListener(create_room_listener);

        //global init
        common = (Common)getApplication();
        common.init();

        server_connection = ServerConnection.getInstance();
        server_connection.addCallback(ROOM_ACTIVITY, this);
        server_connection.connect(Setting.readIPAddress(this), 8000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_room, menu);
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

    protected void onDestroy() {
        super.onDestroy();
        server_connection.removeCallback(ROOM_ACTIVITY);
    }

    //call back method
    public void comeMessage(String json_text){
        //TODO:convert json text to java object
        Log.d("DEBUG", "convert json text");
        //TODO:manage activity with json data
        Log.d("DEBUG", "manage activity");
        Intent intent = new Intent(RoomActivity.this, GameActivity.class);
        startActivity(intent);
    }

    public void comeMessage(JSONObject json){
        Log.d("DEBUG", "come message");
        try {
            String event = json.getString("event");
            if(event=="session_id"){
                common.session_id = json.getJSONObject("data").getInt("session_id");
            }else if(event=="create_room"){
                common.room_id = json.getJSONObject("data").getInt("room_id");
            }
        }catch (JSONException e){
            Log.d("DEBUG", e.toString());
        }
    }
}
