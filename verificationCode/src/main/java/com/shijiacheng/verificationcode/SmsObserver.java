package com.shijiacheng.verificationcode;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsObserver extends ContentObserver {

	private Context mContext;
	private Handler mHandler;

	public SmsObserver(Context context, Handler handler) {
		super(handler);
		mContext = context;
		mHandler = handler;
	}

	/**
	 * 接收到短信时调用此方法
	 */
	@Override
	public void onChange(boolean selfChange, Uri uri) {
		super.onChange(selfChange, uri);

		Log.d("DEBUG", "SMS has changed!");
		Log.d("DEBUG", uri.toString());

		if (uri.toString().equals("content://sms/raw")) {
			return;
		}

		Uri inboxUri = Uri.parse("content://sms/inbox");
		Cursor cursor = mContext.getContentResolver().query(inboxUri, null,
				null, null, "date desc");
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				String address = cursor.getString(cursor
						.getColumnIndex("address"));
				String body = cursor.getString(cursor.getColumnIndex("body"));
				Log.d("DEBUG", "发件人为：" + address + " " + "短信内容为：" + body);

				// if (!address.equals("你的公司的手机号")) {
				// return;
				// }

				Pattern pattern = Pattern.compile("(\\d{6})");//正则表达式
				Matcher matcher = pattern.matcher(body);
				if (matcher.find()) {
					String code = matcher.group(0);
					Log.d("DEBUG", "code is" + code);

					mHandler.obtainMessage(MainActivity.MSG_RECEIVED_CODE, code)
							.sendToTarget();
				}
			}
		}

	}

}
