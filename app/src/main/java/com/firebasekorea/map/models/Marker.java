package com.firebasekorea.map.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by namhoonkim on 26/02/2017.
 */

public class Marker {

    public String markerUid;

    public Double longitude;

    public Double latitude;

    public Boolean isPicture;

    public Boolean isPikachu;

    public String markerImageUrl;

    public Boolean isDeleted;

    public Marker() {}

    public Marker(String markerUid, Double longitude, Double latitude, boolean isPicture, boolean isPikachu) {
        this.markerUid = markerUid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.isPicture = isPicture;
        this.isPikachu = isPikachu;
        this.markerImageUrl = "";
        this.isDeleted = false;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("markerUid", markerUid);
        result.put("longitude", longitude);
        result.put("latitude", latitude);
        result.put("isPicture", isPicture);
        result.put("isPikachu", isPikachu);
        result.put("markerImageUrl", markerImageUrl);
        result.put("isDeleted", isDeleted);
        return result;
    }

    @Exclude
    public static Marker parseSnapshot(DataSnapshot snapshot) {
        Marker marker = new Marker();
        marker.markerUid = (String) snapshot.child("markerUid").getValue();
        marker.longitude = (Double) snapshot.child("longitude").getValue();
        marker.latitude = (Double) snapshot.child("latitude").getValue();
        marker.isPicture = (Boolean) snapshot.child("isPicture").getValue();
        marker.isPikachu = (Boolean) snapshot.child("isPikachu").getValue();
        marker.markerImageUrl = (String) snapshot.child("markerImageUrl").getValue();
        marker.isDeleted = (Boolean) snapshot.child("isDeleted").getValue();

        return marker;
    }
}
