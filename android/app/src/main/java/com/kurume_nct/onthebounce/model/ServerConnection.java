package com.kurume_nct.onthebounce.model;

import android.util.Log;

import com.kurume_nct.onthebounce.utility.MessageCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by minto on 15/09/28.
 */
public class ServerConnection implements Runnable{
    Socket socket = null;

    Map<String, MessageCallback> callbacks = new HashMap<String, MessageCallback>();

    private String host_name;
    private int port;

    static private ServerConnection server_connection = new ServerConnection();

    static public ServerConnection getInstance(){return server_connection;}

    public void addCallback(String key, MessageCallback callback) {
        this.callbacks.put(key, callback);
    }

    public void removeCallback(String key){
        this.callbacks.remove(key);
    }
    /***
     * connect to server
     * @param host
     * @param port
     * @return
     */
    public void connect(String host, int port){
        this.host_name = host;
        this.port = port;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run(){
        Log.d("DEBUG", "run");
        try {
            this.socket = new Socket(host_name, port);
        }catch(IOException e){
            Log.d("DEBUG", e.toString());
            return;
        }
        callBackMessage("connected");
        Log.d("DEBUG", "buffer");
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }catch(IOException e){
            Log.d("ERROR", e.toString());
            return;
        }
        while(true){
            String message = null;
            try {
                Log.d("DEBUG", "message");
                message = buffer.readLine();
                Log.d("DEBUG", "come");
            }catch(IOException e){
                Log.d("DEBUG", e.toString());
                continue;
            }
            Log.d("DEBUG", message);
            JSONObject json = null;
            try {
                json = new JSONObject(message);
            }catch(JSONException e){
                Log.d("ERROR", e.toString());
                continue;
            }
            Log.d("DEBUG", json.toString());

            for(String key: this.callbacks.keySet()){
                MessageCallback callback = this.callbacks.get(key);
                callback.comeMessage(json);
            }
        }
    }

    public void send(String message){
        OutputStreamWriter writer;
        try {
            writer = new OutputStreamWriter(socket.getOutputStream());
            writer.write(message);
            writer.flush();
        }catch (IOException e){
            Log.d("DEBUG", e.toString());
            return;
        }
    }

    private void callBackMessage(String message){
        for(String key: this.callbacks.keySet()){
            MessageCallback callback = this.callbacks.get(key);
            callback.comeMessage(message);
        }
    }

    public void closeConnection(){
        try {
            socket.close();
        }catch(IOException e) {
            Log.d("ERROR", e.toString());
        }
        socket = null;
    }
}
