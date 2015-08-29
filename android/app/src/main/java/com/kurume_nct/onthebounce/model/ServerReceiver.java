package com.kurume_nct.onthebounce.model;

import android.util.Log;

import com.kurume_nct.onthebounce.api.UDPReceiver;
import com.kurume_nct.onthebounce.utility.MessageCallback;

import java.util.HashMap;

import javax.security.auth.callback.Callback;

/**
 * Created by minto on 2015/07/24.
 */
public class ServerReceiver extends ReceiverAbstract implements Model{
    private UDPReceiver receiver;

    static private ServerReceiver server = new ServerReceiver();

    private ServerReceiver(){
        try {
            receiver = new UDPReceiver(114514);
            this.start();
        }catch(java.net.SocketException e){
            Log.d("ERROR", e.toString());
        }
    }

    public ServerReceiver getInstance(){
        return server;
    }

    /**
     * サーバーからデータを受信したら、
     * callBacksに登録されているクラスにメッセージを送る
     */
    public void run(){
        while(true){
            String message = receiver.recieve();
            for(String key: callbacks.keySet()){
                callbacks.get(key).comeMessage(message);
            }
        }
    }
}
