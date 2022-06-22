package com.example.eatfit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParserr {
    private HashMap<String, String> parseJsonObject(JSONObject object){
        //Initialize hashmap
        HashMap<String, String> dataList = new HashMap<>();
        //Get name from object
        try {
            String name = object.getString("name");
            //Get lat from obj
            String latitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lat");

            //get long from object
            String longitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lng");
            //put values in hashmap
            dataList.put("name", name);
            dataList.put("lat", latitude);
            dataList.put("lng", longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //get hashmap
        return dataList;
    }

    private List<HashMap<String,String>> parseJsonArray(JSONArray jsonArray){
        //initialize hashmao list
        List<HashMap<String, String>> dataList = new ArrayList<>();
        for(int i=0; i< jsonArray.length(); i++){
            //initialize hashmap and add data
            try {
                HashMap<String,  String> data = parseJsonObject((JSONObject) jsonArray.get(i));
                dataList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //return hash map list
        return dataList;
    }

    public List<HashMap<String,String>> parseResult(JSONObject object){
        //initialize json array
        JSONArray jsonArray = null;
        //Get result list
        try {
            jsonArray = object.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parseJsonArray(jsonArray);
    }

}
