package com.ragmondo;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: richard
 * Date: 27/02/13
 * Time: 18:29
 * To change this template use File | Settings | File Templates.
 */
public class MerchantLocations {

    public static final boolean isAndroid = false;

    public static final String TAG = "MerchantLocations";

    private static final String TITLE = "title";
    private static final String LAT = "lat";
    private static final String LONG = "long";
    private static final String SNIPPET = "snippet";
    private static final String CLICK_URL = "on_click_url";




    public static void main(String args[])
    {
        MerchantLocations ml = new MerchantLocations();

        String js_data = ml.getRawJsonData();

        System.out.println(js_data);

        ArrayList<BCLocation> bl = getLocationsFromJsonString(js_data);

        System.out.println();

        System.out.println("Finito!");

    }

    private static void Logit(String s){
        if (isAndroid) {
        Log.d(TAG, s); }
        else {
        System.out.println(s);
    }
    }

    public static ArrayList<BCLocation> getLocationsFromJsonString(String json_string) {

        ArrayList<BCLocation> list = new ArrayList<BCLocation>();

        JSONObject jo = null;
        JSONArray ja = null;

        String j = json_string;

        Logit("Got " + j);

        try {
            ja = new JSONArray(j);

            Log.d(TAG, "Got ja = " + ja);
            Log.d(TAG, "Length is " + ja.length());
            for (int x = 0; x < ja.length(); x++) {

                Log.d(TAG, "Iteration " + x);

                jo = ja.getJSONObject(x);

                Log.d(TAG, "Got jo =" + jo);

                String lat,lon, url, title, snippet;

                try {
                    lat = jo.getString(LAT);
                    lon = jo.getString(LONG);
                    url = jo.getString(CLICK_URL);
                    title = jo.getString(TITLE);
                    snippet = jo.getString(SNIPPET);

                    BCLocation b = new BCLocation().location_lat_long(lat, lon).snippet(snippet).title(title).url(url);
                    list.add(b);
            }
                catch (JSONException e) {

                }
        }

        //To change body of created methods use File | Settings | File Templates.
    } catch (JSONException e) {
            Log.e(TAG, "Exception", e);  //To change body of catch statement use File | Settings | File Templates.
        }
        return list;
    }

    public static String getRawJsonData()
    {
        try {
            URL u = new URL("http://bcfxer.appspot.com/json");

            return NetworkBits.request(u.toString());

        } catch (MalformedURLException e) {
            //Log.e(TAG, "Exception", e);  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }



}
