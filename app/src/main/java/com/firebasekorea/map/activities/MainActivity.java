package com.firebasekorea.map.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebasekorea.map.R;
import com.firebasekorea.map.controllers.MarkerController;
import com.firebasekorea.map.fragments.NaverMapFragment;
import com.firebasekorea.map.models.Marker;
import com.firebasekorea.map.utils.ToastUtil;
import com.firebasekorea.map.utils.UserUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nhn.android.maps.NMapActivity;
import com.nhn.android.maps.NMapView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namhoonkim on 25/02/2017.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = MainActivity.class.getSimpleName();


    private long pressTime = 0;

    /* View Component */
    private TextView mLongitudeTextView;
    private TextView mLatitudeTextView;

    private NaverMapFragment mNaverMapFragment;

    private FloatingActionButton mFloatingActionButton;

    /* firebase */
    private String firebaseUid;
    private DatabaseReference mMarkerReference;
    private ValueEventListener mMarkerListener;
    private ArrayList<Marker> mMarkerArrayList = new ArrayList<Marker>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setTitle("Firebase Real-Time Map Example");


        firebaseUid = UserUtil.loadUserFirebaseUid();
//        if (firebaseUid == null || FirebaseAuth.getInstance().getCurrentUser() == null) {
        if (firebaseUid == null) {
            ToastUtil.makeShortToast(this, "마커 정보를 일시적으로 불러올 수 없습니다");
            mMarkerReference = null;
        } else {
            mMarkerReference = FirebaseDatabase.getInstance().getReference().child("markers");
        }

        init();
    }

    @Override
    public void onStart() {
        super.onStart();

        ValueEventListener markerListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //초기화
                mMarkerArrayList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Marker marker = Marker.parseSnapshot(snapshot);
                    if (marker.isDeleted != null && marker.isDeleted == false) {
                        mMarkerArrayList.add(marker);
                    }
                }
                updateMarkerOverlay(mMarkerArrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadMarkerList:onCancelled", databaseError.toException());
                ToastUtil.makeShortToast(MainActivity.this, "마커 정보를 일시적으로 불러올 수 없습니다");
            }
        };

        if (mMarkerReference != null) {
            mMarkerReference.addValueEventListener(markerListener);
        }
        mMarkerListener = markerListener;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mMarkerListener != null) {
            if (mMarkerReference != null) {
                mMarkerReference.removeEventListener(mMarkerListener);
            }
        }
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

        mNaverMapFragment = (NaverMapFragment) getFragmentManager().findFragmentById(R.id.main_map_fragment);
    }

    public void updateGeoPoint(double longitude, double latitude) {
        mLongitudeTextView.setText("경도 : " + longitude);
        mLatitudeTextView.setText("위도 : " + latitude);
    }

    private void updateMarkerOverlay(ArrayList<Marker> markerList) {
        mNaverMapFragment.addMakerOverlay(markerList);
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

        // TODO : 마커의 정보를 상세 입력하는 뷰가 필요하다
        MarkerController.createMarker(Double.parseDouble(longtitude), Double.parseDouble(latitude), "");

    }

}
