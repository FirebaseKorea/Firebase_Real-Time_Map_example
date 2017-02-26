package com.firebasekorea.map.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebasekorea.map.R;
import com.firebasekorea.map.fragments.NaverMapFragment;
import com.firebasekorea.map.utils.ToastUtil;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

/**
 * Created by namhoonkim on 25/02/2017.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = MainActivity.class.getSimpleName();


    private long pressTime = 0;

    /* View Component */
    private TextView mLongitudeTextView;
    private TextView mLatitudeTextView;

    private FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

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
        mLongitudeTextView = (TextView) findViewById(R.id.main_longitude);
        mLatitudeTextView = (TextView) findViewById(R.id.main_latitude);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.main_floating);
        mFloatingActionButton.setOnClickListener(this);
    }

    public void updateGeoPoint(double longitude, double latitude) {
        mLongitudeTextView.setText("경도 : " + longitude);
        mLatitudeTextView.setText("위도 : " + latitude);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_floating:
                onClickFloatingButton();
                break;
        }
    }

    private void onClickFloatingButton() {
        String longtitude = mLongitudeTextView.getText().toString().substring(5);
        String latitude = mLatitudeTextView.getText().toString().substring(5);

        if (longtitude == null || longtitude.length() < 1) {
            ToastUtil.makeShortToast(this, "경도 및 위도가 선택되지 않았습니다");
        }

        Log.d(TAG, "longtitude : " + longtitude);
        Log.d(TAG, "latitude   : " + latitude);
    }

}
