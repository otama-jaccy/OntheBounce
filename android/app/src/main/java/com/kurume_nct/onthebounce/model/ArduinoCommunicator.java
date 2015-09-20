package com.kurume_nct.onthebounce.model;

import android.content.Context;
import android.util.Log;

import com.kurume_nct.onthebounce.utility.MessageCallback;
import com.physicaloid.lib.Physicaloid;

import java.util.HashMap;

import javax.security.auth.callback.Callback;

/**
 * Created by minto on 2015/08/16.
 */
public class ArduinoCommunicator extends Thread{
    private HashMap<String, MessageCallback> callbacks = new HashMap<String, MessageCallback>();
    private boolean is_sending;
    private Context context;

    public ArduinoCommunicator(Context context){
        is_sending = false;
        this.context = context;
        this.start();
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void send(String message){
        is_sending = true;
        Physicaloid physicaloid = new Physicaloid(this.context);
        if(physicaloid.open()){
            byte[] buf = message.getBytes();
            physicaloid.write(buf, buf.length);
            physicaloid.close();
        }
        is_sending = false;
    }

    public void addCallBack(String key, MessageCallback callback){
        callbacks.put(key, callback);
    }

    public void run(){
        while(true) {
            if (is_sending) {
                continue;
            }


            Physicaloid physicaloid = new Physicaloid(this.context);
            physicaloid.write("hoge".getBytes(), "hoge".getBytes().length);
            if(physicaloid.open()) {
                callbacks.get("main").comeMessage("physicaloid");
                byte[] buf = new byte[256];

                physicaloid.read(buf, buf.length);
                String message = new String(buf);
                physicaloid.close();
                Log.d("DEBUG", message);
                for(String key: callbacks.keySet()){
                    callbacks.get(key).comeMessage(message);
                }
            }
        }
    }
}
