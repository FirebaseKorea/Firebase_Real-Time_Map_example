package com.firebasekorea.map.activities;

import android.os.Bundle;

import com.firebasekorea.map.R;
import com.firebasekorea.map.utils.ToastUtil;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

/**
 * Created by namhoonkim on 25/02/2017.
 */

public class MainActivity extends NMapActivity {
    private final String TAG = MainActivity.class.getSimpleName();


    private long pressTime = 0;

    /* View Component */
    private NMapView mMapView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = new NMapView(this);
        setContentView(mMapView);
        mMapView.setClientId(getString(R.string.naver_client_id));
        mMapView.setClickable(true);
        mMapView.setEnabled(true);
        mMapView.setFocusable(true);
        mMapView.setFocusableInTouchMode(true);
        mMapView.requestFocus();

//        setContentView(R.layout.activity_main);

        setTitle("Firebase Real-Time Map Example");

        init();
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() > pressTime + 2000) {
            pressTime = System.currentTimeMillis();
            ToastUtil.makeShortToast(MainActivity.this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.");
        }
        else {
            finishAffinity();
        }
    }

    private void init() {

    }
}
