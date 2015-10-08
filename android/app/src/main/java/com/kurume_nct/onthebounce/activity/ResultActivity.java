package com.kurume_nct.onthebounce.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.fragment.TitleAndValueFragment;
import com.kurume_nct.onthebounce.model.ServerConnection;
import com.kurume_nct.onthebounce.utility.GameDataTag;
import com.kurume_nct.onthebounce.utility.GameEvent;
import com.kurume_nct.onthebounce.utility.MessageCallback;
import com.kurume_nct.onthebounce.utility.ServerRequestMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ResultActivity extends ActionBarActivity implements MessageCallback{
    TitleAndValueFragment hit_fragment;
    TitleAndValueFragment hitted_fragment;
    TitleAndValueFragment hit_rate_fragment;

    final String RESULT_ACTIVITY = "result_activity";
    ServerConnection server_connection = ServerConnection.getInstance();

    boolean is_win;
    int hit_count = 0;
    int hitted_count = 0;


    View.OnClickListener game_over_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO:Game over manage
            Log.d("DEBUG", "GAME OVER");

            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        FragmentManager manager = getSupportFragmentManager();

        //Fragment init
        hit_fragment = (TitleAndValueFragment)manager.findFragmentById(R.id.hit_fragment);
        hitted_fragment = (TitleAndValueFragment)manager.findFragmentById(R.id.hitted_fragment);
        hit_rate_fragment = (TitleAndValueFragment)manager.findFragmentById(R.id.hit_rate_fragment);

        //View init
        findViewById(R.id.game_over_button).setOnClickListener(game_over_listener);

        server_connection.addCallback(RESULT_ACTIVITY, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
    }

    public void comeMessage(JSONObject json) {
        Log.d("DEBUG", "come message");
        try {
            String event = json.getString("event");
            Log.d("DEBUG", "event="+event);
            JSONObject data = json.getJSONObject("data");
            if(event.equals(GameEvent.RESULT)){
                JSONArray array = data.getJSONArray(GameDataTag.ROUNDS);
                int win_count = 0;
                for(int i=0;i<array.length();i++){
                    JSONObject round_data = array.getJSONObject(i);
                    hit_count += round_data.getInt(GameDataTag.HIT_COUNT);
                    hitted_count += round_data.getInt(GameDataTag.HITTED_COUNT);
                    if(round_data.getBoolean(GameDataTag.WIN)){
                        win_count++;
                    }
                }
                int num = array.length();
                if(num%2==1){
                    //TODO: 勝敗
                }else{
                    if(win_count==num/2){
                        //TODO:　引き分け
                    }else{
                        //TODO: 勝敗
                    }
                }
            }
        }catch (JSONException e){
            Log.d("DEBUG", e.toString());
        }
    }
}
