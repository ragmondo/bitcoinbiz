package com.ragmondo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class BitcoinBizActivity extends Activity {


    static String Tag = "BitcoinBizActivity";

    private GoogleMap mMap;

    private Handler mapUpdateHandler;

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        final ArrayList<BCLocation> locations = new ArrayList<BCLocation>();

        mapUpdateHandler = new Handler() {
            public void handleMessage(Message m) {
                displayUpdatedLocations(locations, mMap);
            }
        };

       Thread t = new Thread() {
           @Override
           public void run() {
               getUpdatedLocations(locations);
               Log.d(Tag,"There are " + locations.size() + " in the list");
               mapUpdateHandler.sendMessage(new Message());
               Log.d(Tag,"Map updated");
           }
       };

        t.start();


        Log.d(Tag,"There are " + locations.size() + " in the list");
        // display updated locations
        //


        mMap.setOnInfoWindowClickListener( new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String snippet = marker.getSnippet();
                Log.d(Tag,snippet);
            }
        });

    }

    private void getUpdatedLocations(ArrayList<BCLocation> locations) {
        locations.clear();
        BCLocation b = new BCLocation();
        b.location(new LatLng(1,1)).title("Title").snippet("snippet").url("www.xxx.com");
        locations.add(b); //


        MerchantLocations ml = new MerchantLocations();

        String s=  MerchantLocations.getRawJsonData();

        for (BCLocation bcl : MerchantLocations.getLocationsFromJsonString(s)) {
            locations.add(bcl);
        }

    }

    private void displayUpdatedLocations(ArrayList<BCLocation> locations, GoogleMap m) {
        for (BCLocation b : locations) {
            m.addMarker(new MarkerOptions().position(b.m_latlng).title(b.m_title).snippet(b.m_snippet));
        }
    }


    @Override
    public void onStart() {
        super.onStart();
      //  ... // The rest of your onStart() code.
        EasyTracker.getInstance().activityStart(this); // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
    //    ... // The rest of your onStop() code.
        EasyTracker.getInstance().activityStop(this); // Add this method.
    }

}
