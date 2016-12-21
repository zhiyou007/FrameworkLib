package com.zzy.tools;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *
 * Created by zhiyou007 on 2015/11/2.
 */
public class GsonTools {
    private static Gson mGson = null;
    private static GsonTools mGTools = null;

    public synchronized static GsonTools getInstance()
    {
        if(mGTools == null)
        {
            mGTools = new GsonTools();
        }

        return mGTools;
    }

    public GsonTools()
    {
        mGson = new Gson();
    }

    public String toJson(Object object)
    {
        return mGson.toJson(object);
    }

    public <T> T toObject(String json, Class<T> clazz)
    {
        return mGson.fromJson(json, clazz);
    }

    /**
     * 更具tag解析
     * @param redata
     * @param listtag
     * @param clazz
     * @param <T>
     * @return
     */
    public  <T> ArrayList<T> getTList(String redata, String listtag, Class<T> clazz)
    {
        ArrayList<T> listOfT = new ArrayList<>();
        try {
            JSONObject data = new JSONObject(redata);
            JSONArray list = data.getJSONArray(listtag);
            Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
            ArrayList<JsonObject> jsonObjs = new Gson().fromJson(list.toString(), type);
            for (JsonObject jsonObj : jsonObjs) {
                listOfT.add(new Gson().fromJson(jsonObj, clazz));
            }
        } catch (JSONException e) {
        }
        return listOfT;
    }

    /**
     * 更具tag解析
     * @param listtag
     * @param clazz
     * @param <T>
     * @return
     */
    public  <T> ArrayList<T> getTList(JSONObject data, String listtag, Class<T> clazz)
    {
        ArrayList<T> listOfT = new ArrayList<>();
        try {
            JSONArray list = data.getJSONArray(listtag);
            Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
            ArrayList<JsonObject> jsonObjs = new Gson().fromJson(list.toString(), type);
            for (JsonObject jsonObj : jsonObjs) {
                listOfT.add(new Gson().fromJson(jsonObj, clazz));
            }
        } catch (JSONException e) {
        }
        return listOfT;
    }


    public  <T> ArrayList<T> getList(String redata, Class<T> clazz)
    {
        ArrayList<T> listOfT = new ArrayList<>();
        try {
            JSONArray list = new JSONArray(redata);
            Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
            ArrayList<JsonObject> jsonObjs = new Gson().fromJson(list.toString(), type);
            for (JsonObject jsonObj : jsonObjs) {
                listOfT.add(new Gson().fromJson(jsonObj, clazz));
            }
        } catch (JSONException e) {
        }
        return listOfT;
    }

    public  <T> ArrayList<T> getList(JSONArray list, Class<T> clazz)
    {
        ArrayList<T> listOfT = new ArrayList<>();

        Type type = new TypeToken<ArrayList<JsonObject>>(){}.getType();
        ArrayList<JsonObject> jsonObjs = new Gson().fromJson(list.toString(), type);
        for (JsonObject jsonObj : jsonObjs) {
            listOfT.add(new Gson().fromJson(jsonObj, clazz));
        }

        return listOfT;
    }
}
