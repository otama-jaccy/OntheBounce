package com.kurume_nct.onthebounce.api;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 * Created by minto on 2015/07/23.
 */
public class UDPReceiver {
    int port;
    DatagramSocket socket;

    //throw SocketException
    public UDPReceiver(int port) throws SocketException{
        this.port = port;
        socket = new DatagramSocket(port);
    }

    public String recieve(){
        byte[] buf = new byte[256];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        try {
            socket.receive(packet);
        }catch(IOException e){
            Log.d("ERROR", e.toString());
            return "error";
        }

        String message = new String(buf,0, buf.length);
        return message;
    }
}
