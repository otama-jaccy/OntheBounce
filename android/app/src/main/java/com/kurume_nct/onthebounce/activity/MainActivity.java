package com.kurume_nct.onthebounce.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.model.ArduinoCommunicator;
import com.kurume_nct.onthebounce.utility.MessageCallback;

public class MainActivity extends ActionBarActivity implements MessageCallback{
    public void comeMessage(String message){
        TextView t = (TextView)findViewById(R.id.debug);
        t.setText(message);
        Log.d("DEBUG", message);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArduinoCommunicator ard = new ArduinoCommunicator(this);
        ard.addCallBack("main", this);

    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
