package com.xyf.AddressList.widget;

import android.view.View;

/**
 * Created by shxiayf on 2015/12/1.
 */
public class MyButtonClickListener implements View.OnClickListener{

    private static final long USEFULTIME = 1000L;
    private long lastTime = 0L;

    @Override
    public void onClick(View view) {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastTime > USEFULTIME)
        {
            lastTime = currentTime;
            listener.onUserfulClick(view);
        }
        else
        {
            listener.onunUserfulClick(view);
        }

    }

    public interface MyClickInterface {
        void onUserfulClick(View v);

        void onunUserfulClick(View v);
    }

    private MyClickInterface listener;
    public MyButtonClickListener(MyClickInterface interf)
    {
        this.listener = interf;
    }

}
