package com.firebasekorea.map.activities;


import android.content.Intent;
import android.os.Bundle;

import com.firebasekorea.map.R;
import com.firebasekorea.map.utils.SharedPreferenceUtil;
import com.firebasekorea.map.utils.UserUtil;

public class StartActivity extends BaseActivity {
    private final String TAG = StartActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SharedPreferenceUtil.getInstance().init(getApplicationContext());


        // TODO : Load firebase Uid
//        callLoginActivity();

        init();
    }

    private void init() {

    }

    private void switchActivity(String userName) {
        if (userName == null || userName.length() < 1) {
            callLoginActivity();
        }
        else {
            callMainActivity();
        }
    }


    private void callMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void callLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

}
