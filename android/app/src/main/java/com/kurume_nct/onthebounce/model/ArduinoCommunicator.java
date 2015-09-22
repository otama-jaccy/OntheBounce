package com.kurume_nct.onthebounce.model;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.kurume_nct.onthebounce.utility.MessageCallback;
import com.physicaloid.lib.Physicaloid;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.callback.Callback;

/**
 * Created by minto on 2015/08/16.
 * arduinoとのシリアル送受信クラス
 */
public class ArduinoCommunicator extends Thread{
    UsbManager manager;
    UsbSerialDriver driver;
    MessageCallback callback;
    Context context;
    UsbDeviceConnection connection;
    public ArduinoCommunicator(UsbManager manager, MessageCallback messageCallback, Context context){
        this.manager = manager;
        this.callback = messageCallback;
        this.context = context;
    }

    private UsbSerialDriver getDriver(){
        List<UsbSerialDriver> drivers = UsbSerialProber.getDefaultProber().findAllDrivers(this.manager);
        if(drivers.isEmpty()){
            return null;
        }
        return drivers.get(0);
    }

    //permission取得を行ったらtrueを返す
    public boolean requestPermission(){
        UsbSerialDriver driver = this.getDriver();
        if(driver==null){
            return false;
        }

        Intent intent = new Intent("com.android.example.USB_PERMISSION");
        PendingIntent pending_intent =  PendingIntent.getBroadcast(this.context, 0, intent, 0);
        this.manager.requestPermission(this.driver.getDevice(), pending_intent);
        return true;
    }

    public void run(){
        if(this.driver == null) {
            this.driver = getDriver();
        }
        if(!this.manager.hasPermission(this.driver.getDevice())){
            Log.d("DEBUG", "requestPermission");
            requestPermission();
            return;
        }
        connection = manager.openDevice(driver.getDevice());
        UsbSerialPort port = driver.getPorts().get(0);

        try {
            port.open(connection);
        }catch (IOException e){
            Log.e("ERROR", e.toString());
            return;
        }catch (Exception e){
            Log.e("ERROR", e.toString());
        }
        Log.d("DEBUG", "start reading");

        //while(true){
            byte buffer[] = new byte[16];
            int num = 0;
            try {
                num = port.read(buffer, 1000);
            }catch (IOException e){
                Log.e("ERROR", e.toString());
                callback.comeMessage("ERROR");
                Log.d("DEBUG", "IOException:"+e.toString());
                //continue;
                return;
            }
            Log.d("DEBUG", new String(buffer, 0, num));
            callback.comeMessage(new String(buffer, 0, num));
        //}
    }
}