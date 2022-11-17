package com.varsitycollege.simplelocation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {

    private HashMap<String,String> parsJsonObject (JSONObject object) {
        // intial hash map

        HashMap<String,String> dataList = new HashMap<>();
        try{
            //get name
            String name = object.getString("name");

            // get latitude
            String latitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lat");

            //get longitude
            String longitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lng");
            //hashmap

            dataList.put("name",name);
            dataList.put("lat",latitude);
            dataList.put("lng",longitude);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    private List<HashMap<String,String>> parseJsonArray(JSONArray jsonArray){
        //initial list

        List<HashMap<String,String>> dataList = new ArrayList<>();
        for (int i=0; i<jsonArray.length(); i++){
            //initial map

            try {
                HashMap<String,String> data = parsJsonObject((JSONObject) jsonArray.get(i));

                //add data in hash map list
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return dataList;
    }

    public List<HashMap<String,String>> parseResult(JSONObject object){
        //json array
        JSONArray jsonArray = null;

        //get results array

        try {
            jsonArray = object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parseJsonArray(jsonArray);
    }
}
