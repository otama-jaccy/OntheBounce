package com.kurume_nct.onthebounce.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kurume_nct.onthebounce.R;

public class TitleAndValueFragment extends Fragment {
    TextView title_text_view;
    TextView value_text_view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root_view = inflater.inflate(R.layout.fragment_title_and_value, container, false);
        title_text_view = (TextView)root_view.findViewById(R.id.title_text_view);
        value_text_view = (TextView)root_view.findViewById(R.id.value_text_view);
        return root_view;
    }

    public void set(String title, String value){
        title_text_view.setText(title);
        value_text_view.setText(value);
    }

    public void setValue(String value){
        value_text_view.setText(value);
    }
}
