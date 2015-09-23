package com.kurume_nct.onthebounce.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.utility.DialogCreater;
import com.kurume_nct.onthebounce.fragment.ConnectGun;



public class SettingActivity extends ActionBarActivity {
    final String IP_ADDRESS = "IP_ADDRESS";
    final String SETTING = "SETTING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Button save_ip_button = (Button)findViewById(R.id.save_ip_button);
        save_ip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edit_text = (EditText)findViewById(R.id.ip_edit_text);
                saveIPAddress(edit_text.getText().toString());
            }
        });
        setForm();
    }

    private void setForm(){
        EditText editText = (EditText)findViewById(R.id.ip_edit_text);
        editText.setText(readSetting(IP_ADDRESS));
    }

    private void saveIPAddress(String text){
        String[] splited_text = text.split("\\.");
        if(splited_text.length!=4){
            AlertDialog.Builder dialog = DialogCreater.createOnlyMessage("*.*.*.*", this);
            dialog.show();
            Log.d("DEBUG", splited_text.length + "");
            Log.d("DEBUG", text);
            return;
        }
        for(int i=0;i<splited_text.length;i++){
            if(Integer.parseInt(splited_text[i])>255){
                AlertDialog.Builder dialog = DialogCreater.createOnlyMessage("over", this);
                dialog.show();
                return;
            }
        }
        saveSetting(IP_ADDRESS, text);
    }

    private void saveSetting(String key, String value){
        SharedPreferences shared = getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String readSetting(String key){
        SharedPreferences data = getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        return  data.getString(key, "191.233.35.210");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
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
