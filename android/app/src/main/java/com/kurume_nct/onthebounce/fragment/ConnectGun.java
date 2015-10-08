package com.kurume_nct.onthebounce.fragment;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.model.ArduinoCommunicator;
import com.kurume_nct.onthebounce.utility.MessageCallback;

import org.json.JSONObject;
import org.shokai.firmata.ArduinoFirmata;

public class ConnectGun extends Fragment implements MessageCallback{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    ArduinoCommunicator communicator = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UsbManager manager = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);
        communicator = new ArduinoCommunicator(manager, this, getActivity());
        return inflater.inflate(R.layout.fragment_connect_gun, null);
    }

    @Override
    public void onStart(){
        super.onStart();
        Button button = (Button)getActivity().findViewById(R.id.connect_gun_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO　銃との接続処理
                Log.d("DEBUG", "TODO connect gun");
                if(!communicator.requestPermission() && !communicator.isAlive()){
                    communicator.start();
                }
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public void comeMessage(String message){
        Log.d("DEBUG", message);
    }

    public void comeMessage(JSONObject json){

    }
}
