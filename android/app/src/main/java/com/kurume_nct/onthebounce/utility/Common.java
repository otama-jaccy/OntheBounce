package com.kurume_nct.onthebounce.utility;

import android.app.Application;

/**
 * Created by minto on 2015/10/04.
 */
public class Common extends Application{
    public String session_id;
    public String room_id;
    public int user_count;
    public int hit_point;
    public int round;

    public void init(){
        session_id = null;
        //TODO: WIFI DIRECT
        room_id = "114514";
        user_count = -1;
        hit_point = -1;
        round = -1;
    }
}
