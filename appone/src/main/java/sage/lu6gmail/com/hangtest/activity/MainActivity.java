package sage.lu6gmail.com.hangtest.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import sage.lu6gmail.com.hangtest.R;
import sage.lu6gmail.com.hangtest.observerpattern.ObserverListener;
import sage.lu6gmail.com.hangtest.observerpattern.ObserverManager;
import sage.lu6gmail.com.hangtest.observerpattern.ZLActivity;
import sage.lu6gmail.com.hangtest.printer.Global;
import sage.lu6gmail.com.hangtest.printer.WorkService;
import sage.lu6gmail.com.hangtest.utils.FileUtils;
import sage.lu6gmail.com.hangtest.view.ClearEditText;

//hangtest  sage
public class MainActivity extends Activity implements ObserverListener {

    ClearEditText ett;
    private static Handler mHandler = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ett = (ClearEditText) findViewById(R.id.ett);
        ObserverManager.getInstance().add(this);
        // 初始化字符串资源
        InitGlobalString();
        mHandler = new MHandler(this);
        WorkService.addHandler(mHandler);
        if (null == WorkService.workThread) {
            Intent intent = new Intent(this, WorkService.class);
            startService(intent);
        }
//        handleIntent(getIntent());




    }
    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            } else {
                handleSendRaw(intent);
            }
        }
    }
    private void InitGlobalString() {
        Global.toast_success = getString(R.string.toast_success);
        Global.toast_fail = getString(R.string.toast_fail);
        Global.toast_notconnect = getString(R.string.toast_notconnect);
        Global.toast_usbpermit = getString(R.string.toast_usbpermit);
    }
    public void toCommonView(View view) {
        Intent intent = new Intent(this, DiaLogActivity.class);
        startActivity(intent);
        if (TextUtils.isEmpty(ett.getText())) {
            //设置晃动
            ett.setShakeAnimation();
            //设置提示
            Toast.makeText(MainActivity.this, "kong", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void toPrintPicture(View view) {
        Intent intent = new Intent(this, PrintPictureActivity.class);
        startActivity(intent);

    }
    public void toObserver(View view) {
        Intent intent = new Intent(this, ZLActivity.class);
        startActivity(intent);

    }

    public void toPrintText(View view) {
        Intent intent = new Intent(this, PrintTextActivity.class);
        startActivity(intent);

    }
    public void toConnectPrint(View view) {
        Intent intent = new Intent(this, ConnectBTPairedActivity.class);
        startActivity(intent);

    }

    @Override
    public void observerUpData(String content) {
        ett.setText(content+"sage123456");
    }

    static class MHandler extends Handler {

        WeakReference<MainActivity> mActivity;

        MHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity theActivity = mActivity.get();
            switch (msg.what) {

            }
        }
    }

    private void handleSendText(Intent intent) {
        Uri textUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (textUri != null) {
            // Update UI to reflect text being shared

            if (WorkService.workThread.isConnected()) {
                byte[] buffer = {0x1b, 0x40, 0x1c, 0x26, 0x1b, 0x39, 0x01}; // 设置中文，切换双字节编码。
                Bundle data = new Bundle();
                data.putByteArray(Global.BYTESPARA1, buffer);
                data.putInt(Global.INTPARA1, 0);
                data.putInt(Global.INTPARA2, buffer.length);
                WorkService.workThread.handleCmd(Global.CMD_POS_WRITE, data);
            }
            if (WorkService.workThread.isConnected()) {
                String path = textUri.getPath();
                String strText = FileUtils.ReadToString(path);
                byte buffer[] = strText.getBytes();

                Bundle data = new Bundle();
                data.putByteArray(Global.BYTESPARA1, buffer);
                data.putInt(Global.INTPARA1, 0);
                data.putInt(Global.INTPARA2, buffer.length);
                data.putInt(Global.INTPARA3, 128);
                WorkService.workThread.handleCmd(
                        Global.CMD_POS_WRITE_BT_FLOWCONTROL, data);

            } else {
                Toast.makeText(this, Global.toast_notconnect,
                        Toast.LENGTH_SHORT).show();
            }

            finish();
        }
    }

    private void handleSendRaw(Intent intent) {
        Uri textUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (textUri != null) {
            // Update UI to reflect text being shared
            if (WorkService.workThread.isConnected()) {
                String path = textUri.getPath();
                byte buffer[] = FileUtils.ReadToMem(path);
                // Toast.makeText(this, "length:" + buffer.length,
                // Toast.LENGTH_LONG).show();
                Bundle data = new Bundle();
                data.putByteArray(Global.BYTESPARA1, buffer);
                data.putInt(Global.INTPARA1, 0);
                data.putInt(Global.INTPARA2, buffer.length);
                data.putInt(Global.INTPARA3, 256);
                WorkService.workThread.handleCmd(
                        Global.CMD_POS_WRITE_BT_FLOWCONTROL, data);

            } else {
                Toast.makeText(this, Global.toast_notconnect,
                        Toast.LENGTH_SHORT).show();
            }

            // finish();
        }
    }

    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            String path = getRealPathFromURI(imageUri);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, opts);
            opts.inJustDecodeBounds = false;
            if (opts.outWidth > 1200) {
                opts.inSampleSize = opts.outWidth / 1200;
            }

            Bitmap mBitmap = BitmapFactory.decodeFile(path);

            if (mBitmap != null) {
                if (WorkService.workThread.isConnected()) {
                    Bundle data = new Bundle();
                    data.putParcelable(Global.PARCE1, mBitmap);
                    data.putInt(Global.INTPARA1, 384);
                    data.putInt(Global.INTPARA2, 0);
                    WorkService.workThread.handleCmd(
                            Global.CMD_POS_PRINTPICTURE, data);
                } else {
                    Toast.makeText(this, Global.toast_notconnect,
                            Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.MediaColumns.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null,
                null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }
}
