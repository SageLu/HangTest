package com.shijiacheng.verificationcode;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.EditText;

public class MainActivity extends Activity {
	public static final int MSG_RECEIVED_CODE = 1;

	private EditText etVerficationCode;

	private SmsObserver mObserver;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == MSG_RECEIVED_CODE) {
				String code = (String) msg.obj;
				etVerficationCode.setText(code);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etVerficationCode = (EditText) findViewById(R.id.et_verification_code);

		mObserver = new SmsObserver(MainActivity.this, mHandler);
		Uri uri = Uri.parse("content://sms");
		getContentResolver().registerContentObserver(uri, true, mObserver);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getContentResolver().unregisterContentObserver(mObserver);
	}
}
