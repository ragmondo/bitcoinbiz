package com.ragmondo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class BitcoinBizActivity extends Activity {


    static String Tag = "BitcoinBizActivity";
    private Tracker mGaTracker;
    private GoogleAnalytics mGaInstance;

    private GoogleMap mMap;

    private Handler mapUpdateHandler;

    private WebView mWebView ;

    public class BusinessDisplayFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("more details")
                    .setPositiveButton("ring", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Get the GoogleAnalytics singleton. Note that the SDK uses
        // the application context to avoid leaking the current context.
        mGaInstance = GoogleAnalytics.getInstance(this);

        // Use the GoogleAnalytics singleton to get a Tracker.
        mGaTracker = mGaInstance.getTracker("UA-296782-18"); // Placeholder tracking ID.

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        final ArrayList<BCLocation> locations = new ArrayList<BCLocation>();

        mWebView = new WebView(this);

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
              //  mGaTracker.sendView("Detail:" + marker.getTitle());
                mGaTracker.sendEvent("MainMap", "button_press", marker.getTitle(), new Long(0));
                String snippet = marker.getSnippet();
                Log.d(Tag,snippet);
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(BitcoinBizActivity.this);

                WebView wv = new WebView(BitcoinBizActivity.this);

                wv.loadData(snippet,"text/html",null);

                builder.setView(wv);

                //builder.setMessage(Html.fromHtml(marker.getSnippet()))
                //        .setTitle(marker.getTitle());

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

    }

    private void getUpdatedLocations(ArrayList<BCLocation> locations) {
        locations.clear();

        MerchantLocations ml = new MerchantLocations();

        String s=  MerchantLocations.getRawJsonData();

        for (BCLocation bcl : MerchantLocations.getLocationsFromJsonString(s)) {
            locations.add(bcl);
        }
    }

    private void displayUpdatedLocations(ArrayList<BCLocation> locations, GoogleMap m) {
        for (BCLocation b : locations) {
            m.addMarker(new MarkerOptions().position(b.m_latlng).title(b.m_title).snippet(b.getSnippetAndUrl()));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance().activityStart(this); // Add this method.
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance().activityStop(this); // Add this method.
    }

}
