package com.xyf.AddressList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by shxiayf on 2015/12/2.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Handler uiHanlder = new Handler(Looper.getMainLooper());

    public Handler getUiHanlder() {
        return uiHanlder;
    }
}
