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


public class ResultActivity extends ActionBarActivity {
    TitleAndValueFragment hit_fragment;
    TitleAndValueFragment hitted_fragment;
    TitleAndValueFragment hit_rate_fragment;

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
}
