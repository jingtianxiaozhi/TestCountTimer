package com.xiaozhi.testcounttimer;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * Created by liudezhi on 16/9/5.
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;
    private Handler mHandler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static MyApplication getInstance() {
        return myApplication;
    }

    public void showToast(final String str) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyApplication.this, str, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(MyApplication.this, str, Toast.LENGTH_SHORT).show();
        }
    }
}
