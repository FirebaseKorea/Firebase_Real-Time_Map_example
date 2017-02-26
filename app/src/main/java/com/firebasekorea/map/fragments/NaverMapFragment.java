package com.firebasekorea.map.fragments;

import android.app.Fragment;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.firebasekorea.map.R;
import com.firebasekorea.map.activities.MainActivity;
import com.firebasekorea.map.navermap.NMapPOIflagType;
import com.firebasekorea.map.navermap.NMapViewerResourceProvider;
import com.firebasekorea.map.utils.ToastUtil;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.nhn.android.maps.nmapmodel.NMapError;
import com.nhn.android.maps.overlay.NMapPOIdata;
import com.nhn.android.mapviewer.overlay.NMapOverlayManager;
import com.nhn.android.mapviewer.overlay.NMapPOIdataOverlay;

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

    /* Location */
    private LocationManager mLocationManager;

    /* Map Overlay */
    private NMapViewerResourceProvider mNMapViewerResourceProvider;
    private NMapOverlayManager mNMapOverlayManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMapContext = new NMapContext(super.getActivity());
        mMapContext.onCreate();

        // TODO : 현재 위치로 지도를 이동 시킨다
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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

        mNMapViewerResourceProvider = new NMapViewerResourceProvider(getActivity());

        mNMapOverlayManager = new NMapOverlayManager(getActivity(), mMapView, mNMapViewerResourceProvider);



        init();
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

    private void init() {
        // 초기 세팅
        mMapView.setClickable(true);
        mMapView.setBuiltInZoomControls(true, null);

        // 이벤트 리스너 등록
        mMapView.setOnMapStateChangeListener(mMapStateChangeListener);
        mMapView.setOnMapViewTouchEventListener(mMapViewTouchEventListener);

    }

    NMapView.OnMapStateChangeListener mMapStateChangeListener = new NMapView.OnMapStateChangeListener() {
        @Override
        public void onMapInitHandler(NMapView nMapView, NMapError nMapError) {
            // 지도가 초기화된 후 호출된다. 정상적으로 초기화되면 errorInfo 객체는 null이 전달되며, 초기화 실패 시 errorInfo 객체에 에러 원인이 전달된다
        }

        @Override
        public void onMapCenterChange(NMapView nMapView, NGeoPoint nGeoPoint) {
            // 지도 중심 변경 시 호출되며 변경된 중심 좌표가 파라미터로 전달된다
        }

        @Override
        public void onMapCenterChangeFine(NMapView nMapView) {

        }

        @Override
        public void onZoomLevelChange(NMapView nMapView, int i) {
            // 지도 레벨 변경 시 호출되며 변경된 지도 레벨이 파라미터로 전달된다
        }

        @Override
        public void onAnimationStateChange(NMapView nMapView, int i, int i1) {
            // 지도 애니메이션 상태 변경 시 호출된다
            // animType : ANIMATION_TYPE_PAN or ANIMATION_TYPE_ZOOM
            // animState : ANIMATION_STATE_STARTED or ANIMATION_STATE_FINISHED
        }
    };

    NMapView.OnMapViewTouchEventListener mMapViewTouchEventListener = new NMapView.OnMapViewTouchEventListener() {
        @Override
        public void onLongPress(NMapView nMapView, MotionEvent motionEvent) {
            // 지도 위에서 터치 후 일정 시간이 경과하면 호출된다
        }

        @Override
        public void onLongPressCanceled(NMapView nMapView) {

        }

        @Override
        public void onTouchDown(NMapView nMapView, MotionEvent motionEvent) {
            // 지도 터치 다운 이벤트 발생 시 호출된다
        }

        @Override
        public void onTouchUp(NMapView nMapView, MotionEvent motionEvent) {
            // 지도 터치 업 이벤트 발생 시 호출된다
        }

        @Override
        public void onScroll(NMapView nMapView, MotionEvent motionEvent, MotionEvent motionEvent1) {
            // 지도 위에서 스크롤 이벤트 발생 시 호출된다
        }

        @Override
        public void onSingleTapUp(NMapView nMapView, MotionEvent motionEvent) {
            // 지도 위에서 탭 이벤트 발생 시 호출된다
            NGeoPoint nGeoPoint = nMapView.getMapProjection().fromPixels((int)motionEvent.getX(), (int)motionEvent.getY());
            ((MainActivity)getActivity()).updateGeoPoint(nGeoPoint.getLongitude(), nGeoPoint.getLatitude());
        }
    };

    public void addMakerOverlay() {
        int markerId = NMapPOIflagType.PIN;

        // set POI data
        NMapPOIdata poiData = new NMapPOIdata(2, mNMapViewerResourceProvider);
        poiData.beginPOIdata(2);
        poiData.addPOIitem(127.0630205, 37.5091300, "Pizza 777-111", markerId, 0);
        poiData.addPOIitem(127.061, 37.51, "Pizza 123-456", markerId, 0);
        poiData.endPOIdata();

        // create POI data overlay
        NMapPOIdataOverlay poiDataOverlay = mNMapOverlayManager.createPOIdataOverlay(poiData, null);
    }

}
