package com.kurume_nct.onthebounce.model;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by minto on 2015/08/15.
 */
public abstract class PostAbstract extends Thread{
    protected Queue<String> messages;

    protected PostAbstract(){
        messages = new ArrayDeque<>();
    }

    public void addMessage(String message){
        this.messages.add(message);
    }

    public void send(){
        if(!this.isAlive() && !this.messages.isEmpty()){
            this.start();
        }
    }
}
