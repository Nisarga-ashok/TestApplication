package com.example.nisarga.testapplication;

import android.app.IntentService;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class DurationParser {
    JSONObject jsonObject;
    JSONArray routes;
    JSONObject route0;
    JSONArray legs;
    JSONObject leg0;
    JSONObject duration;
    int value;

    public  int parse(String jsonData)
    {
        try {
            jsonObject = new JSONObject(jsonData);
            routes = jsonObject.getJSONArray("routes");
            route0 = (JSONObject) routes.get(0);
            legs = route0.getJSONArray("legs");
            leg0 = (JSONObject)legs.get(0);
            duration = (JSONObject)leg0.get("duration");
            value = Integer.parseInt(duration.getString("value"));
        }catch (Exception e)
        {
            Log.e("MyTag",e.toString());
        }

        return value;
    }
}
