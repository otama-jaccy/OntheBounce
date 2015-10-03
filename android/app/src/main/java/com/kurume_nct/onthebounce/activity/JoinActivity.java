package com.kurume_nct.onthebounce.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.utility.MessageCallback;

import org.json.JSONArray;

public class JoinActivity extends ActionBarActivity implements MessageCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
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

    //call back method
    public void comeMessage(String json_text){
        //TODO:convert json text to java object
        Log.d("DEBUG", "convert json text");
        //TODO:manage activity with json data
        Log.d("DEBUG", "manage activity");
        Intent intent = new Intent(JoinActivity.this, GameActivity.class);
        startActivity(intent);
    }

    public void comeMessage(JSONArray json){

    }
}
