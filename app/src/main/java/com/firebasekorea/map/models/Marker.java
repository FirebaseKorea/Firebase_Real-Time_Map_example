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

    public String description;

    public Boolean isDeleted;

    public Marker() {}

    public Marker(String markerUid, Double longitude, Double latitude, String description) {
        this.markerUid = markerUid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.isDeleted = false;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("markerUid", markerUid);
        result.put("longitude", longitude);
        result.put("latitude", latitude);
        result.put("description", description);
        result.put("isDeleted", isDeleted);
        return result;
    }

    @Exclude
    public static Marker parseSnapshot(DataSnapshot snapshot) {
        Marker marker = new Marker();
        marker.markerUid = (String) snapshot.child("markerUid").getValue();
        marker.longitude = (Double) snapshot.child("longitude").getValue();
        marker.latitude = (Double) snapshot.child("latitude").getValue();
        marker.description = (String) snapshot.child("description").getValue();
        marker.isDeleted = (Boolean) snapshot.child("isDeleted").getValue();

        return marker;
    }
}
