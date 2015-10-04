package com.kurume_nct.onthebounce.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by minto on 2015/10/04.
 */
public class Setting {
    static final String SETTING = "SETTING";
    static final String IP_ADDRESS = "IP_ADDRESS";

    static public String readIPAddress(Context context){
        SharedPreferences data = context.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        return  data.getString(IP_ADDRESS, "192.1.6.7");
    }


}
