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
    public ArduinoCommunicator(UsbManager manager, MessageCallback messageCallback, Context context){
        if(manager==null){
            //return;
        }

        Log.d("DEBUG", ""+manager);
        this.manager = manager;
        this.callback = messageCallback;
        this.context = context;

        callback.comeMessage("hogehoge");

        List<UsbSerialDriver> drivers = UsbSerialProber.getDefaultProber().findAllDrivers(this.manager);
        if(drivers.isEmpty()){
            Log.d("DEBUG", "drivers is empty");
            return;
        }

        callback.comeMessage("start");
        this.driver = drivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        UsbSerialPort port = driver.getPorts().get(0);
        if(connection==null){
            manager.requestPermission(this.driver.getDevice(), PendingIntent.getBroadcast(this.context, 0, new Intent("com.android.example.USB_PERMISSION"), 0));
            callback.comeMessage(this.driver.getDevice().toString());
            connection = manager.openDevice(driver.getDevice());
        }
    }

    public void run(){
        callback.comeMessage("hogehoge");

        List<UsbSerialDriver> drivers = UsbSerialProber.getDefaultProber().findAllDrivers(this.manager);
        if(drivers.isEmpty()){
            Log.d("DEBUG", "drivers is empty");
            return;
        }

        callback.comeMessage("start");
        this.driver = drivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        UsbSerialPort port = driver.getPorts().get(0);
        if(connection==null){
            manager.requestPermission(this.driver.getDevice(), PendingIntent.getBroadcast(this.context, 0, new Intent("com.android.example.USB_PERMISSION"), 0));
            callback.comeMessage(this.driver.getDevice().toString());
            connection = manager.openDevice(driver.getDevice());
            return;
        }

        try {
            port.open(connection);
        }catch (IOException e){
            Log.e("ERROR", e.toString());
            return;
        }catch (Exception e){
            callback.comeMessage(e.toString());
        }

        callback.comeMessage("egaega");
        while(true){
            byte buffer[] = new byte[256];
            int num = 0;
            try {
                num = port.read(buffer, 1000);
            }catch (IOException e){
                Log.e("ERROR", e.toString());
                callback.comeMessage("ERROR");
                continue;
            }
            Log.d("DEBUG", new String(buffer, 0, num));
            callback.comeMessage(new String(buffer, 0, num));
        }


    }
}
