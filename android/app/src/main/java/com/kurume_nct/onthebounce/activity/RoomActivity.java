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
import com.kurume_nct.onthebounce.utility.MessageCallback;

import org.json.JSONArray;

public class RoomActivity extends ActionBarActivity implements MessageCallback{
    CounterFragment round_counter_fragment;
    CounterFragment hp_counter_fragment;

    //create room botton's listener
    View.OnClickListener create_room_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO:部屋の作成依頼
            Log.d("DEBUG", "Create Room");


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

    //call back method
    public void comeMessage(String json_text){
        //TODO:convert json text to java object
        Log.d("DEBUG", "convert json text");
        //TODO:manage activity with json data
        Log.d("DEBUG", "manage activity");
        Intent intent = new Intent(RoomActivity.this, GameActivity.class);
        startActivity(intent);
    }

    public void comeMessage(JSONArray json){

    }
}
