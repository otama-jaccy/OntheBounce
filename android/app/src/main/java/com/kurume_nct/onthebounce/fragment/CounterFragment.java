package com.kurume_nct.onthebounce.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kurume_nct.onthebounce.R;

public class CounterFragment extends Fragment {
    TextView title_view;
    TextView count_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.fragment_counter, container, false);
        this.title_view = (TextView)root_view.findViewById(R.id.title_text_view);
        this.count_view = (TextView)root_view.findViewById(R.id.count_text_view);
        return root_view;
    }

    public void setValue(String title, int count){
        this.title_view.setText(title);
        this.count_view.setText(count);
    }

    public int getCount(){
        String count_text = this.count_view.getText().toString();
        return Integer.parseInt(count_text);
    }

    private void setCount(int count){
        this.count_view.setText(count);
    }

    public void incrementCount(){
        this.setCount(this.getCount()+1);
    }

    public void decrementCount(){
        int count = this.getCount();
        if(count<2){
            return;
        }
        count--;
        this.setCount(count);
    }
}
