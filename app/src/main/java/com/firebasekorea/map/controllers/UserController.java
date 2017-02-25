package com.firebasekorea.map.controllers;

import com.firebasekorea.map.models.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by namhoonkim on 25/02/2017.
 */

public class UserController {

    public static void createUser(String firebaseUid, String userEmail, String userName, String profilePictureUrl) {
        User user = new User(firebaseUid, userEmail, userName, profilePictureUrl);

        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/users/" + firebaseUid, userValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void editUser(String firebaseUid, Map<String, Object> updateValues) {
        FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUid).updateChildren(updateValues);
    }

    public static void editUser(String firebaseUid, User user) {
        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + firebaseUid, userValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void deleteUser(String firebaseUid, User user) {
        user.isDeleted = true;
        editUser(firebaseUid, user);
    }

}
