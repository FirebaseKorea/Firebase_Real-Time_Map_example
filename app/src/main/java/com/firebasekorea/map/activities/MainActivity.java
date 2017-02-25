package com.firebasekorea.map.activities;

import android.os.Bundle;

import com.firebasekorea.map.R;
import com.firebasekorea.map.utils.ToastUtil;

/**
 * Created by namhoonkim on 25/02/2017.
 */

public class MainActivity extends BaseActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    private long pressTime = 0;

    /* View Component */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
