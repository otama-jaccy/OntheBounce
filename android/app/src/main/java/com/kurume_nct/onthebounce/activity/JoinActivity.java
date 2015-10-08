package com.kurume_nct.onthebounce.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.model.ServerConnection;
import com.kurume_nct.onthebounce.utility.Common;
import com.kurume_nct.onthebounce.utility.GameDataTag;
import com.kurume_nct.onthebounce.utility.GameEvent;
import com.kurume_nct.onthebounce.utility.MessageCallback;
import com.kurume_nct.onthebounce.utility.ServerRequestMaker;
import com.kurume_nct.onthebounce.utility.Setting;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JoinActivity extends ActionBarActivity implements MessageCallback{
    static final String JOIN_ACTIVITY = "join_activity";
    ServerConnection server_connection = ServerConnection.getInstance();
    Common common;

    View.OnClickListener connect_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String request = ServerRequestMaker.join_room(common.session_id, common.room_id);
            server_connection.send(request);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        findViewById(R.id.join_room_button).setOnClickListener(connect_listener);

        server_connection.connect(Setting.readIPAddress(this), 8080);
        server_connection.addCallback(JOIN_ACTIVITY, this);

        common = (Common)getApplication();
        common.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_join, menu);
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
        server_connection.removeCallback(JOIN_ACTIVITY);
    }

    //call back method
    public void comeMessage(String message){
        if(message.equals("connected")){
            String request = ServerRequestMaker.sessionID();
            server_connection.send(request);
        }
    }

    public void comeMessage(JSONObject json){
        try{
            String event = json.getString("event");
            JSONObject data = json.getJSONObject("data");

            if(event.equals(GameEvent.GAME_START)){
                Intent intent = new Intent(JoinActivity.this, GameActivity.class);
                startActivity(intent);
            }
            if(event.equals(GameEvent.SESSION_ID)){
                common.session_id = data.getString(GameDataTag.SESSION_ID);
            }
            if(event.equals(GameEvent.JOIN_ROOM)){
                common.round = data.getInt(GameDataTag.ROUND);
                common.hit_point = data.getInt(GameDataTag.HIT_POINT);
                common.user_count = data.getInt(GameDataTag.USER_COUNT);
                common.team_id = data.getInt(GameDataTag.TEAM_ID);
            }
        }catch(JSONException e){

        }
    }
}
