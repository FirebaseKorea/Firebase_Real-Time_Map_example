package com.firebasekorea.map.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebasekorea.map.R;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;

/**
 * Created by namhoonkim on 25/02/2017.
 */

public class NaverMapFragment extends Fragment {
    private final String TAG = NaverMapFragment.class.getSimpleName();

    /* Naver Map */
    private NMapContext mMapContext;
    private NMapView mMapView;

    /* View Controller */
    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMapContext = new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_naver_map, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView = (NMapView) mView.findViewById(R.id.naver_map_view);
        mMapView.setClientId(getString(R.string.naver_client_id));
        mMapContext.setupMapView(mMapView);
    }

    @Override
    public void onStart(){
        super.onStart();
        mMapContext.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }
    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        mMapContext.onDestroy();
        super.onDestroy();
    }
}
