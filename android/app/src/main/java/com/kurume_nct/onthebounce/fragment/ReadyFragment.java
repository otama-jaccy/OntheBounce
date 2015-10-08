package com.kurume_nct.onthebounce.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kurume_nct.onthebounce.R;
import com.kurume_nct.onthebounce.model.ServerConnection;
import com.kurume_nct.onthebounce.utility.Common;
import com.kurume_nct.onthebounce.utility.ServerRequestMaker;

public class ReadyFragment extends Fragment {
    Common common;

    View.OnClickListener ready_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO: 準備完了時の処理
            Log.d("DEBUG", "READY");
            String request = ServerRequestMaker.user_ready(common.session_id);
            ServerConnection  connection = ServerConnection.getInstance();
            connection.send(request);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.fragment_ready, container, false);
        root_view.findViewById(R.id.ready_button).setOnClickListener(ready_listener);
        common = (Common)this.getActivity().getApplication();
        return root_view;
    }
}
