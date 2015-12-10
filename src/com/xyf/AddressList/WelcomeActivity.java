package com.xyf.AddressList;

import android.content.Intent;
import android.os.Bundle;

public class WelcomeActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        getUiHanlder().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,AddressListActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.downjoy_anim_in,R.anim.downjoy_anim_out);
            }
        },2000);

    }

}
