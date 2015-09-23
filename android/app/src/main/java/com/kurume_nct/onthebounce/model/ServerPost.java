package com.kurume_nct.onthebounce.model;

import com.kurume_nct.onthebounce.api.UDPPost;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by minto on 2015/07/19.
 */
public class ServerPost extends PostAbstract implements Model{
    static private ServerPost udp_server = new ServerPost();

    UDPPost post;

    public void setUDPPost(String ip, int port){
        post = new UDPPost(ip, port);
    }

    static public ServerPost getInstance(){
        return udp_server;
    }

    /**
     * send data to server.
     * if thread is running or messages is empty,
     * this method do nothing.
    */
    public void send(){
        if(!this.isAlive() && !this.messages.isEmpty()) {
            this.start();
        }
    }

    public void run(){
        while(!messages.isEmpty()) {
            String message = this.messages.poll();
            post.sendMessage(message);
        }
    }
}
