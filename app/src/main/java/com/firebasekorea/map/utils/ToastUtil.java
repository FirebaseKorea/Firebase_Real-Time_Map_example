package com.firebasekorea.map.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by namhoonkim on 25/02/2017.
 */

public class ToastUtil {

    public static void makeShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void makeLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
