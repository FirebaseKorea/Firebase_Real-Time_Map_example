package com.firebasekorea.map.utils;

/**
 * Created by namhoonkim on 25/02/2017.
 */

public class UserUtil {

    public static void saveUserFirebaseUid(String firebaseUid) {
        SharedPreferenceUtil.getInstance().putString(SharedPreferenceUtil.FIREBASE_UID, firebaseUid);
    }

    public static String loadUserFirebaseUid() {
        return SharedPreferenceUtil.getInstance().getString(SharedPreferenceUtil.FIREBASE_UID, null);
    }

    public static void saveUserEmail(String userEmail) {
        SharedPreferenceUtil.getInstance().putString(SharedPreferenceUtil.USER_EMAIL, userEmail);
    }
    public static String loadUserEmail() {
        return SharedPreferenceUtil.getInstance().getString(SharedPreferenceUtil.USER_EMAIL, null);
    }

    public static void saveUserName(String userName) {
        SharedPreferenceUtil.getInstance().putString(SharedPreferenceUtil.USER_NAME, userName);
    }
    public static String loadUserName() {
        return SharedPreferenceUtil.getInstance().getString(SharedPreferenceUtil.USER_NAME, null);
    }

    public static void saveUserProfilePictureUrl(String profilePictureUrl) {
        SharedPreferenceUtil.getInstance().putString(SharedPreferenceUtil.USER_PROFILE_URL, profilePictureUrl);
    }
    public static String loadUserProfilePictureUrl() {
        return SharedPreferenceUtil.getInstance().getString(SharedPreferenceUtil.USER_PROFILE_URL, null);
    }

}
