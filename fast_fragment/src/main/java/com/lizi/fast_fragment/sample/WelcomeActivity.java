package com.lizi.fast_fragment.sample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lizi.fast_fragment.R;
import com.lizi.fast_fragment.constants.ConstantValues;
import com.lizi.fast_fragment.core.AppStatusTracker;
import com.lizi.fast_fragment.core.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppStatusTracker.getInstance().setAppStatus(ConstantValues.STATUS_OFFLINE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_welcome, -1, MODE_NONE);
    }

    @Override
    protected void setUpView() {

    }

    @Override
    protected void setUpData() {
        handler.sendEmptyMessageDelayed(0, 1000);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
            finish();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(0);
    }
}
