package com.lizi.fast_fragment.core;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.lizi.fast_fragment.constants.ConstantValues;
import com.lizi.fast_fragment.utils.L;

/**
 * Created by lizi on 2016/11/5.
 */

public class AppStatusTracker implements Application.ActivityLifecycleCallbacks{

    private boolean isForground;
    private int activeCount;//计数器，所有的Activity都执行了stop方法，那么count=0,app在后台执行

    private static final long MAX_INTERVAL = 5 * 60* 1000;
    private long timestamp;//在后台呆的时间够长，再切换到前台的时间差，超过最大值显示图形解锁
    private boolean isScreenOff;//图形解锁的特殊需求，屏幕锁住之后再亮起需要图形解锁

    private static AppStatusTracker tracker;
    private Application application;
    private DaemonReceiver receiver;
    private int mAppStatus = ConstantValues.STATUS_FORCE_KILLED;

    private AppStatusTracker(Application application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);//必须注册才能接受到该方法的回调
    }

    public static void init(Application application) {
        tracker = new AppStatusTracker(application);
    }

    public static AppStatusTracker getInstance() {
        return tracker;
    }

    public void setAppStatus(int status) {
        this.mAppStatus = status;
        if (status == ConstantValues.STATUS_ONLINE){
            if (receiver == null){
                IntentFilter filter = new IntentFilter();
                filter.addAction(Intent.ACTION_SCREEN_OFF);
                receiver = new DaemonReceiver();
                application.registerReceiver(receiver,filter);
            }else if (receiver != null){
                application.unregisterReceiver(receiver);
                receiver = null;
            }
        }
    }

    public int getAppStatus() {
        return this.mAppStatus;
    }

    public boolean isForground() {
        return isForground;
    }

    private void onScreenOff(boolean isScreenOff) {
        this.isScreenOff = isScreenOff;
    }

    public boolean checkIfShowGesture(){
        if (mAppStatus == ConstantValues.STATUS_OFFLINE){
            if (isScreenOff){
                return false;
            }
            if (timestamp != 0l && System.currentTimeMillis() - timestamp > MAX_INTERVAL) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        L.e(activity.toString()+" onActivityStopped");
        activeCount --;
        if (activeCount == 0){
            isForground = false;//在后台执行
            timestamp = System.currentTimeMillis();
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        L.e(activity.toString()+" onActivityCreated");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        L.e(activity.toString()+" onActivityStarted");
        activeCount ++;
    }

    //界面要显示时调用此方法，onCreate -- onResume 之间不该写大量代码，
    // 防止初始化时间过长会导致黑屏，
    //一旦此方法回调，就可以确定当前应用是在前台执行
    @Override
    public void onActivityResumed(Activity activity) {
        L.e(activity.toString()+" onActivityResumed");
        //Activity 跳转过程中，onPause -> onResume 需要时间，这过程中算前台？后台？
        isForground = true;
        timestamp = 0l;
        isScreenOff = false;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        L.e(activity.toString()+" onActivityPaused");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        L.e(activity.toString()+" onActivityDestroyed");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    private class DaemonReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.d("onReceive:" + action);
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                AppStatusTracker.getInstance().onScreenOff(true);
            }
        }
    }
}
