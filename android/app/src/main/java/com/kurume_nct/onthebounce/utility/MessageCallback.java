package com.kurume_nct.onthebounce.utility;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by minto on 2015/07/24.
 */
public interface MessageCallback {
    public void comeMessage(String message);
    public void comeMessage(JSONObject json_array);
}
