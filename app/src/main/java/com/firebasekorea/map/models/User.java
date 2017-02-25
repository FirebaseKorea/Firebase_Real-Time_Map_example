package com.firebasekorea.map.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by namhoonkim on 25/02/2017.
 */

public class User {
    // Firebase User Id
    public String firebaseUid;

    // email address
    public String email;

    // profile picture url
    public String profilePictureUrl;

    // name
    public String name;

    public Boolean isDeleted;

    public User() {}

    public User(String firebaseUid, String email, String profilePictureUrl, String name) {
        this.firebaseUid = firebaseUid;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
        this.name = name;
        this.isDeleted = false;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("firebaseUid", firebaseUid);
        result.put("email", email);
        result.put("profilePictureUrl", profilePictureUrl);
        result.put("name", name);
        result.put("isDeleted", isDeleted);
        return result;
    }

    @Exclude
    public static User parseSanpshot(DataSnapshot snapshot) {
        User user = new User();
        user.firebaseUid = (String) snapshot.child("firebaseUid").getValue();
        user.email = (String) snapshot.child("email").getValue();
        user.profilePictureUrl = (String) snapshot.child("profilePictureUrl").getValue();
        user.name = (String) snapshot.child("name").getValue();
        user.isDeleted = (Boolean) snapshot.child("isDeleted").getValue();
        return user;
    }
}
