package sage.lu6gmail.com.hangtest.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

import sage.lu6gmail.com.hangtest.R;
import sage.lu6gmail.com.hangtest.printer.Global;
import sage.lu6gmail.com.hangtest.printer.WorkService;
import sage.lu6gmail.com.hangtest.utils.DataUtils;

public class PrintTextActivity extends AppCompatActivity  {
    private static Handler mHandler = null;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pint_text);
        mHandler = new MHandler(this);
        WorkService.addHandler(mHandler);
        tv = (TextView)findViewById(R.id.tv);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkService.delHandler(mHandler);
        mHandler = null;
    }
    public void printText(View view){
        // 不要直接和Pos打交道，要通过workThread来交流

        if (WorkService.workThread.isConnected()) {

            String text = tv.getText().toString()
                    + "\r\n\r\n\r\n"; // 加三行换行，避免走纸
            byte header[] = null;
            byte strbuf[] = null;

//            if (radioButtonGBK.isChecked()) {
//                // 设置GBK编码
//                // Android手机默认也是UTF8编码
//                header = new byte[] { 0x1b, 0x40, 0x1c, 0x26, 0x1b, 0x39,
//                        00 };
//                try {
//                    strbuf = text.getBytes("GBK");
//                } catch (UnsupportedEncodingException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            } else if (radioButtonUTF8.isChecked()) {
//                // 设置UTF8编码
//                // Android手机默认也是UTF8编码
//                header = new byte[] { 0x1b, 0x40, 0x1c, 0x26, 0x1b, 0x39,
//                        0x01 };
//                try {
//                    strbuf = text.getBytes("UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            } else if (radioButtonKOREA.isChecked()) {
//                header = new byte[] { 0x1b, 0x40, 0x1c, 0x26, 0x1b, 0x39,
//                        0x05 };
//                try {
//                    strbuf = text.getBytes("euc-kr");
//                } catch (UnsupportedEncodingException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }


            // 设置GBK编码
            // Android手机默认也是UTF8编码
            header = new byte[] { 0x1b, 0x40, 0x1c, 0x26, 0x1b, 0x39,
                    00 };
            try {
                strbuf = text.getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            byte buffer[] = DataUtils.byteArraysToBytes(new byte[][] {
                    header, strbuf });
            Bundle data = new Bundle();
            data.putByteArray(Global.BYTESPARA1, buffer);
            data.putInt(Global.INTPARA1, 0);
            data.putInt(Global.INTPARA2, buffer.length);
            WorkService.workThread.handleCmd(Global.CMD_POS_WRITE, data);

        } else {
            Toast.makeText(this, Global.toast_notconnect,
                    Toast.LENGTH_SHORT).show();
        }

    }


    static class MHandler extends Handler {

        WeakReference<PrintTextActivity> mActivity;

        MHandler(PrintTextActivity activity) {
            mActivity = new WeakReference<PrintTextActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PrintTextActivity theActivity = mActivity.get();
            switch (msg.what) {

                case Global.CMD_POS_WRITERESULT: {
                    int result = msg.arg1;
                    Toast.makeText(
                            theActivity,
                            (result == 1) ? Global.toast_success
                                    : Global.toast_fail, Toast.LENGTH_SHORT).show();
                    Log.v("PrintTextActivity", "Result: " + result);
                    break;
                }

            }
        }
    }
}
