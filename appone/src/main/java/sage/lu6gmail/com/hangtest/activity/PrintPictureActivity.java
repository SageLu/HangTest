package sage.lu6gmail.com.hangtest.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import sage.lu6gmail.com.hangtest.R;
import sage.lu6gmail.com.hangtest.printer.Global;
import sage.lu6gmail.com.hangtest.printer.WorkService;

public class PrintPictureActivity extends AppCompatActivity {
    private static Handler mHandler = null;
    private ImageView iv_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pint_picture);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);

        mHandler = new MHandler(this);
        WorkService.addHandler(mHandler);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WorkService.delHandler(mHandler);
        mHandler = null;
    }

    public void toPrinting(View view) {
        int nPaperWidth = 384;
        Bitmap bitmap = ((BitmapDrawable) iv_icon.getDrawable()).getBitmap();
        if (bitmap != null) {
            if(WorkService.workThread!=null) {
                if (WorkService.workThread.isConnected()) {
                    Bundle data = new Bundle();
                    // data.putParcelable(Global.OBJECT1, mBitmap);
                    data.putParcelable(Global.PARCE1, bitmap);
                    data.putInt(Global.INTPARA1, nPaperWidth);
                    data.putInt(Global.INTPARA2, 0);
                    WorkService.workThread.handleCmd(
                            Global.CMD_POS_PRINTPICTURE, data);
                } else {
                    Toast.makeText(this, Global.toast_notconnect,
                            Toast.LENGTH_SHORT).show();
                }
            }

        }

    }

    static class MHandler extends Handler {

        WeakReference<PrintPictureActivity> mActivity;

        MHandler(PrintPictureActivity activity) {
            mActivity = new WeakReference<PrintPictureActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PrintPictureActivity theActivity = mActivity.get();
            switch (msg.what) {

                case Global.CMD_POS_PRINTPICTURERESULT: {
                    int result = msg.arg1;
                    Toast.makeText(
                            theActivity,
                            (result == 1) ? Global.toast_success
                                    : Global.toast_fail, Toast.LENGTH_SHORT).show();
                    Log.v("PrintPictureActivity", "Result: " + result);
                    break;
                }

            }
        }
    }
}
