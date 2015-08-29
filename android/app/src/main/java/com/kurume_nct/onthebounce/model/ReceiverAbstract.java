package com.kurume_nct.onthebounce.model;

import com.kurume_nct.onthebounce.utility.MessageCallback;

import java.util.HashMap;

/**
 * Created by minto on 2015/08/15.
 */
public abstract class ReceiverAbstract extends Thread{
    protected HashMap<String, MessageCallback> callbacks;

    public void addCallBack(String key, MessageCallback call_back) {
        this.callbacks.put(key, call_back);
    }

    public void removeCallBack(String key){
        this.callbacks.remove(key);
    }
}
