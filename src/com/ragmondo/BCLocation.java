package com.ragmondo;

import com.google.android.gms.maps.model.LatLng;

public class BCLocation {
        LatLng m_latlng;
        String m_title;
        String m_snippet;
        String on_click_url;

        public BCLocation BCLocation() {
            return this;
        }

        public BCLocation location(LatLng ll) {
            this.m_latlng = ll;
            return this;
        }

        public BCLocation title(String t) {
            this.m_title = t;
            return this;
        }

        public BCLocation snippet(String s) {
            this.m_snippet = s;
            return this;
        }

        public BCLocation url(String u) {
            this.on_click_url = u;
            return this;
        }

    public BCLocation location_lat_long(String lat, String lon) {
        this.m_latlng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        return this;
    }
}