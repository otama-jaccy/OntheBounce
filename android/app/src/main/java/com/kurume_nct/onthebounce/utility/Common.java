package com.kurume_nct.onthebounce.utility;

import android.app.Application;

/**
 * Created by minto on 2015/10/04.
 */
public class Common extends Application{
    public int session_id;
    public int room_id;
    public int hit_point;
    public int round;

    public void init(){
        session_id = -1;
        room_id = -1;
        hit_point = -1;
        round = -1;
    }
}
