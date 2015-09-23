package com.kurume_nct.onthebounce.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kurume_nct.onthebounce.R;

public class ConnectGun extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
            }
        });

    }

    @Override
    public void onPause(){
        super.onPause();
    }
}
