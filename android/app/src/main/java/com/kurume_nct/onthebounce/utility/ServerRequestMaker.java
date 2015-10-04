package com.kurume_nct.onthebounce.utility;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by minto on 2015/10/04.
 */
public class ServerRequestMaker {
    static final String EVENT = "event";
    static final String DATA = "data";
    static final String SESSION_ID = "session_id";
    static final String CREATE_ROOM = "create_room";
    static final String ROUND = "round";
    static final String HIT_POINT = "hit_point";
    static final String JOIN_ROOM = "join_room";
    static final String ROOM_ID = "ROOM_ID";
    static final String USERS = "users";
    static final String USER_READY = "user_ready";
    static final String HITTED = "hitted";
    static final String ATTACK_SESSION_ID = "attack_session_id";
    static final String HITTED_SESSION_ID = "hitted_session_id";
    static final String USER_DEAD = "user_dead";
    static final String RESULT = "result";

    static public String sessionID(){
        JSONObject request = new JSONObject();
        try {
            request.put(EVENT, SESSION_ID);
            request.put(DATA, new JSONObject());
        }catch (JSONException e){
            return "";
        }
        return request.toString();
    }

    static public String createRoom(int session_id, int round, int hit_point){
        JSONObject request = new JSONObject();
        try{
            request.put(EVENT, CREATE_ROOM);

            JSONObject data = new JSONObject();
            data.put(SESSION_ID, session_id);
            data.put(ROUND, round);
            data.put(HIT_POINT, hit_point);

            request.put(DATA, data);
        }catch (JSONException e){
            Log.d("DEBUG", e.toString());
        }
        return request.toString();
    }

    static public String join_room(int session_id, int room_id){
        JSONObject request = new JSONObject();
        try{
            request.put(EVENT, JOIN_ROOM);

            JSONObject data = new JSONObject();
            data.put(SESSION_ID, session_id);
            data.put(ROOM_ID, room_id);

            request.put(DATA, data);
        }catch (JSONException e){
            Log.d("DEBUG", e.toString());
        }
        return request.toString();
    }

    static public String users(int session_id, int room_id){
        JSONObject request = new JSONObject();
        try{
            request.put(EVENT, USERS);

            JSONObject data = new JSONObject();
            data.put(SESSION_ID, session_id);
            data.put(ROOM_ID, room_id);

            request.put(DATA, data);
        }catch (JSONException e){
            Log.d("DEBUG", e.toString());
        }
        return request.toString();
    }

    static public String user_ready(int session_id){
        JSONObject request = new JSONObject();
        try{
            request.put(EVENT, USER_READY);

            JSONObject data = new JSONObject();
            data.put(SESSION_ID, session_id);

            request.put(DATA, data);
        }catch (JSONException e){
            Log.d("DEBUG", e.toString());
        }
        return request.toString();
    }

    static public String hitted(int attack_session_id, int hitted_session_id){
        JSONObject request = new JSONObject();
        try{
            request.put(EVENT, HITTED);

            JSONObject data = new JSONObject();
            data.put(ATTACK_SESSION_ID, attack_session_id);
            data.put(HITTED_SESSION_ID, hitted_session_id);

            request.put(DATA, data);
        }catch (JSONException e){
            Log.d("DEBUG", e.toString());
        }
        return request.toString();
    }

    static public String user_dead(int session_id){
        JSONObject request = new JSONObject();
        try{
            request.put(EVENT, USER_DEAD);

            JSONObject data = new JSONObject();
            data.put(SESSION_ID, session_id);

            request.put(DATA, data);
        }catch (JSONException e){
            Log.d("DEBUG", e.toString());
        }
        return request.toString();
    }

    static public String result(int session_id){
        JSONObject request = new JSONObject();
        try{
            request.put(EVENT, RESULT);

            JSONObject data = new JSONObject();
            data.put(SESSION_ID, session_id);

            request.put(DATA, data);
        }catch (JSONException e){
            Log.d("DEBUG", e.toString());
        }
        return request.toString();
    }
}
