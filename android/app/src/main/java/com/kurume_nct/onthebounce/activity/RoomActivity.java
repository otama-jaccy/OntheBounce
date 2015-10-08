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
import android.widget.Toast;

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
    CounterFragment user_counter_fragment;
    Common common;
    ServerConnection server_connection;
    final String ROOM_ACTIVITY = "ROOM_ACTIVITY";

    //create room botton's listener
    View.OnClickListener create_room_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO:部屋の作成依頼
            Log.d("DEBUG", "Create Room");
            if(common.session_id==null){
                return;
            }

            common.round = round_counter_fragment.getCount();
            common.user_count = user_counter_fragment.getCount();
            common.hit_point = hp_counter_fragment.getCount();
            String request = ServerRequestMaker.createRoom(common.session_id ,common.round, common.user_count, common.hit_point);
            server_connection.send(request);
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
        this.user_counter_fragment = (CounterFragment)manager.findFragmentById(R.id.user_counter);
        this.user_counter_fragment.setValue("USER", 2);

        //View init
        findViewById(R.id.create_room_button).setOnClickListener(create_room_listener);

        //global init
        common = (Common)getApplication();
        common.init();

        server_connection = ServerConnection.getInstance();
        server_connection.addCallback(ROOM_ACTIVITY, this);
        server_connection.connect(Setting.readIPAddress(this), 8080);
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
    public void comeMessage(String message){
        if(message=="connected"){
            String request = ServerRequestMaker.sessionID();
            server_connection.send(request);
        }
    }

    public void comeMessage(JSONObject json) {
        Log.d("DEBUG", "come message");
        try {
            String event = json.getString("event");
            Log.d("DEBUG", "event="+event);
            if(event.equals("session_id")){
                String session_id = json.getJSONObject("data").getString("session_id");
                Log.d("DEBUG", "session_id="+session_id);
                common.session_id = session_id;
            }else if(event.equals("create_room")){
                String room_id = json.getJSONObject("data").getString("room_id");
                Log.d("DEBUG", "room_id="+room_id);
                common.room_id = room_id;

                //TODO: WIFI DIRECT
                String request = ServerRequestMaker.setting_room(common.session_id, common.room_id, common.round, common.hit_point, common.user_count);
                server_connection.send(request);
            }else if(event.equals("setting_room")){
                String request = ServerRequestMaker.join_room(common.session_id, common.room_id);
                server_connection.send(request);
            }else{
                Log.d("DEBUG", "nothing event:"+event);
            }
        }catch (JSONException e){
            Log.d("DEBUG", e.toString());
        }
    }
}
