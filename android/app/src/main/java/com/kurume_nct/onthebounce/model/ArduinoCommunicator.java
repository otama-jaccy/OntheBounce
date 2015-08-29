package com.kurume_nct.onthebounce.model;

import android.content.Context;

import com.kurume_nct.onthebounce.utility.MessageCallback;
import com.physicaloid.lib.Physicaloid;

import java.util.HashMap;

import javax.security.auth.callback.Callback;

/**
 * Created by minto on 2015/08/16.
 */
public class ArduinoCommunicator extends Thread{
    private ArduinoCommunicator arduino_communicator = new ArduinoCommunicator();
    private HashMap<String, MessageCallback> callbacks;
    private boolean is_sending;
    private Context context;

    private ArduinoCommunicator(){
        is_sending = false;
        this.start();
    }

    public void setContext(Context context){
        this.context = context;
    }

    public ArduinoCommunicator getInstance(){
        return this.arduino_communicator;
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
            if (is_sending){
                continue;
            }

            Physicaloid physicaloid = new Physicaloid(this.context);
            if(physicaloid.open()) {
                byte[] buf = new byte[256];

                physicaloid.read(buf, buf.length);
                String message = new String(buf);
                physicaloid.close();
                for(String key: callbacks.keySet()){
                    callbacks.get(key).comeMessage(message);
                }
            }
        }
    }
}
