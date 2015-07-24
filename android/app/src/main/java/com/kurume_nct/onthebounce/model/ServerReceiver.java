package com.kurume_nct.onthebounce.model;

import com.kurume_nct.onthebounce.api.UDPReceiver;
import com.kurume_nct.onthebounce.utility.MessageCallback;

import java.util.HashMap;

import javax.security.auth.callback.Callback;

/**
 * Created by minto on 2015/07/24.
 */
public class ServerReceiver extends Thread implements Model{
    private HashMap<String, MessageCallback> callbacks;
    private UDPReceiver receiver;

    static private ServerReceiver server = new ServerReceiver();

    private ServerReceiver(){
        receiver = new UDPReceiver(114514);
        this.start();
    }

    public ServerReceiver getInstance(){
        return server;
    }

    public void addCallBack(String key, MessageCallback call_back) {
        this.callbacks.put(key, call_back);
    }

    public void removeCallBack(String key){
        this.callbacks.remove(key);
    }

    public void run(){
        while(true){
            String message = receiver.recieve();
            for(String key: callbacks.keySet()){
                callbacks.get(key).comeMessage(message);
            }
        }
    }
}
