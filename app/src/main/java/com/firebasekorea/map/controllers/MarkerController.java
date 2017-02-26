package com.firebasekorea.map.controllers;

import com.firebasekorea.map.models.Marker;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by namhoonkim on 26/02/2017.
 */

public class MarkerController {

    public static String createMarker(double longitude, double latitude, String description) {
        String markerUid = FirebaseDatabase.getInstance().getReference().child("markers").push().getKey();

        Marker marker = new Marker(markerUid, longitude, latitude, description);

        Map<String, Object> markerValues = marker.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/markers/" + markerUid, markerValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);

        return markerUid;
    }

    public static void editMarker(String markerUid, Map<String, Object> updateValues) {
        FirebaseDatabase.getInstance().getReference().child("markers").child(markerUid).updateChildren(updateValues);
    }

    public static void editMarker(String markerUid, Marker marker) {
        Map<String, Object> userValues = marker.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/markers/" + markerUid, userValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void deleteMarker(String firebaseUid, Marker marker) {
        marker.isDeleted = true;
        editMarker(firebaseUid, marker);
    }
}
